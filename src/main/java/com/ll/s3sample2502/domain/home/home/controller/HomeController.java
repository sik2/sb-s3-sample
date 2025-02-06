package com.ll.s3sample2502.domain.home.home.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final AmazonS3 amazonS3;

    @GetMapping("/")
    public List<String> home() {
        // S3 버킷 리스트 받아오기
        List<Bucket> bucketList =  amazonS3.listBuckets();
        // 버킷의 이름만 추출하여 리스트 형태로 반환
        return bucketList.stream().map(Bucket::getName).collect(Collectors.toList());
    }
}
