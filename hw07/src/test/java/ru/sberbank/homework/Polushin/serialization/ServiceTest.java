package ru.sberbank.homework.Polushin.serialization;

import org.junit.Before;
import org.junit.Test;
import ru.sberbank.homework.Polushin.serialization.externalize.ExternalizeRouteService;
import ru.sberbank.homework.Polushin.serialization.serialize.SerializableRouteService;
import ru.sberbank.homework.common.CachePathProvider;
import ru.sberbank.homework.common.City;
import ru.sberbank.homework.common.Route;
import ru.sberbank.homework.common.RouteService;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ServiceTest {
    List<RouteService<City, Route<City>>> services = new ArrayList<>();

    @Before
    public void pre() {
        RouteService<City, Route<City>> routeService;
        routeService = new InMemoryRouteService(() -> "C:\\temp\\inMemory\\", true);
        services.add(routeService);
        routeService = new SerializableRouteService(() -> "C:\\temp\\serial\\", true);
        services.add(routeService);
        routeService = new ExternalizeRouteService(() -> "C:\\temp\\extern\\", true);
        services.add(routeService);

    }

    @Test
    public void testExampleRouteService() throws IOException {
        FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") +
                "\\src\\test\\java\\ru\\sberbank\\homework\\Polushin\\serialization\\Result.txt");

        for (RouteService<City, Route<City>> routeService : services) {
            long startTime = System.currentTimeMillis();
            long start = System.nanoTime();
            Route<? extends City> route1 = routeService.getRoute("Saint-Petersburg", "Berlin");
            long end = System.nanoTime();
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println(route1 + " (" + endTime + ")");
            if (!routeService.isDevMode()) {
                assertTrue(endTime >= 2000);
            }
            long timeForSerial = end - start;


            startTime = System.currentTimeMillis();
            Route<? extends City> route2 = routeService.getRoute("Minsk", "Sverdlovsk");
            endTime = System.currentTimeMillis() - startTime;
            System.out.println(route2 + " (" + endTime + ")");
            if (!routeService.isDevMode()) {
                assertTrue(endTime >= 2000);
            }

            startTime = System.currentTimeMillis();
            start = System.nanoTime();
            Route<? extends City> deSerializedRoute1 = routeService.getRoute("Saint-Petersburg", "Berlin");
            end = System.nanoTime();
            endTime = System.currentTimeMillis() - startTime;
            System.out.println(deSerializedRoute1 + " (" + endTime + ")");
            assertTrue(endTime < 100);
            compareCities(deSerializedRoute1.getCities(), route1.getCities());
            long timeForDeSerial = end - start;

            startTime = System.currentTimeMillis();
            Route<? extends City> deSerializedRoute2 = routeService.getRoute("Minsk", "Sverdlovsk");
            endTime = System.currentTimeMillis() - startTime;
            System.out.println(deSerializedRoute2 + " (" + endTime + ")");
            assertTrue(endTime < 100);
            compareCities(deSerializedRoute2.getCities(), route2.getCities());

            /*
            Исследовательсякая часть. Записываем время действий и размер фалов.
             */
            try {
                fileWriter.append(routeService.getClass().getSimpleName() +
                        "\n time for serialize: " + timeForSerial + "; for deserialize:" + timeForDeSerial + "\n");
                long size;
                {
                    Field field = routeService.getClass().getSuperclass().getDeclaredField("pathProvider");
                    field.setAccessible(true);
                    CachePathProvider pathProvider = (CachePathProvider) field.get(routeService);
                    String path = pathProvider.getCacheDirectoryPath() + route1.getRouteName();
                    File file = new File(path);
                    size = file.length();
                }
                fileWriter.append("\n Size of file = " + size + "\n\n");
            } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        fileWriter.close();
    }

    private void compareCities(List<? extends City> cached, List<? extends City> unCached) {
        for (int i = 0; i < cached.size(); i++) {
            City city = unCached.get(i);
            City city1 = cached.get(i);
            boolean equals = city.compare(city1);
            System.out.println(city + (equals ? " == " : " != ") + city1);
            assertTrue(equals);
        }
    }
}

