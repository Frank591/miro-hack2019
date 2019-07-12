package com.miro.hack2019.storage.core;


import java.util.Date;

public class MetricCard {

    private String name;
    private String description;
    private String ownerTeam;
    private String source;
    private String type;
    private Date createdAt;
    private Date lastTimeAvailableAt;

    public MetricCard(String name, String description, String ownerTeam, String source, String type, Date createdAt, Date lastTimeAvailableAt) {
        this.name = name;
        this.description = description;
        this.ownerTeam = ownerTeam;
        this.source = source;
        this.type = type;
        this.createdAt = createdAt;
        this.lastTimeAvailableAt = lastTimeAvailableAt;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerTeam() {
        return ownerTeam;
    }

    public String getSource() {
        return source;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getLastTimeAvailableAt() {
        return lastTimeAvailableAt;
    }

    public String getType() {
        return type;
    }
}
