package com.fb.restbucks.integration.builder;

/**
 * @author dan
 */
public class NullObjectBuilder<T> implements TestBuilder<T> {

    public static <T> TestBuilder<T> nullObject() {
        return new NullObjectBuilder<T>();
    }

    @Override
    public T build() {
        return null;
    }
}
