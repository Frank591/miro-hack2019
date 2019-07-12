package com.miro.hack2019.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.miro.hack2019.sources.MetricCardConverter;
import com.miro.hack2019.sources.MetricCardDto;
import com.miro.hack2019.sources.MetricsService;
import com.miro.hack2019.storage.core.MetricCardRepository;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateMetricServlet extends PathHttpServlet {


    private MetricsService metricsService;
    private Gson gson;

    public UpdateMetricServlet(MetricsService metricsService) {
        this.metricsService = metricsService;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        this.gson = gsonBuilder.create();

    }

    @Override
    public String getPath() {
        return "/metrics/update";
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Headers","Content-Type");
        resp.setHeader("Allow","POST");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Headers","Content-Type");

        var contentType = req.getHeader("content-type");
        if (StringUtils.isBlank(contentType) || !contentType.equalsIgnoreCase("application/json")){
            throw new RuntimeException("Only 'application/json' allowed for content-type");
        }

        try {
            MetricCardDto metricCardDto = gson.fromJson(req.getReader(), MetricCardDto.class);
            var updatedMetricDto =  metricsService.updateMetric(metricCardDto);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(updatedMetricDto));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace(response.getWriter());
        }
    }
}
