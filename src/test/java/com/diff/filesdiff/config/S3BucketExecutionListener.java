package com.diff.filesdiff.config;

import com.amazonaws.services.s3.AmazonS3;
import io.findify.s3mock.S3Mock;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class S3BucketExecutionListener implements TestExecutionListener {

    private S3Mock s3Mock;

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        s3Mock = new S3Mock.Builder().withPort(8090).withInMemoryBackend().build();
        s3Mock.start();

        final var amazonS3 = testContext.getApplicationContext().getBean(AmazonS3.class);
        amazonS3.createBucket("filesdiff");
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        s3Mock.shutdown();
    }
}
