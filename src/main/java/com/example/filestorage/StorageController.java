package com.example.filestorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchFiles(@RequestParam String userName, @RequestParam String fileNameTerm) {
        List<String> files = storageService.searchFiles(userName, fileNameTerm);
        return ResponseEntity.ok(files);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam String userName, @RequestParam("file") MultipartFile file) throws IOException {
        storageService.uploadFile(userName, file);
        return ResponseEntity.ok("File uploaded successfully");
    }
}
