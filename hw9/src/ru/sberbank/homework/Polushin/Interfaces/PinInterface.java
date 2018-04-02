package ru.sberbank.homework.Polushin.Interfaces;

import ru.sberbank.homework.Polushin.Exception.PinValidatorException;

public interface PinInterface {
    boolean inputPin(String pin) throws PinValidatorException;

    boolean changedPin(String oldPin, String newPin) throws PinValidatorException;
}
