package test_WBU;

import dao.DAO;
import dao.DAOFactory;
import model.Adresse;

public class find_adresse {

	public static void main(String[] args) {
		DAO adresse = DAOFactory.getAdresseDAO();
		Adresse adr = (Adresse) adresse.find(1);
		System.out.println(adr.getVoie());
	}

}
