package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

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

public class Etudiant_DAO extends DAO<Etudiant> {

	private MongoClient conn;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public Etudiant_DAO(MongoClient conn) {
		this.conn = conn;
		this.database = conn.getDatabase("notre_database");
		this.collection = this.database.getCollection("etudiant_collection");
	}

	@Override
	public int create(Etudiant obj) {
		try {
			Document document;
			if (obj.getId() != 0) {
				document = new Document("id", obj.getId()).append("nom", obj.getNom()).append("prenom", obj.getPrenom())
						.append("adresse", obj.getAdresse().getId()).append("formation", obj.getFormation().getId())
						.append("statut", obj.getStatut().getName());
			} else {
				int idAdresse = DAOFactory.getAdresseDAO().create(obj.getAdresse());
				document = new Document("id", maxId()).append("nom", obj.getNom()).append("prenom", obj.getPrenom())
						.append("adresse", idAdresse).append("formation", obj.getFormation().getId())
						.append("statut", obj.getStatut().getName());
			}

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
			// update adresse
			DAOFactory.getAdresseDAO().update(obj.getAdresse());
			// update formation
			DAOFactory.getFormationDAO().update(obj.getFormation());
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
			// int id, String nom, String prenom, Adresse adresse, Formation
			// formation, Statut statut
//			System.out.println(doc);
			Etudiant e =  new Etudiant((int) doc.get("id"), (String) doc.get("nom"), (String) doc.get("prenom"),
					(Adresse) DAOFactory.getAdresseDAO().find((int) doc.get("adresse")),
					(Formation) DAOFactory.getFormationDAO().find((int) doc.get("formation")),
					Statut.valueOf((String) doc.get("statut")));
			return e;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Etudiant> findByName(String nom, String prenom) {
		List<Etudiant> listEtudiant = new ArrayList<>();
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("nom", nom);
			whereQuery.put("prenom", prenom);
			FindIterable<Document> cursor = collection.find(whereQuery);
			Iterator it = cursor.iterator();
			while (it.hasNext()) {
				Document doc = (Document) it.next();
				listEtudiant.add(new Etudiant((int) doc.get("id"), (String) doc.get("nom"), (String) doc.get("prenom"),
						(Adresse) DAOFactory.getAdresseDAO().find((int) doc.get("adresse")),
						(Formation) DAOFactory.getFormationDAO().find((int) doc.get("formation")),
						Statut.valueOf(doc.getString("statut"))));
			}
			return listEtudiant;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Etudiant> findByLocation(String ville, int code, String voie) {
		List<Etudiant> listEtudiant = new ArrayList<>();
		try {
			List<Adresse> listAdresse = DAOFactory.getAdresseDAO().findByLocation(ville, code, voie);
			for (Adresse adresse : listAdresse) {
				BasicDBObject whereQuery = new BasicDBObject();
				whereQuery.put("adresse", adresse.getId());
				FindIterable<Document> cursor = collection.find(whereQuery);
				Iterator it = cursor.iterator();
				while (it.hasNext()) {
					Document doc = (Document) it.next();
					listEtudiant
							.add(new Etudiant((int) doc.get("id"), (String) doc.get("nom"), (String) doc.get("prenom"),
									(Adresse) DAOFactory.getAdresseDAO().find((int) doc.get("adresse")),
									(Formation) DAOFactory.getFormationDAO().find((int) doc.get("formation")),
									Statut.getStautByName(doc.getString("statut"))));
				}
			}
			return listEtudiant;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Etudiant> findByStatut(String statut) {
		List<Etudiant> listEtudiant = new ArrayList<>();
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("statut", statut);
			FindIterable<Document> cursor = collection.find(whereQuery);
			Iterator it = cursor.iterator();
			while (it.hasNext()) {
				Document doc = (Document) it.next();
				listEtudiant.add(new Etudiant((int) doc.get("id"), (String) doc.get("nom"), (String) doc.get("prenom"),
						(Adresse) DAOFactory.getAdresseDAO().find((int) doc.get("adresse")),
						(Formation) DAOFactory.getFormationDAO().find((int) doc.get("formation")),
						Statut.valueOf(doc.getString("statut"))));
			}
			return listEtudiant;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int maxId() {
		return (int) collection.count() + 1;
	}

	@Override
	public void printAll() {
		FindIterable<Document> iterDoc = collection.find();

		Iterator it = iterDoc.iterator();
		while (it.hasNext()) {
			Document doc = (Document) it.next();
			System.out.println(new Etudiant((int) doc.get("id"), (String) doc.get("nom"), (String) doc.get("prenom"),
					(Adresse) DAOFactory.getAdresseDAO().find((int) doc.get("adresse")),
					(Formation) DAOFactory.getFormationDAO().find((int) doc.get("formation")),
					Statut.getStautByName(doc.getString("statut"))).toString());
		}
	}
	
	public List<String> getCourses(int id){
		return find(id).getFormation().getDisciplines();
	}

}
