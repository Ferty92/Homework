package ru.sberbank.homework.Polushin.Utils;

import ru.sberbank.homework.Polushin.Exception.PinValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinValidator {
    public static PinCode validate(String input) throws PinValidatorException {
        Pattern pattern = Pattern.compile("^[0-9]{4}$");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            throw new PinValidatorException("Input isn't a pin. Please try again." +
                    " Pin must contains only 4 digits from range 0-9 ");
        }

        return new PinCode(input);
    }
}