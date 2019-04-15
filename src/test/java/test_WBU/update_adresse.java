package test_WBU;

import dao.DAO;
import dao.DAOFactory;
import model.Adresse;

public class update_adresse {

	public static void main(String[] args) {
		DAO adresse = DAOFactory.getAdresseDAO();
		Adresse adr = (Adresse) adresse.find(1);
		System.out.println(adr.getNumero());
		adresse.update(new Adresse(1,33,"chez moi",49000,"angers"));
		adr = (Adresse) adresse.find(1);
		System.out.println(adr.getNumero());
	}

}
