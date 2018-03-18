package ru.sberbank.homework.Polushin;


public class Assert {
    private static final double EPSILON = 0.1e-18;  //Точность

    public static void equals(String message, int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Failed: " + message);
        } else {
            System.out.println("Passed. Result is " + actual);
        }
    }

    public static void equals(String message, double expected, double actual) {
        if (Math.abs(expected - actual) > EPSILON) {
            throw new AssertionError("Failed: " + message);
        } else {
            System.out.println("Passed. Result is " + actual);
        }
    }

    public static void isTrue(String message, boolean condition) {
        if (!condition) {
            throw new AssertionError("Failed: " + message);
        } else {
            System.out.println("Passed " + message);
        }
    }


}