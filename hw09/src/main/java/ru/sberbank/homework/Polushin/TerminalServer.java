package ru.sberbank.homework.Polushin;

import ru.sberbank.homework.Polushin.Exception.AccountIsLockedException;
import ru.sberbank.homework.Polushin.Exception.AccountsException;
import ru.sberbank.homework.Polushin.Exception.InvalidPinCodeException;
import ru.sberbank.homework.Polushin.Exception.NotEnoughMoneyException;
import ru.sberbank.homework.Polushin.Utils.Money;
import ru.sberbank.homework.Polushin.Utils.PinCode;

public class TerminalServer {

    public DataBase getAccount(String account) throws AccountsException, AccountIsLockedException {
        for (DataBase acc : DataBase.values()) {
            if (acc.getAccount().equals(account)) {
                return acc;
            }
        }
        throw new AccountsException("Account not found =(");
    }

    public Money check(DataBase user) {
        return user.getBalance();
    }

    public Money get(DataBase user, Money amount) throws NotEnoughMoneyException {
        user.getBalance().isEnoughMoney(amount);
        user.setBalance(user.getBalance().sub(amount));
        return user.getBalance();
    }

    public Money put(DataBase user, Money amount) {
        user.setBalance(user.getBalance().add(amount));
        return user.getBalance();
    }

    public boolean change(DataBase user, PinCode oldPin, PinCode newPin) throws InvalidPinCodeException {
        user.changePin(oldPin, newPin);
        return true;
    }


}