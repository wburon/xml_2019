package interface_console;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
	
	private static DAO universiteDAO = DAOFactory.getUniversiteDAO();
	private static DAO etablissementDAO = DAOFactory.getEtablissementDAO();
	private static DAO formationDAO = DAOFactory.getFormationDAO();
	private static DAO etudiantDAO = DAOFactory.getEtudiantDAO();
	private static DAO adresseDAO = DAOFactory.getAdresseDAO();

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
				System.out.println("Et maintenant ?");
				System.out.println("1- Ajouter un étudiant");
				System.out.println("2- Modifier un étudiant");
				System.out.println("3- Rechercher un étudiant");
				System.out.println("4- Cancel");
				System.out.println("-----------------------------------------------");
				while (secondChoix != 4) {
					secondChoix = clavier.nextInt();
					switch (secondChoix) {
					case 1:
						ajoutEtudiant(clavier);
						break;
					case 2:
						// TODO : modifier un étudiant
						break;
					case 3:
						// TODO : rechercher un étudiant
						break;
					case 4:
						break;
					default:
						System.out.println("Tu sais pas lire ?");
						System.out.println("-----------------------------------------------");
					}
				}
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
						// TODO : ajout d'un établissement
						break;
					case 2:
						// TODO : modifier un établissement
						break;
					case 3:
						// TODO : rechercher un établissement
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
						// TODO : ajout d'un formation
						break;
					case 2:
						// TODO : modifier un formation
						break;
					case 3:
						// TODO : rechercher un formation
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
				// TODO : lister l'ensemble des étudiants
				break;
			case 5:
				// TODO : lister l'ensemble des formations
				break;
			case 6:
				// TODO : lister l'ensemble des universités
				break;
			case 7:
				// TODO : Exctraction de data
				break;
			case 8:
				List<Universite> u = importationXMLDB();
				chargementMongoDB(u);
				break;
			case 9:
				// TODO or not TODO : les cours d'un étudiant
				break;
			case 10:
				break;
			default:
				System.out.println("Tu sais pas lire ?");
			}
		}
		// fonction save data à chaque fin de programme TODO
		System.out.println("DATA SAVE !");
		System.out.println("BYE !");

	}

	private static void chargementMongoDB(List<Universite> u) {
		for(Universite universite : u){
			universiteDAO.create(universite);
			for(Etablissement etablissement : universite.getEtablissements()){
				etablissementDAO.create(etablissement);
				for(Formation formation : etablissement.getFormations()){
					formationDAO.create(formation);
				}
				for(Etudiant etudiant : etablissement.getEtudiants()){
					etudiantDAO.create(etudiant);
					adresseDAO.create(etudiant.getAdresse());
				}
				adresseDAO.create(etablissement.getAdresse());
				
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

	private static List<String> getDiplomes(String tag, Element element) {
		List<String> listDiplome = new ArrayList<>();
		NodeList nd = element.getElementsByTagName("tag");
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
		System.out.print("Voie : "); String voie = clavier.next();
		adresse.setVoie(voie);
		System.out.print("Code postal : "); int code = clavier.nextInt();
		adresse.setCode_postal(code);
		System.out.print("Ville : "); String ville = clavier.next();
		adresse.setVille(ville);
		etudiant.setAdresse(adresse);
		System.out.println("Select université : ");
		universiteDAO.printAll();
		int idUniv = clavier.nextInt();
		Universite u = ((Universite)universiteDAO.find(idUniv));
		List<Etablissement> etablissements = u.getEtablissements();
		for(Etablissement e : etablissements)
			System.out.println(e.toString());
		System.out.println("Select etablissement : ");
		int idEtab = clavier.nextInt();
		List<Formation> formations = ((Etablissement)etablissementDAO.find(idEtab)).getFormations();
		for(Formation f : formations)
			System.out.println(f.toString());
		System.out.println("Select formation : ");
		int idForm = clavier.nextInt();
		Formation formation = (Formation)formationDAO.find(idForm);
		etudiant.setFormation(formation);
		System.out.println("Inscrit : ");
		etudiant.setStatut(Statut.getStatut(clavier));
		DAO etudiantDAO = DAOFactory.getEtudiantDAO();
		etudiantDAO.create(etudiant);
		
		// incremente nb_etudiant in université
		Universite univ = ((Universite)universiteDAO.find(idUniv));
		univ.addOneStudent();
		universiteDAO.update(univ);
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
		System.out.println("9- Lister les cours d'un étudiant ... (a voir)");
		System.out.println("10- Quitter");
		System.out.println("-----------------------------------------------");
	}

}
