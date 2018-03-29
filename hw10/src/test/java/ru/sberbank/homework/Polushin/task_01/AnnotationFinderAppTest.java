package ru.sberbank.homework.Polushin.task_01;

import org.junit.Test;
import ru.sberbank.homework.common.annotation.ExperimentalFeature;
import ru.sberbank.homework.common.annotation.Prototype;
import ru.sberbank.homework.common.entity.Car;
import ru.sberbank.homework.common.entity.TeslaModel3;
import ru.sberbank.homework.common.entity.TeslaModelHydro;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class AnnotationFinderAppTest {
    
    
    @Test
    public void getFieldAndMethodsByAnnotation() {
        Set<String> actual = AnnotationFinderApp.getFieldAndMethodsByAnnotation(
                TeslaModelHydro.class, ExperimentalFeature.class, ExperimentalFeature.class);
        Set<String> expected = new HashSet<>();
        expected.add("fuelWithWater");
        expected.add("codename");
        assertEquals(expected, actual);
    }
    
    @Test
    public void getClassesAndValueByAnnotation() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<Class, Integer> expected = new HashMap<>();
        expected.put(TeslaModelHydro.class, 9001);
        Map<Class, Integer> actual = AnnotationFinderApp.getClassesAndValueByAnnotation(Arrays.asList(
                Car.class, TeslaModel3.class, TeslaModelHydro.class), Prototype.class, "version");
        assertEquals(expected,actual);
    }
    
   
}