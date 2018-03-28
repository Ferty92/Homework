package ru.sberbank.homework.Polushin.serialization.externalize;

import ru.sberbank.homework.common.City;
import ru.sberbank.homework.common.Route;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ExternalizeRoute extends Route<City> implements Externalizable {
    private String routeName;
    private List<City> cities;

    public ExternalizeRoute() {
        super();
    }

    public ExternalizeRoute(String routeName, List<City> cities) {
        this.routeName = routeName;
        this.cities = cities;
        setRouteName(routeName);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(routeName);
        out.writeInt(cities.size());
        for (City city : cities) {
            ((Externalizable) city).writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        routeName = in.readUTF();
        int size = in.readInt();
        cities = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            ExternalizeCity city = new ExternalizeCity();
            city.readExternal(in);
            cities.add(city);
        }
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