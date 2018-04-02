package ru.sberbank.homework.Polushin.Utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.sberbank.homework.Polushin.Exception.PinValidatorException;

import static org.junit.Assert.assertEquals;

public class PinValidatorTest {
    @Test
    public void checkCompatiblePin() {
        assertEquals(new PinCode("1234"), PinValidator.validate("1234"));
    }
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void checkIncompatiblePin() {
        expectedException.expect(PinValidatorException.class);
        expectedException.expectMessage("Input isn't a pin.");
        PinValidator.validate("hdskdh2212");
        expectedException.expectMessage("Pin must contains only 4 digits from range 0-9");
        PinValidator.validate("234");
        
    }
}