package ru.sberbank.homework.Polushin.generics;

import java.util.Map;

/**
 * Дополнить. Параметризовать. Создать класс, реализующий интерфейс.
 */
public interface CountMap<T> {
    /**
     * добавляет элемент в этот контейнер.
     */
    void add(T element);

    /**
     * Возвращает количество добавлений данного элемента
     */
    int getCount(T element);

    /**
     * Удаляет элемент и контейнера и возвращает количество его добавлений(до удаления)
     */
    int remove(T element);

    /**
     * количество разных элементов
     */
    int size();

    /**
     * Добавить все элементы из source в текущий контейнер, при совпадении ключей, суммировать значения
     */
    void addAll(CountMap<? extends T> source);

    /**
     * Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений
     */
    Map<? super T, Integer> toMap();

    /**
     * Тот же самый контракт как и toMap(), только всю информацию записать в destination
     */
    void toMap(Map<? super T, Integer> destination);

    /**
     * Проверяет есть ли элемент в коллекции
     */
    boolean containsKey(T element);

    /**
     * Проверяет есть ли какой то элемент, который добавлялся value раз в коллекцию
     */
    boolean containsValue(Integer value);

    /**
     * Пусто или нет в коллекции
     */
    boolean isEmpty();
}
