package ru.sberbank.homework.Polushin.Utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PinCodeTest {
    PinCode myPin;
    
    @Before
    public void setUp() throws Exception {
        myPin = new PinCode();
    }
    
    @Test
    public void correctWorkPinMethod() {
        Assert.assertEquals("0000", myPin.getPin());
        myPin.setPin("avc");
        Assert.assertTrue(myPin.getPin().equals(new PinCode("avc").getPin()));
        Assert.assertEquals(4, myPin.toString().length());
    }
    
}