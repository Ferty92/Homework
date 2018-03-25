package ru.sberbank.jschool.homework.classloaders;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String rootDirectory = "C:\\java_sbt\\Homework\\hw05";
        String plugin_1 = "user1";
        String plugin_2 = "user2";
        String plugin_3 = "user3";
        String plugin_4 = "user4";
        String[] urls = {"1234", "5678"};
        PluginManager pluginManager = new PluginManager(rootDirectory);
        try {
            List<Plugin> plugins = new ArrayList<>();
            plugins.addAll(pluginManager.loadPlugin(plugin_1));
            plugins.addAll(pluginManager.loadPlugin(plugin_2));
            plugins.addAll(pluginManager.loadPlugin(plugin_3));
            plugins.addAll(pluginManager.loadPlugin(plugin_3));
            plugins.addAll(pluginManager.loadPlugin(plugin_4));
            plugins.addAll(pluginManager.loadPlugin(plugin_1));
            plugins.addAll(pluginManager.loadPlugin(plugin_2));
            plugins.addAll(pluginManager.loadPlugin(plugin_3));
            plugins.addAll(pluginManager.loadPlugin(plugin_3));
            plugins.addAll(pluginManager.loadPlugin(plugin_4));

            for (Plugin cur : plugins) {
                cur.run(urls);
            }
            //Проверяем какие классы загружены нашим загрузчиком
            pluginManager.loader.getClassLoaded().entrySet().forEach(System.out::println);

        } catch (PluginNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}