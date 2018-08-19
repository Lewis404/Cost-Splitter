package com.lewis.costsplitter.model;/*
 * User: Lewis
 * Date: 06/07/2018
 * Time: 14:05
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lewis.costsplitter.utils.FileUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class Dictionary {
	private static          Dictionary      ourInstance = new Dictionary();
	private static          Gson            gson;
	private @Getter @Setter TreeSet<String> names;


	private Dictionary() {
		names = new TreeSet<>();
		gson = (new GsonBuilder().setPrettyPrinting()).create();
	}

	public static Dictionary getInstance() {
		return ourInstance;
	}

	public static void addNames(String... names) {
		ourInstance.names.addAll(Arrays.asList(names));
	}

	public static void addNames(List<String> names) {
		ourInstance.names.addAll(names);
	}

	public static void clearNames() {
		ourInstance.names.clear();
	}

	public static void print() {
		System.out.println(ourInstance.toString());
	}

	public static void load() {
		try {
			Path       resourceAsPath = FileUtils.getResourceAsPath("dictionary.json");
			FileReader reader         = new FileReader(resourceAsPath.toFile());
			ourInstance = gson.fromJson(reader, Dictionary.class);
		} catch (URISyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void save() {
		try {
			File       file       = FileUtils.getResourceAsPath("dictionary.json").toFile();
			String     json       = gson.toJson(ourInstance);

			PrintWriter writer = new PrintWriter(file);
			writer.print(json);
			writer.close();
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Dictionary{" + "names=" + names + "}";
	}
}
