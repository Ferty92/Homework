package ru.sberbank.homework.Polushin.task_01;


import ru.sberbank.homework.common.annotation.ExperimentalFeature;
import ru.sberbank.homework.common.annotation.Prototype;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationFinderApp {
    private static String PACKAGE_NAME;

    public AnnotationFinderApp() {

    }

    public static void main(String[] args)
            throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException {
        PACKAGE_NAME = "ru.sberbank.homework.common.entity";
        Class<Prototype> annotationForClass = Prototype.class;
        String methodOfAnnotation = "version";
        Class<ExperimentalFeature> annotationForField = ExperimentalFeature.class;
        Class<ExperimentalFeature> annotationForMethod = ExperimentalFeature.class;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<Class> listClasses = getPackageClasses(classLoader, PACKAGE_NAME);
        Map<Class, Integer> mapAnnotatedClasses =
                getClassesAndValueByAnnotation(listClasses, annotationForClass, methodOfAnnotation);
        /*
        так тоже работает, при чем нам вообще все равно какой тип возвращает метод нашей анотации!
        можем спокойно его вывести.
         */
//        Map<Class, Class> test = getClassesAndValueByAnnotation(listClasses, annotationForClass, methodOfAnnotation);
//        for (Class c: test.keySet()){
//            System.out.println(test.get(c));
//        }

        for (Class c : mapAnnotatedClasses.keySet()) {
            System.out.println(c.getName() + ": " + methodOfAnnotation + " = " + mapAnnotatedClasses.get(c));
            Set<String> annotatedFieldsAndMethods = getFieldAndMethodsByAnnotation(c, annotationForField, annotationForMethod);
            annotatedFieldsAndMethods.forEach(System.out::println);
        }


    }

    public static Set<String> getFieldAndMethodsByAnnotation(Class c, Class fieldAnnotation, Class methodAnnotation) {
        Set<String> set = new HashSet<>();

        for (Field field : c.getDeclaredFields()) {
            if (field.getAnnotation(fieldAnnotation) != null) {
                set.add(field.getName());
            }
        }

        for (Method method : c.getDeclaredMethods()) {
            if (method.getAnnotation(methodAnnotation) != null) {
                set.add(method.getName());
            }
        }

        return set;
    }

    /*
    Получаем проанатированные классы и их версии
     */
    public static <T> Map<Class, T> getClassesAndValueByAnnotation(List<Class> classes, Class annotation, String method)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<Class, T> map = new HashMap<>();

        for (Class clazz : classes) {
            Annotation a = clazz.getAnnotation(annotation);
            if (a != null) {
                T value = (T) a.getClass().getMethod(method).invoke(a);
                map.put(clazz, value);
            }
        }

        return map;
    }

    /*
    С помощью рефлексии получаем из загрузчика классы и возвращаем те, что находятся в определенном пакете
     */
    public static List<Class> getPackageClasses(ClassLoader classLoader, String packageName)
            throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        loadPackageFiles(classLoader, packageName);
        List<Class> list = new ArrayList<>();
        Class classOfClassLoader = classLoader.getClass();

        while (classOfClassLoader != java.lang.ClassLoader.class) {
            classOfClassLoader = classOfClassLoader.getSuperclass();
        }
        Field field = classOfClassLoader.getDeclaredField("classes");
        field.setAccessible(true);
        Vector<Class> classes = (Vector) field.get(Thread.currentThread().getContextClassLoader());

        for (Class clazz : classes) {
            if (clazz.getCanonicalName().contains(packageName)) {
                list.add(clazz);
            }
        }

        return list;
    }

    /*
    Метод для загрузки всех классов определенного пакета. Необходим, т.к. иначе jvm не подгружает сама
    файлы из пакета в задании, т.к. он нигде не используется.
     */
    private static void loadPackageFiles(ClassLoader classLoader, String packageName) throws ClassNotFoundException {
        String path = AnnotationFinderApp.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                + packageName.replaceAll("[.]", "/");
        File directory = new File(path);

        for (File file : directory.listFiles()) {
            classLoader.loadClass(packageName + "." + file.getName().replace(".class", ""));
        }
    }
}
