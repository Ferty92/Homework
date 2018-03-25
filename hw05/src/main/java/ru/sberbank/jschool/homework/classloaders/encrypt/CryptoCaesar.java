package ru.sberbank.jschool.homework.classloaders.encrypt;

public class CryptoCaesar {
    private final int offset;

    public CryptoCaesar(int offset) {
        this.offset = offset;
    }

    public byte[] coder(byte[] original) {
        byte[] encrypted = new byte[original.length];
        for (int i = 0; i < original.length; i++) {
            encrypted[i] = (byte) (original[i] + offset);
        }
        return encrypted;
    }

    public byte[] decoder(byte[] encrypted) {
        byte[] original = new byte[encrypted.length];
        for (int i = 0; i < encrypted.length; i++) {
            original[i] = (byte) (encrypted[i] - offset);
        }
        return original;
    }
}