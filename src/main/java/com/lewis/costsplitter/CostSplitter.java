package com.lewis.costsplitter;

/*
 * User: Lewis
 * Date: 09/06/2018
 * Time: 17:58
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
		primaryStage.getIcons()
		            .add(new Image(this.getClass().getClassLoader().getResourceAsStream("images/split.png")));
		primaryStage.setTitle("Cost Splitter");
		primaryStage.setScene(new Scene(parent));
		primaryStage.show();
	}
}
