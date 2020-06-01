package com.diff.filesdiff.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "storage")
public final class BucketProperties {

    private String accessKey;

    private String secretKey;

    private String url;

    private String bucketName;

    public BucketProperties(final String accessKey,
                             final String secretKey,
                             final String url,
                             final String bucketName) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.url = url;
        this.bucketName = bucketName;
    }
}
