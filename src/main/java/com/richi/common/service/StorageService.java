package com.richi.common.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.richi.common.exception.StorageException;

@Service
public class StorageService {
    //? Я не уверен, что этот метод мне нужен
    public void saveFile(MultipartFile file, Path destinationFolder){
        String fileName = file.getOriginalFilename();
        try {
            Path targetLocation = destinationFolder.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    public Path storeInFolder(MultipartFile file, Path folderPath) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path destinationFile = folderPath.resolve(
					Path.of(file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			if(!destinationFile.toFile().exists()){
				destinationFile.toFile().mkdirs();
			}
			if (!destinationFile.getParent().equals(folderPath.toAbsolutePath())) {
				// This is a security check
				throw new StorageException(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
		return folderPath.resolve(Path.of(file.getOriginalFilename())).normalize();
	}

    //TODO нормальную обработку исключительных ситуаций
    public Resource loadAsResource(Path filePath) throws Exception {
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }
        else {
            //TODO Убрать путь к файлу из сообщения об ошибке
            throw new Exception("File by path '" + filePath + "'' is not exists or unreadable");
        }
	}

    public void deleteFile(Path filePath) throws IOException{
		Files.deleteIfExists(filePath);
	}
}
