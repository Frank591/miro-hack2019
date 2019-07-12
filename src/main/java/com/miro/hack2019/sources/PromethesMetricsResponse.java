package com.miro.hack2019.sources;

import java.util.List;

public class PromethesMetricsResponse {
    private String status;
    private List<PrometheusMetric> data;

    public PromethesMetricsResponse(String status, List<PrometheusMetric> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public List<PrometheusMetric> getData() {
        return data;
    }
}
