package model;

public class Adresse {
	
	private int id;
	private int numero;
	private String voie;
	private int code_postal;
	private String ville;
	
	public Adresse(int id, int numero, String voie, int code_postal, String ville) {
		this.id = id;
		this.numero = numero;
		this.voie = voie;
		this.code_postal = code_postal;
		this.ville = ville;
	}
	public Adresse() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getVoie() {
		return voie;
	}
	public void setVoie(String voie) {
		this.voie = voie;
	}
	public int getCode_postal() {
		return code_postal;
	}
	public void setCode_postal(int code_postal) {
		this.code_postal = code_postal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String toString(){
		return ""+ this.numero + " " + this.voie + " " + this.code_postal + " " + this.ville;
	}

}
