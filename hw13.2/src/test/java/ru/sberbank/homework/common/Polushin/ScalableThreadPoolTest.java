package ru.sberbank.homework.common.Polushin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.sberbank.homework.common.Polushin.tasks.HomeWork;

import java.lang.reflect.Field;
import java.util.Objects;

import static java.lang.System.out;
import static org.junit.Assert.*;

public class ScalableThreadPoolTest {
    private final Integer MIN_COUNT = 1;
    private final Integer MAX_COUNT = 4;
    private final Integer NUMBER_OF_TASKS = 4000;
    private ScalableThreadPool threadPool;
    
    @Before
    public void setUp() throws Exception {
        threadPool = new ScalableThreadPool(MIN_COUNT, MAX_COUNT, NUMBER_OF_TASKS);
    }
    
    @Test
    public void work() throws InterruptedException {
        for (int i = 0; i < NUMBER_OF_TASKS / 2; i++) {
            threadPool.execute(new HomeWork());
        }
        threadPool.start();
        Thread.sleep(150);
        assertEquals(MAX_COUNT, threadPool.getCurCount());
        
        while (threadPool.getLatch().getCount() > NUMBER_OF_TASKS / 2) {
        
        }
        Thread.sleep(1000);
        threadPool.execute(new HomeWork());
        threadPool.execute(new HomeWork());
        assertEquals(MIN_COUNT, threadPool.getCurCount());
        
        for (int i = 0; i < NUMBER_OF_TASKS / 2 - 2; i++) {
            threadPool.execute(new HomeWork());
        }
        threadPool.getLatch().await();
        Thread.sleep(1000);
        
        assertEquals(1, threadPool.getExecutors().stream()
                .filter(Objects::nonNull)
                .count());
        
    }
    
    @After
    public void tearDown() throws Exception {
        out.println("start ending.......");
        assertTrue(threadPool.endWorksDay().isEmpty());
        out.println("------------------- ВСе задачи выполнены ! ------------------------------ ");
    }
}