package ru.sberbank.homework.Polushin.generics;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class CollectionUtilsTest {
    List<String> listForTest;

    @Before
    public void setUp() throws Exception {
        listForTest = new ArrayList<>(Arrays.asList("First", "Second", "Third"));
    }

    @Test
    public void addAll() {
        List<String> destination = new ArrayList<>(Arrays.asList("First", "Five"));
        CollectionUtils.addAll(listForTest, destination);
        assertArrayEquals(Arrays.asList("First", "Five", "First", "Second", "Third").toArray(), destination.toArray());
    }

    @Test
    public void newArrayList() {
        List<String> list = CollectionUtils.newArrayList();
        list.add(listForTest.get(0));
        assertArrayEquals(Arrays.asList("First").toArray(), list.toArray());
    }

    @Test
    public void indexOf() {
        assertEquals(2, CollectionUtils.indexOf(listForTest, "Third"));
    }

    @Test
    public void limit() {
        assertArrayEquals(Arrays.asList("First", "Second").toArray(), CollectionUtils.limit(listForTest, 2).toArray());
    }

    @Test
    public void add() {
        List<String> list = CollectionUtils.newArrayList();
        CollectionUtils.addAll(listForTest, list);
        CollectionUtils.add(list, "Fourth");
        CollectionUtils.add(list, "Five");
        assertEquals("Fourth", list.get(3));
        assertEquals(4, CollectionUtils.indexOf(list, "Five"));
    }

    @Test
    public void removeAll() {
        List<String> list = CollectionUtils.newArrayList();
        CollectionUtils.add(list, "Sixes");
        CollectionUtils.add(list, "Fourth");
        CollectionUtils.add(list, "Second");
        CollectionUtils.removeAll(list, listForTest);
        assertArrayEquals(Arrays.asList("Sixes", "Fourth").toArray(), list.toArray());
    }

    @Test
    public void containsAll() {
        List<String> list = CollectionUtils.newArrayList();
        CollectionUtils.addAll(Arrays.asList("First", "Second", "Third"), list);
        assertTrue(CollectionUtils.containsAll(list, listForTest));
        CollectionUtils.add(list, "String");
        assertTrue(CollectionUtils.containsAll(list, listForTest));
    }

    @Test
    public void containsAny() {
        List<String> list = CollectionUtils.newArrayList();
        CollectionUtils.add(list, "Sixes");
        CollectionUtils.add(list, "Fourth");
        CollectionUtils.add(list, "Second");
        assertTrue(CollectionUtils.containsAny(list, listForTest));
    }

    @Test
    public void range() {
        List<Integer> list = CollectionUtils.newArrayList();
        CollectionUtils.addAll(Arrays.asList(1, 5, 8, 2, 3), list);
        list = CollectionUtils.range(list, 2, 5);
        assertEquals(0, CollectionUtils.indexOf(list, 5));
    }

    @Test
    public void range1() {
        List<Integer> list = CollectionUtils.newArrayList();
        CollectionUtils.addAll(Arrays.asList(1, 5, 8, 2, 3), list);
        //Компарато наоборот.
        Comparator<Integer> comparator = (o1, o2) -> o2 - o1;
        list = CollectionUtils.range(list, 2, 5, comparator);
        assertTrue(list.isEmpty());
    }
}