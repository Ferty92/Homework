package ru.sberbank.jschool.homework.classloaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class LoaderForPlugin extends ClassLoader {
    private Map<String, Class<?>> classLoaded = new TreeMap<>();
    private ClassLoader parent;

    public LoaderForPlugin(ClassLoader parent) {
        super(parent);
        this.parent = parent;
    }

    public Map<String, Class<?>> getClassLoaded() {
        return classLoaded;
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(className)) {
            // First, check if the class has already been loaded
            Class<?> c = isLoaded(className);

            /*
            Делегирование родительскому загрузчику. Необходимо, т.к. нужно найти
            и загрузить другие связанные классы, в т.ч. сам интерфейс Plugin .
             */
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                        c = parent.loadClass(className);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(className);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();

                    classLoaded.put(className, c);
                }
            }
            return c;
        }
    }

    private Class<?> isLoaded(String className) {
        for (Map.Entry<String, Class<?>> instance : classLoaded.entrySet()) {
            if (instance.getKey().equals(className)) {
                return instance.getValue();
            }
        }
        return null;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            /*
              Получем байт-код из файла и загружаем класс в рантайм
             */
            File file = new File(className);
            String shortname = file.getName().split(".class$")[0];
            byte b[] = fetchClassFromFS(className.split(".class")[0]);

            /*
            Это КОСТЫЛЬ. Не знаю как обходить ситуацию, когда у плагина пользователя
            есть package. Сделал так, что имя пакета берется из сообщения об ощибке,
            т.к. оно сожержит правильное имя взятое из считывания байт кода файла.
             */
            while (true) {
                try {
                    return defineClass(shortname, b, 0, b.length);
                } catch (NoClassDefFoundError e) {
                    String message = e.getMessage();
                    shortname = message.split("wrong name: ")[1];
                    shortname = shortname.substring(0, shortname.length() - 1)
                            .replace("/", ".");
                } catch (LinkageError e) {
                    /*
                    Обработка классов с одинаковыми именами. Просто создаем новый загрузчик родителем
                    которого является текущий.
                     */
                    return new LoaderForPlugin(this).defineClass(shortname, b, 0, b.length);
                }
            }
        } catch (FileNotFoundException ex) {
            return super.findClass(className);
        } catch (IOException ex) {
            return super.findClass(className);
        }
    }

    /**
     * Взято из www.java-tips.org/java-se-tips/java.io/reading-a-file-into-a-byte-array.html
     * Считывает байт код файла.
     */
    private byte[] fetchClassFromFS(String name) throws FileNotFoundException, IOException {

        File file = new File(name + ".class");
        FileInputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;

    }
}