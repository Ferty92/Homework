package ru.sberbank.homework.Polushin.task_02;

import org.junit.Before;
import org.junit.Test;
import ru.sberbank.homework.Polushin.task_02.resources.First;
import ru.sberbank.homework.Polushin.task_02.resources.Second;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ReflectionFieldCopierTest {
    private First first;
    private Second second;
    private ReflectionFieldCopier copier;
    
    @Before
    public void setUp() throws Exception {
        copier = new ReflectionFieldCopier();
        Set<String> stringSet = new HashSet<>(Arrays.asList("One", "ONE", "one"));
        Set<Integer> integerSet = new HashSet<>(Arrays.asList(2, 22, 222));
        first = new First(1, 100, 0.1, "hey first", Arrays.asList(1, 2, 3), stringSet);
        second = new Second(2, 200L, 0.2, "ho second", integerSet);
    }
    
    @Test
    public void copyFirstSecond() {
        copier.copy(first, second);
        System.out.println(first);
        System.out.println(second);
        assertEquals(first.getI(), second.getI());
        assertEquals(first.getString(), second.getString());
        assertEquals(first.getD(), second.getD(), 1E-10);
        //Можем передать инт в лонг
        assertEquals(first.getL(), second.getL());
    }
    
    @Test
    public void copySecondFirst() {
        copier.copy(second, first);
        System.out.println(first);
        System.out.println(second);
        assertEquals(first.getI(), second.getI());
        assertEquals(first.getString(), second.getString());
        assertEquals(first.getD(), second.getD(), 1E-12);
        //не можем лонг передать в инт
        assertNotEquals(first.getL(), second.getL());
    }
}