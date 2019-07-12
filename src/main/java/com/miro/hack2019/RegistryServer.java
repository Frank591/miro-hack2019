package com.miro.hack2019;

import java.net.URI;
import java.net.URL;

import com.miro.hack2019.servlets.*;
import com.miro.hack2019.sources.EventHubMetricsService;
import com.miro.hack2019.sources.KissMetricsService;
import com.miro.hack2019.sources.MetricsService;
import com.miro.hack2019.sources.PrometheusMetricsService;
import com.miro.hack2019.storage.core.MetricCardRepository;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import javax.sql.DataSource;

public class RegistryServer {


    public static void main(String[] args) throws Exception {

        AppConfig appConfig = new AppConfig();
        DataSource dataSource = appConfig.createDataSource();
        MetricCardRepository metricCardRepository = appConfig.createMetricCardRepository(dataSource);
        PrometheusMetricsService prometheusMetricsService = appConfig.createPrometheusMetricsService(metricCardRepository);
        EventHubMetricsService eventHubMetricsService = appConfig.createEventHubMetricsService(metricCardRepository);
        KissMetricsService kissMetricsService = appConfig.createKissMetricsService(metricCardRepository);
        MetricsService metricsService = appConfig.createMetricsService(metricCardRepository);

        Server server = new Server(8080);
        ResourceHandler staticResourcesHandler = new ResourceHandler();

        // Figure out what path to serve content from
        ClassLoader cl = RegistryServer.class.getClassLoader();
        // We look for a file, as ClassLoader.getResource() is not
        // designed to look for directories (we resolve the directory later)
        URL f = cl.getResource("index.html");
        if (f == null) {
            throw new RuntimeException("Unable to find resource directory");
        }

        // Resolve file to directory
        URI webRootUri = f.toURI().resolve("./").normalize();
        // Path /static/*
        ContextHandler staticContextHandler = new ContextHandler();
        staticContextHandler.setContextPath("/static");
        staticContextHandler.setBaseResource(Resource.newResource(webRootUri));
        staticContextHandler.setHandler(staticResourcesHandler);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        // Path /metrics

        MetricsListServlet metricsListServlet = new MetricsListServlet(metricsService);
        addServlet(servletContextHandler, metricsListServlet);

        //Path /sources/prometheus/sync
        PrometheusSyncServlet prometheusSyncServlet = new PrometheusSyncServlet(prometheusMetricsService);
        addServlet(servletContextHandler, prometheusSyncServlet);

        //Path /sources/prometheus/sync
        EventHubSyncServlet eventHubSyncServlet = new EventHubSyncServlet(eventHubMetricsService);
        addServlet(servletContextHandler, eventHubSyncServlet);

        //Path /sources/kissmetrics/sync
        KissMetricsSyncServlet kissMetricsSyncServlet = new KissMetricsSyncServlet(kissMetricsService);
        addServlet(servletContextHandler, kissMetricsSyncServlet);

        //Path /metrics/update
        UpdateMetricServlet updateMetricServlet = new UpdateMetricServlet(metricsService);
        addServlet(servletContextHandler, updateMetricServlet);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{staticContextHandler, servletContextHandler});

        server.setHandler(contexts);
        server.start();
        server.join();
    }

    private static void addServlet(ServletContextHandler servletContextHandler, PathHttpServlet servlet) {
        servletContextHandler.addServlet(new ServletHolder(servlet), servlet.getPath());

    }
}