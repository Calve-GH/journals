package com.github.calve.util;

import com.github.calve.util.exception.ErrorInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

public class Util {
    private static final Map<String, String> FIELD_NAMES = new HashMap<>();

    static {
        FIELD_NAMES.put("incomeDate","Дата входщего");
        FIELD_NAMES.put("incomeIndex","№ входящего");
        FIELD_NAMES.put("correspondent","Корреспондент");
        FIELD_NAMES.put("outerDate","Реквизиты вх. Дата");
        FIELD_NAMES.put("outerIndex","Реквизиты вх. №");
        FIELD_NAMES.put("executor","Исполнитель");
    }

    private Util() {
    }

    public static <T extends Comparable<? super T>> boolean isBetween(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) <= 0);
    }

    public static ResponseEntity<ErrorInfo> getStringResponseEntity(BindingResult result) {
        List<String> listOfErrors = new ArrayList<>(result.getErrorCount());
        for (FieldError fe : result.getFieldErrors()) {
            String msg = fe.getDefaultMessage();
            if (Objects.nonNull(msg)) {
                if (!msg.startsWith(fe.getField())) {
                    msg = FIELD_NAMES.get(fe.getField()) + ' ' + msg;
                }
                listOfErrors.add(msg);
            }
        }
        String[] array = listOfErrors.toArray(new String[0]);
        return ResponseEntity.unprocessableEntity().body(new ErrorInfo("Ошибки в полях:", array));
    }

}
