package test_WBU;

import dao.DAO;
import dao.DAOFactory;
import model.Adresse;

public class delete_adresse {

	public static void main(String[] args) {
		DAO adresse = DAOFactory.getAdresseDAO();
		adresse.printAll();
		Adresse aD = new Adresse();
		aD.setId(1);
		adresse.delete(aD);
		adresse.printAll();

	}

}
