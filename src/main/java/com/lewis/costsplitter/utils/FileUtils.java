package com.lewis.costsplitter.utils;
/*
 * User: Lewis
 * Date: 15/06/2018
 * Time: 21:17
 */

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FileUtils {
	public static Path getResourcePath(String file) throws URISyntaxException {
		URL url = FileUtils.class.getClassLoader().getResource(file);
		assert url != null;
		return Paths.get(url.toURI());
	}

	private static Set<String> clean(Set<String> set) {
		return set.stream().filter(s -> !s.isEmpty() && !s.trim().isEmpty()).collect(Collectors.toCollection(HashSet::new));
	}

	public static class Autocomplete {

		public static class Names {

			public static Set<String> load() {
				try {
					Path         path  = getResourcePath("auto-complete/people.txt");
					List<String> names = java.nio.file.Files.readAllLines(path, Charset.defaultCharset());
					System.out.println(names);
					return clean(new HashSet<>(names));
				} catch (URISyntaxException | IOException e) {
					e.printStackTrace();
				}
				return new HashSet<>();
			}

			public static void save(Set<String> names, StandardOpenOption openOption) {
				names = clean(names);
				if (!names.isEmpty()) {

					try {
						Path          path    = getResourcePath("auto-complete/people.txt");
						StringBuilder builder = new StringBuilder("\n");
						names.forEach(s -> builder.append(s).append("\n"));
						Files.write(path, builder.toString().getBytes(), openOption);
					} catch (URISyntaxException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}
