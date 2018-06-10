package com.lewis.costsplitter.model;

/*
 * User: Lewis
 * Date: 09/06/2018
 * Time: 17:57
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Table {

	@Getter @Setter private ObservableList<Item> items;
	@Getter @Setter private List<Person>         people;
	@Getter @Setter private int                  totalCost;

	public Table() {
		this.items = FXCollections.observableArrayList();
		this.people = new ArrayList<>();
	}

	public void addItem(Item item) {
		this.items.add(item);
	}

	public void addPerson(Person person) {
		this.people.add(person);
	}

	@Override
	public String toString() {
		return "Table{" + "items=" + items + ", people=" + people + ", totalCost=" + totalCost + '}';
	}
}
