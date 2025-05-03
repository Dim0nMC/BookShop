package com.example.bookshop.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.example.bookshop.util.ValidationUtil;
import com.example.bookshop.util.exception.*;

import jakarta.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class GlobalControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    static final String MSG_APP_ERROR = "Application error";
    static final String MSG_DATA_NOT_FOUND = "Data not found";
    static final String MSG_DATA_ERROR = "Data error";
    static final String MSG_VALIDATION_ERROR = "Validation error";
    static final String MSG_WRONG_REQUEST = "Wrong request";

    static final Map<String, HttpStatus> ERRORS = Map.of(
            MSG_APP_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
            MSG_DATA_NOT_FOUND, HttpStatus.UNPROCESSABLE_ENTITY,
            MSG_DATA_ERROR, HttpStatus.CONFLICT,
            MSG_VALIDATION_ERROR, HttpStatus.UNPROCESSABLE_ENTITY,
            MSG_WRONG_REQUEST, HttpStatus.BAD_REQUEST
    );

    public static final String EXCEPTION_DUPLICATE_USER_NAME = "User with this name already exists";
    public static final String EXCEPTION_DUPLICATE_USER_EMAIL = "User with this email already exists";
    public static final String EXCEPTION_DATA_INTEGRITY_VIOLATION = "Violation of data integrity. Perhaps the entry is in use.";

    private static final Map<String, String> CONSTRAINTS_MSG = Map.of(
            "users_name_unique_idx", EXCEPTION_DUPLICATE_USER_NAME,
            "users_email_unique_idx", EXCEPTION_DUPLICATE_USER_EMAIL);

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<?> dataNotFoundError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, MSG_DATA_NOT_FOUND);
    }


    @ExceptionHandler(UserDeleteViolationException.class)
    ResponseEntity<?> userDeleteViolation(HttpServletRequest req, UserDeleteViolationException e) {
        return logAndGetErrorInfo(req, e, MSG_WRONG_REQUEST);
    }

    @ExceptionHandler(UserUpdateViolationException.class)
    ResponseEntity<?> userUpdateViolation(HttpServletRequest req, UserUpdateViolationException e) {
        return logAndGetErrorInfo(req, e, MSG_WRONG_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, PersistenceException.class})
    ResponseEntity<?> dataIntegrityViolation(HttpServletRequest req, Exception e) {
        String rootMessage = ValidationUtil.getRootCause(e).getMessage();
        if (rootMessage != null) {
            for (Map.Entry<String, String> entry : CONSTRAINTS_MSG.entrySet()) {
                if (rootMessage.toLowerCase().contains(entry.getKey())) {
                    return logAndGetErrorInfo(req, e, MSG_VALIDATION_ERROR + " (" + entry.getValue() + ")");
                }
            }
        }
        if (e instanceof DataIntegrityViolationException || e.getMessage().toLowerCase().contains("constraintviolationexception")) {
            return logAndGetErrorInfo(req, e, MSG_VALIDATION_ERROR + " (" + EXCEPTION_DATA_INTEGRITY_VIOLATION + ")");
        }
        return logAndGetErrorInfo(req, e, MSG_DATA_ERROR);
    }

    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<?> illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, MSG_VALIDATION_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, MSG_APP_ERROR);
    }

    ResponseEntity<?> logAndGetErrorInfo(HttpServletRequest req, Exception e, String messageText) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        HttpStatus status = ERRORS.get(messageText);
        log.warn("Error at request  {}: {} (status: {})", req.getRequestURL(), rootCause.toString(), status.toString());
        return ResponseEntity.status(status).body(Map.of("error", messageText));
    }
}
