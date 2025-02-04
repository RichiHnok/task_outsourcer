package com.richi.common.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
            FileOutputStream fos = new FileOutputStream(destinationFolder.getPath() + "/" + finalArchiveName + ".zip");
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
        // TODO может надо сделать дополнительные проверки переменных

        try (
            FileOutputStream fos = new FileOutputStream(destinationFolderPath + "/" + finalArchiveName + ".zip");
            ZipOutputStream zipOut = new ZipOutputStream(fos)
        ) {
            for(Path sourceFilePath : sourceFilesPathList){
                File fileToZip = sourceFilePath.toFile();
                try (FileInputStream fis = new FileInputStream(fileToZip)) {
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
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // TODO Скопировал из инета никак не адаптируя
    public void zipDirectory(Path sourceFilesFolderPath, Path destinationFolderPath, String finalArchiveName) throws IOException{
        FileOutputStream fos = new FileOutputStream(destinationFolderPath + "/" + finalArchiveName + ".zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        File fileToZip = sourceFilesFolderPath.toFile();
        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException{
        
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
