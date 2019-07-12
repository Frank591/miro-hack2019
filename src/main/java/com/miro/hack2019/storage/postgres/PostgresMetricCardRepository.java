package com.miro.hack2019.storage.postgres;

import com.miro.hack2019.storage.core.MetricCard;
import com.miro.hack2019.storage.core.MetricCardRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collection;

public class PostgresMetricCardRepository implements MetricCardRepository {

    private JdbcTemplate jdbcTemplate;
    private static final MetricCardMapper metricCardMapper = new MetricCardMapper();

    private final String SQL_GET_ALL = "select * from metric_card";
    private final String SQL_GET = "select * from metric_card where name = ? and source = ?";
    private final String SQL_DELETE_BY_SOURCE = "delete from metric_card where source=?";
    private final String SQL_UPDATE = "update metric_card " +
            "set description = ?," +
            "owner_team = ?," +
            "last_time_available_at = ?" +
            "where name = ? and source = ?";
    private final String SQL_INSERT = "insert into metric_card(name, " +
            "source, " +
            "description, " +
            "owner_team, " +
            "created_at, " +
            "last_time_available_at," +
            "type) " +
            "values (?,?,?,?,?,?,?)";

    public PostgresMetricCardRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(MetricCard metricCard) {
        jdbcTemplate.update(SQL_INSERT,
                metricCard.getName(),
                metricCard.getSource(),
                metricCard.getDescription(),
                metricCard.getOwnerTeam(),
                metricCard.getCreatedAt(),
                metricCard.getLastTimeAvailableAt(),
                metricCard.getType());
    }

    public void update(MetricCard metricCard) {
        jdbcTemplate.update(SQL_UPDATE,
                metricCard.getDescription(),
                metricCard.getOwnerTeam(),
                metricCard.getLastTimeAvailableAt(),
                metricCard.getName(),
                metricCard.getSource());
    }

    public Collection<MetricCard> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, metricCardMapper);
    }

    @Override
    public MetricCard get(String name, String source) {
        var result = jdbcTemplate.query(SQL_GET, new String[]{name, source}, metricCardMapper);
        if (result.size() == 0) {
            throw new RuntimeException(String.format("Metric with source=%s and name=%s not found.", source, name));
        }
        if (result.size() > 1) {
            throw new RuntimeException(String.format("Metric with source=%s and name=%s is not unique.", source, name));
        }
        return result.get(0);
    }

    @Override
    public void deleteBySource(String source) {
        jdbcTemplate.update(SQL_DELETE_BY_SOURCE, source);
    }
}
