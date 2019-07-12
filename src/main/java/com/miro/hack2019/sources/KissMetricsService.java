package com.miro.hack2019.sources;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.miro.hack2019.storage.core.MetricCard;
import com.miro.hack2019.storage.core.MetricCardRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.HashSet;

public class KissMetricsService {

    private static final String source = "kissmetrics";
    private MetricCardRepository metricCardRepository;
    private Gson gson;

    public KissMetricsService(MetricCardRepository metricCardRepository) {
        gson = new Gson();
        this.metricCardRepository = metricCardRepository;
    }

    public void sync() throws FileNotFoundException {
        metricCardRepository.deleteBySource(source);
        JsonReader reader = new JsonReader(new FileReader("C:\\Users\\Stepan\\Downloads\\kissmetrics.json"));
        KissMetricsResponse data = gson.fromJson(reader, KissMetricsResponse.class);

        for (var kissMetricsMetric : data.getData()) {
            var metricCard = createMetricCard(kissMetricsMetric);
            metricCardRepository.create(metricCard);
        }
    }



    private static MetricCard createMetricCard(KissMetricsMetric metric) {
        var name = metric.getName();
        var createdAt = metric.getFirstSentAt();
        var lastTimeAvailableAt = metric.getLastSentAt();
        return new MetricCard(name,
                null,
                null,
                source,
                null,
                createdAt,
                lastTimeAvailableAt);
    }


    private static class KissMetricsMetric {

        private String name;
        private Date first_sent_at;
        private Date last_sent_at;


        public KissMetricsMetric(String name, Date first_sent_at, Date last_sent_at) {
            this.name = name;
            this.first_sent_at = first_sent_at;
            this.last_sent_at = last_sent_at;
        }

        public String getName() {
            return name;
        }

        public Date getFirstSentAt() {
            return first_sent_at;
        }

        public Date getLastSentAt() {
            return last_sent_at;
        }
    }

    private static class KissMetricsResponse{
        private KissMetricsMetric[] data;

        private KissMetricsResponse(KissMetricsMetric[] data) {
            this.data = data;
        }

        public KissMetricsMetric[] getData() {
            return data;
        }
    }
}
