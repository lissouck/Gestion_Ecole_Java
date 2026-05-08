package service;

import model.Eleve;
import model.Matiere;
import model.Note;
import repository.NoteRepository;
import util.Validateur;

import java.util.List;

/**
 * Logique métier pour la gestion des notes.
 * Contient aussi les calculs de moyenne et la génération de bulletins.
 *
 * @author Module Notes
 * @version 1.0
 */
public class NoteService {
    private final NoteRepository  noteRepository;
    private final EleveService    eleveService;
    private final MatiereService  matiereService;

    public NoteService(NoteRepository noteRepository,
                       EleveService eleveService,
                       MatiereService matiereService) {
        this.noteRepository = noteRepository;
        this.eleveService   = eleveService;
        this.matiereService = matiereService;

    // ─── Ajout ────────────────────────────────────────────────────────────────

    /**
     * Ajoute une note à un élève pour une matière.
     * @param valeur doit être entre 0 et 20
     */
    public Note ajouterNote(int eleveId, int matiereId, double valeur,
                             String commentaire, String dateEvaluation) {
        Eleve   eleve   = eleveService.trouverParId(eleveId);
        Matiere matiere = matiereService.trouverParId(matiereId);

        if (valeur < 0 || valeur > 20) {
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 20.");
        }
        Validateur.verifierNonVide(dateEvaluation, "La date d'évaluation est obligatoire");

        Note note = new Note(eleve, matiere, valeur, commentaire, dateEvaluation);
        noteRepository.sauvegarder(note);
        return note;
    }

    // ─── Suppression ──────────────────────────────────────────────────────────

    public void supprimerNote(int id) {
        if (!noteRepository.supprimer(id)) {
            throw new IllegalArgumentException("Aucune note trouvée avec l'ID : " + id);
        }
    }

    // ─── Calculs ──────────────────────────────────────────────────────────────

    /**
     * Calcule la moyenne générale d'un élève (pondérée par les coefficients).
     * @return la moyenne ou -1 si aucune note
     */
    public double calculerMoyenneEleve(int eleveId) {
        List<Note> notes = noteRepository.findByEleve(eleveId);
        if (notes.isEmpty()) return -1;

        double totalPondere    = 0;
        int    totalCoefficient = 0;

        for (Note n : notes) {
            int coeff = n.getMatiere().getCoefficient();
            totalPondere     += n.getValeur() * coeff;
            totalCoefficient += coeff;
        }

        return totalCoefficient > 0 ? totalPondere / totalCoefficient : -1;
    }

    /**
     * Calcule la moyenne d'un élève pour une matière précise.
     */
    public double calculerMoyenneEleveParMatiere(int eleveId, int matiereId) {
        List<Note> notes = noteRepository.findByEleveEtMatiere(eleveId, matiereId);
        if (notes.isEmpty()) return -1;

        double total = 0;
        for (Note n : notes) total += n.getValeur();
        return total / notes.size();
    }

    /**
     * Calcule la moyenne de la classe pour une matière donnée.
     */
    public double calculerMoyenneClasseParMatiere(int matiereId) {
        List<Note> notes = noteRepository.findByMatiere(matiereId);
        if (notes.isEmpty()) return -1;

        double total = 0;
        for (Note n : notes) total += n.getValeur();
        return total / notes.size();
    }

    // ─── Statistiques ─────────────────────────────────────────────────────────

    /**
     * Retourne la note maximale pour une matière.
     */
    public double obtenirNoteMax(int matiereId) {
        List<Note> notes = noteRepository.findByMatiere(matiereId);
        if (notes.isEmpty()) return -1;
        return notes.stream().mapToDouble(Note::getValeur).max().orElse(-1);
    }

    /**
     * Retourne la note minimale pour une matière.
     */
    public double obtenirNoteMin(int matiereId) {
        List<Note> notes = noteRepository.findByMatiere(matiereId);
        if (notes.isEmpty()) return -1;
        return notes.stream().mapToDouble(Note::getValeur).min().orElse(-1);
    }

    // ─── Bulletin ─────────────────────────────────────────────────────────────

    /**
     * Génère et affiche le bulletin scolaire d'un élève.
     */
    public String genererBulletin(int eleveId) {
        Eleve eleve  = eleveService.trouverParId(eleveId);
        List<Note> notes = noteRepository.findByEleve(eleveId);

        StringBuilder bulletin = new StringBuilder();
        String ligne = "═".repeat(60);

        bulletin.append("\n").append(ligne).append("\n");
        bulletin.append(String.format("  📋 BULLETIN SCOLAIRE — %s\n", eleve.getNomComplet().toUpperCase()));
        bulletin.append(ligne).append("\n");

        if (notes.isEmpty()) {
            bulletin.append("  Aucune note enregistrée pour cet élève.\n");
        } else {
            bulletin.append(String.format("  %-20s %-6s %-5s %-15s %s\n",
                    "MATIÈRE", "COEFF", "NOTE", "MENTION", "COMMENTAIRE"));
            bulletin.append("─".repeat(60)).append("\n");

            // Regrouper les notes par matière et afficher la moyenne par matière
            List<Matiere> matieresVues = new java.util.ArrayList<>();
            for (Note n : notes) {
                Matiere m = n.getMatiere();
                if (matieresVues.stream().anyMatch(mv -> mv.getId() == m.getId())) continue;
                matieresVues.add(m);

                double moy = calculerMoyenneEleveParMatiere(eleveId, m.getId());
                Note facticeNote = new Note(eleve, m, moy, "", "");  // Pour mention
                bulletin.append(String.format("  %-20s %-6d %-5.2f %-15s %s\n",
                        m.getNom(), m.getCoefficient(), moy,
                        facticeNote.getMention(), ""));
            }

            bulletin.append("─".repeat(60)).append("\n");
            double moyenne = calculerMoyenneEleve(eleveId);
            bulletin.append(String.format("  MOYENNE GÉNÉRALE (pondérée) : %.2f/20\n", moyenne));

            // Mention globale
            String mentionGlobale = obtenirMentionGlobale(moyenne);
            bulletin.append(String.format("  MENTION : %s\n", mentionGlobale));
        }

        bulletin.append(ligne).append("\n");
        return bulletin.toString();
    }

    /** Détermine la mention globale selon la moyenne. */
    private String obtenirMentionGlobale(double moyenne) {
        if (moyenne >= 16) return "🏆 Très Bien — Félicitations !";
        if (moyenne >= 14) return "🥈 Bien — Encouragements";
        if (moyenne >= 12) return "🥉 Assez Bien";
        if (moyenne >= 10) return "✅ Passable — Admis";
        return "❌ Insuffisant — En difficulté";
    }

    // ─── Lecture ──────────────────────────────────────────────────────────────

    public List<Note> obtenirNotesEleve(int eleveId) {
        eleveService.trouverParId(eleveId);   // Vérification existence
        return noteRepository.findByEleve(eleveId);
    }

    public List<Note> obtenirNotesMatiere(int matiereId) {
        matiereService.trouverParId(matiereId);
        return noteRepository.findByMatiere(matiereId);
    }

    public List<Note> obtenirToutesLesNotes() {
        return noteRepository.findAll();
    }

    public int compter() {
        return noteRepository.count();
    }
}
