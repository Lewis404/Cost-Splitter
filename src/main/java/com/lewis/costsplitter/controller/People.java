package com.lewis.costsplitter.controller;
/*
 * User: Lewis
 * Date: 10/06/2018
 * Time: 17:42
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.lewis.costsplitter.component.CustomChip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class People {

	@FXML public JFXButton    btnSubmit;
	@FXML public JFXButton    btnCancel;
	@FXML public JFXTextField txtPerson;
	@FXML public VBox         chipPane;
	@FXML public BorderPane   container;
	@FXML public ScrollPane   scrollPane;

	@FXML
	public void initialize() {
		container.sceneProperty().addListener(((obsScene, oldScene, newScene) -> {
			if (oldScene == null && newScene != null) {
				newScene.windowProperty().addListener(((obsWindow, oldWindow, newWindow) -> {
					if (oldWindow == null && newWindow != null) {
						((Stage) newWindow).setResizable(false);
						((Stage) newWindow).setIconified(false);
					}
				}));
			}
		}));
	}

	@FXML
	public void submit() {
		List<String> text = new ArrayList<>();
		for (Iterator<Node> iterator = chipPane.getChildren().iterator(); iterator.hasNext(); ) {
			CustomChip node = (CustomChip) iterator.next();
			text.add(node.getText());
			iterator.remove();
		}

//		Node  source = (Node) event.getSource();
//		Stage stage  = (Stage) source.getScene().getWindow();
//		stage.close();
		System.out.println(text);
	}

	@FXML
	public void addPerson() {
		String text = txtPerson.getText();
		System.out.println("text -> \"" + text + "\"");
		if (!text.isEmpty()) {
			CustomChip chip = new CustomChip();
			chip.setText(text);
			chipPane.getChildren().add(chip);
			txtPerson.clear();
			txtPerson.requestFocus();
		}
	}

	@FXML
	public void close(ActionEvent event) {
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	}
}
