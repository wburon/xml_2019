package dao;

import java.util.Iterator;

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

public class Etudiant_DAO extends DAO<Etudiant>{

	private MongoClient conn;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	private DAO adresse, formation;

	public Etudiant_DAO(MongoClient conn) {
		this.conn = conn;
		this.database = conn.getDatabase("notre_database");
		this.collection = this.database.getCollection("etudiant_collection");
		this.adresse = DAOFactory.getAdresseDAO();
		this.formation = DAOFactory.getFormationDAO();
	}

	@Override
	public int create(Etudiant obj) {
		try {
			int idAdresse = adresse.create(obj.getAdresse());
			Document document = new Document("id", maxId())
					.append("nom", obj.getNom())
					.append("prenom", obj.getPrenom()).append("adresse", idAdresse)
					.append("formation", obj.getFormation().getId())
					.append("statut", obj.getStatut().getName())
					;
			this.collection.insertOne(document);
			System.out.println("Etudiant insert succefully !");
			return document.getInteger("id");
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public boolean delete(Etudiant obj) {
		try {
			collection.deleteOne(Filters.eq("id", obj.getId()));
			System.out.println("Etudiant delete succefully !");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update(Etudiant obj) {
		try {
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("nom", obj.getNom()));
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("prenom", obj.getPrenom()));
			//update adresse
			adresse.update(obj.getAdresse());
			// update formation
			formation.update(obj.getFormation());
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("statut", obj.getStatut().getName()));
			System.out.println("Etudiant update succefully !");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Etudiant find(int id) {
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", id);
			FindIterable<Document> cursor = collection.find(whereQuery);
			Document doc = cursor.first();
			//int id, String nom, String prenom, Adresse adresse, Formation formation, Statut statut
			System.out.println(doc);
			return new Etudiant((int)doc.get("id"),(String)doc.get("nom"),(String)doc.get("prenom"),(Adresse)adresse.find((int)doc.get("adresse")),(Formation)formation.find((int)doc.get("formation")),(Statut)doc.get("statut"));
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
