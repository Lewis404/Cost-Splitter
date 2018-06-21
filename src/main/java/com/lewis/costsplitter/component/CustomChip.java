package com.lewis.costsplitter.component;

/*
 * User: Lewis
 * Date: 15/06/2018
 * Time: 14:40
 */

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CustomChip extends HBox {
	private JFXButton close;
	private Label     text;

	public CustomChip() {
		super();
		this.close = new JFXButton("X");
		this.text = new Label();
		this.close.setOnMouseClicked(event -> remove());
		this.getStyleClass().add("chip");
		this.getChildren().addAll(close, text);
	}

	public CustomChip(String value) {
		this();
		setText(value);
	}

	public String getText() {
		return textProperty().get();
	}

	public void setText(String value) {
		textProperty().set(value);
	}

	public StringProperty textProperty() {
		return text.textProperty();
	}

	public void remove() {
		if (this.getParent() instanceof Pane) {
			((Pane) this.getParent()).getChildren().remove(this);
		}
	}

	@Override
	public String toString() {
		return "CustomChip{" + "text=" + text.getText() + ", bounds=" + this.localToScene(this.getBoundsInLocal()) +
		       "}";
	}

}
