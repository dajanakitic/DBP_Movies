package javafx;

import java.io.IOException;
import java.sql.SQLException;

import baza.BazaPodataka;
import entiteti.Film;
import entiteti.Osoba;
import entiteti.OsobaVSFilm;
import entiteti.Uloga;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class DodajOsobuVsFilmController {

	@FXML
	private ComboBox<Osoba> osobaComboBox;
	@FXML
	private ComboBox<Film> filmComboBox;
	@FXML
	private ComboBox<Uloga> ulogaComboBox;
	@FXML
	private Button spremiButton;

	@FXML
	public void initialize() throws SQLException, IOException {
		ObservableList<Osoba> oListaOsoba = FXCollections.observableArrayList(BazaPodataka.dohvatiOsobe());
		osobaComboBox.setItems(oListaOsoba);
		osobaComboBox.setValue(oListaOsoba.get(0));

		ObservableList<Film> oListaFilmova = FXCollections.observableArrayList(BazaPodataka.dohvatiFilmove());
		filmComboBox.setItems(oListaFilmova);
		filmComboBox.setValue(oListaFilmova.get(0));

		ObservableList<Uloga> oListaUloga = FXCollections.observableArrayList(Uloga.values());
		ulogaComboBox.setItems(oListaUloga);
		ulogaComboBox.setValue(oListaUloga.get(0));
	}
	
	public void spremi() throws SQLException, IOException {
		int noviId = getZadnjiId() + 1;
		Osoba osoba=osobaComboBox.getValue();
		Film film = filmComboBox.getValue();
		Uloga uloga=ulogaComboBox.getValue();
		
		OsobaVSFilm osVSfi = new OsobaVSFilm(noviId,osoba.getImeOsobe(),osoba.getPrezimeOsobe(),uloga,film);
		
		try {
			OsobeController.dodajNovuOsobuVSFilm(osVSfi);
			BazaPodataka.spremiOsobuVsFilm(osVSfi);
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getZadnjiId() throws SQLException, IOException {
		return BazaPodataka.dohvatiOsobeVSfilm().size();
	}
}
