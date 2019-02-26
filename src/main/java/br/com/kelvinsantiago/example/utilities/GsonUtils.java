package br.com.kelvinsantiago.example.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static br.com.kelvinsantiago.example.utilities.InterfaceSerializer.interfaceSerializer;

public class GsonUtils {

    public static String stringify(Object o){
        return new Gson().toJson(o);
    }

    // Solution: Interface can't be instantiated!
    // http://qaru.site/questions/6320535/gson-deserialzing-objects-containing-lists
    public static Object fromJson(String json, Class c, Class parent){

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(parent, interfaceSerializer(c))
                .create();
        Object obj = gson.fromJson(json, c);
        return obj;
    }

    public static Object fromJson(String json, Class clazz){
        return new Gson().fromJson(json, clazz);
    }
}
