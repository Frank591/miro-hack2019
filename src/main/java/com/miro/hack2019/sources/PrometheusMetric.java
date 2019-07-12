package com.miro.hack2019.sources;

public class PrometheusMetric {
    private String metric;
    private String type;
    private String help;
    private String unit;
    private Target target;

    public PrometheusMetric(String metric, String type, String help, String unit, Target target) {
        this.metric = metric;
        this.type = type;
        this.help = help;
        this.unit = unit;
        this.target = target;
    }

    public Target getTarget() {
        return target;
    }

    public String getUnit() {
        return unit;
    }

    public String getHelp() {
        return help;
    }

    public String getType() {
        return type;
    }

    public String getMetricName() {
        return metric;
    }
}
