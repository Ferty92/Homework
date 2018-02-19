import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CalculatorTest {
    static Calculator calc = new Calculator();
    private static DecimalFormat df4 = new DecimalFormat(".####"); //������ ������ ������
    public CalculatorTest(){
        //�� ��������� ����������� ������� ����� �������, � ����� �����
        DecimalFormatSymbols dfs= df4.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df4.setDecimalFormatSymbols(dfs);

    }


    void twoPlusTwoTest() {
        int sum=calc.sum(2, 2);

        Assert.equals("2+2 must be equals 4, not a " + sum, sum, 4);
    }

    //�������� ��������� �����
    void multiplyIntTest(double a, double b) {
        int first= (int) a;
        int second= (int) b;

        notValidArgumentTest(a, b, 3); //�������� �� ��������� �������� ������ �������
        overflowIntVariableTest(a, b); //������ �� ��� ����� � ���
        overflowTest(first,second,3); //������������� �� ��������� ��� ����� ���������

        int res = calc.multiply(first, second);
        Assert.equals(first + "*" + second + " must be equals" + first * second + ", not a " + res, res, first * second);
    }

    //�������� ��������� � ������
    void subDoubleTest(double a, double b, double actual){
        overflowTest(a,b,6); //�������� ������������ �����

        double res =  calc.sub(a,b);
        Assert.equals(a + "-" + b + " must be equals " + df4.format(actual) + ", not a " + df4.format(res), res, actual);
    }

    //������� � ������
    void divisionDoubleTest(double a, double b, double actual){
        divisionByZeroTest(a, b, 8); // �������� ������� �� ����
        double res=calc.div(a,b);
        Assert.equals(a + "/" + b + " must be equals " + df4.format(actual) + ", not a " + df4.format(res), res, actual);
    }

    void overflowTest( double a, double b, int operation){
        Assert.overflowOrDivisionByZero("Overflow. Result of operation not in a type of operation. ", a, b, operation);
    }

    void divisionByZeroTest(double a, double b, int operation){
        Assert.overflowOrDivisionByZero("Division by zero", a, b,operation);
    }

    void overflowIntVariableTest (double a ,double b){
        Assert.overflowIntVariable("Overflow Integer type by variable "+a, a);
        Assert.overflowIntVariable("Overflow Integer type by variable "+b, b);
    }

    void notValidArgumentTest (double a , double b, int operation){
        if (operation<=4){
            Assert.notValidArgument("Variable "+a+" not an integer", a);
            Assert.notValidArgument("Variable "+b+" not an integer", b);
        }

    }
    public void run(){
        //���� 2+2
        try{
            twoPlusTwoTest();
            System.out.println("Test 2+2 passed");
        }
        catch (AssertionError error){
            System.out.println(error.getMessage());
        }

        // �� �������� �����
        try {
            multiplyIntTest(3, 5);
            System.out.println("Test for int variables passed");
            multiplyIntTest(3, 5.5);
            System.out.println("Test for int variables passed");
        }
        catch (AssertionError error){
            System.out.println(error.getMessage());
        }

        //�� ������ � ���
        try {
            multiplyIntTest(30L, 5000000);
            System.out.println("Test for overflow int variables passed");
            multiplyIntTest(3000000000L, 5000000);
            System.out.println("Test for overflow int variables passed");
        }
        catch (AssertionError error){
            System.out.println(error.getMessage());
        }

        //������������ ���� ����� ����������
        try {
            multiplyIntTest(3000, 5000);
            System.out.println("Test for overflow result of int passed");
            multiplyIntTest(3000000, 5000000);
            System.out.println("Test for overflow result of int passed");
        }
        catch (AssertionError error){
            System.out.println(error.getMessage());
        }

        //������� �� ����
        try {
            divisionDoubleTest(4, 1.5, 2.667);
            System.out.println("Test for division doubles passed");
            divisionDoubleTest(4, 0, 4);
            System.out.println("Test for division doubles passed");
        }
        catch (AssertionError error) {
            System.out.println(error.getMessage());
        }

        //��������� � double
        try {
            subDoubleTest(4.6,2.6,2);
            System.out.println("Test for sub doubles passed");
            subDoubleTest(4.6455,2.6454,2.0001);
            System.out.println("Test for sub doubles passed");
        }
        catch (AssertionError error) {
            System.out.println(error.getMessage());
        }
    }
}