package test_WBU;

import dao.DAO;
import dao.DAOFactory;
import model.Adresse;

public class insert_adresse {

	public static void main(String[] args) {
		
		DAO adresse = DAOFactory.getAdresseDAO();
		
		Adresse obj = new Adresse();
		obj.setId(1);
		obj.setNumero(22);
		obj.setVoie("Chez moi");
		obj.setCode_postal(49000);
		obj.setVille("Angers");
		
		adresse.create(obj);
		adresse.printAll();
	}

}
