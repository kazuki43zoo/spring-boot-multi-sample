package com.github.kazuki43zoo.sample.api.component.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Locale;

@Component
public class ApiErrorCreator {

    @Autowired
    MessageSource messageSource;

    @Autowired
    ApiErrorProperties apiErrorProperties;

    public ApiError create(String message) {
        ApiError apiError = new ApiError();
        apiError.setMessage(message);
        apiError.setDocumentationUrl(apiErrorProperties.getDocumentationUrl());
        return apiError;
    }

    public ApiError createByException(Exception ex, String defaultMessage, Locale locale) {
        return create(apiErrorProperties.resolveMessage(ex, defaultMessage));
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

    private String getMessage(MessageSourceResolvable resolvable, Locale locale) {
        return messageSource.getMessage(resolvable, locale);
    }

}
