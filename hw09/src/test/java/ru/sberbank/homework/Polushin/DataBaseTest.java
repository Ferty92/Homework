package ru.sberbank.homework.Polushin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.sberbank.homework.Polushin.Exception.AccountIsLockedException;
import ru.sberbank.homework.Polushin.Exception.InvalidPinCodeException;
import ru.sberbank.homework.Polushin.Utils.Money;
import ru.sberbank.homework.Polushin.Utils.PinCode;

public class DataBaseTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private DataBase account;
    
    @Before
    public void setUp() throws Exception {
        account = DataBase.FIRST;
    }
    
    @Test
    public void correctChangingBalance() {
        account.setBalance(new Money(-200));
        Assert.assertTrue(new Money(-200).equals(account.getBalance()));
        account.setBalance(new Money(0));
    }
    
    @Test
    public void canGetAccount() {
        Assert.assertEquals("1234", account.getAccount());
        Assert.assertTrue(account.isCorrect(new PinCode("1111")));
    }
    
    @Test
    public void correctChangePin() {
        Assert.assertTrue(account.changePin(new PinCode("1111"), new PinCode("can_set_any_other")));
        Assert.assertTrue(account.changePin(new PinCode("can_set_any_other"), new PinCode("1111")));
    }
    
    @Test
    public void notAccessCausedIncorrectPin() {
        expectedException.expect(InvalidPinCodeException.class);
        expectedException.expectMessage("Incorrect current pin");
        account.isCorrect(new PinCode("1121"));
    }
    
    @Test
    public void incorrectCurrentPin() {
        expectedException.expect(InvalidPinCodeException.class);
        expectedException.expectMessage("input correct current pin and try again");
        account.changePin(new PinCode("2222"), new PinCode("0000"));
    }
    
    @Test
    public void lockedAccess() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            try {
                account.isCorrect(new PinCode("1121"));
            } catch (Exception ignore) {
            }
        }
        expectedException.expect(AccountIsLockedException.class);
        expectedException.expectMessage("Accounts is locked");
        try {
            account.getAccount();
        } finally {
            Thread.sleep(5200);
        }
    }
}