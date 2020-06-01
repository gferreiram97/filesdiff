package com.diff.filesdiff.factory;

import com.diff.filesdiff.annotation.Factory;
import com.diff.filesdiff.resource.FileForm;

@Factory
public class FileFormFactory implements AbstractFactory<FileForm> {

    @Override
    public FileForm createDefault() {
        return FileForm.builder()
                .name("apiV1One.txt")
                .file("YXBpVmVyc2lvbjogdjEKCg==")
                .build();
    }

    @Override
    public FileForm createEmpty() {
        return new FileForm();
    }

    public FileForm createFileApiV1One() {
        return createDefault();
    }

    public FileForm createFileApiV1Two() {
        return FileForm.builder()
                .name("apiV1Two.txt")
                .file("YXBpVmVyc2lvbjogdjEKCg==")
                .build();
    }

    public FileForm createFileApiV1Tree() {
        return FileForm.builder()
                .name("apiV1Tree.txt")
                .file("YXBpVmVyc2lvbjogdjIKCg==")
                .build();
    }

    public FileForm createFileApiV1Four() {
        return FileForm.builder()
                .name("apiV1Four.txt")
                .file("YXBpVmVyc2lvbjogdjEgYW5kIGFwaVZlcnNpb246djIKCg==")
                .build();
    }
}
