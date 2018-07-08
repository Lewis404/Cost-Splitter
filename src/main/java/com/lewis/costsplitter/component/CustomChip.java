package com.lewis.costsplitter.component;

/*
 * User: Lewis
 * Date: 15/06/2018
 * Time: 14:40
 */

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.beans.property.StringProperty;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;

public class CustomChip extends HBox {
	private JFXButton  close;
	private Label      text;
	private boolean    isAnimating = false;
	private Transition transition;
	private Bounds     positionBeforeAnimating;
	private double     opacityBeforeAnimating;

	public CustomChip() {
		super();
		this.close = new JFXButton();
		FontIcon icon = new FontIcon();
		icon.setIconCode(Material.CLOSE);
		icon.setIconColor(new Color(0.85, 0.85, 0.85, 1));
		icon.setIconSize(16);
		close.setGraphic(icon);
		this.text = new Label();
		this.close.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				remove(500, 0);
			}
		});
		this.getStyleClass().add("chip");
		this.getChildren().addAll(close, text);
		this.setOnMouseClicked(event -> {
			if (event.getButton().equals(MouseButton.PRIMARY)) {
				if (isAnimating) {
					transition.stop();
					this.setOpacity(opacityBeforeAnimating);
					this.setTranslateX(positionBeforeAnimating.getMinX() - this.getBoundsInLocal().getMinX());
				}
			}
		});
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

	public void remove(double duration, double delay) {
		if (this.getParent() instanceof Pane) {
			opacityBeforeAnimating = this.getOpacity();
			positionBeforeAnimating = this.getBoundsInLocal();
			transition = getRemoveAnimation(duration, delay);
			isAnimating = true;
			transition.play();
		}
	}

	public Transition getRemoveAnimation(double duration, double delay) {
		PauseTransition pause = new PauseTransition(Duration.millis(delay));

		pause.setOnFinished(e -> {
			TranslateTransition slide = new TranslateTransition(Duration.millis(duration), this);
			slide.setToX(-this.getWidth());

			FadeTransition fade = new FadeTransition(Duration.millis(duration), this);
			fade.setFromValue(this.getOpacity());
			fade.setToValue(0.0);

			ParallelTransition transition = new ParallelTransition();
			transition.getChildren().addAll(slide, fade);

			transition.setOnFinished(event -> ((Pane) this.getParent()).getChildren().remove(this));

			transition.play();
		});


		return pause;
	}

	@Override
	public String toString() {
		return "CustomChip{" + "text=" + text.getText() + ", bounds=" + this.localToScene(this.getBoundsInLocal()) +
		       "}";
	}
}

