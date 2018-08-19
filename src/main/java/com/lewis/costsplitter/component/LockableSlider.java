package com.lewis.costsplitter.component;
/*
 * User: Lewis
 * Date: 19/08/2018
 * Time: 13:52
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.lewis.costsplitter.validator.BoundValueValidator;
import com.lewis.costsplitter.validator.RegexValidator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;

import java.util.Currency;
import java.util.Locale;


public class LockableSlider extends HBox {

	// UI elements
	private JFXButton    lock;
	private FontIcon     icon;
	private JFXSlider    slider;
	private JFXTextField textField;

	// Properties
	private SimpleBooleanProperty disable;
	private SimpleDoubleProperty  totalProperty;
	private SimpleDoubleProperty  currentValue;
	private boolean hasError;

	public LockableSlider() {
		this(200, 25, 36, 100);
	}

	public LockableSlider(double total) {
		this(200, 25, 36, total);
	}

	public LockableSlider(double sliderWidth, double sliderHeight, int iconSize, double total) {
		super();

		// Initialise
		totalProperty = new SimpleDoubleProperty(total);
		disable = new SimpleBooleanProperty(false);
		currentValue = new SimpleDoubleProperty();
		hasError = false;
		icon = new FontIcon();
		lock = new JFXButton();
		slider = new JFXSlider();
		textField = new JFXTextField();

		// Calculate the current value using the slider as a percentage
		currentValue.bind(slider.valueProperty().divide(100).multiply(total));

		// Set up icon switching
		disable.addListener(((observable, oldValue, newValue) -> {
			if (newValue) {
				icon.setIconCode(Material.LOCK_OPEN);
			} else {
				icon.setIconCode(Material.LOCK_OUTLINE);
			}
		}));

		// Set up icon
		icon.setIconCode(Material.LOCK_OUTLINE);
		icon.setIconSize(iconSize);

		// Set up lock button
		lock.setButtonType(JFXButton.ButtonType.FLAT);
		lock.setRipplerFill(Color.WHITE);
		lock.setGraphic(icon);
		lock.setOnMouseClicked(event -> disable.set(!disable.get()));

		// Set up slider
		slider.setMinWidth(sliderWidth);
		slider.setMinHeight(sliderHeight);
		slider.disableProperty().bind(disable);
		slider.setValueFactory(jfxSlider -> Bindings.createStringBinding(() -> ((int) jfxSlider.getValue()) + "%",
		                                                                 slider.valueProperty()));
		slider.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (hasError) {
				textField.resetValidation();
				hasError = false;
			}
		});

		// Set up text input and validators
		FontIcon error = new FontIcon();
		error.setIconCode(Material.ERROR_OUTLINE);
		error.setIconSize(16);
		error.setIconColor(Color.RED);

		BoundValueValidator numberValidator = new BoundValueValidator(0, total);
		numberValidator.setMessage("Input too large");
		numberValidator.setIcon(error);

		RegexValidator regexValidator = new RegexValidator("[0-9]+([.,][0-9]{1,2})?$"); // only allow strings of the form 0*(.,00)
		regexValidator.setMessage("Invalid input");
		regexValidator.setIcon(error);

		textField.getValidators().addAll(numberValidator, regexValidator);
		textField.setOnAction(event -> {
			if (textField.validate()) {
				updateSlider(textField.getText());
			} else {
				hasError = true;
			}
		});
		// only allow [0-9.]
		addTextFieldFilter(KeyEvent.KEY_TYPED, event -> {
			if (!"0123456789.".contains(event.getCharacter())) {
				event.consume();
			}
		});
		textField.disableProperty().bind(disable);
		// unbind when focused
		textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				textField.textProperty().unbind();
			} else {
				bind();
			}
		});
		bind();
		textField.setPrefWidth(50);

		Label currency = new Label();
		currency.setText(Currency.getInstance(Locale.getDefault()).getSymbol());
		currency.disableProperty().bind(disable);

		HBox inputSection = new HBox();
		inputSection.setSpacing(0);
		inputSection.setAlignment(Pos.CENTER);
		inputSection.getChildren().addAll(currency, textField);

		this.getChildren().addAll(lock, slider, inputSection);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(15);
		this.autosize();

	}

	private void updateSlider(String text) {
		double input = Double.parseDouble(text);
		double value = (input / totalProperty.get()) * 100;
		slider.valueProperty().set(value);
	}

	private void bind() {
		textField.textProperty().bind(currentValue.asString("%.2f"));
	}

	public void addTextFieldFilter(EventType<KeyEvent> eventType, EventHandler<? super KeyEvent> eventFilter) {
		textField.addEventFilter(eventType, eventFilter);
	}

	public void removeTextFieldFilter(EventType<KeyEvent> eventType, EventHandler<? super KeyEvent> eventFilter) {
		textField.removeEventFilter(eventType, eventFilter);
	}

	public double getCurrentValue() {
		return currentValue.get();
	}
}
