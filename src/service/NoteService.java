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
    }
}