package baza;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import entiteti.Film;
import entiteti.Ocjena;
import entiteti.Osoba;
import entiteti.OsobaVSFilm;
import entiteti.Uloga;
import entiteti.Zanr;

public class BazaPodataka {

	private static final String DATABASE_FILE = "bazaPodataka.properties";

	private static Connection spajanjeNaBazuPodataka() throws SQLException, IOException {
		Properties svojstva = new Properties();
		svojstva.load(new FileReader(DATABASE_FILE));
		String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
		String korisnickoIme = svojstva.getProperty("korisnickoIme");
		String lozinka = svojstva.getProperty("lozinka");
		Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);
		return veza;
	}

	private static void zatvaranjeVezeNaBazuPodataka(Connection con) {
		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static List<Film> dohvatiFilmove() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementFilmovi = veza.createStatement();
		ResultSet resultSetFilmova = statementFilmovi.executeQuery("SELECT * FROM VIDEOTEKA.FILM");

		List<Film> listaFilmova = new ArrayList<>();
		while (resultSetFilmova.next()) {
			Integer filmId = resultSetFilmova.getInt("ID");
			String naziv = resultSetFilmova.getString("NAZIV_FILMA");
			String zanr = resultSetFilmova.getString("FILM.ZANR");
			Integer godina = resultSetFilmova.getInt("GODINA");
			BigDecimal prosjecnaOcjena = resultSetFilmova.getBigDecimal("PROSJECNA_OCJENA");

			Film film = new Film(filmId,naziv, Zanr.valueOf(zanr), godina, prosjecnaOcjena);
			film.setId(filmId);
			listaFilmova.add(film);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaFilmova;
	}

	public static void spremiFilm(Film film) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(true);
		try {
			PreparedStatement insertFilm = veza.prepareStatement(
					"INSERT INTO VIDEOTEKA.FILM (ID, NAZIV_FILMA, ZANR, GODINA) VALUES (?, ?, ?, ?);");
			insertFilm.setInt(1, film.getId());
			insertFilm.setString(2, film.getNazivFilma());
			insertFilm.setString(3, film.getZanr().toString());
			insertFilm.setInt(4, film.getGodina());

			insertFilm.executeUpdate();
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static List<Ocjena> dohvatiOcjene() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementOcjene = veza.createStatement();
		ResultSet resultSetOcjena = statementOcjene.executeQuery("SELECT * FROM VIDEOTEKA.OCJENA");

		List<Ocjena> listaOcjena = new ArrayList<>();
		while (resultSetOcjena.next()) {
			Integer ocjenaId = resultSetOcjena.getInt("ID");
			Integer filmId = resultSetOcjena.getInt("ID_FILMA");
			String napomena = resultSetOcjena.getString("NAPOMENA");
			Integer ocjenaFilma = resultSetOcjena.getInt("OCJENA");

			Film film = new Film(filmId,
					dohvatiFilmove().stream().filter(f -> Integer.valueOf(f.getId()).equals(filmId)).findFirst().get()
							.getNazivFilma(),
					dohvatiFilmove().stream().filter(f -> Integer.valueOf(f.getId()).equals(filmId)).findFirst().get()
							.getZanr(),
					dohvatiFilmove().stream().filter(f -> Integer.valueOf(f.getId()).equals(filmId)).findFirst().get()
							.getGodina());

			Ocjena ocjena = new Ocjena(film, napomena, ocjenaFilma);
			ocjena.setId(ocjenaId);
			listaOcjena.add(ocjena);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaOcjena;
	}

	public static void spremiOcjenu(Ocjena ocjena) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(true);
		try {
			PreparedStatement insertOcjena = veza.prepareStatement(
					"INSERT INTO VIDEOTEKA.OCJENA (ID, ID_FILMA, NAPOMENA,OCJENA) VALUES (?, ?, ?, ?);");
			insertOcjena.setInt(1, ocjena.getId());
			insertOcjena.setInt(2, ocjena.getFilm().getId());
			insertOcjena.setString(3, ocjena.getNapomena());
			insertOcjena.setInt(4, ocjena.getOcjena());

			insertOcjena.executeUpdate();
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static List<Osoba> dohvatiOsobe() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementOsoba = veza.createStatement();
		ResultSet resultSetOsoba = statementOsoba.executeQuery(
				"SELECT ID,IME,PREZIME FROM VIDEOTEKA.OSOBA");

		List<Osoba> listaOsoba = new ArrayList<>();
		while (resultSetOsoba.next()) {
			Integer osobaId = resultSetOsoba.getInt("ID");
			String ime = resultSetOsoba.getString("IME");
			String prezime = resultSetOsoba.getString("PREZIME");
			
			Osoba osoba = new Osoba(ime, prezime);
			osoba.setId(osobaId);
			listaOsoba.add(osoba);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaOsoba;
	}

	public static List<OsobaVSFilm> dohvatiOsobeVSfilm() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementOsobaVSfilm = veza.createStatement();
		ResultSet resultSetOsobaVSfilm = statementOsobaVSfilm.executeQuery(
				"SELECT OSOBA.ID AS id,OSOBA.IME AS ime,OSOBA.PREZIME AS prezime,"
				+ "OSOBA_VS_FILM.ID_FILMA AS filmId,OSOBA_VS_FILM.ULOGA AS uloga,"
				+ "FILM.NAZIV_FILMA AS nazivFilma,FILM.ZANR AS zanr,FILM.GODINA AS godina,FILM.PROSJECNA_OCJENA AS prosjecnaOcjena "
						+ "FROM VIDEOTEKA.OSOBA_VS_FILM JOIN VIDEOTEKA.OSOBA ON VIDEOTEKA.OSOBA_VS_FILM.ID_OSOBE=VIDEOTEKA.OSOBA.ID "
						+ "JOIN VIDEOTEKA.FILM ON OSOBA_VS_FILM.ID_FILMA=FILM.ID");

		List<OsobaVSFilm> listaOsoba = new ArrayList<>();
		while (resultSetOsobaVSfilm.next()) {
			Integer osobaId = resultSetOsobaVSfilm.getInt("id");
			String ime = resultSetOsobaVSfilm.getString("ime");
			String prezime = resultSetOsobaVSfilm.getString("prezime");
			String uloga = resultSetOsobaVSfilm.getString("uloga");
			String nazivFilma = resultSetOsobaVSfilm.getString("nazivFilma");
			String zanr = resultSetOsobaVSfilm.getString("zanr");
			Integer godinaFilma = resultSetOsobaVSfilm.getInt("godina");
			BigDecimal prosjecnaOcjena = BigDecimal.valueOf(resultSetOsobaVSfilm.getDouble("prosjecnaOcjena"));

			Film film = new Film(nazivFilma, Zanr.valueOf(zanr), godinaFilma, prosjecnaOcjena);
			OsobaVSFilm osobaVSfilm = new OsobaVSFilm(ime, prezime, Uloga.valueOf(uloga), film);
			osobaVSfilm.setId(osobaId);
			listaOsoba.add(osobaVSfilm);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaOsoba;
	}

	public static void spremiOsobu(Osoba osoba) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(true);
		try {
			PreparedStatement insertOsoba = veza
					.prepareStatement("INSERT INTO VIDEOTEKA.OSOBA (ID, IME, PREZIME) VALUES (?, ?, ?);");
			insertOsoba.setInt(1, osoba.getId());
			insertOsoba.setString(2, osoba.getImeOsobe());
			insertOsoba.setString(3, osoba.getPrezimeOsobe());

			insertOsoba.executeUpdate();
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static void spremiOsobuVsFilm(OsobaVSFilm osoba) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(true);
		try {

			PreparedStatement insertOsobaVsFilm = veza.prepareStatement(
					"INSERT INTO VIDEOTEKA.OSOBA_VS_FILM (ID_OSOBE, ID_FILMA, ULOGA) VALUES (?, ?, ?);");
			insertOsobaVsFilm.setInt(1, osoba.getId());
			insertOsobaVsFilm.setInt(2, osoba.getFilm().getId());
			insertOsobaVsFilm.setString(3, osoba.getUloga().toString());

			insertOsobaVsFilm.executeUpdate();
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void updateOcjena(Film film,Integer novaOcjena) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(true);
		
		Statement statementOcjena=veza.createStatement();
		
		String sqlTemplate = "SELECT COUNT(VIDEOTEKA.OCJENA.OCJENA) AS brojOcjena,SUM(VIDEOTEKA.OCJENA.OCJENA) AS zbrojOcjena "
				+ "FROM VIDEOTEKA.OCJENA WHERE ID_FILMA=<film_id>"
				+ "GROUP BY ID_FILMA";
		String selectSQL = sqlTemplate.replaceFirst("<film_id>",Integer.toString(film.getId()));
		
		ResultSet rs = statementOcjena.executeQuery(selectSQL);
		
		BigDecimal novaProsjecnaOcjena=BigDecimal.ZERO;
		BigDecimal bigDbrojOcjena=BigDecimal.ZERO;
		BigDecimal bigDzbrojOcjena=BigDecimal.ZERO;

		while (rs.next()) {
			Integer brojOcjena = rs.getInt("brojOcjena");
			Integer zbrojOcjena = rs.getInt("zbrojOcjena");
			
			bigDbrojOcjena = new BigDecimal(brojOcjena);
			bigDzbrojOcjena = new BigDecimal(zbrojOcjena);
			
			novaProsjecnaOcjena=bigDzbrojOcjena.divide(bigDbrojOcjena,2, RoundingMode.HALF_EVEN);	
		}	
		
		try {
			PreparedStatement updateOcjena = veza.prepareStatement(
					"UPDATE VIDEOTEKA.FILM SET PROSJECNA_OCJENA=?"
					+ "WHERE ID=?;");
			updateOcjena.setDouble(1, Double.parseDouble(novaProsjecnaOcjena.toString()));
			updateOcjena.setInt(2, film.getId());
		
			updateOcjena.executeUpdate();
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

}
