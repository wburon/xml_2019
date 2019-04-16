package model;

import java.util.List;

public class Universite {
	
	private int id;
	private String nom;
	private int nb_facultes;
	private int nb_etudiants;
	private List<Etablissement> etablissements;
	public Universite(){
	}
	public Universite(int id, String nom, int nb_facultes, int nb_etudiants, List<Etablissement> etablissements) {
		this.id = id;
		this.nom = nom;
		this.nb_facultes = nb_facultes;
		this.nb_etudiants = nb_etudiants;
		this.etablissements = etablissements;
	}
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
	public int getNb_facultes() {
		return nb_facultes;
	}
	public void setNb_facultes(int nb_facultes) {
		this.nb_facultes = nb_facultes;
	}
	public int getNb_etudiants() {
		return nb_etudiants;
	}
	public void setNb_etudiants(int nb_etudiants) {
		this.nb_etudiants = nb_etudiants;
	}
	public List<Etablissement> getEtablissements() {
		return etablissements;
	}
	public void setEtablissements(List<Etablissement> etablissements) {
		this.etablissements = etablissements;
	}

}
