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
import model.Etablissement;
import model.Etudiant;
import model.Formation;
import model.Statut;
import model.Universite;

public class Etablissement_DAO extends DAO<Etablissement>{

	private MongoClient conn;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public Etablissement_DAO(MongoClient conn) {
		this.conn = conn;
		this.database = conn.getDatabase("notre_database");
		this.collection = this.database.getCollection("etablissement_collection");
	}

	@Override
	public int create(Etablissement obj) {
		try {
			Document document;
			if(obj.getId() != 0){
				List<Integer> idEtudiants = new ArrayList<>();
				for(Etudiant e : obj.getEtudiants())
					idEtudiants.add(e.getId());
				List<Integer> idFormations = new ArrayList<>();
				for(Formation f : obj.getFormations())
					idFormations.add(f.getId());
				document = new Document("id", obj.getId()).append("nom", obj.getNom())
						.append("type", obj.getType()).append("adresse", obj.getAdresse().getId())
						.append("etudiants", idEtudiants)
						.append("diplomes", obj.getDiplomes())
						.append("formations", idFormations)
						;
			}else{
				int idAdresse = DAOFactory.getAdresseDAO().create(obj.getAdresse());
				List<Integer> idEtudiants = new ArrayList<>();
				for(Etudiant e : obj.getEtudiants())
					idEtudiants.add(e.getId());
				List<Integer> idFormations = new ArrayList<>();
				for(Formation f : obj.getFormations())
					idFormations.add(f.getId());
				document = new Document("id", maxId()).append("nom", obj.getNom())
						.append("type", obj.getType()).append("adresse", idAdresse)
						.append("etudiants", idEtudiants)
						.append("diplomes", obj.getDiplomes())
						.append("formations", idFormations)
						;
			}
			this.collection.insertOne(document);
			System.out.println("etablissement insert succefully !");
			return document.getInteger("id");
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public boolean delete(Etablissement obj) {
		try {
			collection.deleteOne(Filters.eq("id", obj.getId()));
			System.out.println("etablissement delete succefully !");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update(Etablissement obj) {
		try {
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("nom", obj.getNom()));
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("type", obj.getType()));
			//update adresse
			DAOFactory.getAdresseDAO().update(obj.getAdresse());
			//update etudiants
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("etudiants", obj.getEtudiants()));
			//update diplomes
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("diplomes", obj.getDiplomes()));
			//update formations
			collection.updateOne(Filters.eq("id", obj.getId()), Updates.set("formations", obj.getFormations()));
			System.out.println("etablissement update succefully !");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Etablissement find(int id) {
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", id);
			FindIterable<Document> cursor = collection.find(whereQuery);
			Document doc = cursor.first();
			//int id, String nom, String type, Adresse adresse, List<Etudiant> etudiants,List<String> diplomes, List<Formation> formations
//			System.out.println(doc);
			List<Etudiant> etudiants = new ArrayList<>();
			for(int i : (List<Integer>)doc.get("etudiants"))
				etudiants.add(DAOFactory.getEtudiantDAO().find(i));
			List<Formation> formations = new ArrayList<>();
			for(int i : (List<Integer>)doc.get("formations"))
				formations.add(DAOFactory.getFormationDAO().find(i));
			return new Etablissement((int)doc.get("id"),(String)doc.get("nom"),(String)doc.get("type"),DAOFactory.getAdresseDAO().find(doc.getInteger("adresse")),etudiants,(List<String>)doc.get("diplomes"),formations);
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

	public List<Etablissement> findByName(String nom) {
		List<Etablissement> listEtablissement = new ArrayList<>();
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("nom", nom);
			FindIterable<Document> cursor = collection.find(whereQuery);
			Iterator it = cursor.iterator();
			while (it.hasNext()) {
				Document doc = (Document) it.next();
				List<Etudiant> etudiants = new ArrayList<>();
				for(int i : (List<Integer>)doc.get("etudiants"))
					etudiants.add(DAOFactory.getEtudiantDAO().find(i));
				List<Formation> formations = new ArrayList<>();
				for(int i : (List<Integer>)doc.get("formations"))
					formations.add(DAOFactory.getFormationDAO().find(i));
				listEtablissement.add(new Etablissement((int) doc.get("id"), (String) doc.get("nom"), (String) doc.get("type"),
						(Adresse) DAOFactory.getAdresseDAO().find((int) doc.get("adresse")),
						etudiants,(List<String>)doc.get("diplomes"),formations));
			}
			return listEtablissement;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Etablissement> findByLocation(String ville, int code, String voie) {
		List<Etablissement> listEtablissement = new ArrayList<>();
		try {
			List<Adresse> listAdresse = DAOFactory.getAdresseDAO().findByLocation(ville, code, voie);
			for (Adresse adresse : listAdresse) {
				BasicDBObject whereQuery = new BasicDBObject();
				whereQuery.put("adresse", adresse.getId());
				FindIterable<Document> cursor = collection.find(whereQuery);
				Iterator it = cursor.iterator();
				while (it.hasNext()) {
					Document doc = (Document) it.next();
					List<Etudiant> etudiants = new ArrayList<>();
					for(int i : (List<Integer>)doc.get("etudiants"))
						etudiants.add(DAOFactory.getEtudiantDAO().find(i));
					List<Formation> formations = new ArrayList<>();
					for(int i : (List<Integer>)doc.get("formations"))
						formations.add(DAOFactory.getFormationDAO().find(i));
					listEtablissement
							.add(new Etablissement((int) doc.get("id"), (String) doc.get("nom"), doc.getString("type"), (Adresse) DAOFactory.getAdresseDAO().find((int) doc.get("adresse")),
									etudiants,(List<String>)doc.get("diplomes"),formations));
				}
			}
			return listEtablissement;
		} catch (Exception e) {
			return null;
		}
	}

}
