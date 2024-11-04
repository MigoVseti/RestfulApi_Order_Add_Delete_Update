package com.migran.app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ExceptionHandler {
    private LocalDateTime time;
    private String message;
    private String details;

    public ExceptionHandler(LocalDateTime time, String message, String details) {
        this.time = time;
        this.message = message;
        this.details = details;
    }

}
