package com.github.kazuki43zoo.sample.api.controller.error;

import com.github.kazuki43zoo.sample.api.component.error.ApiError;
import com.github.kazuki43zoo.sample.api.component.error.ApiErrorCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ApiErrorPageController implements ErrorController {

    @Autowired
    ServerProperties serverProperties;

    @Autowired
    ApiErrorCreator apiErrorCreator;

    @Override
    public String getErrorPath() {
        return serverProperties.getError().getPath();
    }

    @RequestMapping("${server.error.path:${error.path:/error}}")
    public ApiError handleError(HttpServletRequest request) {

        Exception ex = (Exception) request.getAttribute(
                RequestDispatcher.ERROR_EXCEPTION);
        Integer statusCode = (Integer) request.getAttribute(
                RequestDispatcher.ERROR_STATUS_CODE);

        ApiError apiError;
        if (ex != null) {
            apiError = apiErrorCreator.createByException(ex, "Unhandled system error is occurred", request.getLocale());
        } else {
            apiError = apiErrorCreator.createByStatusCode(statusCode, request.getLocale());
        }

        return apiError;
    }

}
