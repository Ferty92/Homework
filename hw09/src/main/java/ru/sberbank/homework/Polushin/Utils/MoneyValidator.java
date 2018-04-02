package ru.sberbank.homework.Polushin.Utils;

import ru.sberbank.homework.Polushin.Exception.IllegalValueOfMoneyException;

public class MoneyValidator {
    public static double validate(String input) throws IllegalValueOfMoneyException {
        double money;
        try {
            money = Double.parseDouble(input);
            if (money % 100 != 0) {
                throw new IllegalValueOfMoneyException("Input don't frequency rate by 100");
            }
        } catch (NumberFormatException exception) {
            throw new IllegalValueOfMoneyException("Input is not a number!");
        }
        return money;
    }
}