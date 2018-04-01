package ru.sberbank.homework.common.Polushin;

import ru.sberbank.homework.common.ThreadPool;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class FixedThreadPool implements ThreadPool {
    private final Object monitor = new Object();
    private Integer countThreads;
    private List<Thread> executors;
    private ArrayDeque<Runnable> queue = new ArrayDeque<>();
    private CountDownLatch latch;
    
    
    public FixedThreadPool(Integer countThread, Integer numberOfTasks) {
        this.countThreads = countThread;
        latch = new CountDownLatch(numberOfTasks);
        executors = new ArrayList<>(countThreads);
        
        for (int i = 0; i < countThreads; i++) {
            executors.add(new Thread(new Executor(i, queue, monitor, latch)));
        }
        
    }
    
    @Override
    public void start() {
        executors.forEach(Thread::start);
        
    }
    
    @Override
    public void execute(Runnable runnable) {
        synchronized (monitor) {
            queue.offer(runnable);
            monitor.notifyAll();
        }
    }
    
    public Queue<Runnable> endWorksDay() {
        try {
            latch.await();
        } catch (InterruptedException ignore) {
            // handle
        }
        executors.forEach(Thread::interrupt);
        return queue;
    }
    
}