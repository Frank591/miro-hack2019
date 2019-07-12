package com.miro.hack2019.sources;

import com.miro.hack2019.sources.MetricCardDto;
import com.miro.hack2019.storage.core.MetricCard;
import org.apache.commons.lang3.StringUtils;

public class MetricCardConverter {

    private MetricCardConverter() {
    }

    public static MetricCard fromDto(MetricCardDto dto) {
        if (StringUtils.isBlank(dto.getName())) {
            throw new IllegalArgumentException("name can't be null or empty");
        }
        if (StringUtils.isBlank(dto.getSource())) {
            throw new IllegalArgumentException("source can't be null or empty");
        }

        return new MetricCard(dto.getName(),
                dto.getDescription(),
                dto.getOwnerTeam(),
                dto.getSource(),
                dto.getType(),
                dto.getCreatedAt(),
                dto.getLastTimeAvailableAt());
    }

    public static MetricCardDto toDto(MetricCard metricCard, String[] problems) {
        return new MetricCardDto(metricCard.getName(),
                metricCard.getDescription(),
                metricCard.getOwnerTeam(),
                metricCard.getSource(),
                metricCard.getCreatedAt(),
                metricCard.getLastTimeAvailableAt(),
                metricCard.getType(),
                problems);
    }
}
