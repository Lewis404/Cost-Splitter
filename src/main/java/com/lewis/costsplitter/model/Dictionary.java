package com.lewis.costsplitter.model;/*
 * User: Lewis
 * Date: 06/07/2018
 * Time: 14:05
 */

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class Dictionary {
	private static                 Dictionary      ourInstance = new Dictionary();
	private static @Getter @Setter TreeSet<String> names;

	private Dictionary() {
		names = new TreeSet<>();
	}

	public static Dictionary getInstance() {
		return ourInstance;
	}

	public static void addNames(String... names) {
		Dictionary.names.addAll(Arrays.asList(names));
	}

	public static void addNames(List<String> names) {
		Dictionary.names.addAll(names);
	}

	public static void clearNames() {
		Dictionary.names.clear();
	}

	public static void print() {
		Dictionary dictionary = Dictionary.getInstance();
		System.out.println(dictionary.toString());
	}

	@Override
	public String toString() {
		return "Dictionary{" + "names=" + names + "}";
	}
}
