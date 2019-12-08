package javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import baza.BazaPodataka;
import entiteti.Ocjena;
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

public class OcjeneController {

	List<Ocjena> listaOcjena = new ArrayList<>();
	static ObservableList<Ocjena> oListaOcjena = FXCollections.observableArrayList();
	
	@FXML
	private TextField filmoviFilterTextField;
	@FXML
	private TableView<Ocjena> ocjeneTableView;
	@FXML
	private TableColumn<Ocjena, String> idColumn;
	@FXML
	private TableColumn<Ocjena, String> nazivFilmaColumn;
	@FXML
	private TableColumn<Ocjena, String> napomenaColumn;
	@FXML
	private TableColumn<Ocjena, String> ocjenaColumn;
	
	@FXML
	public void initialize() throws SQLException, IOException {
		
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nazivFilmaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ocjena, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Ocjena, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getFilm().getNazivFilma());
			}
		});
		napomenaColumn.setCellValueFactory(new PropertyValueFactory<>("napomena"));
		ocjenaColumn.setCellValueFactory(new PropertyValueFactory<>("ocjena"));		

		listaOcjena = BazaPodataka.dohvatiOcjene();

		popuni();
	}
	
	public void popuni() {
		List<Ocjena> filtriraneOcjene = new ArrayList<Ocjena>();
		if (filmoviFilterTextField.getText().isEmpty() == false) {
			filtriraneOcjene = listaOcjena.stream().filter(m -> m.getFilm().getNazivFilma().toLowerCase().contains(filmoviFilterTextField.getText().toLowerCase())).collect(Collectors.toList());
		} else {
			filtriraneOcjene = listaOcjena.stream().collect(Collectors.toList());
		}
		oListaOcjena = FXCollections.observableArrayList(filtriraneOcjene);
		ocjeneTableView.setItems(oListaOcjena);
	}

	public static void dodajNovuOcjenu(Ocjena f) {
		oListaOcjena.add(f);
	}

}
