package javafx;

import java.io.IOException;
import java.sql.SQLException;

import baza.BazaPodataka;
import entiteti.Film;
import entiteti.Zanr;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajFilmController {
	
	@FXML
	private TextField nazivFilmaTextField;
	@FXML
	private ComboBox<Zanr> zanrComboBox;
	@FXML
	private TextField godinaTextField;
	@FXML
	private Button spremiButton;
	
	@FXML
	public void initialize() {
		ObservableList<Zanr> oListaZanrova = FXCollections.observableArrayList(Zanr.values());
		zanrComboBox.setItems(oListaZanrova);
		zanrComboBox.setValue(oListaZanrova.get(0));
	}
	
	public void spremi() throws SQLException, IOException {
		int noviId = getZadnjiId() + 1;
		String nazivFilma = nazivFilmaTextField.getText();
		Zanr zanr = zanrComboBox.getValue();
		Integer godina = Integer.parseInt(godinaTextField.getText());
		
		Film film=new Film(noviId,nazivFilma,zanr,godina);

		try {
			FilmoviController.dodajNoviFilm(film);
			BazaPodataka.spremiFilm(film);
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getZadnjiId() throws SQLException, IOException {
		return BazaPodataka.dohvatiFilmove().size();
	}

}
