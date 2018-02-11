package ru.sberbank.homework.kashin.main.util;

import ru.sberbank.homework.kashin.main.exception.WrongExpression;
import ru.sberbank.homework.kashin.main.model.Expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculateHelper {
    private static final String binaryNumber = "^-?(0b)(0|1)+(l)?$";
    private static final String octalNumber = "^-?(0)([0-7])+(l)?$";
    private static final String hexNumber = "^-?(0x)([0-9]|[a-f])+(l)?$";
    private static final String correctLiteralRegExp = "^-?(0b|0x)?\\d+(.\\d+)?(l)?$";
    private static Double preResult;

    public static Double getPreResult() {
        return preResult;
    }

    public static void setPreResult(Double preResult) {
        CalculateHelper.preResult = preResult;
    }

    public static Double checkNotation(String number) {
        if (number.endsWith("l")) {
            number = number.substring(0, number.length() - 1);
        }
        if (checkWithRegExp(number, binaryNumber)) {
            number = String.valueOf(Integer.parseInt(number.substring(2, number.length()), 2));
        } else if (checkWithRegExp(number, octalNumber)) {
            number = String.valueOf(Integer.parseInt(number.substring(1, number.length()), 8));
        } else if (checkWithRegExp(number, hexNumber)) {
            number = String.valueOf(Integer.parseInt(number.substring(2, number.length()), 16));
        }
        return Double.valueOf(number);
    }

    public static Expression parser(String expString) {
        String[] elements = expString.split(" ");
        if (!checkWithRegExp(elements[0], correctLiteralRegExp)) {
            throw new WrongExpression(String.format("error > %s", elements[0]));
        }
        if (!checkWithRegExp(elements[2], correctLiteralRegExp)) {
            throw new WrongExpression(String.format("error > %s", elements[2]));
        }
        Expression expression = Factory.getExpression(elements[1].charAt(0));
        expression.setFirst(checkNotation(elements[0]));
        expression.setSecond(checkNotation(elements[2]));
        return expression;

    }

    public static boolean checkWithRegExp(String checkExp, String regExp){
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(checkExp);
        return m.matches();
    }

    public static boolean checkInteger(double checkNumber) {
        return (checkNumber == Math.floor(checkNumber)) && !Double.isInfinite(checkNumber);
    }
}
