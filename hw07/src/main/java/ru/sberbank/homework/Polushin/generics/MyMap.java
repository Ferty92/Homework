package ru.sberbank.homework.Polushin.generics;

import java.util.Map;
import java.util.TreeMap;

public class MyMap<T> implements CountMap<T> {
    private Map<T, Integer> innerMap = new TreeMap<>();


    @Override
    public void add(T element) {
        innerMap.compute(element, (key, value) -> value == null ? 1 : value + 1);
    }

    @Override
    public int getCount(T element) {
        return innerMap.getOrDefault(element, 0);
    }

    @Override
    public int remove(T element) {
        int value = getCount(element);
        innerMap.compute(element, (key, val) -> null);
        return value;
    }

    @Override
    public int size() {
        return innerMap.size();
    }

    @Override
    public void addAll(CountMap source) {
        source.toMap().forEach((key, value) ->
                innerMap.compute((T) key, (k, v) ->
                        (v == null) ? 1 : (v + (int) value)));
    }

    @Override
    public Map toMap() {
        return new TreeMap<>(innerMap);

    }

    @Override
    public void toMap(Map destination) {
        destination.putAll(innerMap);
    }


    @Override
    public boolean isEmpty() {
        return innerMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object element) {
        return innerMap.containsKey(element);
    }

    @Override
    public boolean containsValue(Integer value) {
        return innerMap.containsValue(value);
    }

    @Override
    public String toString() {
        return innerMap.toString();
    }
}