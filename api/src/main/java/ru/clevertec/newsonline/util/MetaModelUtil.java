package ru.clevertec.newsonline.util;

import jakarta.persistence.metamodel.SingularAttribute;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.HashMap;

public class MetaModelUtil {


    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T, V> SingularAttribute<T, V> createMetaModelAttribute(
            Class<T> entityClazz, String attributeName) {
        String nameMetaClass = entityClazz.getName() + "_";
        Class<?> metaClass = Class.forName(nameMetaClass);

        Field declaredField = metaClass.getDeclaredField(attributeName);
        return (SingularAttribute<T, V>) declaredField.get(null);

    }
}
