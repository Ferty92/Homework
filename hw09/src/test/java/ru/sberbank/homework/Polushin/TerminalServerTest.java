package ru.sberbank.homework.Polushin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.sberbank.homework.Polushin.Exception.AccountsException;
import ru.sberbank.homework.Polushin.Exception.InvalidPinCodeException;
import ru.sberbank.homework.Polushin.Exception.NotEnoughMoneyException;
import ru.sberbank.homework.Polushin.Utils.Money;
import ru.sberbank.homework.Polushin.Utils.PinCode;

public class TerminalServerTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private TerminalServer server;
    
    @Before
    public void setUp() throws Exception {
        server = new TerminalServer();
    }
    
    @Test
    public void getExistsAccount() {
        Assert.assertEquals(DataBase.FIRST, server.getAccount("1234"));
    }
    
    @Test
    public void check() {
        Assert.assertEquals(new Money(5555.44), server.check(server.getAccount("0001")));
    }
    
    @Test
    public void correctGetAndPutMoney() {
        Assert.assertEquals(new Money(700), server.get(server.getAccount("9876"), new Money(300)));
        Assert.assertEquals(new Money(1000), server.put(server.getAccount("9876"), new Money(300)));
    }
    
    
    @Test
    public void correctChangePin() {
        Assert.assertTrue(server.change(DataBase.FIRST, new PinCode("1111"), new PinCode("can_set_any_other")));
        Assert.assertTrue(server.change(DataBase.FIRST, new PinCode("can_set_any_other"), new PinCode("1111")));
    }
    
    @Test
    public void incorrectCurrentPin() {
        expectedException.expect(InvalidPinCodeException.class);
        expectedException.expectMessage("Incorrect current pin");
        Assert.assertTrue(server.change(DataBase.FIRST, new PinCode("can_set_any_other"), new PinCode("1111")));
    }
    
    @Test
    public void getNotExistsAccount() {
        expectedException.expect(AccountsException.class);
        expectedException.expectMessage("Account not found");
        server.getAccount("12345");
    }
    
    @Test
    public void notEnoughMoney() {
        expectedException.expect(NotEnoughMoneyException.class);
        expectedException.expectMessage("haven't enough money");
        server.get(server.getAccount("9876"), new Money(1300));
    }
}