package com.lewis.costsplitter.validator;
/*
 * User: Lewis
 * Date: 19/08/2018
 * Time: 17:39
 */

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator extends ValidatorBase {

	private String  regex;
	private Pattern pattern;

	public RegexValidator(String message, String regex) {
		super(message);
		setRegex(regex);
	}

	public RegexValidator(String regex) {
		this("", regex);
	}

	public RegexValidator() {
		super();
	}

	public void setRegex(String regex) {
		this.regex = regex;
		compile();
	}

	private void compile() {
		pattern = Pattern.compile(regex);
	}

	@Override
	protected void eval() {
		if (pattern == null) {
			throw new IllegalArgumentException("A regex pattern needs to be set");
		}
		if (!(srcControl.get() instanceof TextInputControl)) {
			throw new IllegalStateException("Source node must be an instance of TextInputControl");
		}
		Matcher matcher = pattern.matcher(((TextInputControl) srcControl.get()).getText());
		hasErrors.set(!matcher.matches());
	}
}
