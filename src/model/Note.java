import java.util.ArrayList;
import java.util.List;


public class Note {
    private Eleve eleve;
    private Matiere matiere;
    private double valeur;

    public Note(){};

    public Note(Eleve eleve, Matiere matiere, double valeur) {
        this.eleve = eleve;
        this.matiere = matiere;
        this.valeur = valeur;
    }

    public Eleve getEleve() { return eleve; }
    public Matiere getMatiere() { return matiere; }
    public double getValeur() { return valeur; }

    @Override
    public String toString() {
        return matiere.getNom() + " : " + valeur + "/20";
    }


    private List<Note> notes = new ArrayList<>();

    // 1. Ajouter une note à un élève
    public void ajouterNote(Eleve eleve, Matiere matiere, double valeur) {
        if (valeur < 0 || valeur > 20) {
            System.out.println("Note invalide. Elle doit etre entre 0 et 20.");
            return;
        }
        notes.add(new Note(eleve, matiere, valeur));
        System.out.println("Note ajoutee : " + eleve.getNom() + " " + eleve.getPrenom()
                + " -> " + matiere.getNom() + " : " + valeur + "/20");
    }

    // 2. Afficher les notes d'un élève
    public void afficherNotes(Eleve eleve) {
        System.out.println("\n=== Notes de " + eleve.getNom() + " " + eleve.getPrenom() + " ===");
        List<Note> notesEleve = getNotesParEleve(eleve);
        if (notesEleve.isEmpty()) {
            System.out.println("Aucune note enregistree.");
        } else {
            for (Note n : notesEleve) {
                System.out.println("  - " + n);
            }
        }
    }

    // 3. Calculer la moyenne d'un élève
    public double calculerMoyenne(Eleve eleve) {
        List<Note> notesEleve = getNotesParEleve(eleve);
        if (notesEleve.isEmpty()) return 0;
        double total = 0;
        for (Note n : notesEleve){
            total += n.getValeur();
        } 
        return total / notesEleve.size();
    }

    // 4. Afficher le bulletin d'un élève
    public void afficherBulletin(Eleve eleve) {
        System.out.println("\n========================================");
        System.out.println("          BULLETIN DE NOTES             ");
        System.out.println("========================================");
        System.out.println("Matricule  : " + eleve.getId());
        System.out.println("Nom de l'Eleve  : " + eleve.getNom() + " " + eleve.getPrenom());
        System.out.println("Classe : " + eleve.getClasse().getNom());
        System.out.println("----------------------------------------");
        List<Note> notesEleve = getNotesParEleve(eleve);
        if (notesEleve.isEmpty()) {
            System.out.println("Aucune note disponible.");
        } else {
            for (Note n : notesEleve) {
                System.out.printf("  %-20s : %5.2f/20%n", n.getMatiere().getNom(), n.getValeur());
            }
            System.out.println("----------------------------------------");
            double moy = calculerMoyenne(eleve);
            System.out.printf("  %-20s : %5.2f/20%n", "MOYENNE GENERALE", moy);
            System.out.println("  Appreciation : " + appreciation(moy));
        }
        System.out.println("========================================\n");
    }

    // 5. Afficher les statistiques de la classe
    public void afficherStatistiquesClasse(Classe classe) {
        System.out.println("\n=== Statistiques de la classe : " + classe.getNom() + " ===");
        List<Eleve> eleves = classe.getEleves();
        if (eleves.isEmpty()) {
            System.out.println("Aucun eleve dans cette classe.");
            return;
        }

        double totalMoyennes = 0;
        double meilleureNote = Double.MIN_VALUE;
        double mauvaiseNote = Double.MAX_VALUE;
        int nbReussi = 0;

        System.out.printf("  %-25s | %s%n", "Nom(s) & Prenom(s)", "Moyenne");
        System.out.println("  " + "-".repeat(40));

        for (Eleve e : eleves) {
            double moy = calculerMoyenne(e);
            System.out.printf("  %-25s | %.2f/20%n",
                    e.getPrenom() + " " + e.getNom(), moy);
            totalMoyennes += moy;
            if (moy > meilleureNote) meilleureNote = moy;
            if (moy < mauvaiseNote) mauvaiseNote = moy;
            if (moy >= 10) nbReussi++;
        }

        double moyClasse = totalMoyennes / eleves.size();
        System.out.println("  " + "-".repeat(40));
        System.out.printf("  Moyenne de la classe : %.2f/20%n", moyClasse);
        System.out.printf("  Plus grande moyenne    : %.2f/20%n", meilleureNote);
        System.out.printf("  Plus failble moyenne: %.2f/20%n", mauvaiseNote);
        System.out.printf("  Taux de reussite     : %d/%d (%.0f%%)%n",
                nbReussi, eleves.size(), (double) nbReussi / eleves.size() * 100);
    }

    // Méthode utilitaire : notes d'un élève
    private List<Note> getNotesParEleve(Eleve eleve) {
        List<Note> result = new ArrayList<>();
        for (Note n : notes) {
            if (n.getEleve().getId() == eleve.getId()){
                result.add(n);
            } 
        }
        return result;
    }

    // Appréciation selon la moyenne
    private String appreciation(double moy) {
        if (moy >= 16) return "Tres Bien";
        if (moy >= 14) return "Bien";
        if (moy >= 12) return "Assez Bien";
        if (moy >= 10) return "Passable";
        if (moy >= 8) return "Insuffisant";
        return "Très insuffisant";
    }
}
