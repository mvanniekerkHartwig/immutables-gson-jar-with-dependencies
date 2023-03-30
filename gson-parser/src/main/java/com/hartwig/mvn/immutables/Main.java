package com.hartwig.mvn.immutables;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ServiceLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

public class Main {

    private static Gson gson;
    private static ClassLoader classloader;

    public static void main(String[] args) throws IOException {
        var gsonBuilder = new GsonBuilder();
        for (TypeAdapterFactory factory : ServiceLoader.load(TypeAdapterFactory.class)) {
            System.out.println("Loading type adapter: " + factory.getClass().getSimpleName());
            gsonBuilder.registerTypeAdapterFactory(factory);
        }
        gson = gsonBuilder.serializeNulls().serializeSpecialFloatingPointValues().create();
        classloader = Thread.currentThread().getContextClassLoader();

        readResource("data-one.json", DataOne.class);
        readResource("data-two.json", DataTwo.class);

    }

    private static void readResource(String filename, Class<?> clazz) throws IOException {
        try (var resource = classloader.getResourceAsStream(filename);
        var reader = new InputStreamReader(resource)) {
            var result = gson.fromJson(reader, clazz);
            System.out.println(result);
        }
    }
}