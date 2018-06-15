package com.lewis.costsplitter.utils;
/*
 * User: Lewis
 * Date: 15/06/2018
 * Time: 21:17
 */

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
	public static Path getResourcePath(String file) throws URISyntaxException {
		URL url = FileUtils.class.getClassLoader().getResource(file);
		assert url != null;
		return Paths.get(url.toURI());
	}
}
