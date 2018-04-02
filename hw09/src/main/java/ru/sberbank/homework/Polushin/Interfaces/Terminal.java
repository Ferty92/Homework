package ru.sberbank.homework.Polushin.Interfaces;


import ru.sberbank.homework.Polushin.Exception.IllegalValueOfMoneyException;
import ru.sberbank.homework.Polushin.Utils.Money;

public interface Terminal {

    Money getStatementOfAccount();

    Money deposit(String amount) throws IllegalValueOfMoneyException;

    Money withdraw(String amount) throws IllegalValueOfMoneyException;
}
