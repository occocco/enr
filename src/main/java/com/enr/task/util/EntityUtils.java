package com.enr.task.util;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 제네릭을 통한 공통 처리 클래스
 */
public class EntityUtils {

    public static void assertListNotEmpty(List<?> list, String errorMessage) {
        if (list.isEmpty()) {
            throw new NoSuchElementException(errorMessage);
        }
    }

    public static  <T> T getEntityFromOpt(Optional<T> optional, String exMessage) {
        return optional.orElseThrow(() -> new NoSuchElementException(exMessage));
    }

    public static  <T, U> List<U> convertEntityListToDtoList(List<T> entities, Function<T, U> converter) {
        return entities.stream()
                .map(converter)
                .collect(Collectors.toList());
    }

}
