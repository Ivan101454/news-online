package ru.clevertec.newsonline.controller.newcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class BadRequestAdviseController {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException exception, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST,
                        messageSource.getMessage("errors.400.name", new Object[0], "errors.400.name", locale));
        problemDetail.setProperty("errors",
                exception.getAllErrors().stream()
                        .map(MessageSourceResolvable::getDefaultMessage).toList());
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
