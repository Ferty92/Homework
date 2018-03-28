package ru.sberbank.homework.Polushin.serialization.serialize;

import ru.sberbank.homework.common.CachePathProvider;
import ru.sberbank.homework.common.City;
import ru.sberbank.homework.common.Route;
import ru.sberbank.homework.common.RouteService;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SerializableRouteService extends RouteService<City, Route<City>> {
    private Set<String> hash = new HashSet<>();
    private String name;

    public SerializableRouteService(CachePathProvider pathProvider, boolean devMode) {
        super(pathProvider, devMode);
    }

    @Override
    public Route<City> getRoute(String from, String to) {
        name = from + " - " + to;
        Route<City> route = null;
        String pathToFile = pathProvider.getCacheDirectoryPath() + "/" + name;

        if (hash.contains(name)) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pathToFile))) {

                route = (SerializableRoute) ois.readObject();

            } catch (IOException | ClassNotFoundException ignore) {
            }
        } else {
            route = super.getRoute(from, to);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathToFile))) {

                SerializableRoute serializableRoute = new SerializableRoute(route);
                oos.writeObject(serializableRoute);

            } catch (IOException ignore) {
            }

            hash.add(name);
        }
        return route;
    }

    @Override
    protected City createCity(int id, String cityName, LocalDate foundDate, long numberOfInhabitants) {
        return new City(id, cityName, foundDate, numberOfInhabitants);
    }

    @Override
    protected Route<City> createRoute(List<City> cities) {
        return new Route(name, cities);
    }
}