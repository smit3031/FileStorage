package com.example.filestorage;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class StorageService {

    @Autowired
    private AmazonS3 amazonS3;

    public List<String> searchFiles(String userName, String fileNameTerm) {
        List<String> objectKeys = amazonS3.listObjectsV2(userName).getObjectSummaries()
                .stream()
                .map(summary -> summary.getKey())
                .collect(Collectors.toList());

        return objectKeys.stream()
                .filter(key -> key.contains(fileNameTerm))
                .collect(Collectors.toList());
    }

    public void uploadFile(String userName, MultipartFile file) throws IOException {
        String key = userName + "/" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        amazonS3.putObject(userName, key, file.getInputStream(), metadata);
    }
}