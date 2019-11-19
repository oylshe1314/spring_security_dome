package com.example.dome.application.util;


import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class IterateUtils {

    public static <S, T> List<T> convert(Iterable<S> list, Converter<S, T> converter) {
        List<T> results = new ArrayList<>();
        Iterator<S> iterator = list.iterator();
        while (iterator.hasNext()) {
            results.add(converter.convert(iterator.next()));
        }
        return results;
    }

    public static <E> List<E> copy(Iterable<E> list) {
        List<E> results = new ArrayList<>();
        Iterator<E> iterator = list.iterator();
        while (iterator.hasNext()) {
            results.add(iterator.next());
        }
        return results;
    }
}
