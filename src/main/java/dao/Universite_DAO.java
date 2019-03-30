package dao;

import com.mongodb.MongoClient;

import model.Universite;

public class Universite_DAO extends DAO<Universite>{
	
	private MongoClient conn;

	public Universite_DAO(MongoClient conn) {
		this.conn = conn;
	}

	@Override
	public boolean create(Universite obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Universite obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Universite obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Universite find(int id) {
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
