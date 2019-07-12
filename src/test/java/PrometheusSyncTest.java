import com.google.gson.Gson;
import com.miro.hack2019.sources.PromethesMetricsResponse;
import com.miro.hack2019.sources.PrometheusMetricsService;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class PrometheusSyncTest {

    @Test
    public void getMetricsTest() throws IOException, InterruptedException {
        PromethesMetricsResponse response = PrometheusMetricsService.getMetrics("host.docker.internal:7071", new Gson());
        String s="";
    }


}
