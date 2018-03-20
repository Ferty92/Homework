package ru.sberbank.homework.Polushin.hw3_1;

import ru.sberbank.homework.Polushin.hw3_1.Exception.AccountIsLockedException;
import ru.sberbank.homework.Polushin.hw3_1.Exception.InvalidPinCodeException;
import ru.sberbank.homework.Polushin.hw3_1.Utils.Money;
import ru.sberbank.homework.Polushin.hw3_1.Utils.PinCode;


import java.util.Formatter;


public enum DataBase {
    FIRST("1234", new PinCode("1111"), new Money(0.0)),
    SECOND("9876", new PinCode("2222"), new Money(1000.0)),
    THIRD("0001", new PinCode("3333"), new Money(5555.44));

    private static final int MAX_COUNTS = 3;
    //TODO Changed to 5000!!!
    private static final int LOCK = 10000;
    public final String account;
    public long lockedTime;
    private int counts = 0;
    private PinCode pin;
    private Money balance;
    private boolean locked = false;

    DataBase(String account, PinCode pinCode, Money balance) {
        this.balance = balance;
        this.account = account;
        this.pin = pinCode;
        this.locked = false;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    private void setCounts() throws AccountIsLockedException {
        this.counts++;
        if (this.counts >= MAX_COUNTS) {
            this.locked = true;
            this.counts = 0;
            this.lockedTime = System.currentTimeMillis();
            throw new AccountIsLockedException("You exceed max counts of attempts. " +
                    "Accounts is locked now. Try again after: " +
                    new Formatter().format("%tS second(s)", LOCK - (System.currentTimeMillis() - this.lockedTime)));
        }
    }

    private void setPin(PinCode pin) {
        this.pin = pin;
    }

    public String getAccount() throws AccountIsLockedException {
        if (this.locked == true && System.currentTimeMillis() - this.lockedTime < LOCK) {
            throw new AccountIsLockedException("You exceed max counts of attempts. " +
                    "Accounts is locked now. Try again after: " +
                    new Formatter().format("%tS second(s)", LOCK - (System.currentTimeMillis() - this.lockedTime)));
        }
        this.locked = false;
        return account;
    }

    public boolean changePin(PinCode oldPin, PinCode newPin) throws InvalidPinCodeException {
        if (this.pin.equals(oldPin)) {
            this.setPin(newPin);
        } else {
            throw new InvalidPinCodeException("Incorrect current pin. Please input correct current pin and try again.");
        }
        return true;
    }

    public boolean isCorrect(PinCode pin) throws InvalidPinCodeException, AccountIsLockedException {
        if (this.pin.equals(pin)) {
            return true;
        } else {
            getAccount();
            setCounts();
            throw new InvalidPinCodeException("Incorrect current pin. Please try again.");
        }

    }
}
