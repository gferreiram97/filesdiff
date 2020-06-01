package com.diff.filesdiff.service;

import com.diff.filesdiff.resource.FileForm;

public interface FileService {

    Long upload(String identifier, FileForm file);

    String compare(String identifier);
}
