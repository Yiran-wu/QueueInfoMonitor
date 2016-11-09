package com.jd.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YiRan on 10/31/16.
 */
public class JMXQueue {
    private String name;
    private String modelerType;
    private String tag_Queue;
    private String tag_Context;
    private String tag_Host;
    private int running_0;
    private int running_60;
    private int running_300;
    private int running_1440;
    private int fairShareMB;
    private int fairShareVCores;
    private int steadyFairShareMB;
    private int steadyFairShareVCores;
    private int MinShareMB;
    private int MinShareVCores;
    private int MaxShareMB;
    private int MaxShareVCores;
    private int AppsSubmitted;
    private int AppsRunning;
    private int AppsPending;
    private int AppsCompleted;
    private int AppsKilled;
    private int AppsFailed;
    private int AllocatedMB;
    private int AllocatedVCores;
    private int AllocatedContainers;
    private int AggregateContainersAllocated;
    private int AggregateContainersReleased;
    private int AvailableMB;
    private int AvailableVCores;
    private int PendingMB;
    private int PendingVCores;
    private int PendingContainers;
    private int ReservedMB;
    private int ReservedVCores;
    private int ReservedContainers;
    private int ActiveUsers;
    private int ActiveApplications;
    private List<JMXQueue> childQueue;
    private double             mbratio;
    private double             vcratio;
    private JMXQueue         parent;

    public JMXQueue() {
        childQueue = new ArrayList<JMXQueue>();
        name = "";
        modelerType = "";
        tag_Queue = "";
        tag_Context = "";
        tag_Host = "";
        parent = null;

    }

    public double getMbratio() {
        return mbratio;
    }

    public void setMbratio(double mbratio) {
        this.mbratio = mbratio;
    }

    public double getVcratio() {
        return vcratio;
    }

    public void setVcratio(double vcratio) {
        this.vcratio = vcratio;
    }

    public JMXQueue getParent() {
        return parent;
    }

