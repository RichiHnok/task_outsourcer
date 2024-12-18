package com.richi.web_part.functions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.richi.web_part.configuration.StorageProperties;

public class MultipartFileNameChanger {

    private static StorageProperties storageProperties = new StorageProperties();
    
    public static void changeMultipartFileName(MultipartFile file, String newFileName){
        try {
            byte[] bytes = file.getBytes();
            String insPath = storageProperties.getLocation() + newFileName; // Directory path where you want to save ;
            Files.write(Path.of(insPath), bytes);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
