package ru.sberbank.homework.Polushin.hw3_1.Exception;

public class InvalidPinCodeException extends PinValidatorException {
    public InvalidPinCodeException(String message) {
        super(message);
    }
}