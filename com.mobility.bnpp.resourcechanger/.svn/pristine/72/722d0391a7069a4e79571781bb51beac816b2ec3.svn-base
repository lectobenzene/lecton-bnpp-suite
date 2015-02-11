package com.mobility.bnpp.resourcechanger.corecode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ResourceSwapper {

	private String sourcePathString;

	private Path resourceDefault;
	private Path resourceEasy;
	private Path resourceHello;

	private static final String DEFAULT_RESOURCE_NAME = "res";
	private static final String HELLO_BANKING_RESOURCE_NAME = "res_hellobank";

	private static final String EASY_BANKING_RESOURCE_NAME = "res_easybank";

	public ResourceSwapper(String sourcePathString) {
		this.sourcePathString = sourcePathString;
	}

	public void runSwapper() {

		resourceDefault = Paths.get(sourcePathString + "/" + DEFAULT_RESOURCE_NAME);
		resourceEasy = Paths.get(sourcePathString + "/" + EASY_BANKING_RESOURCE_NAME);
		resourceHello = Paths.get(sourcePathString + "/" + HELLO_BANKING_RESOURCE_NAME);

		try {
			if (Files.exists(resourceHello, LinkOption.NOFOLLOW_LINKS)) {
				Files.move(resourceDefault, resourceEasy, StandardCopyOption.REPLACE_EXISTING);
				Files.move(resourceHello, resourceDefault, StandardCopyOption.REPLACE_EXISTING);
			} else if (Files.exists(resourceEasy, LinkOption.NOFOLLOW_LINKS)) {
				Files.move(resourceDefault, resourceHello, StandardCopyOption.REPLACE_EXISTING);
				Files.move(resourceEasy, resourceDefault, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
