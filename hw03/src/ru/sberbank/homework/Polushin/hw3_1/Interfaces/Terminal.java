package ru.sberbank.homework.Polushin.hw3_1.Interfaces;


import ru.sberbank.homework.Polushin.hw3_1.Exception.IllegalValueOfMoneyException;
import ru.sberbank.homework.Polushin.hw3_1.Utils.Money;

public interface Terminal {

    Money getStatementOfAccount();

    Money deposit(String amount) throws IllegalValueOfMoneyException;

    Money withdraw(String amount) throws IllegalValueOfMoneyException;
}
