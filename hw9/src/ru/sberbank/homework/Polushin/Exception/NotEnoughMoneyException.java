package ru.sberbank.homework.Polushin.Exception;

public class NotEnoughMoneyException extends IllegalValueOfMoneyException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}