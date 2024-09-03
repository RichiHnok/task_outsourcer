package com.richi.richis_app.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "C:\\RichiFolder\\Projects\\RichisAppForServer\\richis_app\\src\\main\\resources\\files";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
