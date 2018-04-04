package ru.sberbank.homework.Polushin.hw3_1;

import ru.sberbank.homework.Polushin.hw3_1.Exception.*;
import ru.sberbank.homework.Polushin.hw3_1.Interfaces.PinInterface;
import ru.sberbank.homework.Polushin.hw3_1.Interfaces.Terminal;
import ru.sberbank.homework.Polushin.hw3_1.Utils.*;

import java.io.*;
import java.util.Scanner;

class TerminalImpl implements Terminal, PinInterface, Runnable {
    
    private final TerminalServer server = new TerminalServer();
    private final PinValidator pinValidator = new PinValidator();
    private final MoneyValidator moneyValidator = new MoneyValidator();
    private DataBase user;
    private PrintStream printStream;
    private Scanner scanner;
    
    TerminalImpl(OutputStream outputStream, InputStream inputStream) {
        this.printStream = new PrintStream(outputStream);
        this.scanner = new Scanner(inputStream);
    }
    
    /*
    Метод проверяющий состояния подключения к серверу.
     */
    private void connect() throws ServerConnectionException {
        if (System.currentTimeMillis() % 4 == 0) {
            throw new ServerConnectionException("Server connection failed. Try again.");
        }
    }
    
    /*
    Вводим индификатор аккаунта пользователя
     */
    public void setAccount(String account) throws AccountsException, AccountIsLockedException {
        this.user = server.getAccount(account);
    }
    
    @Override
    public Money getStatementOfAccount() {
        return server.check(this.user);
    }
    
    @Override
    public Money deposit(String amount) throws IllegalValueOfMoneyException {
        Money money = new Money(moneyValidator.validate(amount));
        server.put(this.user, money);
        return getStatementOfAccount();
    }
    
    @Override
    public Money withdraw(String amount) throws IllegalValueOfMoneyException {
        Money money = new Money(moneyValidator.validate(amount));
        server.get(this.user, money);
        return getStatementOfAccount();
    }
    
    @Override
    public boolean changedPin(String oldPin, String newPin) throws PinValidatorException {
        pinValidator.validate(oldPin);
        PinCode oldPinCode = new PinCode(oldPin);
        pinValidator.validate(newPin);
        PinCode newPinCode = new PinCode(newPin);
        server.change(this.user, oldPinCode, newPinCode);
        return true;
    }
    
    @Override
    public boolean inputPin(String pin) throws PinValidatorException {
        PinCode pinCode = pinValidator.validate(pin);
        user.isCorrect(pinCode);
        return true;
    }
    
    @Override
    public void run() {
        final String selectOperations = "Input quit for exit or select operation: \n" +
                "1. Put Money;\n" +
                "2. Get Money;\n" +
                "3. Check Balance;\n" +
                "4. Changed Pin Code.\n";
        
        String line;        //Считыватель команд
        
        printStream.print("Hello! This is THE TERMINAL!\n Please Input your account or exit for close THE TERMINAL:\n");
        while (scanner.hasNext() && !(line = scanner.nextLine()).equals("exit")) {
            try {
                /*
                Ввод аккаунта
                 */
                connect();
                setAccount(line);

                /*
                Ввод пароля от аккаунта
                 */
                printStream.print("Input you pin code or cancel:\n");
                while (scanner.hasNext() && !(line = scanner.nextLine()).equals("cancel")) {
                    try {
                        inputPin(line);
                        break;
                    } catch (PinValidatorException e) {     //AccountsIsLocked | InvalidPinCode
                        printStream.print(e.getMessage() + "\n");
                    }
                }
                if (line.equals("cancel")) {
                    continue;
                }

                /*
                Операции с аккаунтом
                 */
                printStream.print(selectOperations);
                while (!(line = scanner.nextLine()).equals("quit")) {
                    try {
                        connect();
                        switch (Integer.parseInt(line)) {
                            case 1:
                                printStream.print("Input value:\n");
                                deposit(scanner.nextLine());
                                printStream.print("Success!\n");
                                break;
                            case 2:
                                printStream.print("Input value:\n");
                                withdraw(scanner.nextLine());
                                printStream.print("Success!\n");
                                break;
                            case 3:
                                printStream.print(getStatementOfAccount().toString() + "\n");
                                break;
                            case 4:
                                printStream.print("Input old pin:\n");
                                String oldPin = scanner.nextLine();
                                printStream.print("Input new pin:\n");
                                String newPin = scanner.nextLine();
                                changedPin(oldPin, newPin);
                                printStream.print("Success!\n");
                                break;
                            default:
                                throw new IllegalOperationException("Incorrect number of operation. " +
                                        "Please input number 1-4.");
                        }
                        
                    } catch (IllegalOperationException | PinValidatorException
                            | IllegalValueOfMoneyException | ServerConnectionException e) {
                        printStream.print(e.getMessage() + "\n");
                    } catch (NumberFormatException e) {
                        printStream.print("Not valid command. Try again.\n");
                    } finally {
                        printStream.print(selectOperations);
                    }
                }
                
            } catch (AccountsException | AccountIsLockedException | ServerConnectionException e) {
                printStream.print(e.getMessage() + "\n");
            } finally {
                printStream.print("Please Input your account or exit for close THE TERMINAL:\n");
            }
        }
    }
}