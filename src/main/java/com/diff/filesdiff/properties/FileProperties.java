package com.diff.filesdiff.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "file")
public final class FileProperties {

    private String basePath;

    private FileProperties(final String basePath) {
        this.basePath = basePath;
    }
}
