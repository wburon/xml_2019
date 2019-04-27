package model;

import java.util.List;

public class Formation {
	
	private int id;
	private String intitule;
	private List<String> disciplines;
	
	public Formation(int id, String intitule, List<String> disciplines) {
		this.id = id;
		this.intitule = intitule;
		this.disciplines = disciplines;
	}
	public Formation() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public List<String> getDisciplines() {
		return disciplines;
	}
	public void setDisciplines(List<String> disciplines) {
		this.disciplines = disciplines;
	}
	public String toString(){
		String res = "" + this.id + " " + this.intitule;
		for (String disc : this.disciplines)
			res += " " + disc;
		return res;
	}
}
