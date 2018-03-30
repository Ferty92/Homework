package ru.sberbank.homework.Polushin;

import ru.sberbank.homework.common.tasks.CalculationTask;
import ru.sberbank.homework.common.tasks.SleepyTask;
import ru.sberbank.homework.common.tasks.StringsTask;


public class ParallelThreads {
    public static void main(String[] args) throws InterruptedException {
        long timeForNotParallel;
        long timeParallel;
        
        {
            long millis = System.currentTimeMillis();
            new StringsTask().run();
            new CalculationTask().run();
            new SleepyTask().run();
            timeForNotParallel = System.currentTimeMillis() - millis;
        }
        
        {
            long startTime = System.currentTimeMillis();
            Thread t1 = new Thread(new CalculationTask());
            Thread t2 = new Thread(new SleepyTask());
            Thread t3 = new Thread(new StringsTask());
            t1.start();
            t2.start();
            t3.start();
            t1.join();
            t2.join();
            t3.join();
            timeParallel = System.currentTimeMillis() - startTime;
        }
        
        System.out.println("Time without parallel: " + timeForNotParallel);
        System.out.println("Time parallel processing: " + timeParallel);
        System.out.println("All task completed");
    }
}
