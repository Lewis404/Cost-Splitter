package com.lewis.costsplitter;

/*
 * User: Lewis
 * Date: 09/06/2018
 * Time: 17:58
 */


import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CostSplitter extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent parent = FXMLLoader.load(Objects.requireNonNull(
				this.getClass().getClassLoader().getResource("views/people.fxml")));

		JFXDecorator decorator = new JFXDecorator(primaryStage, parent, false, false, true);
		decorator.setTitle("Cost Splitter");
//		decorator.setCustomMaximize(true);
		primaryStage.setScene(new Scene(decorator));
		primaryStage.show();
	}
}
