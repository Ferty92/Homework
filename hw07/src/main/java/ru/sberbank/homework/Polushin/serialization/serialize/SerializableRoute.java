package ru.sberbank.homework.Polushin.serialization.serialize;

import ru.sberbank.homework.common.City;
import ru.sberbank.homework.common.Route;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SerializableRoute extends Route<City> implements Serializable {
    private String routeName;
    private List<City> cities;


    public SerializableRoute(Route<City> route) {
        this.routeName = route.getRouteName();
        this.cities = route.getCities();
        setRouteName(routeName);
    }

    @Override
    public List<City> getCities() {
        return cities;
    }

    @Override
    public String toString() {
        return "Route: { " +
                String.join(" -> ", cities.stream().map(City::getCityName).collect(Collectors.toList()))
                + " }";
    }
}