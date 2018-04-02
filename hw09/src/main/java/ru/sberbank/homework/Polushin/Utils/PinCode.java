package ru.sberbank.homework.Polushin.Utils;

public class PinCode {
    private String pin = "0000";

    public PinCode(String pin) {
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public boolean equals(Object obj) {
        PinCode pinCode = (PinCode)obj;
        return this.getPin().equals(pinCode.getPin());
    }

    @Override
    public String toString() {
        return new String("****");
    }
}