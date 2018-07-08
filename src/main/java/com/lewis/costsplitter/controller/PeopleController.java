package com.lewis.costsplitter.controller;
/*
 * User: Lewis
 * Date: 10/06/2018
 * Time: 17:42
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import com.lewis.costsplitter.component.CustomChip;
import com.lewis.costsplitter.model.Dictionary;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PeopleController {

	@FXML public JFXButton    btnSubmit;
	@FXML public JFXButton    btnCancel;
	@FXML public JFXTextField txtPerson;
	@FXML public VBox         chipBox;
	@FXML public BorderPane   container;
	@FXML public ScrollPane   scrollPane;
	private      ContextMenu  autoCompleteMenu;
	private      boolean      submitting;

	@FXML
	public void initialize() {
		TreeSet<String> names = Dictionary.getInstance().getNames();

		// Set the scene to be not be resizeable
		container.sceneProperty().addListener(((obsScene, oldScene, newScene) -> {
			if (oldScene == null && newScene != null) {
				newScene.windowProperty().addListener(((obsWindow, oldWindow, newWindow) -> {
					if (oldWindow == null && newWindow != null) {
						((Stage) newWindow).setResizable(false);
					}
				}));
			}
		}));

		// disable the submit button if nothing is added
		btnSubmit.disableProperty().bind(Bindings.isEmpty(chipBox.getChildren()));

		JFXScrollPane.smoothScrolling(scrollPane);

		autoCompleteMenu = new ContextMenu();
		txtPerson.textProperty().addListener(observable -> {
			if (txtPerson.getText().length() == 0) {
				autoCompleteMenu.hide();
			} else {
				LinkedList<String> results =
						names.stream().filter(s -> s.toLowerCase().contains(txtPerson.getText().toLowerCase()))
						     .collect(Collectors.toCollection(LinkedList::new));
				if (results.size() > 0) {
					populateAutoComplete(results);
					if (!autoCompleteMenu.isShowing()) {
						autoCompleteMenu.show(txtPerson, Side.BOTTOM, 5, 2);
					}
				}

			}
		});

		txtPerson.requestFocus();
	}

	@FXML
	public void submit() {
		if (!submitting) {
			submitting = true;
			List<String> names = new ArrayList<>();
			chipBox.getChildrenUnmodifiable().stream().map(node -> ((CustomChip) node).getText()).forEach(names::add);
			Dictionary.addNames(names);
			Dictionary.save();

			Timeline             closing          = new Timeline();
			ObservableList<Node> nodes            = chipBox.getChildrenUnmodifiable();
			int                  keyFrameDuration = 350;
			for (int i = 0; i < nodes.size(); i++) {
				Node       node     = nodes.get(i);
				CustomChip chip     = ((CustomChip) node);
				KeyFrame   keyFrame = new KeyFrame(Duration.millis(keyFrameDuration * i), event -> chip.remove(350));
				closing.getKeyFrames().add(keyFrame);
			}

			int      wait  = (keyFrameDuration * (nodes.size() + 1));
			KeyFrame pause = new KeyFrame(Duration.millis(wait));
			closing.getKeyFrames().add(pause);

			closing.setOnFinished(event -> close());

			Platform.runLater(closing::play);
		} else {
			close();
		}
	}

	private void close() {
		Stage stage = (Stage) container.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void addPerson() {
		addPerson(txtPerson.getText());
	}

	private void addPerson(String text) {
		if (!text.isEmpty()) {
			CustomChip chip = new CustomChip(text);
			chipBox.getChildren().add(chip);
			txtPerson.clear();
			txtPerson.requestFocus();
		}
	}

	@FXML
	public void close(ActionEvent event) {
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	}

	private void populateAutoComplete(List<String> results) {
		autoCompleteMenu.getItems().clear();
		int limit = 5;
		if (results.size() > limit) {
			results = results.subList(0, limit);
		}
		List<MenuItem> items = new LinkedList<>();

		results.forEach(s -> {
			MenuItem item = new MenuItem(s);
			item.setOnAction(event -> txtPerson.setText(s));
			items.add(item);
		});

		autoCompleteMenu.getItems().addAll(items);
	}
}
