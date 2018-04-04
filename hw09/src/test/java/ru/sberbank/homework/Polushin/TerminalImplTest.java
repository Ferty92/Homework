package ru.sberbank.homework.Polushin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.sberbank.homework.Polushin.Exception.IllegalValueOfMoneyException;
import ru.sberbank.homework.Polushin.Exception.PinValidatorException;
import ru.sberbank.homework.Polushin.Utils.Money;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class TerminalImplTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private TerminalImpl terminal;
    private InputStream inputStream;
    private ByteArrayOutputStream outputStream;
    
    @Before
    public void setUp() throws Exception {
        inputStream = System.in;
        outputStream = new ByteArrayOutputStream();
        terminal = new TerminalImpl(outputStream, inputStream);
        terminal.setAccount("0001");
    }
    
    @Test
    public void canGetBalance() {
        Assert.assertEquals(new Money(5555.44), terminal.getStatementOfAccount());
    }
    
    @Test
    public void correctDepositAndWithdraw() {
        Assert.assertEquals(new Money(6555.44), terminal.deposit("1000"));
        Assert.assertEquals(new Money(5555.44), terminal.withdraw("1000"));
    }
    
    @Test
    public void changedPin() {
        Assert.assertTrue(terminal.changedPin("3333", "3456"));
        Assert.assertTrue(terminal.changedPin("3456", "3333"));
    }
    
    @Test
    public void correctInputPin() {
        Assert.assertTrue(terminal.inputPin("3333"));
    }
    
    @Test
    public void inputAccountCorrectPinGetBalanceThenExit() {
        for (int i = 0; i < 20; i++) {
            outputStream.reset();
            inputStream = new ByteArrayInputStream("0001\n3333\n3\nquit\nexit\n".getBytes());
            terminal = new TerminalImpl(outputStream, inputStream);
            terminal.run();
            String output = outputStream.toString();
            Assert.assertTrue(output.contains("5555.44 Руб.") || output.contains("Server connection failed"));
        }
    }
    
    @Test
    public void inputAccountCorrectPinGetMoneyThenExit() {
        for (int i = 0; i < 100; i++) {
            outputStream.reset();
            inputStream = new ByteArrayInputStream("0001\n3333\n2\n1000\nquit\nexit\n".getBytes());
            terminal = new TerminalImpl(outputStream, inputStream);
            terminal.run();
            String output = outputStream.toString();
            Assert.assertTrue(output.contains("Success") || output.contains("Server connection failed"));
            
            if (output.contains("Success")) {
                Assert.assertEquals(DataBase.THIRD.getBalance(), new Money(4555.44));
                DataBase.THIRD.setBalance(new Money(5555.44));
            }
        }
    }
    
    @Test
    public void incorrectDeposit() {
        expectedException.expect(IllegalValueOfMoneyException.class);
        expectedException.expectMessage("Input value must more than 100");
        terminal.deposit("-1000");
    }
    
    @Test
    public void incorrectWithdraw() {
        expectedException.expect(IllegalValueOfMoneyException.class);
        terminal.deposit("55");
    }
    
    @Test
    public void notValidInputPin() {
        expectedException.expect(PinValidatorException.class);
        terminal.inputPin("333");
    }
    
    @Test
    public void failedChangingPin() {
        expectedException.expect(PinValidatorException.class);
        terminal.changedPin("3333", "asdf");
    }
    
    @Test
    public void incorrectPinAndLockedAccount() throws InterruptedException {
        String output = "";
        outputStream.reset();
        while (!output.contains("You exceeded max counts of attempts")) {
            inputStream = new ByteArrayInputStream("9876\n3322\ncancel\nexit".getBytes());
            terminal = new TerminalImpl(outputStream, inputStream);
            terminal.run();
            output = outputStream.toString();
            Assert.assertTrue(output.contains("Incorrect current pin") || output.contains("Server connection failed"));
            outputStream.reset();
        }
            inputStream = new ByteArrayInputStream("9876\nexit\n".getBytes());
            terminal = new TerminalImpl(outputStream, inputStream);
            terminal.run();
            output = outputStream.toString();
            Assert.assertTrue(output.contains("Accounts is locked") || output.contains("Server connection failed"));
            Thread.sleep(5500);
        
    }
}