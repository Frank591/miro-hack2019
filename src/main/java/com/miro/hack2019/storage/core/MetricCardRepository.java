package com.miro.hack2019.storage.core;

import java.util.Collection;

public interface MetricCardRepository {
    void create(MetricCard metricCard);

    void update(MetricCard metricCard);

    Collection<MetricCard> getAll();

    MetricCard get(String name, String source);

    void deleteBySource(String source);

}
