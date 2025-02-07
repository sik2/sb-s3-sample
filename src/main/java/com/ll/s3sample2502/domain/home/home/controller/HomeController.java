package com.ll.s3sample2502.domain.home.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final S3Client s3Client;

    @GetMapping("/")
    public List<String> home() {
        List<Bucket> bucketList = s3Client.listBuckets().buckets();
        return bucketList.stream().map(Bucket::name).collect(Collectors.toList());
    }
}