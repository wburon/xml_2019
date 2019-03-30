package dao;

import java.util.Iterator;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Adresse;

public class Adresse_DAO extends DAO<Adresse> {

	private MongoClient conn;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public Adresse_DAO(MongoClient conn) {
		this.conn = conn;
		this.database = conn.getDatabase("notre_database");
		this.collection = this.database.getCollection("adresse_collection");
	}

	@Override
	public boolean create(Adresse obj) {
		try {
			Document document = new Document("id", obj.getId()).append("numero", obj.getNumero())
					.append("voie", obj.getVoie()).append("code postal", obj.getCode_postal())
					.append("ville", obj.getVille());
			this.collection.insertOne(document);
			System.out.println("Adresse insert succefully !");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(Adresse obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Adresse obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Adresse find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int maxId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void printAll(){
		FindIterable<Document> iterDoc = collection.find();
		int i = 1;
		
		Iterator it = iterDoc.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
			i++;
		}
	}

}
