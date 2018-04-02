package ru.sberbank.homework.Polushin.Exception;

public class AccountIsLockedException extends PinValidatorException {
    public AccountIsLockedException(String message) {
        super(message);
    }
}