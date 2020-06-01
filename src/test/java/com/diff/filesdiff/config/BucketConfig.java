package com.diff.filesdiff.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.diff.filesdiff.properties.BucketProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfig {

    private final BucketProperties bucketProperties;

    public BucketConfig(final BucketProperties bucketProperties) {
        this.bucketProperties = bucketProperties;
    }

    @Bean
    public AmazonS3 initBucketService() {
        final var endpoint = new AwsClientBuilder.EndpointConfiguration(bucketProperties.getUrl(), "us-west-2");
        final var client = (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();
        return client;
    }
}
