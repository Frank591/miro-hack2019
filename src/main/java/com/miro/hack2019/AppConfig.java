package com.miro.hack2019;

import com.miro.hack2019.sources.EventHubMetricsService;
import com.miro.hack2019.sources.KissMetricsService;
import com.miro.hack2019.sources.MetricsService;
import com.miro.hack2019.sources.PrometheusMetricsService;
import com.miro.hack2019.storage.core.MetricCardRepository;
import com.miro.hack2019.storage.postgres.PostgresMetricCardRepository;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class AppConfig {

    public DataSource createDataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/mirohack2019");
        driverManagerDataSource.setUsername("postgres");
        driverManagerDataSource.setPassword("postgres");
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        return driverManagerDataSource;
    }

    public MetricCardRepository createMetricCardRepository(DataSource dataSource){
        return new PostgresMetricCardRepository(dataSource);
    }

    public PrometheusMetricsService createPrometheusMetricsService(MetricCardRepository metricCardRepository){
        return new PrometheusMetricsService(metricCardRepository);
    }

    public EventHubMetricsService createEventHubMetricsService(MetricCardRepository metricCardRepository){
        return new EventHubMetricsService(metricCardRepository);
    }

    public KissMetricsService createKissMetricsService(MetricCardRepository metricCardRepository){
        return new KissMetricsService(metricCardRepository);
    }

    public MetricsService createMetricsService(MetricCardRepository metricCardRepository){
        return new MetricsService(metricCardRepository);
    }
}
