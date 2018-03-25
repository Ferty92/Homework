package ru.sberbank.jschool.homework.classloaders;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {

    // directory that contains plugin folders
    private final String rootDirectory;
    LoaderForPlugin loader = new LoaderForPlugin(this.getClass().getClassLoader());
    private List<Class> loaded = new ArrayList<>();

    public PluginManager(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    /**
     * method takes as a parameter a folder name in the root plugin directory,
     * loads the plugin .class files from the folder if present,
     * and returns a Plugin object
     *
     * @param pluginName - name of the plugin folder
     * @return Plugin[]
     * @throws PluginNotFoundException - when folder named 'pluginName' is missing,
     *                                 or it contains no .class files
     */
    public List<Plugin> loadPlugin(String pluginName) throws PluginNotFoundException {
        List<Plugin> loadedPlugins = new ArrayList<>();
        Plugin plugin;
        boolean findFlag = false;


        File directories = new File(rootDirectory);
        for (File directory : directories.listFiles()) {

                /*
                Ищем нашу папку с плагинами: если это директория и ее имя совпадает
                 с именем плагина, введеным пользователем, то пытаемся найти .class файлы,
                иначе кидаем исключение.
                 */
            if (directory.isDirectory() && directory.getName().equals(pluginName)) {

                directories = new File(rootDirectory + "\\" + pluginName);

                    /*
                    Подгружаем все .class файлы или кидаем исключение если таких нет
                     */
                for (File file : directories.listFiles()) {
                        /*
                        Проверяем что текущий экземпляр является файлом и его расширение .class
                         */
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        try {
                            Class clazz = loader.loadClass(file.getAbsolutePath());

                            /*
                            Проверка уже загруженных плагинов
                             */
                            if (!loaded.contains(clazz)) {
                                plugin = (Plugin) clazz.newInstance();
                                loaded.add(clazz);
                                loadedPlugins.add(plugin);
                            }
                            findFlag = true;

                        } catch (ClassNotFoundException e) {
                            throw new PluginNotFoundException(
                                    e.getMessage() + " couldn't locate plugin " + pluginName);
                        } catch (InstantiationException e) {
                            throw new PluginNotFoundException(
                                    e.getMessage() + " couldn't locate plugin " + pluginName);
                        } catch (IllegalAccessException e) {
                            throw new PluginNotFoundException(
                                    e.getMessage() + " couldn't locate plugin " + pluginName);
                        }
                    }
                }
                break; //Выходим из цикла если нашли нужную директорию и загрузили все .class файлы.
            }

        }
        /*
         Бросаем исключение если не смогли подгрузить плагин, или возращаем список загруженных файлов
        */
        if (findFlag == false) {
            throw new PluginNotFoundException("couldn't locate plugin " + pluginName);
        } else {

            return loadedPlugins;
        }

    }


}
