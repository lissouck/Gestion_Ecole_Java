public class Enseignant {
    private String id;
    private String nom;
    private String matiere;

    public Enseignant(String id, String nom, String matiere) {
        this.id = id;
        this.nom = nom;
        this.matiere = matiere;
    }

    // Getters et Setters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }

    @Override
    public String toString() {
        return "ID: " + id + " | Nom: " + nom + " | Matière: " + matiere;
    }
}import java.util.ArrayList;
import java.util.List;

public class GestionEnseignants {
    private List<Enseignant> listeEnseignants;

    public GestionEnseignants() {
        this.listeEnseignants = new ArrayList<>();
    }

    // Ajouter un enseignant
    public void ajouterEnseignant(Enseignant e) {
        listeEnseignants.add(e);
        System.out.println("Enseignant ajouté avec succès.");
    }

    // Supprimer un enseignant par ID
    public void supprimerEnseignant(String id) {
        boolean trouve = false;
        for (int i = 0; i < listeEnseignants.size(); i++) {
            if (listeEnseignants.get(i).getId().equals(id)) {
                listeEnseignants.remove(i);
                System.out.println("Enseignant supprimé.");
                trouve = true;
                break;
            }
        }
        if (!trouve) System.out.println("Enseignant non trouvé.");
    }

    // Afficher tous les enseignants
    public void afficherEnseignants() {
        if (listeEnseignants.isEmpty()) {
            System.out.println("Aucun enseignant dans la liste.");
        } else {
            for (Enseignant e : listeEnseignants) {
                System.out.println(e);
            }
        }
    }
}import java.util.Scanner;

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