package ru.sberbank.homework.Polushin.serialization;

import ru.sberbank.homework.common.CachePathProvider;
import ru.sberbank.homework.common.City;
import ru.sberbank.homework.common.Route;
import ru.sberbank.homework.common.RouteService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * ПРИМЕР
 */
public class InMemoryRouteService extends RouteService<City, Route<City>> {
    private HashMap<String, Route<City>> routeHashMap = new HashMap<>();
    private String key;

    public InMemoryRouteService(CachePathProvider cachePathProvider, boolean devMode) {
        super(cachePathProvider, devMode);
    }

    @Override
    public Route<City> getRoute(String from, String to) {
        key = from + "_" + to;
        Route<City> route = routeHashMap.get(key);
        if (route == null) {
            route = super.getRoute(from, to);
            routeHashMap.put(key, route);
        }

        return route;
    }

    @Override
    protected City createCity(int id, String cityName, LocalDate foundDate, long numberOfInhabitants) {
        return new City(id, cityName, foundDate, numberOfInhabitants);
    }

    @Override
    protected Route<City> createRoute(List<City> cities) {
        return new Route<>(key, cities);
    }
}
