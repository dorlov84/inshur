package com.inshur.shared.datalayer;

import com.inshur.shared.model.WeatherRequestHistory;

import java.util.List;

public class WeatherRequestHistoryRepository extends LocalFileDataRepository<WeatherRequestHistory> {

    public WeatherRequestHistoryRepository() {
        super(WeatherRequestHistory.class);
    }

    public List<WeatherRequestHistory> findWithFilters(Double lat, Double lon, String sortBy, boolean isAsc, Integer limit) {
        List<WeatherRequestHistory> all = findAll();
        if (all == null || all.isEmpty()) {
            return all;
        }
        return all.stream()
                .sorted((l, r) -> {
                    return switch (sortBy) {
                        case "lat" -> isAsc
                                ? l.getLat().compareTo(r.getLat())
                                : r.getLat().compareTo(l.getLat());
                        case "lon" -> isAsc
                                ? l.getLon().compareTo(r.getLon())
                                : r.getLon().compareTo(l.getLon());
                        default -> isAsc
                                ? l.getRequestDateTime().compareTo(r.getRequestDateTime())
                                : r.getRequestDateTime().compareTo(l.getRequestDateTime());
                    };
                })
                .filter(nxt -> {

                    if (lat != null && !lat.equals(nxt.getLat())) {
                        return false;
                    }

                    return lon == null || lon.equals(nxt.getLon());

                })
                .limit(limit == null || limit < 0 ? all.size() : limit)
                .toList();
    }
}
