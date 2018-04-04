package ru.sberbank.homework.Polushin.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.sberbank.homework.Polushin.Exception.PinValidatorException;

import static org.junit.Assert.assertEquals;

public class PinValidatorTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private PinValidator pinValidator;
    
    @Before
    public void setUp() throws Exception {
        pinValidator = new PinValidator();
    }
    
    @Test
    public void checkCompatiblePin() {
        assertEquals(new PinCode("1234"), pinValidator.validate("1234"));
    }
    
    @Test
    public void containsNotOnlyDigits() {
        expectedException.expect(PinValidatorException.class);
        expectedException.expectMessage("Input isn't a pin.");
        pinValidator.validate("hdskdh2212");
    }
    
    @Test
    public void lessOrMoreThen4Digits() {
        expectedException.expect(PinValidatorException.class);
        expectedException.expectMessage("Pin must contains only 4 digits from range 0-9");
        pinValidator.validate("234");
    }
}