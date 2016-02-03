package com.github.kazuki43zoo.sample.api.component.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

@Component
public class ApiErrorCreator {

    @SuppressWarnings("unchecked")
    private final Map<Class<? extends Exception>, String> messageMappings =
            Collections.unmodifiableMap(new LinkedHashMap() {{
                put(HttpMessageNotReadableException.class, "Request body is invalid");
                put(MethodArgumentNotValidException.class, "Request value is invalid");
                put(BindException.class, "Request value is invalid");
            }});

    @Autowired
    MessageSource messageSource;

    public ApiError create(String message) {
        ApiError apiError = new ApiError();
        apiError.setMessage(message);
        apiError.setDocumentationUrl("http://example.com/api/errors");
        return apiError;
    }

    public ApiError createByException(Exception ex, String defaultMessage, Locale locale) {
        return create(resolveMessage(ex, defaultMessage));
    }

    public ApiError createByBindingResult(BindingResult bindingResult, Exception ex, String defaultMessage, Locale locale) {
        ApiError apiError = createByException(ex, defaultMessage, locale);
        bindingResult.getGlobalErrors()
                .forEach(e -> apiError.addDetail(e.getObjectName(), getMessage(e, locale)));
        bindingResult.getFieldErrors()
                .forEach(e -> apiError.addDetail(e.getField(), getMessage(e, locale)));
        return apiError;
    }

    public ApiError createByStatusCode(Integer statusCode, Locale locale) {
        String message;
        if (Arrays.asList(HttpStatus.values()).stream()
                .anyMatch(status -> status.value() == statusCode)) {
            message = HttpStatus.valueOf(statusCode).getReasonPhrase();
        } else {
            message = "Custom error(" + statusCode + ") is occurred";
        }
        return create(message);
    }

    private String resolveMessage(Exception ex, String defaultMessage) {
        return messageMappings.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(ex.getClass())).findFirst()
                .map(Map.Entry::getValue).orElse(defaultMessage);
    }

    private String getMessage(MessageSourceResolvable resolvable, Locale locale) {
        return messageSource.getMessage(resolvable, locale);
    }

}
