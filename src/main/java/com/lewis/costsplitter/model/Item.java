package com.lewis.costsplitter.model;

/*
 * User: Lewis
 * Date: 09/06/2018
 * Time: 18:01
 */

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item extends RecursiveTreeObject<Item> {

	@Getter @Setter private StringProperty              nameProperty;
	@Getter @Setter private DoubleProperty              costProperty;
	@Getter @Setter private Map<Person, DoubleProperty> individualCost;

	public Item() {}

	public Item(String name, double cost, List<Person> people) {
		this.nameProperty = new SimpleStringProperty(name);
		this.costProperty = new SimpleDoubleProperty(cost);
		this.individualCost = new HashMap<>();
		people.forEach(this::addPerson);
	}

	public void addPerson(Person person) {
		individualCost.put(person, new SimpleDoubleProperty(0.0));
	}

	public void split(List<Person> people) {
		double cost = this.costProperty.doubleValue() / people.size();
		people.forEach(person -> {
			SimpleDoubleProperty property = (SimpleDoubleProperty) individualCost.get(person);
			property.set(cost);
			individualCost.put(person, property);
		});
	}

	@Override
	public String toString() {
		return "Item{" + "nameProperty=" + nameProperty + ", costProperty=" + costProperty + ", individualCost=" +
		       individualCost + '}';
	}
}
