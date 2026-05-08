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
}
