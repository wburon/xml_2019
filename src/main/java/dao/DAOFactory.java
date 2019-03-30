package dao;

import com.mongodb.MongoClient;

import singleton.Singleton;

public class DAOFactory {
	
	protected static final MongoClient conn = Singleton.getInstance();

	/**
	 * @return object Adresse_DAO
	 */
	public static DAO getAdresseDAO(){
		return new Adresse_DAO(conn);
	}
	
	public static DAO getEtudiantDAO(){
		return new Etudiant_DAO(conn);
	}
	
	public static DAO getFormationDAO(){
		return new Formation_DAO(conn);
	}
	
	public static DAO getEtablissementDAO(){
		return new Etablissement_DAO(conn);
	}
	
	public static DAO getUniversiteDAO(){
		return new Universite_DAO(conn);
	}
}
