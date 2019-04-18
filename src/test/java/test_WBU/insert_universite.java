package test_WBU;

import java.util.ArrayList;
import java.util.List;

import dao.DAO;
import dao.DAOFactory;
import model.Adresse;
import model.Etablissement;
import model.Universite;

public class insert_universite {

	public static void main(String[] args) {
		//int id, String nom, int nb_facultes, int nb_etudiants, List<Etablissement> etablissements
		Universite univ = new Universite();
		univ.setNom("UCO");
		List<Etablissement> etablissements = new ArrayList<Etablissement>();
		Etablissement eta2 = (Etablissement) DAOFactory.getEtablissementDAO().find(3);
		etablissements.add(eta2);
		univ.setEtablissements(etablissements);
		univ.setNb_etudiants(0);
		univ.setNb_facultes(0);
		
		DAO universite = DAOFactory.getUniversiteDAO();
		universite.create(univ);
		universite.printAll();

	}

}
