package entiteti;

public class OsobaVSFilm extends Osoba{
	
	private Uloga uloga;
	private Film film;
	
	public OsobaVSFilm(String imeOsobe, String prezimeOsobe, Uloga uloga, Film film) {
		super(imeOsobe, prezimeOsobe);
		this.uloga = uloga;
		this.film = film;
	}
	
	public OsobaVSFilm(int id,String imeOsobe, String prezimeOsobe, Uloga uloga, Film film) {
		super(id,imeOsobe, prezimeOsobe);
		this.uloga = uloga;
		this.film = film;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}
		
}
