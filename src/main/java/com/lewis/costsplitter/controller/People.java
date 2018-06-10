package com.lewis.costsplitter.controller;
/*
 * User: Lewis
 * Date: 10/06/2018
 * Time: 17:42
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.lewis.costsplitter.model.Person;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class People {

	public JFXChipView<String> chips;
	public JFXButton           btnSubmit;
	public BorderPane          container;

	public void submit(ActionEvent event) {
		chips.getChips().stream().map(Person::new).forEach(Main::addPerson);

		Node  source = (Node)  event.getSource();
		Stage stage  = (Stage) source.getScene().getWindow();
		stage.close();
	}
}
