package entiteti;

public class Ocjena extends BazniEntitet{
	
	private Film film;
	private String napomena;
	private Integer ocjena;
	
	public Ocjena(Film film, String napomena, Integer ocjena) {
		super();
		this.film = film;
		this.napomena = napomena;
		this.ocjena = ocjena;
	}
	
	public Ocjena(int id,Film film, String napomena, Integer ocjena) {
		super();
		this.film = film;
		this.napomena = napomena;
		this.ocjena = ocjena;
		this.setId(id);
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}

	public Integer getOcjena() {
		return ocjena;
	}

	public void setOcjena(Integer ocjena) {
		this.ocjena = ocjena;
	}
		
}
