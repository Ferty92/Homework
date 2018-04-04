package ru.sberbank.homework.Polushin;

import ru.sberbank.homework.Polushin.Exception.AccountIsLockedException;
import ru.sberbank.homework.Polushin.Exception.AccountsException;
import ru.sberbank.homework.Polushin.Exception.InvalidPinCodeException;
import ru.sberbank.homework.Polushin.Exception.NotEnoughMoneyException;
import ru.sberbank.homework.Polushin.Utils.Money;
import ru.sberbank.homework.Polushin.Utils.PinCode;

class TerminalServer {
    
    protected DataBase getAccount(String account) throws AccountsException, AccountIsLockedException {
        for (DataBase acc : DataBase.values()) {
            
            if (acc.account.equals(account)) {
                acc.getAccount();
                return acc;
            }
            
        }
        throw new AccountsException("Account not found =(");
    }
    
    protected Money check(DataBase user) {
        return user.getBalance();
    }
    
    protected Money get(DataBase user, Money amount) throws NotEnoughMoneyException {
        user.getBalance().isEnoughMoney(amount);
        user.setBalance(user.getBalance().sub(amount));
        return user.getBalance();
    }
    
    protected Money put(DataBase user, Money amount) {
        user.setBalance(user.getBalance().add(amount));
        return user.getBalance();
    }
    
    protected boolean change(DataBase user, PinCode oldPin, PinCode newPin) throws InvalidPinCodeException {
        user.changePin(oldPin, newPin);
        return true;
    }
    
    
}