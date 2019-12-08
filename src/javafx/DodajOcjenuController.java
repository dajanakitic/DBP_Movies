package javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import baza.BazaPodataka;
import entiteti.Film;
import entiteti.Ocjena;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajOcjenuController {

	@FXML
	private ComboBox<Film> filmComboBox;
	@FXML
	private TextField napomenaTextField;
	@FXML
	private ComboBox<Integer> ocjenaComboBox;
	@FXML
	private Button spremiButton;
	
	@FXML
	public void initialize() throws SQLException, IOException {
		ObservableList<Film> oListaFilmova = FXCollections.observableArrayList(BazaPodataka.dohvatiFilmove());
		filmComboBox.setItems(oListaFilmova);
		filmComboBox.setValue(oListaFilmova.get(0));
		
		List<Integer> moguceOcjene=new ArrayList<>();
		moguceOcjene.add(1);
		moguceOcjene.add(2);
		moguceOcjene.add(3);
		moguceOcjene.add(4);
		moguceOcjene.add(5);
		ObservableList<Integer> oMoguceOcjene = FXCollections.observableArrayList(moguceOcjene);
		ocjenaComboBox.setItems(oMoguceOcjene);
		ocjenaComboBox.setValue(oMoguceOcjene.get(0));
	}
	
	public void spremi() throws SQLException, IOException {
		int noviId = getZadnjiId() + 1;
		Film film = filmComboBox.getValue();
		String napomena=napomenaTextField.getText();
		Integer ocjenaFilma=ocjenaComboBox.getValue();
		
		Ocjena ocjena = new Ocjena(noviId,film, napomena, ocjenaFilma);

		try {
			OcjeneController.dodajNovuOcjenu(ocjena);
			BazaPodataka.spremiOcjenu(ocjena);
			BazaPodataka.updateOcjena(film, ocjenaFilma);
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getZadnjiId() throws SQLException, IOException {
		return BazaPodataka.dohvatiOcjene().size();
	}
	
}
