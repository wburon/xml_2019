package test_WBU;

import java.util.ArrayList;
import java.util.List;

import dao.DAO;
import dao.DAOFactory;
import model.Formation;

public class insert_formation {

	public static void main(String[] args) {
		
		DAO formation = DAOFactory.getFormationDAO();
		
		Formation obj = new Formation();
		obj.setId(1);
		obj.setIntitule("MIASHS");
		List<String> disciplines = new ArrayList<>();
		disciplines.add("maths");
		disciplines.add("stats");
		obj.setDisciplines(disciplines);
		
		formation.create(obj);
		formation.printAll();
	
	}

}
