package com.miro.hack2019.sources;

import com.google.gson.Gson;
import com.miro.hack2019.storage.core.MetricCard;
import com.miro.hack2019.storage.core.MetricCardRepository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;

public class PrometheusMetricsService {

    private static final String severAddress = "http://localhost:9090";
    private static final String targetsUrl = "/api/v1/targets";
    private static final String instance = "host.docker.internal:7071";
    private static final String source = "prometheus";
    private final Gson gson;
    private MetricCardRepository metricCardRepository;

    public PrometheusMetricsService(MetricCardRepository metricCardRepository) {
        this.metricCardRepository = metricCardRepository;
        this.gson = new Gson();
    }


    public void sync() throws Exception {
        PromethesMetricsResponse promethesMetricsResponse = getMetrics(instance, gson);
        metricCardRepository.deleteBySource(source);
        for (PrometheusMetric metric : promethesMetricsResponse.getData()) {
            MetricCard metricCard = new MetricCard(metric.getMetricName(),
                    metric.getHelp(),
                    null,
                    source,
                    determineMetricType(metric.getMetricName()),
                    new Date(),
                    new Date());
            metricCardRepository.create(metricCard);
        }
    }

    private static String createTargetMetadataUrl(String instance) {
        return severAddress + "/api/v1/targets/metadata?match_target=%7Binstance=%22" + instance + "%22%7D";
    }

    private static String determineMetricType(String metricName) {
        if (metricName.toLowerCase().startsWith("rtb_")) {
            return "server";
        }
        return "system";
    }

    public static PromethesMetricsResponse getMetrics(String instance, Gson gson) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String url = createTargetMetadataUrl(instance);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        String responseBody = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

        StatusResponse statusResponse = gson.fromJson(responseBody, StatusResponse.class);
        if (statusResponse.getStatus() == "error") {
            throw new RuntimeException(responseBody);
        }
        PromethesMetricsResponse promethesMetricsResponse = gson.fromJson(responseBody, PromethesMetricsResponse.class);
        return promethesMetricsResponse;
    }
}
