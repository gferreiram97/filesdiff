package com.diff.filesdiff.service;

import java.io.InputStream;

public interface StorageService {

    void upload(final String fileName, final byte[] data);

    InputStream donwload(String fileName);
}
