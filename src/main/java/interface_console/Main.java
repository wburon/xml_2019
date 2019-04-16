package interface_console;

import java.util.Scanner;

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
						// TODO : ajout d'un étudiant
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
	
	public static void printChoix(){
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
