package ru.sberbank.homework.Polushin.hw3_1;

import java.io.InputStream;
import java.io.OutputStream;

class Main {
    public static void main(String[] args) {
        OutputStream outputStream = System.out;
        InputStream inputStream = System.in;
        TerminalImpl terminal = new TerminalImpl(outputStream, inputStream);
        terminal.run();
    }
}