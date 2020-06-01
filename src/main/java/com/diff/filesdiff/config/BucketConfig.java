package com.diff.filesdiff.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.diff.filesdiff.properties.BucketProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class BucketConfig {

    private final BucketProperties bucketProperties;

    public BucketConfig(final BucketProperties bucketProperties) {
        this.bucketProperties = bucketProperties;
    }

    @Bean
    public AmazonS3 initBucketService() {
        final var doCred = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(bucketProperties.getAccessKey(), bucketProperties.getSecretKey()));
        return AmazonS3ClientBuilder.standard()
                .withCredentials(doCred)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        bucketProperties.getUrl(), "sfo2"
                ))
                .build();
    }
}