package ru.sberbank.homework.Polushin.generics;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MyMapTest {
    CountMap<Integer> mapForTest;

    @Before
    public void setUp() throws Exception {
        mapForTest = new MyMap<>();
        mapForTest.add(1);
        mapForTest.add(2);
        mapForTest.add(2);
        mapForTest.add(3);
        mapForTest.add(3);
        mapForTest.add(3);

    }

    @Test
    public void add() {
        Map<Integer, Integer> mapFromMapForTest = mapForTest.toMap();
        CountMap<Integer> test = new MyMap<>(mapFromMapForTest);
        test.add(1);
        assertTrue(test.getCount(1) == 2);
    }

    @Test
    public void getCount() {
        assertTrue(mapForTest.getCount(1) == 1);
    }

    @Test
    public void remove() {
        Map<Integer, Integer> mapFromMapForTest = mapForTest.toMap();
        CountMap<Integer> test = new MyMap<>(mapFromMapForTest);
        assertTrue(test.remove(2) == 2);
        assertTrue(!test.containsKey(2));
    }

    @Test
    public void size() {
        assertEquals(3, mapForTest.size());
    }

    @Test
    public void addAll() {
        Map<Integer, Integer> mapFromMapForTest = mapForTest.toMap();
        CountMap<Integer> test = new MyMap<>(mapFromMapForTest);
        test.addAll(mapForTest);
        assertEquals(6, test.getCount(3));
        assertEquals(0, test.getCount(99));
    }


    @Test
    public void toMap1() {
        Map<Integer, Number> test = new HashMap<>();
        mapForTest.toMap(test);
        assertTrue(mapForTest.getCount(3) == (Integer) test.get(3));
        test.put(3, 4);
        assertTrue(mapForTest.getCount(3) != (Integer) test.get(3));
    }

    @Test
    public void isEmpty() {
        assertTrue(!mapForTest.isEmpty());
    }

    @Test
    public void containsKey() {
        assertTrue(mapForTest.containsKey(2));
        assertTrue(!mapForTest.containsKey(4));

    }

    @Test
    public void containsValue() {
        assertTrue(mapForTest.containsValue(3));
        assertTrue(!mapForTest.containsValue(0));
    }

}