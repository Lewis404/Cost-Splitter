package com.lewis.costsplitter.model;

/*
 * User: Lewis
 * Date: 09/06/2018
 * Time: 18:01
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

public class Person {

	@Getter @Setter private StringProperty nameProperty;

	public Person(String name) {
		this.nameProperty = new SimpleStringProperty(name);
	}

	@Override
	public String toString() {
		return "Person{" + "nameProperty=" + nameProperty + '}';
	}
}
