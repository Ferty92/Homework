package ru.sberbank.jschool.homework.classloaders.encrypt;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EncryptedClassLoader extends URLClassLoader {

    private CryptoCaesar cryptoCaesar;

    public EncryptedClassLoader(URL url, ClassLoader parent, int offset) {
        super(new URL[]{url}, parent);
        cryptoCaesar = new CryptoCaesar(offset);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] original = null;
        byte[] encrypt;
        try {
            encrypt = Files.readAllBytes(Paths.get(getURLs()[0].getFile().substring(1) + name + ".class"));
            original = cryptoCaesar.decoder(encrypt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, original, 0, original.length);
    }
}