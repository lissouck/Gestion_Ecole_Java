import java.util.Scanner;

class Matiere {
    private String nom;
    private int coefficient;

    public Matiere(String nom, int coefficient) {
        this.nom = nom;
        this.coefficient = coefficient;
    }

    public void setNom(String nom) { this.nom = nom; }
    public void setCoefficient(int coefficient) { this.coefficient = coefficient; }

    @Override
    public String toString() {
        return nom.toUpperCase() + " | coeff " + coefficient;
    }
}

class GestionMatieres {

    private Matiere[] matieres;
    private int count;

    public GestionMatieres() {
        matieres = new Matiere[2];
        count = 0;
    }

    private void agrandir() {
        Matiere[] nouveau = new Matiere[matieres.length * 2];

        for (int i = 0; i < matieres.length; i++) {
            nouveau[i] = matieres[i];
        }

        matieres = nouveau;
    }

    public void ajouterMatieres(Scanner sc) {

        System.out.print("Combien de matieres voulez-vous ajouter ? ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {

            if (count == matieres.length) {
                agrandir();
            }

            System.out.println("\nMatiere " + (i + 1));

            System.out.print("Nom : ");
            String nom = sc.nextLine();

            System.out.print("Coefficient : ");
            int coef = sc.nextInt();
            sc.nextLine();

            matieres[count] = new Matiere(nom, coef);
            count++;
        }
    }

    public void afficherMatieres() {

        System.out.println("\nLISTE DES MATIERES");

        if (count == 0) {
            System.out.println("Aucune matiere enregistree");
            return;
        }

        for (int i = 0; i < count; i++) {
            System.out.println(i + " - " + matieres[i]);
        }
    }

    public void supprimerMatiere(int index) {

        if (index >= 0 && index < count) {

            for (int i = index; i < count - 1; i++) {
                matieres[i] = matieres[i + 1];
            }

            matieres[count - 1] = null;
            count--;

            System.out.println("Matiere supprimee");
        } else {
            System.out.println("Index invalide");
        }
    }

    public void modifierMatiere(int index, String nom, int coef) {

        if (index >= 0 && index < count) {

            matieres[index].setNom(nom);
            matieres[index].setCoefficient(coef);

            System.out.println("Matiere modifiee");
        } else {
            System.out.println("Index invalide");
        }
    }
}

class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        GestionMatieres gestion = new GestionMatieres();

        int choix;

        do {
            System.out.println("\nMENU");
            System.out.println("1 Afficher matieres");
            System.out.println("2 Ajouter matieres");
            System.out.println("3 Modifier matiere");
            System.out.println("4 Supprimer matiere");
            System.out.println("0 Quitter");

            System.out.print("Choix : ");
            choix = sc.nextInt();
            sc.nextLine();

            if (choix == 1) {
                gestion.afficherMatieres();
            }

            else if (choix == 2) {
                gestion.ajouterMatieres(sc);
            }

            else if (choix == 3) {

                gestion.afficherMatieres();

                System.out.print("Index : ");
                int i = sc.nextInt();
                sc.nextLine();

                System.out.print("Nom : ");
                String nom = sc.nextLine();

                System.out.print("Coefficient : ");
                int coef = sc.nextInt();
                sc.nextLine();

                gestion.modifierMatiere(i, nom, coef);
            }

            else if (choix == 4) {

                gestion.afficherMatieres();

                System.out.print("Index : ");
                int i = sc.nextInt();
                sc.nextLine();

                gestion.supprimerMatiere(i);
            }

        } while (choix != 0);

        System.out.println("Fin programme");
        sc.close();
    }
}