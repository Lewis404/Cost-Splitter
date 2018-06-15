package com.lewis.costsplitter.model;/*
 * User: Lewis
 * Date: 15/06/2018
 * Time: 14:03
 */

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Getter;
import lombok.Setter;

import java.text.NumberFormat;

public class Cost {

	@Getter @Setter private DoubleProperty costProperty;

	public Cost() {
		this.costProperty = new SimpleDoubleProperty(0.0);
	}

	public Cost(double cost) {
		this();
		setCost(cost);
	}

	public double getCost() {
		return getCostProperty().get();
	}

	public void setCost(double cost) {
		this.costProperty.set(cost);
	}

	@Override
	public String toString() {
		if (costProperty != null) {
			NumberFormat format = NumberFormat.getCurrencyInstance();
			return format.format(costProperty.get());
		}
		return "-";
	}
}
