package model;

public enum Statut {
	
	present,absent;
	
	public String getName(){
		if(this == Statut.present)
			return "present";
		else
			return "absent";
	}

}
