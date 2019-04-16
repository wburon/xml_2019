package test_WBU;

import java.util.ArrayList;
import java.util.List;

import dao.DAO;
import dao.DAOFactory;
import model.Adresse;
import model.Etablissement;
import model.Etudiant;
import model.Formation;
import model.Statut;

public class insert_etablissement {

	public static void main(String[] args) {
		Etablissement eta = new Etablissement();
		eta.setNom("IMA");
		eta.setType("institut");
		List<Etudiant> etudiants = new ArrayList<Etudiant>();
		eta.setEtudiants(etudiants);
		List<Formation> formations = new ArrayList<>();
		eta.setFormations(formations);
		Adresse adresse = new Adresse();
		adresse.setNumero(20);
		adresse.setVoie("voie");
		adresse.setCode_postal(49000);
		adresse.setVille("angers");
		eta.setAdresse(adresse);
		List<String> diplomes = new ArrayList<>();
		diplomes.add("diplome IMA");
		eta.setDiplomes(diplomes);
		
		DAO etablissement = DAOFactory.getEtablissementDAO();
		etablissement.create(eta);
		etablissement.printAll();
	}

}
