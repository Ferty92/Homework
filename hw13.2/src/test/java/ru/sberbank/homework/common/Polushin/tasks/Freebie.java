package ru.sberbank.homework.common.Polushin.tasks;

public class Freebie implements Runnable {
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        System.out.println("EASY BREEZY! - " + (System.currentTimeMillis() - start));
    }
}