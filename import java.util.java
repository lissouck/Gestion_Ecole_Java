import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestionEnseignants gestionnaire = new GestionEnseignants();
        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("\n--- Système de Gestion des Enseignants ---");
            System.out.println("1. Ajouter un enseignant");
            System.out.println("2. Supprimer un enseignant");
            System.out.println("3. Afficher les enseignants");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le retour à la ligne

            switch (choix) {
                case 1:
                    System.out.print("ID : ");
                    String id = scanner.nextLine();
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine();
                    System.out.print("Matière : ");
                    String matiere = scanner.nextLine();
                    gestionnaire.ajouterEnseignant(new Enseignant(id, nom, matiere));
                    break;
                case 2:
                    System.out.print("ID de l'enseignant à supprimer : ");
                    String idSupp = scanner.nextLine();
                    gestionnaire.supprimerEnseignant(idSupp);
                    break;
                case 3:
                    gestionnaire.afficherEnseignants();
                    break;
                case 4:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        } while (choix != 4);
        scanner.close();
    }
}