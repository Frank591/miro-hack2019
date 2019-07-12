package com.miro.hack2019.sources;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.miro.hack2019.storage.core.MetricCard;
import com.miro.hack2019.storage.core.MetricCardRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class EventHubMetricsService {


    private static final String source = "EventHub";
    private MetricCardRepository metricCardRepository;
    private Gson gson;

    public EventHubMetricsService(MetricCardRepository metricCardRepository) {
        gson = new Gson();
        this.metricCardRepository = metricCardRepository;
    }

    public void sync() throws FileNotFoundException {
        metricCardRepository.deleteBySource(source);
        JsonReader reader = new JsonReader(new FileReader("C:\\Users\\Stepan\\Downloads\\events.json"));
        EventHubMetric[] data = gson.fromJson(reader, EventHubMetric[].class);

        var uniqueMetricNames = new HashSet<String>();
        var uniqueMetrics = new ArrayList<EventHubMetric>();
        for (int i = 0; i < data.length; i++) {
            if (uniqueMetricNames.add(data[i].getName())) {
                uniqueMetrics.add(data[i]);
            }
        }

        for (var eventHubMetric: uniqueMetrics) {
            var metricCard = createMetricCard(eventHubMetric);
            metricCardRepository.create(metricCard);
        }
    }


    private static class EventHubMetric {

        private String name;
        private String description;
        private Date date_added;
        private Date date_changed;
        private int source;

        public EventHubMetric(String name, String description, Date date_added, Date date_changed, int source) {
            this.name = name;
            this.description = description;
            this.date_changed = date_changed;
            this.date_added = date_added;
            this.source = source;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Date getDateAdded() {
            return date_added;
        }

        public int getSource() {
            return source;
        }

        public Date getDateChanged() {
            return date_changed;
        }
    }


    private static MetricCard createMetricCard(EventHubMetric eventHubMetric) {
        var name = eventHubMetric.getName();
        var descr = eventHubMetric.getDescription();
        var createdAt = eventHubMetric.getDateAdded();
        var lastTimeAvailableAt = eventHubMetric.getDateChanged();
        String type = null;
        switch (eventHubMetric.getSource()) {
            case 1:
                type = "canvas";
                break;
            case 2:
                type = "eventhub";
                break;
            case 3:
                type = "projections";
                break;
        }
        return new MetricCard(name,
                descr,
                null,
                source,
                type,
                createdAt,
                lastTimeAvailableAt);
    }

}
