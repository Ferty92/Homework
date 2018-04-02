package ru.sberbank.homework.Polushin.Exception;

public class InvalidPinCodeException extends PinValidatorException {
    public InvalidPinCodeException(String message) {
        super(message);
    }
}