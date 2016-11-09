package com.jd.queuemonitor;


import com.jd.json.JMXQueue;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YiRan on 10/17/16.
 */
public class QueueInfoMonitor implements Runnable{

    private String address;
    private long count;
    private long interval;
    private Client webclient;   // jersey client
    private String url;
    private static final String SEPARATOR = "-";

    public QueueInfoMonitor(String address,  long count,  long interval) {
        this.address = "http://" + address + "";
        this.count = count;
        this.interval = interval;
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        webclient = Client.create(clientConfig);
        url = this.address + "/jmx?qry=Hadoop:*";
        System.out.println("URL: " + address + " , count: " + this.count + ", interval:" + interval + ", url=" + this.url);
    }

    @Override
    public void run() {
        int x = 0;
        while (true) {
            try {
                if (++x > count)
                    break;
                process();

                Thread.sleep(interval);
            }catch (Exception e) {
                System.out.print(e);
                break;
            }
        }
    }

    protected void process() throws IOException {
        readQueues(url);
    }

    private void readQueues(String url) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        String scheduleinfostr = readJsonFromURL2(url);

        JsonNode rootNode = obj.readTree(scheduleinfostr);

        JsonNode queues = rootNode.path("beans");
        JMXQueue rootQueue = new JMXQueue();
        rootQueue.setName("root");
        for (JsonNode q : queues) {
            if (q.get("modelerType").asText().contains("QueueMetrics,q0=root") &&
                    q.get("tag.User") == null) {
                JMXQueue newQueue = new JMXQueue();
                json2Queue(q, newQueue);
                addQueue(rootQueue, newQueue);  // 建樹
            }
        }

        calc(rootQueue);
        System.out.println("======================BEGIN=======================================================");
        //System.out.println("QueueName\t\t\tVCoresUsed%\tMBUsed%\tAppRunning\tAppPending\tminShareMB\tmaxShareMB\tminShareVCores\tmaxShareVCores");
        System.out.printf("%-25s%5s %5s%10s%15s%15s%15s%15s%15s\n",
                "QueueName", "MBUsed%",
                "VCoresUsed%", "AppRunning",
                "AppPending", "minShareMB",
                "maxShareMB", "minShareVCores",
                "maxShareVCores"
        );

        cout(rootQueue, "");

        System.out.println("=============================================================================");
       // System.out.println("QueueName\t\t\tVCoresUsed%\tMBUsed%\tAllocatedMB\tAllocatedVCores\tAllocatedContainers\tminShareMB\tminShareVCores");
        System.out.printf("%-25s%5s %5s%13s%17s%15s%17s%17s\n",
                "QueueName", "MBUsed%",
                "VCoresUsed%", "AllocatedMB",
                "AllocatedVCores", "AllocatedContainers",
                "minShareMB", "minShareVCores");
        cout2(rootQueue, "");

        System.out.println("=============================================================================");
        //System.out.println("QueueName\t\t\tVCoresUsed%\tMBUsed%\tPendingMB\tPendingVCores\tPendingContainers\tReservedMB\tReservedVCores");
        System.out.printf("%-25s%5s  %5s%15s%15s%15s%15s%15s\n",
                "QueueName", "MBUsed%",
                "VCoresUsed%", "tPendingMB",
                "tPendingVCores", "tPendingContainers",
                "tReservedMB", "tReservedVCores");
        cout3(rootQueue, "");

        System.out.println("=============================================================================");
        //System.out.println("QueueName\t\t\tVCoresUsed%\tMBUsed%\tReservedContainers\tActiveApplications\tfairShareMB\tfairShareVCores");
        System.out.printf("%-25s%5s %5s%20s%20s%20s%20s\n",
                "QueueName", "MBUsed%",
                "VCoresUsed%", "ReservedContainers",
                "ActiveApplications", "fairShareMB",
                "fairShareVCores");

