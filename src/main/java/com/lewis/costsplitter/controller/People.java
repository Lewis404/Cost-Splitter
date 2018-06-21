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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class People {

	@FXML public JFXButton    btnSubmit;
	@FXML public JFXButton    btnCancel;
	@FXML public JFXTextField txtPerson;
	@FXML public VBox         chipBox;
	@FXML public BorderPane   container;
	@FXML public ScrollPane   scrollPane;

	private Set<String> names;

	@FXML
	public void initialize() {
//		names = FileUtils.Autocomplete.Names.load();

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

		JFXScrollPane.smoothScrolling(scrollPane);

		// TODO: 21/06/2018 Figure out why fading isn't being applied and the nodes are not being retrieved correctly
//		scrollPane.vvalueProperty().addListener(
//				(observable, oldValue, newValue) -> performFade(getVisibleNodes(scrollPane, false), scrollPane,
//				                                                false));
//
//		chipBox.getChildren().addListener((ListChangeListener<Node>) c -> {
//			while (c.next()) {
//				if (c.wasRemoved()) {
//					chipBox.getChildren().forEach(node -> node.setOpacity(1.0));
//					List<Node> nodes = getVisibleNodes(scrollPane, true);
//					nodes.forEach(System.out::println);
//					performFade(nodes, scrollPane, true);
//				}
//			}
//		});

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

		txtPerson.requestFocus();
		for (int i = 0; i < 20; i++) {
			addPerson("chip" + i);
		}
	}

	@FXML
	public void submit() {
		List<String> text     = new ArrayList<>();
		Set<String>  newNames = new HashSet<>();
		for (Iterator<Node> iterator = chipBox.getChildren().iterator(); iterator.hasNext(); ) {
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
//		FileUtils.Autocomplete.Names.save(newNames, StandardOpenOption.APPEND);
	}

	@FXML
	public void addPerson() {
		addPerson(txtPerson.getText());
	}

	public void addPerson(String text) {
		if (!text.isEmpty()) {
			CustomChip chip = new CustomChip();
			chip.setText(text);
			chipBox.getChildren().add(chip);
			txtPerson.clear();
			txtPerson.requestFocus();
		}
	}

	@FXML
	public void close(ActionEvent event) {
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	}

	private void performFade(List<Node> nodes, ScrollPane pane, boolean refresh) {
		Bounds paneBounds = pane.localToScene(pane.getBoundsInParent());

		for (int i = 0; i < nodes.size(); i++) {
			Node   node        = nodes.get(i);
			Bounds localBounds = node.localToScene(node.getBoundsInLocal());
			double opacity     = 1.0; // Set to 1 by default
			if (i == 0 && localBounds.getMinY() < 0) { // Scrolling out of the top
				opacity = (localBounds.getMinY() + localBounds.getHeight()) / localBounds.getHeight();
			}
			if ((i == (nodes.size() - 1)) &&
			    (localBounds.getMaxY() > paneBounds.getMaxY())) { // Scrolling out of the bottom
				// On refresh the last item will be recognised as off the pane still as the removed element isn't
				// removed from the children yet
				opacity = (localBounds.getHeight() -
				           (localBounds.getMaxY() - paneBounds.getMaxY() - (refresh ? localBounds.getHeight() : 0.0)
				           )) /
				          localBounds.getHeight();
			}
			node.setOpacity(opacity);
		}
	}

	private List<Node> getVisibleNodes(ScrollPane pane, boolean refresh) {
		Set<Node> visibleNodes = new LinkedHashSet<>();
		Bounds    paneBounds   = pane.localToScene(pane.getBoundsInParent());
		printBounds(paneBounds, "\npane");
		if (pane.getContent() instanceof Parent) {
			ObservableList<Node> childrenUnmodifiable = ((Parent) pane.getContent()).getChildrenUnmodifiable();
			for (int i = 0; i < childrenUnmodifiable.size(); i++) {
				Node   node       = childrenUnmodifiable.get(i);
				Bounds nodeBounds = node.localToScene(node.getBoundsInLocal());
				printBounds(nodeBounds, ((CustomChip) node).getText());
				if (paneBounds.intersects(nodeBounds)) {
					visibleNodes.add(node);
					if (refresh && i + 1 < childrenUnmodifiable.size()) {
						visibleNodes.add(childrenUnmodifiable.get(i + 1));
					}
				}
			}
		}
		System.out.println("######################");
		System.out.println("Visible");
		visibleNodes.forEach(node -> printBounds(node.localToScene(node.getLayoutBounds()), ((CustomChip) node).getText()));
		return new ArrayList<>(visibleNodes);
	}

	private void printBounds(Bounds bounds, String name) {
		System.out.print(name + " -> ");
		System.out.printf("BoundingBox [minX:%s, minY:%s, width:%s, height:%s, maxX:%s, maxY:%s]%n", bounds.getMinX(),
		                  bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), bounds.getMaxX(), bounds.getMaxY());
	}
}
