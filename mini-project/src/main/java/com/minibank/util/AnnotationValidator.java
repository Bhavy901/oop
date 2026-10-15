package com.minibank.util;

import com.minibank.model.annotation.MaxLength;
import com.minibank.model.annotation.Positive;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationValidator {

    public static String[] validate(Object obj) {
        if (obj == null) {
            return new String[] { "Validated target is null" };
        }

        List<String> errors = new ArrayList<>();
        Class<?> clazz = obj.getClass();

        while (clazz != null && clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object val = field.get(obj);

                    if (field.isAnnotationPresent(Positive.class)) {
                        Positive positive = field.getAnnotation(Positive.class);
                        if (val instanceof Number num) {
                            if (num.doubleValue() < 0) {
                                errors.add("Field '" + field.getName() + "': " + positive.message() + " (Actual: " + val + ")");
                            }
                        }
                    }

                    if (field.isAnnotationPresent(MaxLength.class)) {
                        MaxLength maxLength = field.getAnnotation(MaxLength.class);
                        if (val != null) {
                            String strVal = val.toString();
                            if (strVal.length() > maxLength.value()) {
                                errors.add("Field '" + field.getName() + "': " + maxLength.message() +
                                        " (Max: " + maxLength.value() + ", Actual: " + strVal.length() + ")");
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Field access error on '" + field.getName() + "': " + e.getMessage());
                }
            }
            clazz = clazz.getSuperclass();
        }

        return errors.toArray(new String[0]);
    }
}
