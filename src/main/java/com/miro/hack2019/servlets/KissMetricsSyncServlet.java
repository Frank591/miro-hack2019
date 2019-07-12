package com.miro.hack2019.servlets;

import com.miro.hack2019.sources.KissMetricsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class KissMetricsSyncServlet extends PathHttpServlet {

    private KissMetricsService service;

    public KissMetricsSyncServlet(KissMetricsService service){
        this.service = service;
    }

    @Override
    public String getPath() {
        return "/sources/kissmetrics/sync";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setHeader("Access-Control-Allow-Origin","*");
        try {
            service.sync();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace(response.getWriter());
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
