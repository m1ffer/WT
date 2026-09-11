package com.epam.rd.autotasks.requirements;

import java.util.Objects;

public class Requirements {

    public static void requireNonNull(Object obj) {
        Objects.requireNonNull(obj);
    }

    public static void requireNonNull(Object obj, String message) {
        Objects.requireNonNull(obj, message);
    }

    public static void checkArgument(boolean value) {
        if (!value)
            throw new IllegalArgumentException();
    }

    public static void checkArgument(boolean value, String message) {
        if (!value)
            throw new IllegalArgumentException(message);
    }

    public static void checkState(boolean value) {
        if (!value)
            throw new IllegalStateException();
    }

    public static void checkState(boolean value, String message) {
        if (!value)
            throw new IllegalStateException(message);
    }

    public static void checkIndex(int index, int size) {
        Objects.checkIndex(index, size);
    }


}
