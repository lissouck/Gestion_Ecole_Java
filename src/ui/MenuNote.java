package ui;

import model.Note;
import service.NoteService;
import util.Validateur;

import java.util.List;

/**
 * Interface utilisateur pour la gestion des notes.
 *
 * @author Module Notes
 * @version 1.0
 */
public class MenuNote {

    private final NoteService   noteService;
    private final MenuEleve     menuEleve;
    private final MenuMatiere   menuMatiere;

    public MenuNote(NoteService noteService, MenuEleve menuEleve, MenuMatiere menuMatiere) {
        this.noteService = noteService;
        this.menuEleve   = menuEleve;
        this.menuMatiere = menuMatiere;
    }

    public void afficherMenu() {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│        📝  GESTION DES NOTES         │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. Ajouter une note                │");
            System.out.println("│  2. Voir les notes d'un élève       │");
            System.out.println("│  3. Moyenne d'un élève              │");
            System.out.println("│  4. Bulletin scolaire               │");
            System.out.println("│  5. Statistiques par matière        │");
            System.out.println("│  6. Supprimer une note              │");
            System.out.println("│  0. Retour au menu principal        │");
            System.out.println("└─────────────────────────────────────┘");

            int choix = Validateur.lireEntier("  Votre choix : ", 0, 6);

            switch (choix) {
                case 1 -> ajouterNote();
                case 2 -> voirNotesEleve();
                case 3 -> afficherMoyenneEleve();
                case 4 -> afficherBulletin();
                case 5 -> statistiquesParMatiere();
                case 6 -> supprimerNote();
                case 0 -> continuer = false;
            }
        }
    }

    private void ajouterNote() {
        System.out.println("\n  ── Ajouter une note ──");
        menuEleve.afficherTousLesEleves();
        menuMatiere.afficherToutesLesMatieres();
        try {
            int    eleveId   = Validateur.lireEntier("  ID de l'élève     : ");
            int    matiereId = Validateur.lireEntier("  ID de la matière  : ");
            double valeur    = Validateur.lireDouble("  Note (0-20)       : ", 0, 20);
            String commentaire = Validateur.lireChaineOptionnelle("  Commentaire       : ");
            String date        = Validateur.lireChaine("  Date (JJ/MM/AAAA) : ");

            Note note = noteService.ajouterNote(eleveId, matiereId, valeur, commentaire, date);
            System.out.printf("  ✅ Note ajoutée ! (%.2f — %s)%n", note.getValeur(), note.getMention());
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Erreur : " + e.getMessage());
        }
    }

    private void voirNotesEleve() {
        System.out.println("\n  ── Notes d'un élève ──");
        menuEleve.afficherTousLesEleves();
        try {
            int id = Validateur.lireEntier("  ID de l'élève : ");
            List<Note> notes = noteService.obtenirNotesEleve(id);

            if (notes.isEmpty()) {
                System.out.println("  Aucune note enregistrée pour cet élève.");
                return;
            }

            System.out.printf("  %-4s %-18s %-6s %-15s %-12s %s%n",
                    "ID", "MATIÈRE", "NOTE", "MENTION", "DATE", "COMMENTAIRE");
            System.out.println("  " + "─".repeat(75));

            for (Note n : notes) {
                System.out.printf("  %-4d %-18s %-6.2f %-15s %-12s %s%n",
                        n.getId(), n.getMatiere().getNom(), n.getValeur(),
                        n.getMention(), n.getDateEvaluation(), n.getCommentaire());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Erreur : " + e.getMessage());
        }
    }

    private void afficherMoyenneEleve() {
        System.out.println("\n  ── Moyenne générale d'un élève ──");
        menuEleve.afficherTousLesEleves();
        try {
            int    id     = Validateur.lireEntier("  ID de l'élève : ");
            double moy    = noteService.calculerMoyenneEleve(id);

            if (moy < 0) {
                System.out.println("  Cet élève n'a pas encore de notes.");
            } else {
                System.out.printf("  Moyenne générale (pondérée) : %.2f/20%n", moy);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Erreur : " + e.getMessage());
        }
    }

    private void afficherBulletin() {
        System.out.println("\n  ── Bulletin scolaire ──");
        menuEleve.afficherTousLesEleves();
        try {
            int id = Validateur.lireEntier("  ID de l'élève : ");
            System.out.println(noteService.genererBulletin(id));
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Erreur : " + e.getMessage());
        }
    }

    private void statistiquesParMatiere() {
        System.out.println("\n  ── Statistiques par matière ──");
        menuMatiere.afficherToutesLesMatieres();
        try {
            int id = Validateur.lireEntier("  ID de la matière : ");
            double moy = noteService.calculerMoyenneClasseParMatiere(id);
            double max = noteService.obtenirNoteMax(id);
            double min = noteService.obtenirNoteMin(id);

            if (moy < 0) {
                System.out.println("  Aucune note enregistrée pour cette matière.");
            } else {
                System.out.printf("  Moyenne de la classe : %.2f/20%n", moy);
                System.out.printf("  Note maximale        : %.2f/20%n", max);
                System.out.printf("  Note minimale        : %.2f/20%n", min);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Erreur : " + e.getMessage());
        }
    }

    private void supprimerNote() {
        System.out.println("\n  ── Supprimer une note ──");
        System.out.println("  Notes disponibles :");
        List<Note> toutes = noteService.obtenirToutesLesNotes();
        if (toutes.isEmpty()) {
            System.out.println("  Aucune note.");
            return;
        }
        toutes.forEach(n -> System.out.printf("    [%d] %s - %s : %.2f%n",
                n.getId(), n.getEleve().getNomComplet(), n.getMatiere().getNom(), n.getValeur()));
        try {
            int id = Validateur.lireEntier("  ID de la note à supprimer : ");
            if (Validateur.confirmer("  Confirmer la suppression ?")) {
                noteService.supprimerNote(id);
                System.out.println("  ✅ Note supprimée.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Erreur : " + e.getMessage());
        }
    }
}
