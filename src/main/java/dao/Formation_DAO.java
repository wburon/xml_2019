package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
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
import model.Formation;

public class Formation_DAO extends DAO<Formation>{

	private MongoClient conn;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public Formation_DAO(MongoClient conn) {
		this.conn = conn;
		this.database = conn.getDatabase("notre_database");
		this.collection = this.database.getCollection("formation_collection");
	}

	@Override
	public int create(Formation obj) {
		try {
			Document document;
			if(obj.getId() != 0){
				document = new Document("id", obj.getId()).append("intitule", obj.getIntitule())
						.append("disciplines", obj.getDisciplines());
			}else{
				document = new Document("id", maxId()).append("intitule", obj.getIntitule())
						.append("disciplines", obj.getDisciplines());
			}
			
			this.collection.insertOne(document);
			System.out.println("Formation insert succefully !");
			return document.getInteger("id");
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public boolean delete(Formation obj) {
		try {
			collection.deleteOne(Filters.eq("id", obj.getId()));
			System.out.println("Formation delete succefully !");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update(Formation obj) {
		try {
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("intitule", obj.getIntitule()));
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("disciplines", obj.getDisciplines()));
			System.out.println("Formation update succefully !");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Formation find(int id) {
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", id);
			FindIterable<Document> cursor = collection.find(whereQuery);
			Document doc = cursor.first();
			//int id, String intitule, List<String> disciplines
			//System.out.println(doc);
			return new Formation((int)doc.get("id"),(String)doc.get("intitule"),(List<String>) doc.get("disciplines"));
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

		Iterator it = iterDoc.iterator();
		while (it.hasNext()) {
			Document doc = (Document) it.next();
			System.out.println(new Formation((int)doc.get("id"),(String)doc.get("intitule"),(List<String>) doc.get("disciplines")).toString());
		}
	}

	public List<Formation> findByIntitule(String intitule) {
		List<Formation> listFormation = new ArrayList<>();
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("intitule", intitule);
			FindIterable<Document> cursor = collection.find(whereQuery);
			Iterator it = cursor.iterator();
			while (it.hasNext()) {
				Document doc = (Document) it.next();
				listFormation.add(new Formation((int)doc.get("id"),(String)doc.get("intitule"),(List<String>) doc.get("disciplines")));
			}
			return listFormation;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Formation> findByDiscipline(List<String> listDisc) {
		List<Formation> listFormation = new ArrayList<>();
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			FindIterable<Document> cursor = collection.find(whereQuery);
			Iterator it = cursor.iterator();
			while (it.hasNext()) {
				Document doc = (Document) it.next();
				List<String> disciplines = (List<String>) doc.get("disciplines");
				if(disciplines.containsAll(listDisc))
					listFormation.add(new Formation((int)doc.get("id"),(String)doc.get("intitule"),disciplines));
			}
			return listFormation;
		} catch (Exception e) {
			return null;
		}
	}

}
