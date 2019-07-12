package com.miro.hack2019.storage.postgres;

import com.miro.hack2019.storage.core.MetricCard;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MetricCardMapper implements RowMapper<MetricCard> {
    public MetricCard mapRow(ResultSet resultSet, int i) throws SQLException {
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        String ownerTeam = resultSet.getString("owner_team");
        String source = resultSet.getString("source");
        String type = resultSet.getString("type");
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        Timestamp lastTimeAvailableAt = resultSet.getTimestamp("last_time_available_at");
        return new MetricCard(name, description, ownerTeam, source, type, createdAt, lastTimeAvailableAt);
    }
}
