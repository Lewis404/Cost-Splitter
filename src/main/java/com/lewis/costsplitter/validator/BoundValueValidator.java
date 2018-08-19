package com.lewis.costsplitter.validator;
/*
 * User: Lewis
 * Date: 19/08/2018
 * Time: 17:00
 */

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class BoundValueValidator extends ValidatorBase {

	private double min;
	private double max;

	public BoundValueValidator(String message, double min, double max) {
		super(message);
		this.min = min;
		this.max = max;
	}

	public BoundValueValidator(double min, double max) {
		this("", min, max);
	}

	@Override
	protected void eval() {
		if (srcControl.get() instanceof TextInputControl) {
			TextInputControl inputControl = (TextInputControl) srcControl.get();
			double input = Double.parseDouble(inputControl.getText());
			if (input >= min && input <= max) {
				hasErrors.set(false);
			} else {
				hasErrors.set(true);
			}
		} else {
			throw new IllegalStateException("Source node must be an instance of TextInputControl");
		}
	}
}
