package javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import baza.BazaPodataka;
import entiteti.Osoba;
import entiteti.OsobaVSFilm;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class OsobeController {

	List<OsobaVSFilm> listaOsobaVSFilm = new ArrayList<>();
	static ObservableList<OsobaVSFilm> oListaOsobaVSFilm = FXCollections.observableArrayList();
		
	List<Osoba> listaOsoba = new ArrayList<>();
	static ObservableList<Osoba> oListaOsoba = FXCollections.observableArrayList();

	@FXML
	private TableView<OsobaVSFilm> osobeTableView;
	@FXML
	private TextField osobeFilterTextField;
	@FXML
	private TableColumn<OsobaVSFilm, String> idColumn;
	@FXML
	private TableColumn<OsobaVSFilm, String> imePrezimeColumn;
	@FXML
	private TableColumn<OsobaVSFilm, String> nazivFilmaColumn;
	@FXML
	private TableColumn<OsobaVSFilm, String> ulogaColumn;

	@FXML
	public void initialize() throws SQLException, IOException {
		
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		imePrezimeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OsobaVSFilm, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<OsobaVSFilm, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().dohvatiPodatkeOsobe());
			}
		});
		nazivFilmaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OsobaVSFilm, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<OsobaVSFilm, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getFilm().getNazivFilma());
			}
		});
		ulogaColumn.setCellValueFactory(new PropertyValueFactory<>("uloga"));	

		listaOsobaVSFilm = BazaPodataka.dohvatiOsobeVSfilm();

		popuni();
	}
	
	public void popuni() {
		List<OsobaVSFilm> filtriraneOsobe = new ArrayList<OsobaVSFilm>();
		if (osobeFilterTextField.getText().isEmpty() == false) {
			filtriraneOsobe = listaOsobaVSFilm.stream().filter(m -> m.dohvatiPodatkeOsobe().toLowerCase().contains(osobeFilterTextField.getText().toLowerCase())).collect(Collectors.toList());
		} else {
			filtriraneOsobe = listaOsobaVSFilm.stream().collect(Collectors.toList());
		}
		oListaOsobaVSFilm = FXCollections.observableArrayList(filtriraneOsobe);
		osobeTableView.setItems(oListaOsobaVSFilm);
	}

	public static void dodajNovuOsobuVSFilm(OsobaVSFilm f) {
		oListaOsoba.add(f);
	}
	
}
