package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import model.Adresse;
import model.Etudiant;
import model.Formation;
import model.Statut;

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
	public int create(Adresse obj) {
		try {
			Document document;
			if(obj.getId() != 0){
				document = new Document("id", obj.getId()).append("numero", obj.getNumero())
					.append("voie", obj.getVoie()).append("code postal", obj.getCode_postal())
					.append("ville", obj.getVille());
			}else{
				document = new Document("id", maxId()).append("numero", obj.getNumero())
						.append("voie", obj.getVoie()).append("code postal", obj.getCode_postal())
						.append("ville", obj.getVille());
			}
			this.collection.insertOne(document);
			System.out.println("Adresse insert succefully !");
			return document.getInteger("id");
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public boolean delete(Adresse obj) {
		try {
			collection.deleteOne(Filters.eq("id", obj.getId()));
			System.out.println("Adresse delete succefully !");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update(Adresse obj) {
		try {
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("numero", obj.getNumero()));
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("code postal", obj.getCode_postal()));
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("voie", obj.getVoie()));
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("ville", obj.getVille()));
			System.out.println("Adresse update succefully !");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Adresse find(int id) {
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", id);
			FindIterable<Document> cursor = collection.find(whereQuery);
			Document doc = cursor.first();
			//int id, int numero, String voie, int code_postal, String ville
			//System.out.println(doc);
			return new Adresse((int)doc.get("id"),(int)doc.get("numero"),(String)doc.get("voie"),(int)doc.get("code postal"),(String)doc.get("ville"));
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Adresse> findByLocation(String ville, int code, String voie) {
		List<Adresse> listAdresse = new ArrayList<>();
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("ville", ville);
			whereQuery.put("code postal", code);
			whereQuery.put("voie", voie);
			FindIterable<Document> cursor = collection.find(whereQuery);
			Iterator it = cursor.iterator();
			while(it.hasNext()){
				Document doc = (Document) it.next();
				listAdresse.add(new Adresse((int)doc.get("id"),(int)doc.get("numero"),(String)doc.get("voie"),(int)doc.get("code postal"),(String)doc.get("ville")));
			}
			return listAdresse;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int maxId() {
		return (int)collection.count() +1;
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
