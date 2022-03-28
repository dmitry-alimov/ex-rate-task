package com.gd.alimov.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorExchangeRatesController implements ErrorController {

    Logger LOGGER = LoggerFactory.getLogger(ErrorExchangeRatesController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        String error = "error";
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                LOGGER.error("Error 404");
                error = "404 Error. Page not found.";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                LOGGER.error("Error 403");
                error = "403 Error. Request forbidden.";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                LOGGER.error("Error 500");
                error = "500 Error. Internal server error.";
            } else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                LOGGER.error("Error 405");
                error = "405 Error. Method not allowed.";
            }
        }
        return error;
    }
}
