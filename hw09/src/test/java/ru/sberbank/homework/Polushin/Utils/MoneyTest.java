package ru.sberbank.homework.Polushin.Utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.sberbank.homework.Polushin.Exception.IllegalValueOfMoneyException;
import ru.sberbank.homework.Polushin.Exception.NotEnoughMoneyException;

public class MoneyTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    Money money;
    
    @Before
    public void setUp() throws Exception {
        money = new Money(0);
    }
    
    @Test
    public void twoHundredIsTwoHundred() {
        money.add(new Money(100));
        money.add(new Money(100));
        Assert.assertTrue(money.equals(new Money(200)));
    }
    
    @Test
    public void correctChangingMoney() {
        money.add(new Money(233.124));
        money.sub(new Money(133.12));
        Assert.assertEquals("100.00 Руб.", money.toString());
        money.add(new Money(100.002));
        Assert.assertEquals("200.01 Руб.", money.toString());
        money.sub(new Money(201));
        Assert.assertEquals("-0.99 Руб.", money.toString());
    }
    
    @Test
    public void convertToDollar() {
        money.add(new Money(Money.getRATE() * 2.06499999));
        Assert.assertEquals("2.06 $", money.convertToDollar());
    }
    
    @Test
    public void isEnoughMoney() {
        money.add(new Money(200));
        Assert.assertTrue(money.isEnoughMoney(new Money(100)));
    }
    
    @Test
    public void notEnoughMoney() {
        money.add(new Money(99.999999));
        expectedException.expect(NotEnoughMoneyException.class);
        expectedException.expectMessage("haven't enough money");
        money.isEnoughMoney(new Money(100));
    }
    
    @Test
    public void incorrectDecreaseMoney() {
        expectedException.expect(IllegalValueOfMoneyException.class);
        expectedException.expectMessage("Illegal value");
        money.sub(new Money(-201));
    }
    
    @Test
    public void incorrectIncreaseMoney() {
        expectedException.expect(IllegalValueOfMoneyException.class);
        expectedException.expectMessage("Illegal value");
        money.add(new Money(-0.01));
    }
}