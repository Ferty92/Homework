package ru.sberbank.homework.Polushin.generics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Параметризовать методы, используя правило PECS, и реализовать их.
 */
public class CollectionUtils {
    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> int indexOf(List<? extends T> source, T o) {
        return source.indexOf(o);
    }

    public static <T> List<? super T> limit(List<? extends T> source, int size) {
        List<? super T> retList = newArrayList();
        for (int i = 0; i < size; i++) {
            retList.add(source.get(i));
        }
        return retList;
    }

    public static <T> void add(List<? super T> source, T o) {
        source.add(o);
    }

    public static <T> void removeAll(List<? super T> removeFrom, List<? extends T> c2) {
        removeFrom.removeAll(c2);
    }

    public static <T> boolean containsAll(List<? extends T> c1, List<? extends T> c2) {
        return c1.containsAll(c2);
    }

    //true если первый лист содержит хотя-бы 1 второго
    public static <T> boolean containsAny(List<? extends T> c1, List<? extends T> c2) {
        return c2.stream().anyMatch(c1::contains);

    }

    // Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
    // Элементы сравнивать через Comparable.
    // Пример range(Arrays.asList(8,1,3,5,6, 4), 3, 6) вернет {3,4,5,6}
    public static <T> List<T> range(List<? extends T> list, Comparable<T> min, Comparable<T> max) {
        List<T> retList = newArrayList();
        list.forEach(element -> {
            if (min.compareTo(element)<=0 && max.compareTo(element)>=0) {
                retList.add(element);
            }
        });
        return retList;
    }

    public static <T> List<T> range(List<? extends T> list, T min, T max, Comparator<T> comparator) {
        List<T> retList = newArrayList();
        list.forEach(element -> {
            if (comparator.compare(element, min) >= 0 && comparator.compare(element, max) <= 0) {
                retList.add(element);
            }
        });
        return retList;
    }

}
