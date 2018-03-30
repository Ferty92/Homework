package ru.sberbank.homework.Polushin.task_02;

import org.apache.commons.lang3.reflect.TypeUtils;
import ru.sberbank.homework.common.BeanFieldCopier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionFieldCopier implements BeanFieldCopier {
    @Override
    public void copy(Object from, Object to) {
        List<String> methodsFrom = Arrays.stream(from.getClass().getMethods())
                .filter(element -> element.getName().contains("get"))
                .map(Method::getName)
                .collect(Collectors.toList());
        
        for (String get : methodsFrom) {
            
            try {
                Method fromMethod = from.getClass().getMethod(get);
                
                String set = get.replace("get", "set");
                
                for (Method toMethod : to.getClass().getMethods()) {
                    if (toMethod.getName().contains(set)) {
                        Type typeRetFrom = from.getClass().getMethod(get).getGenericReturnType();
                        Type typeGetTo = toMethod.getGenericParameterTypes()[0];
                        
                        /*
                        Проверяем не только сам тип поля, но и параметр типа.
                        После чего можем вызывать метод сет объекта потребителя и внего передавать
                         значение полученное из метода гет обекта продюсера
                         */
                        if (TypeUtils.isAssignable(typeRetFrom, typeGetTo)) {
                            toMethod.invoke(to, fromMethod.invoke(from));
                        }
                    }
                }
                
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
