package ru.sberbank.homework.common.Polushin;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class Executor implements Runnable {
    private Queue<Runnable> tasks;
    private Object monitor;
    private int number;
    private CountDownLatch latch;
    
    public Executor(Integer number, Queue<Runnable> tasks, Object monitor, CountDownLatch latch) {
        this.tasks = tasks;
        this.monitor = monitor;
        this.number = number;
        this.latch = latch;
    }
    
    @Override
    public void run() {
        Runnable job;
        while (true) {
            synchronized (monitor) {
                while (tasks.isEmpty()) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException ignore) {
                        return;
                    }
                }
                job = tasks.poll();
            }
            job.run();
            System.out.println("Executor " + number + " finished work");
            latch.countDown();
        }
    }
    
}
