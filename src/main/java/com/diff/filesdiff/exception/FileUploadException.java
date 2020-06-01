package com.diff.filesdiff.exception;

import java.io.Serializable;

public class FileUploadException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 2581131635233157186L;

    public FileUploadException() {
    }

    public FileUploadException(String message) {
        super(message);
    }
}
