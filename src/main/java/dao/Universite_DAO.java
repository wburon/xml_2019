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

import model.Etablissement;
import model.Universite;

public class Universite_DAO extends DAO<Universite>{
	
	private MongoClient conn;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	private DAO etablissement;

	public Universite_DAO(MongoClient conn) {
		this.conn = conn;
		this.database = conn.getDatabase("notre_database");
		this.collection = this.database.getCollection("universite_collection");
		this.etablissement = DAOFactory.getEtablissementDAO();
	}

	@Override
	public int create(Universite obj) {
		try {
			Document document;
			if(obj.getId() != 0){
				List<Integer> idEtablissements = new ArrayList<>();
				for(Etablissement e : obj.getEtablissements())
					idEtablissements.add(e.getId());
				//int id, String nom, int nb_facultes, int nb_etudiants, List<Etablissement> etablissements
				document = new Document("id", obj.getId())
						.append("nom", obj.getNom())
						.append("nb_facultes", obj.getNb_facultes())
						.append("nb_etudiants", obj.getNb_etudiants())
						.append("etablissements", idEtablissements)
						;
			}else{
				List<Integer> idEtablissements = new ArrayList<>();
				for(Etablissement e : obj.getEtablissements())
					idEtablissements.add(e.getId());
				//int id, String nom, int nb_facultes, int nb_etudiants, List<Etablissement> etablissements
				document = new Document("id", maxId())
						.append("nom", obj.getNom())
						.append("nb_facultes", obj.getNb_facultes())
						.append("nb_etudiants", obj.getNb_etudiants())
						.append("etablissements", idEtablissements)
						;
			}
			this.collection.insertOne(document);
			System.out.println("Universite insert succefully !");
			return document.getInteger("id");
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public boolean delete(Universite obj) {
		try {
			collection.deleteOne(Filters.eq("id", obj.getId()));
			System.out.println("Universite delete succefully !");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update(Universite obj) {
		try {
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("nom", obj.getNom()));
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("nb_facultes", obj.getNb_facultes()));
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("nb_etudiants", obj.getNb_etudiants()));
			//update etablissements
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("formations", obj.getEtablissements()));
			System.out.println("Universite update succefully !");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Universite find(int id) {
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", id);
			FindIterable<Document> cursor = collection.find(whereQuery);
			Document doc = cursor.first();
			//int id, String nom, int nb_facultes, int nb_etudiants, List<Etablissement> etablissements
//			System.out.println(doc);
			List<Etablissement> etablissements = new ArrayList<>();
			for(int i : (List<Integer>)doc.get("etablissements"))
				etablissements.add((Etablissement)this.etablissement.find(i));
			return new Universite(doc.getInteger("id"),doc.getString("nom"),doc.getInteger("nb_facultes"),(int)doc.getInteger("nb_etudiants"),etablissements);
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
		printDocumentUniv(iterDoc);

	}

	private void printDocumentUniv(FindIterable<Document> iterDoc) {
		for(Document doc : iterDoc){
			System.out.println(""+doc.getInteger("id")+" "+doc.getString("nom"));
		}
	}

}
