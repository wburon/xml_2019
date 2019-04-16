package test_WBU;

import java.util.ArrayList;
import java.util.List;

import dao.DAO;
import dao.DAOFactory;
import model.Adresse;
import model.Etudiant;
import model.Formation;
import model.Statut;

public class etudiant {

	public static void main(String[] args) {
		
		// create etudiant
		Etudiant etudiant = new Etudiant();
		etudiant.setNom("buron");
		etudiant.setPrenom("william");
		Adresse adresse = new Adresse();
		adresse.setNumero(20);
		adresse.setVoie("voie");
		adresse.setCode_postal(49000);
		adresse.setVille("angers");
		etudiant.setAdresse(adresse);
		Formation formation = new Formation();
		formation.setId(1);
		etudiant.setFormation(formation);
		etudiant.setStatut(Statut.present);
		DAO etudiantDAO = DAOFactory.getEtudiantDAO();
		etudiantDAO.create(etudiant);
		System.out.println("print all");
		etudiantDAO.printAll();

	}

}
