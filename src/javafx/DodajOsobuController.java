package javafx;

import java.io.IOException;
import java.sql.SQLException;

import baza.BazaPodataka;
import entiteti.Osoba;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajOsobuController {

	@FXML
	private TextField imeTextField;
	@FXML
	private TextField prezimeTextField;
	@FXML
	private Button spremiButton;
	
	@FXML
	public void initialize() {
	}
	
	public void spremi() throws SQLException, IOException {
		int noviId = getZadnjiId() + 1;
		String ime = imeTextField.getText();
		String prezime = prezimeTextField.getText();
		
		Osoba osoba=new Osoba(noviId,ime,prezime);

		try {
			//OsobeController.dodajOsobu(osoba);
			BazaPodataka.spremiOsobu(osoba);
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getZadnjiId() throws SQLException, IOException {
		return BazaPodataka.dohvatiOsobe().size();
	}
	
}
