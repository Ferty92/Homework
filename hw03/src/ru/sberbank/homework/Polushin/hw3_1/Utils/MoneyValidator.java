package ru.sberbank.homework.Polushin.hw3_1.Utils;

import ru.sberbank.homework.Polushin.hw3_1.Exception.IllegalValueOfMoneyException;

public class MoneyValidator {
    public double validate(String input) throws IllegalValueOfMoneyException {
        double money;
        try {
            money = Double.parseDouble(input);
            if (money < 0) {
                throw new IllegalValueOfMoneyException("Input value must more than 100");
            }
            if (money % 100 != 0) {
                throw new IllegalValueOfMoneyException("Input don't frequency rate by 100");
            }
        } catch (NumberFormatException exception) {
            throw new IllegalValueOfMoneyException("Input is not a number!");
        }
        return money;
    }
}