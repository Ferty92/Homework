package ru.sberbank.homework.common.Polushin.tasks;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class HardWork implements Runnable {
    @Override
    public void run() {
        for (int i=0;i<5;i++){
            int time = ThreadLocalRandom.current().nextInt(1, 3);
            try {
                sleep(time);
                System.out.println("hard hard hard - " + time);
            } catch (InterruptedException e) {
            }
        }
    }
}