package ru.sberbank.homework.common.Polushin.tasks;

import static java.lang.Thread.sleep;

public class HomeWork implements Runnable {
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            System.out.println("Begin homework");
            sleep(5);
            System.out.println("End homework with time - " + (System.currentTimeMillis() - start));
        } catch (InterruptedException e) {
        }
        
    }
}