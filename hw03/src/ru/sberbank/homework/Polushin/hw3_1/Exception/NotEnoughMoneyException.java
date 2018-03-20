package ru.sberbank.homework.Polushin.hw3_1.Exception;

public class NotEnoughMoneyException extends IllegalValueOfMoneyException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}