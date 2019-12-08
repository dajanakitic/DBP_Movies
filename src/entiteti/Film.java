package entiteti;

import java.math.BigDecimal;

public class Film extends BazniEntitet {
	
	private String nazivFilma;
	private Zanr zanr;
	private Integer godina;
	private BigDecimal prosjecnaOcjena;
	
	public Film(String nazivFilma, Zanr zanr,Integer godina) {
		super();
		this.nazivFilma = nazivFilma;
		this.zanr = zanr;
		this.godina = godina;
	}
	
	public Film(String nazivFilma, Zanr zanr,Integer godina,BigDecimal prosjecnaOcjena) {
		super();
		this.nazivFilma = nazivFilma;
		this.zanr = zanr;
		this.godina = godina;
		this.prosjecnaOcjena=prosjecnaOcjena;
	}
	
	public Film(int id,String nazivFilma, Zanr zanr,Integer godina) {
		super();
		this.nazivFilma = nazivFilma;
		this.zanr = zanr;
		this.godina = godina;
		this.setId(id);
	}
	
	public Film(int id,String nazivFilma, Zanr zanr,Integer godina,BigDecimal prosjecnaOcjena) {
		super();
		this.nazivFilma = nazivFilma;
		this.zanr = zanr;
		this.godina = godina;
		this.prosjecnaOcjena=prosjecnaOcjena;
		this.setId(id);
	}

	public String getNazivFilma() {
		return nazivFilma;
	}

	public void setNazivFilma(String nazivFilma) {
		this.nazivFilma = nazivFilma;
	}

	public Integer getGodina() {
		return godina;
	}

	public void setGodina(Integer godina) {
		this.godina = godina;
	}

	public BigDecimal getProsjecnaOcjena() {
		return prosjecnaOcjena;
	}

	public void setProsjecnaOcjena(BigDecimal prosjecnaOcjena) {
		this.prosjecnaOcjena = prosjecnaOcjena;
	}

	public Zanr getZanr() {
		return zanr;
	}

	public void setZanr(Zanr zanr) {
		this.zanr = zanr;
	}
	
	@Override
	public String toString() {
		return nazivFilma;
	}

}
