package com.lewis.costsplitter.model;

/*
 * User: Lewis
 * Date: 09/06/2018
 * Time: 18:01
 */

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item extends RecursiveTreeObject<Item> {

	@Getter @Setter private StringProperty    nameProperty;
	@Getter @Setter private Cost              cost;
	@Getter @Setter private Map<Person, Cost> individualCost;

	public Item() {}

	public Item(String name, double cost, List<Person> people) {
		this.nameProperty = new SimpleStringProperty(name);
		this.cost = new Cost(cost);
		this.individualCost = new HashMap<>();
		people.forEach(this::addPerson);
	}

	public void addPerson(Person person) {
		individualCost.put(person, new Cost(0.0));
	}

	public void split(List<Person> people) {
		double cost = this.cost.getCost() / people.size();
		people.forEach(person -> {
			Cost indiCost = individualCost.get(person);
			indiCost.setCost(cost);
			individualCost.put(person, indiCost);
		});
	}

	@Override
	public String toString() {
		return "Item{" + "nameProperty=" + nameProperty + ", cost=" + cost + ", individualCost=" + individualCost +
		       '}';
	}
}
