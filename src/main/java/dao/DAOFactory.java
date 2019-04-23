package dao;

import com.mongodb.MongoClient;

import singleton.Singleton;

public class DAOFactory {
	
	protected static final MongoClient conn = Singleton.getInstance();

	/**
	 * @return object Adresse_DAO
	 */
	public static Adresse_DAO getAdresseDAO(){
		return new Adresse_DAO(conn);
	}
	
	public static Etudiant_DAO getEtudiantDAO(){
		return new Etudiant_DAO(conn);
	}
	
	public static Formation_DAO getFormationDAO(){
		return new Formation_DAO(conn);
	}
	
	public static Etablissement_DAO getEtablissementDAO(){
		return new Etablissement_DAO(conn);
	}
	
	public static Universite_DAO getUniversiteDAO(){
		return new Universite_DAO(conn);
	}
}
