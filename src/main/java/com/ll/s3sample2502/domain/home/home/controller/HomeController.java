package com.ll.s3sample2502.domain.home.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
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

    @GetMapping("/upload")
    public String showUpload() {
        return """
                <form action="/upload" method="post" enctype="multipart/form-data">
                    <input type="file" name="file" accept="image/*">
                    <input type="submit" value="Upload">
                </form>
                """;
    }

    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(MultipartFile file) throws IOException {
        // PutObjectRequest 객체 생성
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("dev-bucket-sik2-1")
                .key("test/" + file.getOriginalFilename())
                .contentType(file.getContentType())
                .build();

        // RequestBody 생성 및 S3에 파일 업로드
        s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return "Upload Success";
    }
}
