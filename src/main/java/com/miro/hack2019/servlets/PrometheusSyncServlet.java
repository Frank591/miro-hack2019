package com.miro.hack2019.servlets;

import com.miro.hack2019.sources.PrometheusMetricsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PrometheusSyncServlet extends PathHttpServlet {

    private PrometheusMetricsService prometheusMetricsService;

    public PrometheusSyncServlet(PrometheusMetricsService prometheusMetricsService){

        this.prometheusMetricsService = prometheusMetricsService;
    }

    @Override
    public String getPath() {
        return "/sources/prometheus/sync";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setHeader("Access-Control-Allow-Origin","*");

        try {
            prometheusMetricsService.sync();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace(response.getWriter());
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
