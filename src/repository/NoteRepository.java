package repository;

import model.Note;
import model.Eleve;
import model.Matiere;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Couche de données pour les notes.
 *
 * @author Module Notes
 * @version 1.0
 */
public class NoteRepository {

    private final List<Note> notes = new ArrayList<>();

    public void sauvegarder(Note note) {
        notes.add(note);
    }

    public List<Note> findAll() {
        return new ArrayList<>(notes);
    }

    /** Toutes les notes d'un élève spécifique. */
    public List<Note> findByEleve(int eleveId) {
        List<Note> resultats = new ArrayList<>();
        for (Note n : notes) {
            if (n.getEleve().getId() == eleveId) {
                resultats.add(n);
            }
        }
        return resultats;
    }

    /** Toutes les notes pour une matière spécifique. */
    public List<Note> findByMatiere(int matiereId) {
        List<Note> resultats = new ArrayList<>();
        for (Note n : notes) {
            if (n.getMatiere().getId() == matiereId) {
                resultats.add(n);
            }
        }
        return resultats;
    }

    /** Notes d'un élève pour une matière précise. */
    public List<Note> findByEleveEtMatiere(int eleveId, int matiereId) {
        List<Note> resultats = new ArrayList<>();
        for (Note n : notes) {
            if (n.getEleve().getId() == eleveId
             && n.getMatiere().getId() == matiereId) {
                resultats.add(n);
            }
        }
        return resultats;
    }

    public Optional<Note> findById(int id) {
        return notes.stream().filter(n -> n.getId() == id).findFirst();
    }

    public boolean supprimer(int id) {
        return notes.removeIf(n -> n.getId() == id);
    }

    public int count() {
        return notes.size();
        
    }
}