package main;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by e.shubin on 24.03.2016.
 */
public class Context {
    @NotNull
    private final Map<Class, Object> contextMap = new HashMap<>();

    public void put(@NotNull Class clazz, @NotNull Object object){
        contextMap.put(clazz, object);
    }

    @NotNull
    public <T> T get(@NotNull Class<T> clazz){
        //noinspection unchecked
        return (T) contextMap.get(clazz);
    }
}