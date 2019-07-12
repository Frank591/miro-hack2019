package com.miro.hack2019.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.miro.hack2019.sources.MetricsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MetricsListServlet extends PathHttpServlet {


    private final Gson gson;
    private MetricsService metricsService;

    public MetricsListServlet(MetricsService metricsService) {
        this.metricsService = metricsService;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        this.gson = gsonBuilder.create();

    }

    public String getPath() {
        return "/metrics";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        var result = metricsService.getAllMetrics();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(result));
    }

}
