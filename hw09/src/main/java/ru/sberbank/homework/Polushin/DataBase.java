package ru.sberbank.homework.Polushin;

import ru.sberbank.homework.Polushin.Exception.AccountIsLockedException;
import ru.sberbank.homework.Polushin.Exception.InvalidPinCodeException;
import ru.sberbank.homework.Polushin.Utils.Money;
import ru.sberbank.homework.Polushin.Utils.PinCode;


import java.util.Formatter;

/*
Мини база данных , содержащая три счета и информацию о них.
 */
public enum DataBase {
    FIRST("1234", new PinCode("1111"), new Money(0.0)),
    SECOND("9876", new PinCode("2222"), new Money(1000.0)),
    THIRD("0001", new PinCode("3333"), new Money(5555.44));

    /*
    Поля для каждой сущности (счета): максимальное число неверных попыток,
    время блокировки в милисекундах, время сброса неудачных попыток в милисекундах,
    номер счета, время начала блокировки, количество попыток ввода пина, пин-код, баланс, статус блокировки.
     */
    private static final int MAX_COUNTS = 3;
    private static final int LOCK = 5000;
    private static final int RESET = 60000;
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

    /*
    Сброс счетчика неудачных попыток по времени.
     */
    private void resetCounts() {
        if (System.currentTimeMillis() - this.lockedTime > RESET) {
            this.counts = 0;
        }
    }

    /*
    Счетчик попыток
     */
    private void setCounts() throws AccountIsLockedException {
        resetCounts();
        getAccount();
        this.counts++;
        this.lockedTime = System.currentTimeMillis();
        if (this.counts >= MAX_COUNTS) {
            this.locked = true;
            this.counts = 0;
            throw new AccountIsLockedException("You exceeded max counts of attempts. " +
                    "Accounts is locked now. Try again after: " +
                    new Formatter().format("%tS second(s)", LOCK - (System.currentTimeMillis() - this.lockedTime)));
        }
    }

    private void setPin(PinCode pin) {
        this.pin = pin;
    }

    /*
    Проверяем доступен ли аккаунт и возвращаем его, если доступен.
     */
    public String getAccount() throws AccountIsLockedException {
        if (this.locked == true && System.currentTimeMillis() - this.lockedTime < LOCK) {
            throw new AccountIsLockedException("You exceeded max counts of attempts. " +
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

    /*
    Проверка валидности пин-кода
     */
    public boolean isCorrect(PinCode pin) throws InvalidPinCodeException, AccountIsLockedException {
        setCounts();
        if (this.pin.equals(pin)) {
            return true;
        } else {
            throw new InvalidPinCodeException("Incorrect current pin. Please try again.");
        }

    }
}
