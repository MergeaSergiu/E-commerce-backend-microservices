package com.mycompany.app.service.impl;

import com.mycompany.app.service.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;

@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final String bucketName;

    public S3ServiceImpl( @Value("${aws.s3.bucket-name}")String bucketName,
                          @Value("${aws.s3.region}") String region,
                          @Value("${aws.access-key}") String accessKey,
                          @Value("${aws.secret-key}") String secretKey) {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
        this.bucketName = bucketName;
    }

    @Override
    public String uploadFile(String filename, File file) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));
        return "https://" + bucketName + ".s3.amazonaws.com/" + filename;
    }
}
