import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.miro.hack2019.AppConfig;
import com.miro.hack2019.storage.core.MetricCard;
import com.miro.hack2019.storage.core.MetricCardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class PostgresTest {

    private static AppConfig appConfig;
    private static MetricCardRepository metricCardRepository;

    @BeforeAll
    protected static void setUp() throws Exception {
        appConfig = new AppConfig();
        metricCardRepository = appConfig.createMetricCardRepository(appConfig.createDataSource());
    }

    @org.junit.jupiter.api.Test
    public void crud() {
        MetricCard card1 = createRandomCard();
        metricCardRepository.create(card1);
        Collection<MetricCard> metricCards = metricCardRepository.getAll();
        Assertions.assertTrue(metricCards.size() == 1);
        MetricCard cardFromDb = metricCards.iterator().next();
        assertEquals(card1, cardFromDb);
    }



    private static void assertEquals(MetricCard expected, MetricCard actual) {
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getSource(), actual.getSource());
        Assertions.assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getOwnerTeam(), actual.getOwnerTeam());
        Assertions.assertEquals(expected.getLastTimeAvailableAt(), actual.getLastTimeAvailableAt());
    }

    private static MetricCard createRandomCard() {
        String uuid = UUID.randomUUID().toString();
        return new MetricCard("test_" + uuid,
                "test_" + uuid,
                "test",
                "test",
                "test",
                new Date(),
                new Date());
    }



}
