package model;

import java.util.Scanner;

public enum Statut {
	
	present,absent;
	
	public String getName(){
		if(this == Statut.present)
			return "present";
		else
			return "absent";
	}

	public static Statut getStatut(Scanner clavier) {
		System.out.println("1- PRESENT");
		System.out.println("2- ABSENT");
		int statut = clavier.nextInt();
		if(statut == 1)
			return Statut.present;
		else
			return Statut.absent;
	}
	
	public static Statut getStautByName(String name){
		if(name.equals("present"))
			return Statut.present;
		else
			return Statut.absent;
	}
	
	public static Statut getOpposite(Statut s){
		if(s.equals(Statut.present))
			return Statut.absent;
		else
			return Statut.present;
	}

}
