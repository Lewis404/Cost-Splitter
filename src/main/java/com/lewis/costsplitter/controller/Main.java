package com.lewis.costsplitter.controller;
/*
 * User: Lewis
 * Date: 09/06/2018
 * Time: 18:24
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.lewis.costsplitter.model.Item;
import com.lewis.costsplitter.model.Person;
import com.lewis.costsplitter.model.Table;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class Main {
	private static ObservableList<Person>      people     = FXCollections.observableArrayList();
	public         JFXTreeTableView<Item>      tblItems;
	public         JFXTextField                txtItemPrice;
	public         FlowPane                    checkListPane;
	public         JFXButton                   btnAddItem;
	public         JFXButton                   btnNew;
	public         JFXButton                   btnReset;
	public         AnchorPane                  container;
	public         JFXButton                   btnFinish;
	private        ObservableList<JFXCheckBox> checkBoxes = FXCollections.observableArrayList();
	private        Table                       activeTable;

	public static void addPerson(Person person) {
		people.add(person);
	}

	@FXML
	public void initialize() {
		checkBoxes.addListener((ListChangeListener<JFXCheckBox>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					for (int i = c.getFrom(); i < c.getTo(); i++) {
						JFXCheckBox checkBox = c.getList().get(i);
						checkListPane.getChildren().add(checkBox);
					}
				}
			}
		});

		people.addListener((ListChangeListener<Person>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					for (int i = c.getFrom(); i < c.getTo(); i++) {
						Person p = c.getList().get(i);
						checkBoxes.add(new JFXCheckBox(p.getNameProperty().getValue()));
						activeTable.addPerson(p);
					}
				}
			}
		});
	}

	public void addItem(ActionEvent actionEvent) {

	}

}
