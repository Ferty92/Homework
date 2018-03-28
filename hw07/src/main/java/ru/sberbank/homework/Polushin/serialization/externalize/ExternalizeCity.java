package ru.sberbank.homework.Polushin.serialization.externalize;

import ru.sberbank.homework.common.City;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;
import java.util.List;

public class ExternalizeCity extends City implements Externalizable {

    public ExternalizeCity() {
        super();
    }

    public ExternalizeCity(int id, String cityName, LocalDate foundDate, long numberOfInhabitants) {
        super(id, cityName, foundDate, numberOfInhabitants);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.getId());
        out.writeObject(this.getCityName());
        out.writeObject(this.getFoundDate());
        out.writeLong(this.getNumberOfInhabitants());

        List<City> nearCities = this.getNearCities();
        out.writeInt(nearCities.size());
        for (City city : this.getNearCities()) {
            out.writeObject(city);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.setId(in.readInt());
        super.setCityName((String) in.readObject());
        super.setFoundDate((LocalDate) in.readObject());
        super.setNumberOfInhabitants(in.readLong());

        List<City> nearCities = super.getNearCities();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            nearCities.add((City) in.readObject());
        }
    }
}