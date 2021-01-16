package com.devohost.tasktracker.exceptions;

public class ProjectPhaseException extends RuntimeException{
    public ProjectPhaseException() {
        super();
    }

    public ProjectPhaseException(String message) {
        super(message);
    }

    public ProjectPhaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectPhaseException(Throwable cause) {
        super(cause);
    }
}
