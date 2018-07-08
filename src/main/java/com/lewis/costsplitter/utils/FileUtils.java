package com.lewis.costsplitter.utils;
/*
 * User: Lewis
 * Date: 15/06/2018
 * Time: 21:17
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileUtils {


	private static Set<String> clean(Set<String> set) {
		return set.stream().filter(s -> !s.isEmpty() && !s.trim().isEmpty())
		          .collect(Collectors.toCollection(HashSet::new));
	}

	public static Path getResourceAsPath(String file) throws URISyntaxException {
		URL url = getContextClassLoader().getResource(file);
		return Paths.get(Objects.requireNonNull(url).toURI());
	}

	public static List<InputStream> getResourceFiles(String path) throws IOException {
		List<InputStream> filenames = new ArrayList<>();

		try (InputStream in = getResourceAsStream(path);
		     BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String resource;

			while ((resource = br.readLine()) != null) {
				InputStream stream = getResourceAsStream(path + "/" + resource);
				filenames.add(stream);
			}
		}

		return filenames;
	}

	private static InputStream getResourceAsStream(String resource) {
		final InputStream in = getContextClassLoader().getResourceAsStream(resource);
		return in == null ? FileUtils.class.getClass().getResourceAsStream(resource) : in;
	}

	private static ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

}
