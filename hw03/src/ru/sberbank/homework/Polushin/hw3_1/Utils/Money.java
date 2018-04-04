package ru.sberbank.homework.Polushin.hw3_1.Utils;

import ru.sberbank.homework.Polushin.hw3_1.Exception.IllegalValueOfMoneyException;
import ru.sberbank.homework.Polushin.hw3_1.Exception.NotEnoughMoneyException;

import java.util.Locale;
import java.util.Objects;

public class Money {
    //Actual on Monday 19.03.2018
    private static final double RATE = 57.5521;
    private double value;
    
    
    public Money(double value) {
        this.value = value;
    }
    
    public static double getRATE() {
        return RATE;
    }
    
    public double getValue() {
        return value;
    }
    
    public Money add(Money amount) {
        if (amount.getValue() < 0) {
            throw new IllegalValueOfMoneyException("Illegal value for operation. Value must more then 0.");
        }
        this.value += amount.getValue();
        return this;
    }
    
    public Money sub(Money amount) {
        if (amount.getValue() < 0) {
            throw new IllegalValueOfMoneyException("Illegal value for operation. Value must more then 0");
        }
        this.value -= amount.getValue();
        return this;
    }
    
    public String convertToDollar() {
        return String.format(Locale.ROOT, "%.2f $", this.value / RATE);
    }
    
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%.2f Руб.", this.value);
    }
    
    public boolean isEnoughMoney(Money amount) throws NotEnoughMoneyException {
        if (this.value < amount.value) {
            throw new NotEnoughMoneyException("Sorry, you haven't enough money for this operation.");
        }
        return true;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(money.value, value) == 0;
    }
    
}