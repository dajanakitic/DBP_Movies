package javafx;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private static BorderPane root;
	private Stage primaryStage;
	
	@Override
	public void start(Stage stage) {
		primaryStage = stage;
		try {
			root = (BorderPane) FXMLLoader.load(getClass().getResource("Naslovnica.fxml"));
			BorderPane mjestaPane = FXMLLoader.load(Main.class.getResource("Filmovi.fxml"));
		    root.setCenter(mjestaPane);
		    
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("file:img\\icon.jpg"));
			primaryStage.setTitle("Filmovi");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setCenterPane(BorderPane centerPane) {
		root.setCenter(centerPane);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
