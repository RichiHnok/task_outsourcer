package com.richi.common.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.richi.common.exception.StorageException;
import com.richi.common.exception.StorageFileNotFoundException;
import com.richi.web_part.configuration.StorageProperties;

@Service
public class StorageService {

   private final Path rootLocation;

	public StorageService(StorageProperties properties) {
        
        if(properties.getLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty."); 
        }

		this.rootLocation = Path.of(properties.getLocation());
	}

	public Path store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path destinationFile = this.rootLocation.resolve(
					Path.of(file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
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
		return this.rootLocation.resolve(Path.of(file.getOriginalFilename())).normalize();
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

	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	public void deleteFile(Path filePath) throws IOException{
		Files.deleteIfExists(filePath);
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	public Path getRootLocation() {
		return rootLocation;
	}
}
