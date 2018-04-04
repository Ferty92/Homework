package ru.sberbank.homework.Polushin.Utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.sberbank.homework.Polushin.Exception.IllegalValueOfMoneyException;

public class MoneyValidatorTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private MoneyValidator moneyValidator;
    
    @Before
    public void setUp() throws Exception {
        moneyValidator = new MoneyValidator();
    }
    
    @Test
    public void isCorrectValueForTerminal() {
        Assert.assertEquals(5000, moneyValidator.validate("5000"), 1E-5);
        Assert.assertEquals(100, moneyValidator.validate("0x1.9p6"), 1E-5);
    }
    
    @Test
    public void valueNotFrequency() {
        expectedException.expect(IllegalValueOfMoneyException.class);
        expectedException.expectMessage("Input don't frequency rate by 100");
        moneyValidator.validate("250");
    }
    
    @Test
    public void valueNotNumber() {
        expectedException.expect(IllegalValueOfMoneyException.class);
        expectedException.expectMessage("Input is not a number!");
        moneyValidator.validate("0x555");
    }
    
    @Test
    public void lessThenZero() {
        expectedException.expect(IllegalValueOfMoneyException.class);
        expectedException.expectMessage("Input value must more than 100");
        moneyValidator.validate("-200");
    }
    
}