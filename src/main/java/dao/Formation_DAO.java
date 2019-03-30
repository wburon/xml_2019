package dao;

import com.mongodb.MongoClient;

import model.Formation;

public class Formation_DAO extends DAO<Formation>{

	private MongoClient conn;
	
	public Formation_DAO(MongoClient conn) {
		this.conn = conn;
	}

	@Override
	public boolean create(Formation obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Formation obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Formation obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Formation find(int id) {
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
