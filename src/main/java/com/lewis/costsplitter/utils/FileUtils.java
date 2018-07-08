package com.lewis.costsplitter.utils;
/*
 * User: Lewis
 * Date: 15/06/2018
 * Time: 21:17
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lewis.costsplitter.model.Dictionary;

import java.io.*;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FileUtils {

	private static Gson gson = (new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.TRANSIENT)).create();

	private static void saveDictionary() {
		Dictionary dictionary = Dictionary.getInstance();
		String toWrite = gson.toJson(dictionary);
		try {
			Path resourceAsPath = getResourceAsPath("dictionary.json");

			File file = resourceAsPath.toFile();
			file.createNewFile();

			PrintWriter writer = new PrintWriter(file);
			writer.print(toWrite);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void initialiseDictionary() {
		try {
			Path resourceAsPath = getResourceAsPath("dictionary.json");
			FileReader reader = new FileReader(resourceAsPath.toFile());
			Dictionary dictionary = gson.fromJson(reader, Dictionary.class);
		} catch (URISyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static Set<String> clean(Set<String> set) {
		return set.stream().filter(s -> !s.isEmpty() && !s.trim().isEmpty())
		          .collect(Collectors.toCollection(HashSet::new));
	}

	public static Path getResourceAsPath(String file) throws URISyntaxException {
		URL url = getContextClassLoader().getResource(file);
		assert url != null;
		return Paths.get(url.toURI());
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
