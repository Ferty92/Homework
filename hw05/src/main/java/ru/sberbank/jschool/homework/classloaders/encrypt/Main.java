package ru.sberbank.jschool.homework.classloaders.encrypt;

//import ru.sberbank.jschool.homework.classloaders.Plugin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) throws MalformedURLException {
        String rootDirectory = "C:\\java_sbt\\Homework\\hw05\\encrypted";
        URL url = Paths.get(rootDirectory).toUri().toURL();
        int offset = 5;

        EncryptedClassLoader loader = new EncryptedClassLoader(url, Main.class.getClassLoader(), offset);

        /*
        Для шифрования обычного класса
         */
//        CryptoCaesar cryptoCaesar = new CryptoCaesar(offset);
//        coding(cryptoCaesar, rootDirectory);

        /*
        Читаем зашифрованный файл и выполняем его
         */
        try {
            Class<?> clazz = loader.findClass("PluginB");
//            Plugin plugin = (Plugin) clazz.newInstance();
//            plugin.run(new String[]{"something.."});
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
//        catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
        /*
        Пробуем прочитать без дешифровки
         */

    }

    /*
      Шифруем класс, если нет зашифрованного.
     */
    public static void coding(CryptoCaesar cryptoCaesar, String rootDirectory) {
        try {
            byte[] original = Files.readAllBytes(Paths.get(rootDirectory + "\\" + "PluginB.class"));
            byte[] encrypt = cryptoCaesar.coder(original);
            FileOutputStream output = new FileOutputStream(rootDirectory + "\\" + "PluginB.class");
            output.write(encrypt);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}