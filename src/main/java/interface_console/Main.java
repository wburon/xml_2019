package interface_console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
				// TODO : Importation de data
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
		List<Etablissement> etablissements = ((Universite)universiteDAO.find(idUniv)).getEtablissements();
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
