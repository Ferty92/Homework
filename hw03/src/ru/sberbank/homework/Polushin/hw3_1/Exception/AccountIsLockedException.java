package ru.sberbank.homework.Polushin.hw3_1.Exception;

public class AccountIsLockedException extends PinValidatorException {
    public AccountIsLockedException(String message) {
        super(message);
    }
}