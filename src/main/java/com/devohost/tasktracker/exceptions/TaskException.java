package com.devohost.tasktracker.exceptions;

public class TaskException extends RuntimeException{
    public TaskException() {
        super();
    }

    public TaskException(String message) {
        super(message);
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskException(Throwable cause) {
        super(cause);
    }
}
