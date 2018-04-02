package ru.sberbank.homework.Polushin.Exception;

public class ServerConnectionException extends RuntimeException {
    public ServerConnectionException(String message) {
        super(message);
    }
}