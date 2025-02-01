package com.richi.common.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;

@Service
public class ZipService {
    
    public void zipFile(Path sourceFilePath, Path destinationFolderPath, String finalArchiveName){
        File fileToZip = sourceFilePath.toFile();
        if(fileToZip.isDirectory()){
            throw new IllegalArgumentException("Source file must be a file");
        }
        
        File destinationFolder = destinationFolderPath.toFile();
        if(!destinationFolder.isDirectory()){
            throw new IllegalArgumentException("Destination folder must be a directory");
        }

        try (
            FileOutputStream fos = new FileOutputStream(finalArchiveName + ".zip");
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            FileInputStream fis = new FileInputStream(fileToZip)
        ) {
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void zipMultipleFiles(List<Path> sourceFilesPathList, Path destinationFolderPath, String finalArchiveName){

    }

    public void zipDirectory(Path sourceFilesFolderPath, Path destinationFolderPath, String finalArchiveName){

    }
}
