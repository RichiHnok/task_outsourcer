package com.richi.web_part.controller;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
@RequestMapping("/test")
public class TestController {

    Logger log = LoggerFactory.getLogger(TestController.class);
    String strPath = "src\\main\\resources\\files\\users\\R\\1137\\Result-11-R-22-20-06-02-02-2025.zip";
    
    @GetMapping
    public String test(
        Model model
    ) throws Exception{

        Path actualPath = Path.of(strPath);

        try{
            Assert.isTrue(actualPath.toFile().exists(), "File by path '" + actualPath + "' does not exist");
        }catch(IllegalArgumentException e){
            log.warn(e.getMessage());
        }

        String link = MvcUriComponentsBuilder.fromMethodName(
            TestController.class
            , "serveFile"
            , "aboba"
        ).build().toUri().toString();

        log.info("link: " + link);

        model.addAttribute("file", link);
        return "test-page";
    }

    @GetMapping("/{fileP}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(
        @PathVariable String fileP
    ) throws Exception {
        log.info("file: " + fileP);
		Resource file = loadAsResource(Path.of(strPath));

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

    public Resource loadAsResource(Path filePath) throws Exception {
		try {
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
                log.warn("Could not read file: " + filePath);
			}
		}
		catch (Exception e) {
			log.warn("Could not read file: " + filePath);
		}
        throw new Exception();
	}
}
