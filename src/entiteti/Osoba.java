package entiteti;

public class Osoba extends BazniEntitet {

	private String imeOsobe;
	private String prezimeOsobe;

	public Osoba(String imeOsobe, String prezimeOsobe) {
		super();
		this.imeOsobe = imeOsobe;
		this.prezimeOsobe = prezimeOsobe;
	}

	public Osoba(int id, String imeOsobe, String prezimeOsobe) {
		super();
		this.imeOsobe = imeOsobe;
		this.prezimeOsobe = prezimeOsobe;
		this.setId(id);
	}

	public String getImeOsobe() {
		return imeOsobe;
	}

	public void setImeOsobe(String imeOsobe) {
		this.imeOsobe = imeOsobe;
	}

	public String getPrezimeOsobe() {
		return prezimeOsobe;
	}

	public void setPrezimeOsobe(String prezimeOsobe) {
		this.prezimeOsobe = prezimeOsobe;
	}

	public String dohvatiPodatkeOsobe() {
		return this.getImeOsobe() + " " + this.getPrezimeOsobe();
	};
	
	@Override
	public String toString() {
		return dohvatiPodatkeOsobe();
	}

}
