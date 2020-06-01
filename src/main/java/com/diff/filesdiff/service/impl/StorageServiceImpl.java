package com.diff.filesdiff.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.diff.filesdiff.properties.BucketProperties;
import com.diff.filesdiff.service.StorageService;
import com.diff.filesdiff.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    private final AmazonS3 storageClient;

    private final BucketProperties bucketProperties;

    public StorageServiceImpl(final AmazonS3 storageClient,
                              final BucketProperties bucketProperties) {
        this.storageClient = storageClient;
        this.bucketProperties = bucketProperties;
    }

    @Override
    public void upload(final String fileName, final byte[] data) {
        log.info("M=uploadFile, iniciando upload no bucket, fileName={}", fileName);

        final var inputStream = FileUtils.getInputStream(data);

        final var fileType = FileUtils.getFileType(fileName);

        final var metadata = getMetadata(fileType);

        storageClient.putObject(bucketProperties.getBucketName(), fileName, inputStream, metadata);
    }

    @Override
    public InputStream donwload(final String fileName) {

        final var s3Object = storageClient.getObject(
                new GetObjectRequest(bucketProperties.getBucketName(), fileName
                ));

        return s3Object.getObjectContent();
    }

    private ObjectMetadata getMetadata(final String fileType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(fileType);
        metadata.setCacheControl("public, max-age=31536000");
        return metadata;
    }
}
