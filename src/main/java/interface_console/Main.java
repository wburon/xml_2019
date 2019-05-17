package interface_console;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dao.DAO;
import dao.DAOFactory;
import model.Adresse;
import model.Etablissement;
import model.Etudiant;
import model.Formation;
import model.Statut;
import model.Universite;

public class Main {

	public static void main(String[] args) {

		System.out.println("Bonjour !");

		Scanner clavier = new Scanner(System.in);

		int choix = 0;
		while (choix != 10) {
			printChoix();
			choix = clavier.nextInt();
			int secondChoix = 0;
			switch (choix) {
			case 1:
				do{
				System.out.println("Et maintenant ?");
				System.out.println("1- Ajouter un étudiant");
				System.out.println("2- Modifier un étudiant");
				System.out.println("3- Rechercher un étudiant");
				System.out.println("4- Cancel");
				System.out.println("-----------------------------------------------");
				 
					secondChoix = clavier.nextInt();
					switch (secondChoix) {
					case 1:
						ajoutEtudiant(clavier);
						break;
					case 2:
						modifyEtudiant(clavier);
						break;
					case 3:
						findEtudiant(clavier);
						break;
					case 4:
						break;
					default:
						System.out.println("Tu sais pas lire ?");
						System.out.println("-----------------------------------------------");
					}
				}while (secondChoix != 4);
				break;
			case 2:
				while (secondChoix != 4) {
					System.out.println("Et maintenant ?");
					System.out.println("1- Ajouter un établissement");
					System.out.println("2- Modifier un établissement");
					System.out.println("3- Rechercher un établissement");
					System.out.println("4- Cancel");
					secondChoix = clavier.nextInt();
					System.out.println("-----------------------------------------------");
					switch (secondChoix) {
					case 1:
						ajoutEtablissement(clavier);
						break;
					case 2:
						mofifyEtablissement(clavier);
						break;
					case 3:
						findEtablissement(clavier);
						break;
					case 4:
						break;
					default:
						System.out.println("Tu sais pas lire ?");
						System.out.println("-----------------------------------------------");
					}
				}
				break;
			case 3:
				while (secondChoix != 4) {
					System.out.println("Et maintenant ?");
					System.out.println("1- Ajouter un formation");
					System.out.println("2- Modifier un formation");
					System.out.println("3- Rechercher un formation");
					System.out.println("4- Cancel");
					secondChoix = clavier.nextInt();
					System.out.println("-----------------------------------------------");
					switch (secondChoix) {
					case 1:
						ajoutFormation(clavier);
						break;
					case 2:
						modifyFormation(clavier);
						break;
					case 3:
						findFormation(clavier);
						break;
					case 4:
						break;
					default:
						System.out.println("Tu sais pas lire ?");
						System.out.println("-----------------------------------------------");
					}
				}
				break;
			case 4:
				printAllStudent();
				System.out.println("-----------------------------------------------");
				break;
			case 5:
				printAllFormation();
				System.out.println("-----------------------------------------------");
				break;
			case 6:
				printAllUniversity();
				System.out.println("-----------------------------------------------");
				break;
			case 7:
				saveToXML(DAOFactory.getUniversiteDAO().getUniversity());
				break;
			case 8:
				List<Universite> u = importationXMLDB();
				chargementMongoDB(u);
				break;
			case 9:
				coursesOfOneStudent(clavier);
				System.out.println("-----------------------------------------------");
				break;
			case 10:
				try {
					pageWeb();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("Tu sais pas lire ?");
			}
		}
		// fonction save data à chaque fin de programme TODO
		System.out.println("DATA SAVE !");
		System.out.println("BYE !");

	}
	
	private static void pageWeb() throws TransformerException {
/*		TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("transform.xslt"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File("dbProject.xml"));
        transformer.transform(text, new StreamResult(new File("output.xml")));*/

	}

	private static void findFormation(Scanner clavier) {
		int choix;
		do{
		System.out.println("FONCTION RECHERCHE FORMATION :");
		System.out.println("1- by ID");
		System.out.println("2- by intitule");
		System.out.println("3- by discipline");
		System.out.println("4- Annuler");
		choix = clavier.nextInt();
		switch(choix){
		case 1:
			System.out.print("ID : ");
			System.out.println(DAOFactory.getFormationDAO().find(clavier.nextInt()).toString());
			break;
		case 2:
			System.out.print("Intitule : ");
			String intitule = clavier.next();
			for(Formation f : DAOFactory.getFormationDAO().findByIntitule(intitule)){
				System.out.println(f.toString());
			}
			break;
		case 3:
			System.out.print("Recherche par combien de disciplines ?");
			int nbDisc = clavier.nextInt();
			List<String> listDisc = new ArrayList<>();
			for(int i = 0; i < nbDisc; i++){
				System.out.print(i+ ": ");
				clavier.nextLine();
				listDisc.add(clavier.nextLine());
			}
			for(Formation f : DAOFactory.getFormationDAO().findByDiscipline(listDisc)){
				System.out.println(f.toString());
			}
			break;
		case 4:
			System.out.println("Merci d'avoir utilisé la fonction recherche formation !");
			break;
		default:
		}
		}while(choix != 4);
		
	}

	private static void modifyFormation(Scanner clavier) {
		int choix;
		System.out.print("ID : "); int id = clavier.nextInt();
		do{
			System.out.println("Voici les informations que nous avons à ce jour : ");
			Formation f = DAOFactory.getFormationDAO().find(id);
			System.out.println(f.toString());
			System.out.println("Que souhaitez-vous modifier ?");
			System.out.println("1- Intitule");
			System.out.println("2- Disciplines");
			System.out.println("3- Annuler");
			choix = clavier.nextInt();
			switch(choix){
			case 1:
				System.out.print("Nouvel intitule : "); clavier.nextLine(); String intitule = clavier.nextLine();
				f.setIntitule(intitule);
				DAOFactory.getFormationDAO().update(f);
				break;
			case 2:
				System.out.println("Voici les disciplines de cette formation :");
				for(String s : f.getDisciplines())
					System.out.print(s + " ");
				int secondChoix;
				do{
					System.out.println("Que faisons nous ?");
					System.out.println("1- Ajout d'une discipline");
					System.out.println("2- Suppression d'une discipline");
					System.out.println("3- Annuler");
					secondChoix = clavier.nextInt();
					switch(secondChoix){
					case 1:
						System.out.print("Nom de la nouvelle discipline : "); clavier.nextLine(); String newDisc = clavier.nextLine();
						List<String> disciplines = f.getDisciplines();
						disciplines.add(newDisc);
						f.setDisciplines(disciplines);
						DAOFactory.getFormationDAO().update(f);
						break;
					case 2:
						System.out.print("Le nom de la discipline a supprimer : "); clavier.nextLine(); String supprDisc = clavier.nextLine();
						List<String> discipliness = f.getDisciplines();
						discipliness.remove(discipliness.indexOf(supprDisc));
						f.setDisciplines(discipliness);
						DAOFactory.getFormationDAO().update(f);
						break;
					case 3:
						break;
					default:
					}
				}while(secondChoix != 3);
				break;
			case 3:
				break;
			default:
			}
		}while(choix != 3);
		
	}

	private static void ajoutFormation(Scanner clavier) {
		System.out.println("Select université : ");
		DAOFactory.getUniversiteDAO().printAll();
		int idUniv = clavier.nextInt();
		Universite u = (DAOFactory.getUniversiteDAO().find(idUniv));
		List<Etablissement> etablissements = u.getEtablissements();
		for(Etablissement e : etablissements)
			System.out.println(e.toString());
		System.out.println("Select etablissement : ");
		int idEtab = clavier.nextInt();
		Etablissement e = DAOFactory.getEtablissementDAO().find(idEtab);
		List<Formation> listFormations = e.getFormations();
		System.out.println("Ajoutons maintenant la formation :");
		System.out.print("Intitule : "); clavier.nextLine(); String intitule = clavier.nextLine();
		System.out.print("Combien de discipline ? "); int nbDisc = clavier.nextInt();
		List<String> disciplines = new ArrayList<>();
		for(int i=0; i<nbDisc; i++){
			System.out.print(i+" : "); clavier.nextLine(); disciplines.add(clavier.nextLine());
		}
		int idF = DAOFactory.getFormationDAO().create(new Formation(0,intitule,disciplines));
		listFormations.add(new Formation(idF, intitule, disciplines));
		e.setFormations(listFormations);
		DAOFactory.getEtablissementDAO().update(e);
	}

	private static void findEtablissement(Scanner clavier) {
		int choix;
		do{
		System.out.println("FONCTION RECHERCHE ETABLISSEMENT :");
		System.out.println("1- by ID");
		System.out.println("2- by name");
		System.out.println("3- by location");
		System.out.println("4- Annuler");
		choix = clavier.nextInt();
		switch(choix){
		case 1:
			System.out.print("ID : ");
			System.out.println(DAOFactory.getEtablissementDAO().find(clavier.nextInt()).toString());
			break;
		case 2:
			System.out.print("Nom : ");
			String nom = clavier.next();
			for(Etablissement e : DAOFactory.getEtablissementDAO().findByName(nom)){
				System.out.println(e.toString());
			}
			break;
		case 3:
			System.out.print("ville : ");
			clavier.nextLine();
			String ville = clavier.nextLine();
			System.out.print("code postal : ");
			int code = clavier.nextInt();
			System.out.print("voie : ");
			clavier.nextLine();
			String voie = clavier.nextLine();
			for(Etablissement e : DAOFactory.getEtablissementDAO().findByLocation(ville,code,voie)){
				System.out.println(e.toString());
			}
			break;
		case 4:
			System.out.println("Merci d'avoir utilisé la fonction recherche établissement !");
			break;
		default:
		}
		}while(choix != 4);
		
	}

	private static void mofifyEtablissement(Scanner clavier) {
		int choix;
		System.out.print("ID : ");
		int id = clavier.nextInt();
		do{
		System.out.println("Voici les informations que nous avons à ce jour : ");
		Etablissement e = DAOFactory.getEtablissementDAO().find(id);
		System.out.println(e.toString());
		System.out.println("Que souhaitez-vous modifier ?");
		System.out.println("1- Nom/Type");
		System.out.println("2- Adresse");
		System.out.println("3- Etudiants");
		System.out.println("4- Diplomes");
		System.out.println("5- Formations");
		System.out.println("6- Annuler");
		choix = clavier.nextInt();
		switch(choix){
		case 1:
			System.out.print("Nom : ");
			clavier.nextLine();
			e.setNom(clavier.nextLine());
			System.out.print("Type : ");
			clavier.nextLine();
			e.setType(clavier.nextLine());
			DAOFactory.getEtablissementDAO().update(e);
			break;
		case 2:
			Adresse adresse = e.getAdresse();
			System.out.print("Adresse, numero : ");
			adresse.setNumero(clavier.nextInt());
			System.out.print("voie : ");
			clavier.nextLine();
			adresse.setVoie(clavier.nextLine());
			System.out.print("code postal : ");
			adresse.setCode_postal(clavier.nextInt());
			System.out.print("ville : ");
			clavier.nextLine();
			adresse.setVille(clavier.nextLine());
			e.setAdresse(adresse);
			DAOFactory.getEtablissementDAO().update(e);
			break;
		case 3:
			int secondChoix;
			do{
			System.out.println("A propos des étudiants :");
			System.out.println("1- Affectation d'un étudiant");
			System.out.println("2- Suppression d'un étudiant");
			System.out.println("3- Annuler");
			secondChoix = clavier.nextInt();
			switch(secondChoix){
			case 1:
				System.out.println("Quel étudiant affectons-nous à cet établissement ?");
				DAOFactory.getEtudiantDAO().printAll();
				System.out.println("ID : "); int idEtud = clavier.nextInt();
				if(containsIDEtud(e.getEtudiants(), idEtud)){
					System.out.println("Cet étudiant est déjà affecter a cet établissement !");
				}else {
					List<Etudiant> le = e.getEtudiants();
					le.add(DAOFactory.getEtudiantDAO().find(idEtud));
					e.setEtudiants(le);
					DAOFactory.getEtablissementDAO().update(e);
				}
				break;
			case 2:
				System.out.println("Quel étudiant supprimons-nous de cet établissement ?");
				for(Etudiant etud : e.getEtudiants()){
					System.out.println(etud.toString());
				}
				System.out.println("ID : "); int idEtudS = clavier.nextInt();
				if(containsIDEtud(e.getEtudiants(), idEtudS)){
					List<Etudiant> let = e.getEtudiants();
					for(int i=0; i<let.size(); i++){
						Etudiant etudi = let.get(i);
						if(etudi.getId() == idEtudS)
							let.remove(i);
					}
					e.setEtudiants(let);
					DAOFactory.getEtablissementDAO().update(e);
				}else{
					System.out.println("Cet étudiant n'existe pas");
				}
				break;
			case 3:
				break;
			default:
			}
			} while(secondChoix != 3);
			break;
		case 4:
			//TODO
			break;
		case 5:
			//TODO
			break;
		case 6:
			System.out.println("-----------------------------------------------");
			break;
		default:	
		}
		}while(choix != 6);
	}

	private static void ajoutEtablissement(Scanner clavier) {
		System.out.println("Pour quelle université souhaitez-vous ajouter un établissement ?");
		System.out.println("Select université : ");
		DAOFactory.getUniversiteDAO().printAll();
		int idUniv = clavier.nextInt();
		Universite u = (DAOFactory.getUniversiteDAO().find(idUniv));
		// Let's create etablissement
		// int id, String nom, String type, Adresse adresse, List<Etudiant> etudiants,List<String> diplomes, List<Formation> formations
		System.out.println("Let's create an etablissement !");
		System.out.print("Nom : "); clavier.nextLine(); String name = clavier.nextLine();
		System.out.print("Type (faculte,institut,..): "); clavier.nextLine(); String type = clavier.nextLine();
		System.out.println("Adresse,");
		Adresse adresse = new Adresse();
		System.out.print("Numéro : "); int numero = clavier.nextInt();
		adresse.setNumero(numero);
		System.out.print("Voie : "); clavier.nextLine(); String voie = clavier.nextLine();
		adresse.setVoie(voie);
		System.out.print("Code postal : "); int code = clavier.nextInt();
		adresse.setCode_postal(code);
		System.out.print("Ville : "); clavier.nextLine(); String ville = clavier.nextLine();
		adresse.setVille(ville);
		System.out.print("Nombre de diplome : "); int nbDiplome = clavier.nextInt();
		List<String> diplomes = new ArrayList<>();
		for(int i = 0; i<nbDiplome ; i++){
			System.out.print(""+i+" : "); clavier.nextLine(); diplomes.add(clavier.nextLine());
		}
		Etablissement etab = new Etablissement();
		etab.setNom(name);
		etab.setType(type);
		etab.setAdresse(adresse);
		etab.setDiplomes(diplomes);
		etab.setEtudiants(new ArrayList<Etudiant>());
		etab.setFormations(new ArrayList<Formation>());
		List<Etablissement> le = u.getEtablissements();
		le.add(DAOFactory.getEtablissementDAO().find(DAOFactory.getEtablissementDAO().create(etab)));
		u.setEtablissements(le);
		if(type.equals("faculte")){
			u.addOneFaculte();
		}
		DAOFactory.getUniversiteDAO().update(u);
		System.out.println("Pour associer des étudiants ou des formations à cet établissement, referez-vous aux fonctions correspondantes.");
	}

	private static void saveToXML(List<Universite> universites) {
	    Document dom;

	    // instance of a DocumentBuilderFactory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        // use factory to get an instance of document builder
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        // create instance of DOM
	        dom = db.newDocument();

	        // create the root element
	        Element rootEle = dom.createElement("universites");
	        
	        for(Universite univ : universites){
	        	// create data elements and place them under root
	        	Element universite = dom.createElement("universite");
	        		// append id
	        		Element id = dom.createElement("id");
	        		id.appendChild(dom.createTextNode(Integer.toString((univ.getId()))));
	        		universite.appendChild(id);
	        		// append name
	        		Element name = dom.createElement("name");
	        		name.appendChild(dom.createTextNode(univ.getNom()));
	        		universite.appendChild(name);
	        		// append nbFaculte
	        		Element nbFaculte = dom.createElement("nbFaculte");
	        		nbFaculte.appendChild(dom.createTextNode(Integer.toString(univ.getNb_facultes())));
	        		universite.appendChild(nbFaculte);
	        		// append nbEtudiant
	        		Element nbEtudiant = dom.createElement("nbEtudiant");
	        		nbEtudiant.appendChild(dom.createTextNode(Integer.toString(univ.getNb_etudiants())));
	        		universite.appendChild(nbEtudiant);
	        		// append etablissements
	        		Element etablissements = dom.createElement("etablissements");
	        		for (Etablissement etab : univ.getEtablissements()){
	        			Element etablissement = dom.createElement("etablissement");
	        			// append id
		        		Element idEtab = dom.createElement("id");
		        		idEtab.appendChild(dom.createTextNode(Integer.toString((etab.getId()))));
		        		etablissement.appendChild(idEtab);
		        		// append name
		        		Element nameEtab = dom.createElement("name");
		        		nameEtab.appendChild(dom.createTextNode(etab.getNom()));
		        		etablissement.appendChild(nameEtab);
	        			// append type
		        		Element typeEtab = dom.createElement("type");
		        		typeEtab.appendChild(dom.createTextNode(etab.getType()));
		        		etablissement.appendChild(typeEtab);
		        		// append adresse
		        		Element adresseEtab = dom.createElement("adresse");
		        			// append id
		        			Element idAdresse = dom.createElement("id");
		        			idAdresse.appendChild(dom.createTextNode(Integer.toString(etab.getAdresse().getId())));
		        			adresseEtab.appendChild(idAdresse);
		        			// append numero
		        			Element numeroAdresse = dom.createElement("numero");
		        			numeroAdresse.appendChild(dom.createTextNode(Integer.toString(etab.getAdresse().getNumero())));
		        			adresseEtab.appendChild(numeroAdresse);
		        			// append voie
		        			Element voieAdresse = dom.createElement("voie");
		        			voieAdresse.appendChild(dom.createTextNode(etab.getAdresse().getVoie()));
		        			adresseEtab.appendChild(voieAdresse);
		        			// append code postal
		        			Element codeAdresse = dom.createElement("codePostal");
		        			codeAdresse.appendChild(dom.createTextNode(Integer.toString(etab.getAdresse().getCode_postal())));
		        			adresseEtab.appendChild(codeAdresse);
		        			// append ville
		        			Element villeAdresse = dom.createElement("ville");
		        			villeAdresse.appendChild(dom.createTextNode(etab.getAdresse().getVille()));
		        			adresseEtab.appendChild(villeAdresse);
		        		etablissement.appendChild(adresseEtab);
		        		// append etudiants
		        		Element etudiants = dom.createElement("etudiants");
		        		for(Etudiant etud : etab.getEtudiants()){
		        			Element etudiant = dom.createElement("etudiant");
		        			// append id
		        			Element idEtudiant = dom.createElement("id");
		        			idEtudiant.appendChild(dom.createTextNode(Integer.toString(etud.getId())));
		        			etudiant.appendChild(idEtudiant);
		        			// append nom
		        			Element nomEtudiant = dom.createElement("nom");
		        			nomEtudiant.appendChild(dom.createTextNode(etud.getNom()));
		        			etudiant.appendChild(nomEtudiant);
		        			// append prenom
		        			Element prenomEtudiant = dom.createElement("prenom");
		        			prenomEtudiant.appendChild(dom.createTextNode(etud.getPrenom()));
		        			etudiant.appendChild(prenomEtudiant);
		        			// append adresse
			        		Element adresseEtud = dom.createElement("adresse");
			        			// append id
			        			Element idAdresseEtud = dom.createElement("id");
			        			idAdresseEtud.appendChild(dom.createTextNode(Integer.toString(etud.getAdresse().getId())));
			        			adresseEtud.appendChild(idAdresseEtud);
			        			// append numero
			        			Element numeroAdresseEtud = dom.createElement("numero");
			        			numeroAdresseEtud.appendChild(dom.createTextNode(Integer.toString(etud.getAdresse().getNumero())));
			        			adresseEtud.appendChild(numeroAdresseEtud);
			        			// append voie
			        			Element voieAdresseEtud = dom.createElement("voie");
			        			voieAdresseEtud.appendChild(dom.createTextNode((etud.getAdresse().getVoie())));
			        			adresseEtud.appendChild(voieAdresseEtud);
			        			// append code postal
			        			Element codeAdresseEtud = dom.createElement("codePostal");
			        			codeAdresseEtud.appendChild(dom.createTextNode(Integer.toString(etud.getAdresse().getCode_postal())));
			        			adresseEtud.appendChild(codeAdresseEtud);
			        			// append ville
			        			Element villeAdresseEtud = dom.createElement("ville");
			        			villeAdresseEtud.appendChild(dom.createTextNode(etud.getAdresse().getVille()));
			        			adresseEtud.appendChild(villeAdresseEtud);
			        		etudiant.appendChild(adresseEtud);
			        		// append formation
			        		Element formationEtud = dom.createElement("formation");
			        			//append id
			        			Element idFormation = dom.createElement("id");
			        			idFormation.appendChild(dom.createTextNode(Integer.toString(etud.getFormation().getId())));
			        			formationEtud.appendChild(idFormation);
			        			// append intitule
			        			Element intituleFormation = dom.createElement("intitule");
			        			intituleFormation.appendChild(dom.createTextNode(etud.getFormation().getIntitule()));
			        			formationEtud.appendChild(intituleFormation);
			        			// append disciplines
			        			Element disciplinesEtud = dom.createElement("disciplines");
			        			for(String disc : etud.getFormation().getDisciplines()){
			        				Element discipline = dom.createElement("discipline");
			        				discipline.appendChild(dom.createTextNode(disc));
			        				disciplinesEtud.appendChild(discipline);
			        			}
			        			formationEtud.appendChild(disciplinesEtud);
			        		etudiant.appendChild(formationEtud);
			        		// append statut
			        		Element statutEtud = dom.createElement("statut");
			        		statutEtud.appendChild(dom.createTextNode(etud.getStatut().getName()));
			        		etudiant.appendChild(statutEtud);
			        	etudiants.appendChild(etudiant);	
		        		}
		        		etablissement.appendChild(etudiants);
		        		// append diplome
		        		Element diplomes = dom.createElement("diplomes");
		        		for(String d : etab.getDiplomes()){
		        			Element diplome = dom.createElement("diplome");
		        			diplome.appendChild(dom.createTextNode(d));
		        			diplomes.appendChild(diplome);
		        		}
		        		etablissement.appendChild(diplomes);
		        		// append formations
		        		Element formations = dom.createElement("formations");
		        		for(Formation form : etab.getFormations()){
		        			Element formation = dom.createElement("formation");
		        			//append id
		        			Element idFormation = dom.createElement("id");
		        			idFormation.appendChild(dom.createTextNode(Integer.toString(form.getId())));
		        			formation.appendChild(idFormation);
		        			// append intitule
		        			Element intituleFormation = dom.createElement("intitule");
		        			intituleFormation.appendChild(dom.createTextNode(form.getIntitule()));
		        			formation.appendChild(intituleFormation);
		        			// append disciplines
		        			Element disciplinesEtud = dom.createElement("disciplines");
		        			for(String disc : form.getDisciplines()){
		        				Element discipline = dom.createElement("discipline");
		        				discipline.appendChild(dom.createTextNode(disc));
		        				disciplinesEtud.appendChild(discipline);
		        			}
		        			formation.appendChild(disciplinesEtud);
		        			formations.appendChild(formation);
		        		}
		        		etablissement.appendChild(formations);
		        		etablissements.appendChild(etablissement);
	        		}
	        		universite.appendChild(etablissements);
	        		rootEle.appendChild(universite);
	        }

	        dom.appendChild(rootEle);

	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

	            // send DOM to file
	            tr.transform(new DOMSource(dom), 
	                                 new StreamResult(new FileOutputStream("dbProject.xml")));

	        } catch (TransformerException te) {
	            System.out.println(te.getMessage());
	        } catch (IOException ioe) {
	            System.out.println(ioe.getMessage());
	        }
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	    }
	}

	private static void coursesOfOneStudent(Scanner clavier) {
		System.out.println("Vous devez avoir le numero d'un étudiant pour connaitre ces cours ! Si vous ne le connaissez pas nous vous invitons à utiliser la fonction de recherche.");
		int choix;
		do{
			System.out.println("1- ID");
			System.out.println("2- Recherche");
			System.out.println("3- Annuler");
			choix = clavier.nextInt();
			switch(choix){
			case 1:
				System.out.print("ID : "); 
				for(String disc : DAOFactory.getEtudiantDAO().getCourses(clavier.nextInt())){
					System.out.println(disc);
				}
				break;
			case 2:
				findEtudiant(clavier);
				break;
			case 3:
				break;
			default:
			}
		}while(choix != 3 && choix != 1);
	}

	private static void printAllUniversity() {
		DAOFactory.getUniversiteDAO().printAll();
	}

	private static void printAllFormation() {
		DAOFactory.getFormationDAO().printAll();		
	}

	private static void printAllStudent() {
		DAOFactory.getEtudiantDAO().printAll();
	}

	private static void findEtudiant(Scanner clavier) {
		int choix;
		do{
		System.out.println("FONCTION RECHERCHE ETUDIANT :");
		System.out.println("1- by ID");
		System.out.println("2- by name");
		System.out.println("3- by location");
		System.out.println("4- by statut");
		System.out.println("5- Annuler");
		choix = clavier.nextInt();
		switch(choix){
		case 1:
			System.out.print("ID : ");
			System.out.println(DAOFactory.getEtudiantDAO().find(clavier.nextInt()).toString());
			break;
		case 2:
			System.out.print("Nom : ");
			String nom = clavier.next();
			System.out.print("Prénom : ");
			String prenom = clavier.next();
			List<Etudiant> listeEtape = DAOFactory.getEtudiantDAO().findByName(nom,prenom);
			for(Etudiant e : listeEtape){
				System.out.println(e.toString());
			}
			break;
		case 3:
			System.out.print("ville : ");
			clavier.nextLine();
			String ville = clavier.nextLine();
			System.out.print("code postal : ");
			int code = clavier.nextInt();
			System.out.print("voie : ");
			clavier.nextLine();
			String voie = clavier.nextLine();
			for(Etudiant e : DAOFactory.getEtudiantDAO().findByLocation(ville,code,voie)){
				System.out.println(e.toString());
			}
			break;
		case 4:
			System.out.print("statut : ");
			for(Etudiant e : DAOFactory.getEtudiantDAO().findByStatut(clavier.next())){
				System.out.println(e.toString());
			}
			break;
		case 5:
			System.out.println("Merci d'avoir utilisé la fonction recherche étudiant !");
		default:
		}
		}while(choix != 5);
	}

	private static void modifyEtudiant(Scanner clavier) {
		int choix;
		System.out.print("ID : ");
		int id = clavier.nextInt();
		do{
		System.out.println("Voici les informations que nous avons à ce jour : ");
		Etudiant e = (Etudiant) DAOFactory.getEtudiantDAO().find(id);
		System.out.println(e.toString());
		System.out.println("Que souhaitez-vous modifier ?");
		System.out.println("1- Nom/Prénom");
		System.out.println("2- Adresse");
		System.out.println("3- Statut");
		System.out.println("4- Annuler");
		choix = clavier.nextInt();
		switch(choix){
		case 1:
			System.out.print("Nom : ");
			e.setNom(clavier.next());
			System.out.print("Prénom : ");
			e.setPrenom(clavier.next());
			DAOFactory.getEtudiantDAO().update(e);
			break;
		case 2:
			Adresse adresse = e.getAdresse();
			System.out.print("Adresse, numero : ");
			adresse.setNumero(clavier.nextInt());
			System.out.print("voie : ");
			clavier.nextLine();
			adresse.setVoie(clavier.nextLine());
			System.out.print("code postal : ");
			adresse.setCode_postal(clavier.nextInt());
			System.out.print("ville : ");
			clavier.nextLine();
			adresse.setVille(clavier.nextLine());
			e.setAdresse(adresse);
			DAOFactory.getEtudiantDAO().update(e);
			break;
		case 3:
			e.setStatut(Statut.getOpposite(e.getStatut()));
			DAOFactory.getEtudiantDAO().update(e);
			break;
		case 4:
			System.out.println("-----------------------------------------------");
			break;
		default:	
		}
		}while(choix != 4);
	}

	private static void chargementMongoDB(List<Universite> u) {
		for(Universite universite : u){
			DAOFactory.getUniversiteDAO().create(universite);
			for(Etablissement etablissement : universite.getEtablissements()){
				DAOFactory.getEtablissementDAO().create(etablissement);
				for(Formation formation : etablissement.getFormations()){
					DAOFactory.getFormationDAO().create(formation);
				}
				for(Etudiant etudiant : etablissement.getEtudiants()){
					DAOFactory.getEtudiantDAO().create(etudiant);
					DAOFactory.getAdresseDAO().create(etudiant.getAdresse());
				}
				DAOFactory.getAdresseDAO().create(etablissement.getAdresse());
				
			}
		}
	}

	private static List<Universite> importationXMLDB() {
		String filePath = "dbProject.xml";
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try{
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nodeList = doc.getElementsByTagName("universite");
			List<Universite> univList = new ArrayList<Universite>();
			NodeList nl = doc.getElementsByTagName("adresse");
			for(int i=0; i<nodeList.getLength(); i++){
				univList.add(getUniversite(nodeList.item(i)));
			}
			return univList;
			
		}catch (SAXException | ParserConfigurationException | IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	private static Universite getUniversite(Node node){
		Universite univ = new Universite();
		if(node.getNodeType() == Node.ELEMENT_NODE){
			Element element = (Element) node;
			univ.setId(Integer.parseInt(getTagValue("id", element)));
			univ.setNom(getTagValue("name", element));
			univ.setNb_facultes((Integer.parseInt(getTagValue("nbFaculte", element))));
			univ.setNb_etudiants(Integer.parseInt(getTagValue("nbEtudiant", element)));
			univ.setEtablissements(getEtablissementValue("etablissement", element));
		}
		return univ;
	}
	
	private static String getTagValue(String tag, Element element){
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}
	
	private static List<Etablissement> getEtablissementValue(String tag, Element element){
		List<Etablissement> listEtablissements = new ArrayList<>();
		NodeList etablissements = element.getElementsByTagName(tag);
		for(int j = 0 ; j < etablissements.getLength(); j++ ){
			Element etablissement = (Element) etablissements.item(j);
			Etablissement eta = new Etablissement();
			eta.setId(Integer.parseInt(etablissement.getElementsByTagName("id").item(0).getTextContent()));
			eta.setNom(etablissement.getElementsByTagName("name").item(0).getTextContent());
			eta.setType(etablissement.getElementsByTagName("type").item(0).getTextContent());
			eta.setAdresse(getAdresseValue("adresse", etablissement));
			eta.setEtudiants(getEtudiantsValue("etudiant", etablissement));
			eta.setDiplomes(getDiplomes("diplome", etablissement));
			eta.setFormations(getFormations("formation", etablissement));
			listEtablissements.add(eta);
		}
		return listEtablissements;
	}

	private static List<Formation> getFormations(String tag, Element element) {
		List<Formation> listFormations = new ArrayList<>();
		NodeList nd = element.getElementsByTagName(tag);
		for(int n=0; n<nd.getLength(); n++){
			Element e = (Element) nd.item(n);
			Formation f = new Formation();
			f.setId(Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent()));
			f.setIntitule(e.getElementsByTagName("intitule").item(0).getTextContent());
			List<String> disciplines = new ArrayList<>();
			NodeList nd2 = e.getElementsByTagName("discipline");
			for(int l=0; l < nd2.getLength(); l++){
				Element disci = (Element) nd2.item(l);
				disciplines.add(disci.getTextContent());
			}
			f.setDisciplines(disciplines);
			boolean c = containsID(listFormations,f.getId());
			if(!c)
				listFormations.add(f);
		}
		return listFormations;
	}

	private static boolean containsID(List<Formation> listFormations, int id) {
		for(Formation f : listFormations)
			if(f.getId() == id)
				return true;
		return false;
	}

	private static boolean containsIDEtud(List<Etudiant> listEtudiants, int id){
		for(Etudiant e : listEtudiants)
			if(e.getId() == id)
				return true;
		return false;
	}
	
	private static List<String> getDiplomes(String tag, Element element) {
		List<String> listDiplome = new ArrayList<>();
		NodeList nd = element.getElementsByTagName(tag);
		for(int m=0; m<nd.getLength(); m++){
			Element e = (Element) nd.item(m);
			listDiplome.add(e.getTextContent());
		}
		return listDiplome;
	}

	private static List<Etudiant> getEtudiantsValue(String tag, Element element) {
		List<Etudiant> listEtudiants = new ArrayList<>();
		NodeList nodeEtudiants = element.getElementsByTagName(tag);
		for (int k = 0; k < nodeEtudiants.getLength() ; k++){
			Element etudiant = (Element) nodeEtudiants.item(k);
			Etudiant etud = new Etudiant();
			etud.setId(Integer.parseInt(etudiant.getElementsByTagName("id").item(0).getTextContent()));
			etud.setNom(etudiant.getElementsByTagName("nom").item(0).getTextContent());
			etud.setPrenom(etudiant.getElementsByTagName("prenom").item(0).getTextContent());
			etud.setAdresse(getAdresseValue("adresse", etudiant));
			etud.setFormation(getFormationValue("formation", etudiant));
			etud.setStatut(Statut.getStautByName(etudiant.getElementsByTagName("statut").item(0).getTextContent()));
			listEtudiants.add(etud);
		}
		return listEtudiants;
	}

	private static Formation getFormationValue(String tag, Element etudiant) {
		Formation f = new Formation();
		Element formation = (Element) etudiant.getElementsByTagName(tag).item(0);
		f.setId(Integer.parseInt(formation.getElementsByTagName("id").item(0).getTextContent()));
		f.setIntitule(formation.getElementsByTagName("intitule").item(0).getTextContent());
		List<String> disciplines = new ArrayList<>();
		NodeList nd = formation.getElementsByTagName("discipline");
		for(int l=0; l < nd.getLength(); l++){
			Element disci = (Element) nd.item(l);
			disciplines.add(disci.getTextContent());
		}
		f.setDisciplines(disciplines);
		return f;
	}

	private static Adresse getAdresseValue(String tag, Element element) {
		Adresse adresse = new Adresse();
		Element adresses = (Element) element.getElementsByTagName(tag).item(0);
		adresse.setId(Integer.parseInt(adresses.getElementsByTagName("id").item(0).getTextContent()));
		adresse.setNumero(Integer.parseInt(adresses.getElementsByTagName("numero").item(0).getTextContent()));
		adresse.setVoie(adresses.getElementsByTagName("voie").item(0).getTextContent());
		adresse.setCode_postal(Integer.parseInt(adresses.getElementsByTagName("codePostal").item(0).getTextContent()));
		adresse.setVille(adresses.getElementsByTagName("ville").item(0).getTextContent());
		return adresse;
	}

	private static void ajoutEtudiant(Scanner clavier) {
		System.out.println("Let's go create a student !");
		Etudiant etudiant = new Etudiant();
		System.out.print("Nom : "); String name = clavier.next();
		etudiant.setNom(name);
		System.out.print("Prénom : "); String prenom = clavier.next();
		etudiant.setPrenom(prenom);
		System.out.println("Adresse,");
		Adresse adresse = new Adresse();
		System.out.print("Numéro : "); int numero = clavier.nextInt();
		adresse.setNumero(numero);
		System.out.print("Voie : "); clavier.nextLine(); String voie = clavier.nextLine();
		adresse.setVoie(voie);
		System.out.print("Code postal : "); int code = clavier.nextInt();
		adresse.setCode_postal(code);
		System.out.print("Ville : "); clavier.nextLine(); String ville = clavier.nextLine();
		adresse.setVille(ville);
		etudiant.setAdresse(adresse);
		System.out.println("Select université : ");
		DAOFactory.getUniversiteDAO().printAll();
		int idUniv = clavier.nextInt();
		Universite u = (DAOFactory.getUniversiteDAO().find(idUniv));
		List<Etablissement> etablissements = u.getEtablissements();
		for(Etablissement e : etablissements)
			System.out.println(e.toString());
		System.out.println("Select etablissement : ");
		int idEtab = clavier.nextInt();
		List<Formation> formations = (DAOFactory.getEtablissementDAO().find(idEtab)).getFormations();
		for(Formation f : formations)
			System.out.println(f.toString());
		System.out.println("Select formation : ");
		int idForm = clavier.nextInt();
		Formation formation = DAOFactory.getFormationDAO().find(idForm);
		etudiant.setFormation(formation);
		System.out.println("Inscrit : ");
		etudiant.setStatut(Statut.getStatut(clavier));
		int idNewEtud = DAOFactory.getEtudiantDAO().create(etudiant);
		
		// incremente nb_etudiant in université
		Universite univ = (DAOFactory.getUniversiteDAO().find(idUniv));
		univ.addOneStudent();
		DAOFactory.getUniversiteDAO().update(univ);
		
		// add student to etablissement
		Etablissement etab = DAOFactory.getEtablissementDAO().find(idEtab);
		List<Etudiant> le = etab.getEtudiants();
		le.add(DAOFactory.getEtudiantDAO().find(idNewEtud));
		etab.setEtudiants(le);
		DAOFactory.getEtablissementDAO().update(etab);
	}

	public static void printChoix() {
		System.out.println("Que souhaitez-vous faire ? Au menu :");
		System.out.println("1- Ajouter/modifier/rechercher un étudiant");
		System.out.println("2- Ajouter/modifier/rechercher un établissement");
		System.out.println("3- Ajouter/modifier/rechercher une formation");
		System.out.println("4- Lister l'ensemble des étudiants");
		System.out.println("5- Lister l'ensemble des formations");
		System.out.println("6- Lister l'ensemble des universités");
		System.out.println("7- Extraire data sous format XML");
		System.out.println("8- Importation data depuis XML file");
		System.out.println("9- Lister les cours d'un étudiant");
		System.out.println("10- Quitter");
		System.out.println("-----------------------------------------------");
	}

}