    public void setParent(JMXQueue parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelerType() {
        return modelerType;
    }

    public void setModelerType(String modelerType) {
        this.modelerType = modelerType;
    }

    public String getTag_Queue() {
        return tag_Queue;
    }

    public void setTag_Queue(String tag_Queue) {
        this.tag_Queue = tag_Queue;
    }

    public String getTag_Context() {
        return tag_Context;
    }

    public void setTag_Context(String tag_Context) {
        this.tag_Context = tag_Context;
    }

    public String getTag_Host() {
        return tag_Host;
    }

    public void setTag_Host(String tag_Host) {
        this.tag_Host = tag_Host;
    }

    public int getRunning_0() {
        return running_0;
    }

    public void setRunning_0(int running_0) {
        this.running_0 = running_0;
    }

    public int getRunning_60() {
        return running_60;
    }

    public void setRunning_60(int running_60) {
        this.running_60 = running_60;
    }

    public int getRunning_300() {
        return running_300;
    }

    public void setRunning_300(int running_300) {
        this.running_300 = running_300;
    }

    public int getRunning_1440() {
        return running_1440;
    }

    public void setRunning_1440(int running_1440) {
        this.running_1440 = running_1440;
    }

    public int getFairShareMB() {
        return fairShareMB;
    }

    public void setFairShareMB(int fairShareMB) {
        this.fairShareMB = fairShareMB;
    }

    public int getFairShareVCores() {
        return fairShareVCores;
    }

    public void setFairShareVCores(int fairShareVCores) {
        this.fairShareVCores = fairShareVCores;
    }

    public int getSteadyFairShareMB() {
        return steadyFairShareMB;
    }

    public void setSteadyFairShareMB(int steadyFairShareMB) {
        this.steadyFairShareMB = steadyFairShareMB;
    }

    public int getSteadyFairShareVCores() {
        return steadyFairShareVCores;
    }

    public void setSteadyFairShareVCores(int steadyFairShareVCores) {
        this.steadyFairShareVCores = steadyFairShareVCores;
    }

    public int getMinShareMB() {
        return MinShareMB;
    }

    public void setMinShareMB(int minShareMB) {
        MinShareMB = minShareMB;
    }

    public int getMinShareVCores() {
        return MinShareVCores;
    }

    public void setMinShareVCores(int minShareVCores) {
        MinShareVCores = minShareVCores;
    }

    public int getMaxShareMB() {
        return MaxShareMB;
    }

    public void setMaxShareMB(int maxShareMB) {
        MaxShareMB = maxShareMB;
    }

    public int getMaxShareVCores() {
        return MaxShareVCores;
    }

    public void setMaxShareVCores(int maxShareVCores) {
        MaxShareVCores = maxShareVCores;
    }

    public int getAppsSubmitted() {
        return AppsSubmitted;
    }

    public void setAppsSubmitted(int appsSubmitted) {
        AppsSubmitted = appsSubmitted;
    }

    public int getAppsRunning() {
        return AppsRunning;
    }

    public void setAppsRunning(int appsRunning) {
        AppsRunning = appsRunning;
    }

    public int getAppsPending() {
        return AppsPending;
    }

    public void setAppsPending(int appsPending) {
        AppsPending = appsPending;
    }

    public int getAppsCompleted() {
        return AppsCompleted;
    }

    public void setAppsCompleted(int appsCompleted) {
        AppsCompleted = appsCompleted;
    }

    public int getAppsKilled() {
        return AppsKilled;
    }

    public void setAppsKilled(int appsKilled) {
        AppsKilled = appsKilled;
    }

    public int getAppsFailed() {
        return AppsFailed;
    }

    public void setAppsFailed(int appsFailed) {
        AppsFailed = appsFailed;
    }

    public int getAllocatedMB() {
        return AllocatedMB;
    }

    public void setAllocatedMB(int allocatedMB) {
        AllocatedMB = allocatedMB;
    }

    public int getAllocatedVCores() {
        return AllocatedVCores;
    }

    public void setAllocatedVCores(int allocatedVCores) {
        AllocatedVCores = allocatedVCores;
    }

    public int getAllocatedContainers() {
        return AllocatedContainers;
    }

    public void setAllocatedContainers(int allocatedContainers) {
        AllocatedContainers = allocatedContainers;
    }

    public int getAggregateContainersAllocated() {
        return AggregateContainersAllocated;
    }

    public void setAggregateContainersAllocated(int aggregateContainersAllocated) {
        AggregateContainersAllocated = aggregateContainersAllocated;
    }

    public int getAggregateContainersReleased() {
        return AggregateContainersReleased;
    }

    public void setAggregateContainersReleased(int aggregateContainersReleased) {
        AggregateContainersReleased = aggregateContainersReleased;
    }

    public int getAvailableMB() {
        return AvailableMB;
    }

    public void setAvailableMB(int availableMB) {
        AvailableMB = availableMB;
    }

    public int getAvailableVCores() {
        return AvailableVCores;
    }

    public void setAvailableVCores(int availableVCores) {
        AvailableVCores = availableVCores;
    }

    public int getPendingMB() {
        return PendingMB;
    }

    public void setPendingMB(int pendingMB) {
        PendingMB = pendingMB;
    }

    public int getPendingVCores() {
        return PendingVCores;
    }

    public void setPendingVCores(int pendingVCores) {
        PendingVCores = pendingVCores;
    }

    public int getPendingContainers() {
        return PendingContainers;
    }

    public void setPendingContainers(int pendingContainers) {
        PendingContainers = pendingContainers;
    }

    public int getReservedMB() {
        return ReservedMB;
    }

    public void setReservedMB(int reservedMB) {
        ReservedMB = reservedMB;
    }

    public int getReservedVCores() {
        return ReservedVCores;
    }

    public void setReservedVCores(int reservedVCores) {
        ReservedVCores = reservedVCores;
    }

    public int getReservedContainers() {
        return ReservedContainers;
    }

    public void setReservedContainers(int reservedContainers) {
        ReservedContainers = reservedContainers;
    }

    public int getActiveUsers() {
        return ActiveUsers;
    }

    public void setActiveUsers(int activeUsers) {
        ActiveUsers = activeUsers;
    }

    public int getActiveApplications() {
        return ActiveApplications;
    }

    public void setActiveApplications(int activeApplications) {
        ActiveApplications = activeApplications;
    }

    public void addChildQueue(JMXQueue queue) { childQueue.add(queue);}

    public JMXQueue getChildQueue(String name) {
        for (JMXQueue q : childQueue) {
            if (q.getName().equals(name)) {
                return q;
            }
        }
        return null;
    }
    public List<JMXQueue> getChildQueue() {
        return childQueue;
    }

    public JMXQueue clone (JMXQueue jsonNode) {
        this.setName(jsonNode.getName());
        this.setModelerType(jsonNode.getModelerType());
        this.setTag_Host(jsonNode.getTag_Host());
        this.setTag_Context(jsonNode.getTag_Context());
        this.setTag_Host(jsonNode.getTag_Host());
        this.setRunning_0(jsonNode.getRunning_0());
        this.setRunning_60(jsonNode.getRunning_60());
        this.setRunning_300(jsonNode.getRunning_300());
        this.setRunning_1440(jsonNode.getRunning_1440());
        this.setFairShareMB(jsonNode.getFairShareMB());
        this.setFairShareVCores(jsonNode.getFairShareVCores());
        this.setSteadyFairShareMB(jsonNode.getSteadyFairShareMB());
        this.setSteadyFairShareVCores(jsonNode.getSteadyFairShareVCores());
        this.setMinShareMB(jsonNode.getMinShareMB());
        this.setMinShareVCores(jsonNode.getMinShareVCores());
        this.setMaxShareMB(jsonNode.getMaxShareMB());
        this.setMaxShareVCores(jsonNode.getMaxShareVCores());
        this.setAppsSubmitted(jsonNode.getAppsSubmitted());
        this.setAppsRunning(jsonNode.getAppsRunning());
        this.setAppsPending(jsonNode.getAppsPending());
        this.setAppsCompleted(jsonNode.getAppsCompleted());
        this.setAppsKilled(jsonNode.getAppsKilled());
        this.setAppsFailed(jsonNode.getAppsFailed());
        this.setAllocatedMB(jsonNode.getAllocatedMB());
        this.setAllocatedVCores(jsonNode.getAllocatedVCores());
        this.setAllocatedContainers(jsonNode.getAllocatedContainers());
        this.setAggregateContainersAllocated(jsonNode.getAggregateContainersAllocated());
        this.setAggregateContainersReleased(jsonNode.getAggregateContainersReleased());
        this.setAvailableMB(jsonNode.getAvailableMB());
        this.setAvailableVCores(jsonNode.getAvailableVCores());
        this.setPendingMB(jsonNode.getPendingMB());
        this.setPendingVCores(jsonNode.getPendingVCores());
        this.setPendingContainers(jsonNode.getPendingContainers());
        this.setReservedMB(jsonNode.getReservedMB());
        this.setReservedVCores(jsonNode.getReservedVCores());
        this.setReservedContainers(jsonNode.getReservedContainers());
        this.setActiveUsers(jsonNode.getActiveUsers());
        this.setActiveApplications(jsonNode.getActiveApplications());
        return this;

    }

    public void setChildQueue(List<JMXQueue> childQueue) {
        this.childQueue = childQueue;
    }

    @Override
    public String toString() {
        return "JMXQueue{" +
                "name='" + name + '\'' +
                ", modelerType='" + modelerType + '\'' +
                ", tag_Queue='" + tag_Queue + '\'' +
                ", tag_Context='" + tag_Context + '\'' +
                ", tag_Host='" + tag_Host + '\'' +
                ", running_0=" + running_0 +
                ", running_60=" + running_60 +
                ", running_300=" + running_300 +
                ", running_1440=" + running_1440 +
                ", fairShareMB=" + fairShareMB +
                ", fairShareVCores=" + fairShareVCores +
                ", steadyFairShareMB=" + steadyFairShareMB +
                ", steadyFairShareVCores=" + steadyFairShareVCores +
                ", MinShareMB=" + MinShareMB +
                ", MinShareVCores=" + MinShareVCores +
                ", MaxShareMB=" + MaxShareMB +
                ", MaxShareVCores=" + MaxShareVCores +
                ", AppsSubmitted=" + AppsSubmitted +
                ", AppsRunning=" + AppsRunning +
                ", AppsPending=" + AppsPending +
                ", AppsCompleted=" + AppsCompleted +
                ", AppsKilled=" + AppsKilled +
                ", AppsFailed=" + AppsFailed +
                ", AllocatedMB=" + AllocatedMB +
                ", AllocatedVCores=" + AllocatedVCores +
                ", AllocatedContainers=" + AllocatedContainers +
                ", AggregateContainersAllocated=" + AggregateContainersAllocated +
                ", AggregateContainersReleased=" + AggregateContainersReleased +
                ", AvailableMB=" + AvailableMB +
                ", AvailableVCores=" + AvailableVCores +
                ", PendingMB=" + PendingMB +
                ", PendingVCores=" + PendingVCores +
                ", PendingContainers=" + PendingContainers +
                ", ReservedMB=" + ReservedMB +
                ", ReservedVCores=" + ReservedVCores +
                ", ReservedContainers=" + ReservedContainers +
                ", ActiveUsers=" + ActiveUsers +
                ", ActiveApplications=" + ActiveApplications +
                ", childQueue=" + childQueue +
                '}';
    }
}
