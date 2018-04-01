package ru.sberbank.homework.common.Polushin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.sberbank.homework.common.Polushin.tasks.Freebie;
import ru.sberbank.homework.common.Polushin.tasks.HardWork;
import ru.sberbank.homework.common.Polushin.tasks.HomeWork;


import static java.lang.System.out;
import static org.junit.Assert.*;

public class FixedThreadPoolTest {
    private final Integer COUNT = 40;
    private FixedThreadPool threadPool;
    private int numberOfTasks = 10000;
    
    @Before
    public void setUp() throws Exception {
        threadPool = new FixedThreadPool(COUNT, numberOfTasks * 6);
        
    }
    
    @Test
    public void work() throws Exception {
        for (int i = 0; i < numberOfTasks; i++) {
            threadPool.execute(new HardWork());
            threadPool.execute(new Freebie());
            threadPool.execute(new HomeWork());
        }
        threadPool.start();
        for (int i = 0; i < numberOfTasks; i++) {
            threadPool.execute(new HardWork());
            threadPool.execute(new Freebie());
            threadPool.execute(new HomeWork());
        }
    }
    
    @After
    public void tearDown() throws Exception {
        
        out.println("start ending.......");
        assertTrue(threadPool.endWorksDay().isEmpty());
        out.println("------------------- ВСе задачи выполнены ! ------------------------------ ");
        
    }
}