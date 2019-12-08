package javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import baza.BazaPodataka;
import entiteti.Film;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class FilmoviController {
	
	List<Film> listaFilmova = new ArrayList<>();
	static ObservableList<Film> oListaFilmova = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Film> filmoviTableView;
	@FXML
	private TextField filmoviFilterTextField;
	@FXML
	private TableColumn<Film, String> idColumn;
	@FXML
	private TableColumn<Film, String> nazivColumn;
	@FXML
	private TableColumn<Film, String> zanrColumn;
	@FXML
	private TableColumn<Film, String> godinaColumn;
	@FXML
	private TableColumn<Film, String> ocjenaColumn;
	
	@FXML
	public void initialize() throws SQLException, IOException {
		
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nazivColumn.setCellValueFactory(new PropertyValueFactory<>("nazivFilma"));
		zanrColumn.setCellValueFactory(new PropertyValueFactory<>("zanr"));
		godinaColumn.setCellValueFactory(new PropertyValueFactory<>("godina"));
		ocjenaColumn.setCellValueFactory(new PropertyValueFactory<>("prosjecnaOcjena"));

		listaFilmova = BazaPodataka.dohvatiFilmove();

		popuni();
	}
	
	public void popuni() {
		List<Film> filtriraniFilmovi = new ArrayList<Film>();
		
		if (filmoviFilterTextField.getText().isEmpty() == false) {
			filtriraniFilmovi = listaFilmova.stream().filter(m -> m.getNazivFilma().toLowerCase().contains(filmoviFilterTextField.getText().toLowerCase())).collect(Collectors.toList());
		} else {
			filtriraniFilmovi = listaFilmova.stream().collect(Collectors.toList());
		}
		oListaFilmova = FXCollections.observableArrayList(filtriraniFilmovi);
		filmoviTableView.setItems(oListaFilmova);
	}

	public static void dodajNoviFilm(Film f) {
		oListaFilmova.add(f);
	}

}