        cout4(rootQueue, "");
        System.out.println("=========================END====================================================");
    }

    private void calc(JMXQueue rootQueue) {
        if (rootQueue == null)
            return;

        List<JMXQueue> child = rootQueue.getChildQueue();

        for (int i = 0; i < child.size(); i++) {
            calc(child.get(i));
        }
        String parent = "NULL";
        if (rootQueue.getParent() != null || rootQueue.getName() == "root") {
            if (rootQueue.getMinShareVCores() > 0)
                rootQueue.setVcratio((((double)rootQueue.getAllocatedVCores()) / ((double)rootQueue.getMinShareVCores()) )* 100.0);
            if (rootQueue.getMinShareMB() > 0)
                rootQueue.setMbratio((((double)rootQueue.getAllocatedMB()) / ((double)rootQueue.getMinShareMB()) )* 100.0);
            if (rootQueue.getName() == "root") {
                rootQueue.setVcratio((((double) rootQueue.getAllocatedVCores()) / ((double) rootQueue.getMaxShareVCores())) * 100.0);
                rootQueue.setMbratio((((double)rootQueue.getAllocatedMB()) / ((double)rootQueue.getMaxShareMB()) )* 100.0);

            }
            if (rootQueue.getParent() != null)
                parent = rootQueue.getParent().getName();
        }
    }

    private void cout(JMXQueue rootQueue, String str) {
        if (rootQueue == null)
            return;
        List<JMXQueue> child = rootQueue.getChildQueue();
//System.out.println("QueueName\t\tVCoresUsed%\tMBUsed%\tAppRunning\tAppPending\tminShareMB\tmaxShareMB\tminShareVCores\tmaxShareVCores\tAllocatedMB\tAllocatedVCores\tAllocatedContainers\tAvailableMB\tAvailableVCores");
        //System.out.println("QueueName\t\t\tVCoresUsed%\tMBUsed%\tAppRunning\tAppPending\tminShareMB\tmaxShareMB\tminShareVCores\tmaxShareVCores");
        System.out.printf("%-25s%2.1f           %2.1f%8s%16s%16s%16s%16s%15s\n", str + rootQueue.getName() ,
                rootQueue.getMbratio(),
                rootQueue.getVcratio(),
                rootQueue.getAppsRunning(),
                rootQueue.getAppsPending(),
                rootQueue.getMinShareMB() ,
                rootQueue.getMaxShareMB(),
                rootQueue.getMinShareVCores() ,
                rootQueue.getMaxShareVCores()
        );
        for (int i = 0; i < child.size(); i++) {
            cout(child.get(i), str + SEPARATOR + SEPARATOR);
        }
    }
    private void cout2(JMXQueue rootQueue, String str) {
        if (rootQueue == null)
            return;
        List<JMXQueue> child = rootQueue.getChildQueue();
//System.out.println("QueueName\t\tVCoresUsed%\tMBUsed%\tAppRunning\tAppPending\tminShareMB\tmaxShareMB\tminShareVCores\tmaxShareVCores\tAllocatedMB\tAllocatedVCores\tAllocatedContainers\tAvailableMB\tAvailableVCores");
        //System.out.println("QueueName\t\t\tVCoresUsed%\tMBUsed%\tAllocatedMB\tAllocatedVCores\tAllocatedContainers\tAvailableMB\tAvailableVCores");
        System.out.printf("%-25s%2.1f\t%2.1f%13s%17s%20s%17s%17s\n", str + rootQueue.getName() ,
                rootQueue.getMbratio(),
                rootQueue.getVcratio(),
                rootQueue.getAllocatedMB(),
                rootQueue.getAllocatedVCores() ,
                rootQueue.getAllocatedContainers(),
                rootQueue.getMinShareMB() ,
                rootQueue.getMinShareVCores()
        );
        for (int i = 0; i < child.size(); i++) {
            cout2(child.get(i), str + SEPARATOR + SEPARATOR);
        }
    }
    private void cout3(JMXQueue rootQueue, String str) {
        if (rootQueue == null)
            return;
        List<JMXQueue> child = rootQueue.getChildQueue();
//System.out.println("QueueName\t\tVCoresUsed%\tMBUsed%\tPendingMB\tPendingVCores\tPendingContainers\tReservedMB\tReservedVCores\tReservedContainers\tActiveApplications,fairShareMB,fairShareVCores");
        //System.out.println("QueueName\t\t\tVCoresUsed%\tMBUsed%\tPendingMB\tPendingVCores\tPendingContainers\tReservedMB\tReservedVCores");
        System.out.printf("%-25s%2.1f\t%2.1f%15s%15s%15s%15s%15s\n", str + rootQueue.getName() ,
                rootQueue.getMbratio(),
                rootQueue.getVcratio(),
                rootQueue.getPendingMB(),
                rootQueue.getPendingVCores(),
                rootQueue.getPendingContainers() ,
                rootQueue.getReservedMB(),
                rootQueue.getReservedVCores()
        );
        for (int i = 0; i < child.size(); i++) {
            cout3(child.get(i), str + SEPARATOR + SEPARATOR);
        }
    }
    private void cout4(JMXQueue rootQueue, String str) {
        if (rootQueue == null)
            return;
        List<JMXQueue> child = rootQueue.getChildQueue();
//System.out.println("QueueName\t\tVCoresUsed%\tMBUsed%\tPendingMB\tPendingVCores\tPendingContainers\tReservedMB\tReservedVCores\tReservedContainers\tActiveApplications,fairShareMB,fairShareVCores");
        //System.out.println("QueueName\t\t\tVCoresUsed%\tMBUsed%\tReservedContainers\tActiveApplications\tfairShareMB\tfairShareVCores");
        System.out.printf("%-25s%2.1f\t%2.1f%15s%15s%15s%15s\n",
                str + rootQueue.getName() ,
                rootQueue.getMbratio(),
                rootQueue.getVcratio(),
                rootQueue.getReservedContainers() ,
                rootQueue.getActiveApplications(),
                rootQueue.getFairShareMB(),
                rootQueue.getFairShareVCores()
        );
        for (int i = 0; i < child.size(); i++) {
            cout4(child.get(i), str + SEPARATOR + SEPARATOR);
        }
    }
    // get parent
    private JMXQueue addQueue(JMXQueue rootQueue, List<String> queueLevel, int x, JMXQueue addQ) {
        // 1. 是當前節點
        // 2. 是子節點
        if (x >= queueLevel.size() - 1) {
            String name = rootQueue.getName();
            JMXQueue parent = rootQueue.getParent();
            rootQueue.clone( addQ);
            rootQueue.setName(name);
            rootQueue.setParent(parent);
            return rootQueue;
        }

        // 判斷子節點
        JMXQueue tmpQueue = rootQueue;
        JMXQueue tmpQueue2 = tmpQueue.getChildQueue(queueLevel.get(x+1));
        if (tmpQueue2 != null) {
            return addQueue(tmpQueue2, queueLevel, x+1, addQ);
        }

        JMXQueue newqueue = new JMXQueue();
        newqueue.setName(queueLevel.get(x+1));
        //System.out.println("add queue :" + tmpQueue.getName() + " => " + newqueue.getName());
        newqueue.setParent(tmpQueue);
        tmpQueue.addChildQueue(newqueue);
        return addQueue(newqueue, queueLevel, x+1, addQ);
    }

    private List<String> parseQueueName(String str) {
        List<String> result = new ArrayList<String>();

        String [] array = str.split(",");
        if (array.length >= 3) {
            for (int i = 2; i < array.length; i++) {
                String[] s2 = array[i].split("=");
                if (s2.length >= 2) {
                    result.add(s2[1]);
                }
            }
        }

        return result;
    }

    private void addQueue(JMXQueue rootQueue, JMXQueue queue) {
        List<String> queueLevel = parseQueueName(queue.getName());
        JMXQueue xqueue = addQueue(rootQueue, queueLevel, 0, queue);
//        xqueue.getChildQueue().add(queue);
    }

    private void json2Queue(JsonNode jsonNode, JMXQueue queue) {
        queue.setName(jsonNode.get("name").asText());
        queue.setModelerType(jsonNode.get("modelerType").asText());
        queue.setTag_Queue(jsonNode.get("tag.Queue").asText());
        queue.setTag_Context(jsonNode.get("tag.Context").asText());
        queue.setTag_Host(jsonNode.get("tag.Hostname").asText());
        queue.setRunning_0(jsonNode.get("running_0").asInt());
        queue.setRunning_60(jsonNode.get("running_60").asInt());
        queue.setRunning_300(jsonNode.get("running_300").asInt());
        queue.setRunning_1440(jsonNode.get("running_1440").asInt());
        queue.setFairShareMB(jsonNode.get("FairShareMB").asInt());
        queue.setFairShareVCores(jsonNode.get("FairShareVCores").asInt());
        queue.setSteadyFairShareMB(jsonNode.get("SteadyFairShareMB").asInt());
        queue.setSteadyFairShareVCores(jsonNode.get("SteadyFairShareVCores").asInt());
        queue.setMinShareMB(jsonNode.get("MinShareMB").asInt());
        queue.setMinShareVCores(jsonNode.get("MinShareVCores").asInt());
        queue.setMaxShareMB(jsonNode.get("MaxShareMB").asInt());
        queue.setMaxShareVCores(jsonNode.get("MaxShareVCores").asInt());
        queue.setAppsSubmitted(jsonNode.get("AppsSubmitted").asInt());
        queue.setAppsRunning(jsonNode.get("AppsRunning").asInt());
        queue.setAppsPending(jsonNode.get("AppsPending").asInt());
        queue.setAppsCompleted(jsonNode.get("AppsCompleted").asInt());
        queue.setAppsKilled(jsonNode.get("AppsKilled").asInt());
        queue.setAppsFailed(jsonNode.get("AppsFailed").asInt());
        queue.setAllocatedMB(jsonNode.get("AllocatedMB").asInt());
        queue.setAllocatedVCores(jsonNode.get("AllocatedVCores").asInt());
        queue.setAllocatedContainers(jsonNode.get("AllocatedContainers").asInt());
        queue.setAggregateContainersAllocated(jsonNode.get("AggregateContainersAllocated").asInt());
        queue.setAggregateContainersReleased(jsonNode.get("AggregateContainersReleased").asInt());
        queue.setAvailableMB(jsonNode.get("AvailableMB").asInt());
        queue.setAvailableVCores(jsonNode.get("AvailableVCores").asInt());
        queue.setPendingMB(jsonNode.get("PendingMB").asInt());
        queue.setPendingVCores(jsonNode.get("PendingVCores").asInt());
        queue.setPendingContainers(jsonNode.get("PendingContainers").asInt());
        queue.setReservedMB(jsonNode.get("ReservedMB").asInt());
        queue.setReservedVCores(jsonNode.get("ReservedVCores").asInt());
        queue.setReservedContainers(jsonNode.get("ReservedContainers").asInt());
        queue.setActiveUsers(jsonNode.get("ActiveUsers").asInt());
        queue.setActiveApplications(jsonNode.get("ActiveApplications").asInt());
    }

    public String readJsonFromURL2 (String strurl) throws IOException {
        URL url = new URL(strurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        StringBuffer hresult = new StringBuffer();
        String tmp = "";
        while ((tmp = in.readLine()) != null) {
            hresult.append(tmp);
        }
        return hresult.toString();
    }

    static public void main(String []args) {

        String usage = "Usage:  rmAddress:8080   3   1000 \n";

        if (args.length < 3) {
            System.out.print(usage);
            System.exit(1);
        }
        //String [] args = {"172.22.96.66", "1", "1000"};
        QueueInfoMonitor queueInfo = new QueueInfoMonitor(args[0], Long.valueOf(args[1]), Long.valueOf(args[2]));

        new Thread(queueInfo).start();
        System.out.println("usage");
    }


}
