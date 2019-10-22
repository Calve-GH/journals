package com.github.calve.web.mails;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.Arrays;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {SQLException.class, IllegalStateException.class})
    protected ResponseEntity<ErrorInfo> handleConflict(RuntimeException ex, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        ErrorInfo errorInfo = new ErrorInfo("Ошибка сохранения данных", new String[] {getUniqueConstraintsMessage(rootCause.getLocalizedMessage())});
        return ResponseEntity.unprocessableEntity().body(errorInfo);
    }

    private static String getUniqueConstraintsMessage(String text) {
//        ОШИБКА: повторяющееся значение ключа нарушает ограничение уникальности "generics_idx"
//        Detail: Ключ "(income_date, income_index)=(2018-01-05 00:00:00, 1-к)" уже существует.
        String[] split = text.split("Detail:");
        if (split.length > 1) {
            text = split[1];
            text = text.replaceAll("\"", "");
            text = text.replaceAll("\\(income_date, income_index\\)=\\(", "");
            text = text.replaceAll("\\)", "");
            text = text.replaceAll(" 00:00:00,", "");
        }
        return text;
    }
}
