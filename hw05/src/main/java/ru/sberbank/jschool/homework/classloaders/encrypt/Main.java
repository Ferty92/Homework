package ru.sberbank.jschool.homework.classloaders.encrypt;

//import ru.sberbank.jschool.homework.classloaders.Plugin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {
    final static String FILE_NAME = "PluginB.class";
    final static String ROOT_DIRECTORY = "C:\\java_sbt\\Homework\\hw05\\encrypted";
    final static int OFFSET = 5;

    public static void main(String[] args) throws MalformedURLException {
        URL url = Paths.get(ROOT_DIRECTORY).toUri().toURL();

        EncryptedClassLoader loader = new EncryptedClassLoader(url, Main.class.getClassLoader(), OFFSET);

        /*
        Для шифрования обычного класса
         */
//        CryptoCaesar cryptoCaesar = new CryptoCaesar(OFFSET);
//        coding(cryptoCaesar, ROOT_DIRECTORY);

        /*
        Читаем зашифрованный файл и выполняем его
         */
        try {
            Class<?> clazz = loader.findClass(FILE_NAME);
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
            byte[] original = Files.readAllBytes(Paths.get(rootDirectory + "\\" + FILE_NAME));
            byte[] encrypt = cryptoCaesar.coder(original);
            FileOutputStream output = new FileOutputStream(rootDirectory + "\\" + FILE_NAME);
            output.write(encrypt);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}