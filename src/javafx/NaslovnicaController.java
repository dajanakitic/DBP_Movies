package javafx;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NaslovnicaController {

	public void prikaziFilmove() {
		pregled("Filmovi.fxml");
	}

	public void dodajFilm() {
		prikaziFilmove();
		noviProzor("DodajFilm.fxml");
	}

	public void prikaziOcjene() {
		pregled("Ocjene.fxml");
	}
	
	public void dodajOcjenu() {
		prikaziOcjene();
		noviProzor("DodajOcjenu.fxml");
	}
	
	public void prikaziOsobe() {
		pregled("Osobe.fxml");
	}

	public void dodajOsobu() {
		prikaziOsobe();
		noviProzor("DodajOsobu.fxml");
	}

	public void dodajOsobaVsFilm() {
		prikaziOsobe();
		noviProzor("DodajOsobuVsFilm.fxml");
	}
	
	public void pregled(String fxml) {
		try {
			BorderPane pane = FXMLLoader.load(Main.class.getResource(fxml));
			Main.setCenterPane(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void noviProzor(String fxml) {
		try {
			BorderPane Pane = FXMLLoader.load(Main.class.getResource(fxml));
			Scene scene = new Scene(Pane, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.getIcons().add(new Image("file:img\\icon.jpg"));
			stage.initModality(Modality.APPLICATION_MODAL); 
			stage.showAndWait();
			//stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
