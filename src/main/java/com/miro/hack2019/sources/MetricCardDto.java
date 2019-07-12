package com.miro.hack2019.sources;

import java.util.Date;

public class MetricCardDto {
    private String name;
    private String description;
    private String ownerTeam;
    private String source;
    private Date createdAt;
    private Date lastTimeAvailableAt;
    private String type;
    private String[] problems;

    public MetricCardDto(String name, String description, String ownerTeam, String source, Date createdAt, Date lastTimeAvailableAt, String type, String[] problems) {
        this.name = name;
        this.description = description;
        this.ownerTeam = ownerTeam;
        this.source = source;
        this.createdAt = createdAt;
        this.lastTimeAvailableAt = lastTimeAvailableAt;
        this.type = type;
        this.problems = problems;
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

    public String[]  getProblems(){
        return problems;
    }
}