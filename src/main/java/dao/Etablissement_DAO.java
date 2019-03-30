package dao;

import com.mongodb.MongoClient;

import model.Etablissement;

public class Etablissement_DAO extends DAO<Etablissement>{

	private MongoClient conn;
	
	public Etablissement_DAO(MongoClient conn) {
		this.conn = conn;
	}

	@Override
	public boolean create(Etablissement obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Etablissement obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Etablissement obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Etablissement find(int id) {
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
