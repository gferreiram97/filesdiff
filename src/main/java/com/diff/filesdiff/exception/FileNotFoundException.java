package com.diff.filesdiff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Arquivo não existe para identificador informado")
public class FileNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -6157529969346523240L;

    public FileNotFoundException() {
    }
}
