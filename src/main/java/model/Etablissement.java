package model;

import java.util.List;

public class Etablissement {
	
	private int id;
	private String nom;
	private String type;
	private Adresse adresse;
	private List<Etudiant> etudiants;
	private List<String> diplomes;
	private List<Formation> formations;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	public List<Etudiant> getEtudiants() {
		return etudiants;
	}
	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}
	public List<String> getDiplomes() {
		return diplomes;
	}
	public void setDiplomes(List<String> diplomes) {
		this.diplomes = diplomes;
	}
	public List<Formation> getFormations() {
		return formations;
	}
	public void setFormations(List<Formation> formations) {
		this.formations = formations;
	}

}
