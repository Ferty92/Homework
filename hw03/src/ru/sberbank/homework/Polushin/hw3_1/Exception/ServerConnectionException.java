package ru.sberbank.homework.Polushin.hw3_1.Exception;

public class ServerConnectionException extends RuntimeException {
    public ServerConnectionException(String message) {
        super(message);
    }
}