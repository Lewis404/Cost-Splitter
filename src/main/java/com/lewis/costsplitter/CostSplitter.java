package com.lewis.costsplitter;

/*
 * User: Lewis
 * Date: 09/06/2018
 * Time: 17:58
 */


import com.lewis.costsplitter.model.Dictionary;
import com.lewis.costsplitter.utils.FileUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CostSplitter extends Application {

	public static void main(String[] args) {
		Dictionary.load();
		System.out.println(Dictionary.getInstance());
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent parent = FXMLLoader.load(Objects.requireNonNull(
				this.getClass().getClassLoader().getResource("views/people.fxml")));
		primaryStage.getIcons().addAll(getIcons());
		primaryStage.setTitle("Cost Splitter");
		primaryStage.setScene(new Scene(parent));
		primaryStage.show();
	}

	private List<Image> getIcons() throws IOException {
		List<Image> images = new ArrayList<>();

		for (InputStream resource : FileUtils.getResourceFiles("images/icon/coin")) {
			Image image = new Image(resource);
			images.add(image);
		}

		return images;
	}

}
