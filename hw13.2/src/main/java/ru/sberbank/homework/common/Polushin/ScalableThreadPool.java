package ru.sberbank.homework.common.Polushin;

import ru.sberbank.homework.common.ThreadPool;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ScalableThreadPool implements ThreadPool {
    private final Object monitor = new Object();
    private final Integer MIN_COUNT_THREADS;
    private final Integer MAX_COUNT_THREADS;
    private Integer curCount;
    private List<Thread> executors;
    private ArrayDeque<Runnable> queue = new ArrayDeque<>();
    private CountDownLatch latch;
    private Map<Integer, Boolean> allWorkers;
    private Thread controlThread;
    
    public ScalableThreadPool(Integer minCountThreads, Integer maxCountThreads, Integer numberOfTasks) {
        this.MIN_COUNT_THREADS = minCountThreads;
        this.MAX_COUNT_THREADS = maxCountThreads;
        this.curCount = minCountThreads;
        latch = new CountDownLatch(numberOfTasks);
        allWorkers = new HashMap<>(maxCountThreads);
        executors = new ArrayList<>(MAX_COUNT_THREADS);
        
        for (int i = 0; i < MAX_COUNT_THREADS; i++) {
            allWorkers.put(i, false);
        }
        
        for (int i = 0; i < this.MIN_COUNT_THREADS; i++) {
            allWorkers.put(i, true);
            executors.add(i, new Thread(new Executor(i, queue, monitor, latch)));
        }
        
        controlThread = new Thread(this::reOrganize);
    }
    
    public CountDownLatch getLatch() {
        return latch;
    }
    
    @Override
    public void start() {
        executors.forEach(Thread::start);
        controlThread.start();
    }
    
    @Override
    public void execute(Runnable runnable) {
        synchronized (monitor) {
            queue.offer(runnable);
            monitor.notifyAll();
        }
    }
    
    private void reOrganize() {
        while (true) {
            synchronized (monitor) {
                
                if (!queue.isEmpty() && curCount < MAX_COUNT_THREADS) {
                    int newThreads = Math.min(MAX_COUNT_THREADS - curCount, queue.size());
                    for (int i = 0; i < newThreads; i++) {
                        int numberOfExecutor = allWorkers.entrySet().stream()
                                .filter(element -> !element.getValue())
                                .findFirst()
                                .get()
                                .getKey();
                        
                        allWorkers.put(numberOfExecutor, true);
                        executors.add(numberOfExecutor, new Thread(new Executor(numberOfExecutor, queue, monitor, latch)));
                        curCount = ++curCount;
                        executors.get(numberOfExecutor).start();
                    }
                    
                } else if (queue.isEmpty() && curCount > MIN_COUNT_THREADS) {
                    
                    AtomicInteger numberOfExecutor = new AtomicInteger();
                    List<Thread> list = new ArrayList<>();
                    executors.stream()
                            .filter(element -> element.getState() == Thread.State.WAITING)
                            .limit(curCount - MIN_COUNT_THREADS)
                            .forEach(element -> {
                                numberOfExecutor.set(executors.indexOf(element));
                                element.interrupt();
                                allWorkers.put(numberOfExecutor.get(), false);
                                list.add(element);
                                curCount = --curCount;
                            });
                    executors.removeAll(list);
                }
                System.out.println("Current active thread is ---------------------" + curCount);
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) {
            }
        }
        
    }
    
    public Integer getCurCount() {
        return curCount;
    }
    
    public List<Thread> getExecutors() {
        return executors;
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