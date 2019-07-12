package com.miro.hack2019.sources;

import com.miro.hack2019.storage.core.MetricCard;
import com.miro.hack2019.storage.core.MetricCardRepository;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MetricsService {


    private MetricCardRepository repository;

    public MetricsService(MetricCardRepository repository) {

        this.repository = repository;
    }

    public List<MetricCardDto> getAllMetrics() {
        var metricCards = repository.getAll();

        var result = new ArrayList<MetricCardDto>();
        for (var metricCard : metricCards) {

            var problems = determineProblems(metricCard);
            var dto = MetricCardConverter.toDto(metricCard, problems);
            result.add(dto);


        }
        return result;
    }


    public MetricCardDto updateMetric(MetricCardDto newMetricState){
        repository.update(MetricCardConverter.fromDto(newMetricState));
        var updatedMetricFromDB = repository.get(newMetricState.getName(), newMetricState.getSource());
        return MetricCardConverter.toDto(updatedMetricFromDB, determineProblems(updatedMetricFromDB));
    }

    public String[] determineProblems(MetricCard metricCard) {
        var problems = new ArrayList<>();
        if (StringUtils.isBlank(metricCard.getDescription())) {
            problems.add("Description is empty");
        } else if (metricCard.getDescription().length() < 15) {
            problems.add("Description is too short");
        }

        if (!StringUtils.isBlank(metricCard.getDescription())
                && metricCard.getName().length() > metricCard.getDescription().length()) {
            problems.add("Name is longer than description. Description might be too short.");
        }

        if (StringUtils.isBlank(metricCard.getOwnerTeam())) {
            problems.add("Owner team is not assigned.");
        }

        return problems.toArray(new String[problems.size()]);
    }
}
