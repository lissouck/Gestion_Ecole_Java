import java.util.Scanner;

public class Eleve {
    private String nom;
    private String prenom;
    private int age;

    public Eleve(String nom, String prenom, int age) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public String toString() {
        return nom.toUpperCase() + " " + prenom + " | " + age + " ans";
    }
}

class GestionEleves {

    private Eleve[] eleves;
    private int count;

    public GestionEleves() {
        eleves = new Eleve[2]; // petite base, mais extensible
        count = 0;
    }

    private void agrandirTableau() {
        Eleve[] nouveau = new Eleve[eleves.length * 2];

        for (int i = 0; i < eleves.length; i++) {
            nouveau[i] = eleves[i];
        }

        eleves = nouveau;
    }

    public void ajouterEleves(Scanner sc) {

        System.out.print("Combien d'eleves voulez-vous ajouter ? ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {

            if (count == eleves.length) {
                agrandirTableau();
            }

            System.out.println("\nEleve " + (i + 1));

            System.out.print("Nom : ");
            String nom = sc.nextLine();

            System.out.print("Prenom : ");
            String prenom = sc.nextLine();

            System.out.print("Age : ");
            int age = sc.nextInt();
            sc.nextLine();

            eleves[count] = new Eleve(nom, prenom, age);
            count++;
        }
    }

    public void afficherEleves() {

        System.out.println("\nLISTE DES ELEVES");

        if (count == 0) {
            System.out.println("Aucun eleve enregistre");
            return;
        }

        System.out.println("--------------------");

        for (int i = 0; i < count; i++) {
            System.out.println(i + " - " + eleves[i]);
        }

        System.out.println("--------------------");
    }

    public void supprimerEleve(int index) {

        if (index >= 0 && index < count) {

            for (int i = index; i < count - 1; i++) {
                eleves[i] = eleves[i + 1];
            }

            eleves[count - 1] = null;
            count--;

            System.out.println("Eleve supprime");
        } else {
            System.out.println("Index invalide");
        }
    }

    public void modifierEleve(int index, String nom, String prenom, int age) {

        if (index >= 0 && index < count) {

            eleves[index] = new Eleve(nom, prenom, age);
            System.out.println("Eleve modifie");
        } else {
            System.out.println("Index invalide");
        }
    }
}

class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        GestionEleves gestion = new GestionEleves();

        int choix;

        do {
            System.out.println("\nMENU");
            System.out.println("1 Afficher eleves");
            System.out.println("2 Ajouter eleves");
            System.out.println("3 Modifier eleve");
            System.out.println("4 Supprimer eleve");
            System.out.println("0 Quitter");

            System.out.print("Choix : ");
            choix = sc.nextInt();
            sc.nextLine();

            if (choix == 1) {
                gestion.afficherEleves();
            }

            else if (choix == 2) {
                gestion.ajouterEleves(sc);
            }

            else if (choix == 3) {

                gestion.afficherEleves();

                System.out.print("Index : ");
                int i = sc.nextInt();
                sc.nextLine();

                System.out.print("Nom : ");
                String nom = sc.nextLine();

                System.out.print("Prenom : ");
                String prenom = sc.nextLine();

                System.out.print("Age : ");
                int age = sc.nextInt();
                sc.nextLine();

                gestion.modifierEleve(i, nom, prenom, age);
            }

            else if (choix == 4) {

                gestion.afficherEleves();

                System.out.print("Index : ");
                int i = sc.nextInt();
                sc.nextLine();

                gestion.supprimerEleve(i);
            }

        } while (choix != 0);

        System.out.println("Fin programme");
        sc.close();
    }
}