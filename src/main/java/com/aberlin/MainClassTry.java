package com.aberlin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainClassTry {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
        URL[] urls = new URL[]{new URL("file:/Users/swathimakkena/.m2/repository/tim/stuff/SimpleObj/1.0-SNAPSHOT/SimpleObj-1.0-SNAPSHOT.jar")};

        URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls);

        urlClassLoader.getURLs();
        Class<?> aClass = urlClassLoader.loadClass("tim.stuff.SimpleObj");

        Object instance = null;
        try {
            instance = aClass.newInstance();
            Method[] methods = instance.getClass().getDeclaredMethods();

            List<Method> methodList = Arrays.asList(methods).stream()
                    .filter(method -> method.getName().contains("get"))
                    .collect(Collectors.toList());

            final Object finalInstance = instance;
            methodList.stream()
                    .forEach(method -> {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes[0] == String.class) {
                            try {
                                method.invoke(finalInstance, "String");
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(instance);
    }
}
