package com.miro.hack2019.sources;

public class Target {
    private String job;
    private String instance;

    public Target(String job, String instance) {
        this.job = job;
        this.instance = instance;
    }

    public String getJob() {
        return job;
    }

    public String getInstance() {
        return instance;
    }
}
