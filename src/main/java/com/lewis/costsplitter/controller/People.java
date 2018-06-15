package com.lewis.costsplitter.controller;
/*
 * User: Lewis
 * Date: 10/06/2018
 * Time: 17:42
 */

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.lewis.costsplitter.component.CustomChip;
import com.lewis.costsplitter.utils.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class People {

	@FXML public JFXButton    btnSubmit;
	@FXML public JFXButton    btnCancel;
	@FXML public JFXTextField txtPerson;
	@FXML public VBox         chipPane;
	@FXML public BorderPane   container;
	@FXML public ScrollPane   scrollPane;

	private Set<String> names;

	@FXML
	public void initialize() {
		names = loadNames();

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
// TODO: 15/06/2018 Try to get autocomplete working
//		JFXAutoCompletePopup<String> autoComplete = new JFXAutoCompletePopup<>();
//		autoComplete.getSuggestions().addAll(names);
//
//		txtPerson.textProperty().addListener(observable -> {
//			// Make the filter
//			autoComplete.filter(item -> item.toLowerCase().contains(txtPerson.getText().toLowerCase()));
//
//			//Hide the popup if the suggestions are empty
//			if (autoComplete.getFilteredSuggestions().isEmpty()) {
//				autoComplete.hide();
//			} else {
//				autoComplete.show(txtPerson);
//			}
//		});
	}

	private Set<String> loadNames() {
		try {
			Path         path  = FileUtils.getResourcePath("auto-complete/people.txt");
			List<String> names = Files.readAllLines(path, Charset.defaultCharset());
			System.out.println(names);
			return new HashSet<>(names);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		return new HashSet<>();
	}

	private void saveNames(Set<String> names) {
		if (!names.isEmpty()) {
			try {
				Path          path    = FileUtils.getResourcePath("auto-complete/people.txt");
				StringBuilder builder = new StringBuilder("\n");
				names.forEach(s -> builder.append(s).append("\n"));
				Files.write(path, builder.toString().getBytes(), StandardOpenOption.APPEND);
			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void submit() {
		List<String> text     = new ArrayList<>();
		Set<String>  newNames = new HashSet<>();
		for (Iterator<Node> iterator = chipPane.getChildren().iterator(); iterator.hasNext(); ) {
			CustomChip chip = (CustomChip) iterator.next();
			String     name = chip.getText();
			text.add(name);
			if (!names.contains(name)) {
				newNames.add(name);
			}
			iterator.remove();
		}

//		Node  source = (Node) event.getSource();
//		Stage stage  = (Stage) source.getScene().getWindow();
//		stage.close();
		System.out.println(text);
		System.out.println(newNames);
		saveNames(newNames);
	}

	@FXML
	public void addPerson() {
		String text = txtPerson.getText();
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
