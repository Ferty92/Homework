package ru.sberbank.homework.Polushin;


public class CalculatorTest {
    static Calculator calc = new Calculator();

    public CalculatorTest() {
    }


    private void twoPlusTwoTest() throws AssertionError {
        Exception ex = new Exception();
        System.out.println(ex.getStackTrace()[0].getMethodName() + ":");
        sumIntTest(2, 2, 4);
    }

    private void sumDoubleTest(double a, double b, double expected) throws AssertionError {
        Exception ex = new Exception();
        System.out.println(ex.getStackTrace()[0].getMethodName() + ":");
        double result = calc.sum(a, b);
        Assert.equals(String.format("The result is %f but must be %f", result, expected), expected, result);
    }

    private void sumIntTest(int a, int b, int expected) throws AssertionError {
        Exception ex = new Exception();
        System.out.println(ex.getStackTrace()[0].getMethodName() + ":");
        int result = calc.sum(a, b);
        Assert.equals(String.format("The result is %d but must be %d", result, expected), expected, result);
    }

    private void subDoubleTest(double a, double b, double expected) throws AssertionError {
        Exception ex = new Exception();
        System.out.println(ex.getStackTrace()[0].getMethodName() + ":");
        double result = calc.sub(a, b);
        Assert.equals(String.format("The result is %f but must be %f", result, expected), expected, result);
    }

    private void subIntTest(int a, int b, int expected) throws AssertionError {
        Exception ex = new Exception();
        System.out.println(ex.getStackTrace()[0].getMethodName() + ":");
        int result = calc.sub(a, b);
        Assert.equals(String.format("The result is %d but must be %d", result, expected), expected, result);
    }

    private void multiplyIntTest(int a, int b, int expected) throws AssertionError {
        Exception ex = new Exception();
        System.out.println(ex.getStackTrace()[0].getMethodName() + ":");
        Assert.isTrue("Overflow result for Integer", Integer.MAX_VALUE / Math.abs(a) > b);
        int result = calc.multiply(a, b);
        Assert.equals(String.format("The result is %d but must be %d", result, expected), expected, result);
    }

    private void multiplyDoubleTest(double a, double b, double expected) throws AssertionError {
        Exception ex = new Exception();
        System.out.println(ex.getStackTrace()[0].getMethodName() + ":");
        Assert.isTrue("Overflow result for Double", Double.MAX_VALUE / Math.abs(a) > b);
        double result = calc.multiply(a, b);
        Assert.equals(String.format("The result is %f but must be %f", result, expected), expected, result);
    }


    private void divisionDoubleTest(double a, double b, double expected) throws AssertionError {
        Exception ex = new Exception();
        System.out.println(ex.getStackTrace()[0].getMethodName() + ":");
        Assert.isTrue("Division by zero!", b != 0);
        double result = calc.div(a, b);
        Assert.equals(String.format("The result is %f but must be %f", result, expected), expected, result);
    }

    private void divisionIntTest(int a, int b, int expected) throws AssertionError {
        Exception ex = new Exception();
        System.out.println(ex.getStackTrace()[0].getMethodName() + ":");
        Assert.isTrue("Division by zero!", b != 0);
        int result = calc.div(a, b);
        Assert.equals(String.format("The result is %d but must be %d", result, expected), expected, result);
    }


    public void run() {
        try {
            twoPlusTwoTest();
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
        }

        try {
            multiplyIntTest(Integer.MAX_VALUE, 2, -2);
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
        }

        try {
            multiplyIntTest(3_000, 5_000_000, 2_115_098_112);
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
        }

        //Деление на 0
        try {
            divisionDoubleTest(4.5, calc.div(1, 2), 9);
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
        }

        try {
            divisionDoubleTest(4, 1.5, 3.0 - 0.333_333_333);
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
        }

        try {
            subDoubleTest(4.6, 2.6, 2);
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
        }

        try {
            subDoubleTest(4.64, 2.64, 2);
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
        }

        try {
            sumIntTest(Integer.MIN_VALUE, -1, Integer.MAX_VALUE);
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
        }

        //Деление на 0
        try {
            divisionIntTest(4, calc.div(4, 5), 0);
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
        }

        try {
            multiplyDoubleTest(2, 2.0, 4);
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
        }

        try {
            sumDoubleTest(1E-10, 1.999_999_999_9, 2);
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
        }
    }
}