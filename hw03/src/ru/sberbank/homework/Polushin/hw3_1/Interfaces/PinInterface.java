package ru.sberbank.homework.Polushin.hw3_1.Interfaces;

import ru.sberbank.homework.Polushin.hw3_1.Exception.PinValidatorException;
import ru.sberbank.homework.Polushin.hw3_1.Utils.PinCode;

public interface PinInterface {
    boolean inputPin(String pin) throws PinValidatorException;

    boolean changedPin(String oldPin, String newPin) throws PinValidatorException;
}
