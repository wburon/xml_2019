package dao;

import com.mongodb.MongoClient;

import model.Etudiant;

public class Etudiant_DAO extends DAO<Etudiant>{

	private MongoClient conn;
	
	public Etudiant_DAO(MongoClient conn) {
		this.conn = conn;
	}

	@Override
	public boolean create(Etudiant obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Etudiant obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Etudiant obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Etudiant find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int maxId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void printAll() {
		// TODO Auto-generated method stub
		
	}

}
