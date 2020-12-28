package fractal_compression;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Class<? extends Main> s = getClass();
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("AppView.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

	public static void main(String[] args) {
		launch(args);
	}

}
