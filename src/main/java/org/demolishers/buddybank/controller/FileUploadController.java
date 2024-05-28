package org.demolishers.buddybank.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Dosya bulunamadı.";
        }

        String uploadDir = "uploads/";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        try {
            String filePath = uploadDir + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            return "Dosya başarıyla yüklendi: " + filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "Dosya yükleme sırasında bir hata oluştu.";
        }
    }
}
