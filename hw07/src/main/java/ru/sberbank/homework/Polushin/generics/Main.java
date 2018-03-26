package ru.sberbank.homework.Polushin.generics;


public class Main {
    public static void main(String[] args) {
        CountMap<String> firstMap = new MyMap<>();
        firstMap.add("First");
        firstMap.add("Second");
        firstMap.add("Third");
        firstMap.add("First");
        CountMap<String> secondMap = new MyMap<>();
        secondMap.add("First");
        secondMap.add("Second");
        secondMap.add("Third");
        secondMap.add("First");
        secondMap.add("Fourth");
        System.out.println(secondMap.size());
        System.out.println(firstMap.size());
        secondMap.addAll(firstMap);
        System.out.println(secondMap);
        firstMap.remove("First");
        System.out.println(firstMap.containsValue(2));
        System.out.println(firstMap.containsKey("First"));
        firstMap.remove("Second");
        firstMap.remove("Third");
        System.out.println(firstMap.isEmpty());
    }
}