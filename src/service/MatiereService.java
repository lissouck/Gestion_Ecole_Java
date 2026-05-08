package service;

import model.Matiere;
import model.Professeur;
import repository.MatiereRepository;
import util.Validateur;

import java.util.List;

/**
 * Logique métier pour la gestion des matières.
 *
 * @author Module Matières
 * @version 1.0
 */
public class MatiereService {

    private final MatiereRepository  matiereRepository;
    private final ProfesseurService  professeurService;

    public MatiereService(MatiereRepository matiereRepository,
                          ProfesseurService professeurService) {
        this.matiereRepository = matiereRepository;
        this.professeurService  = professeurService;
    }

    // ─── Ajout ────────────────────────────────────────────────────────────────

    public Matiere ajouterMatiere(String nom, String description, int coefficient) {
        Validateur.verifierNonVide(nom, "Le nom de la matière est obligatoire");

        if (coefficient < 1 || coefficient > 10) {
            throw new IllegalArgumentException("Le coefficient doit être entre 1 et 10.");
        }
        if (matiereRepository.nomExiste(nom)) {
            throw new IllegalArgumentException("Une matière nommée '" + nom + "' existe déjà.");
        }

        Matiere matiere = new Matiere(nom.trim(), description.trim(), coefficient);
        matiereRepository.sauvegarder(matiere);
        return matiere;
    }

    // ─── Modification ─────────────────────────────────────────────────────────

    public void modifierMatiere(int id, String nom, String description, int coefficient) {
        Matiere matiere = trouverParId(id);

        Validateur.verifierNonVide(nom, "Le nom est obligatoire");

        if (coefficient < 1 || coefficient > 10) {
            throw new IllegalArgumentException("Le coefficient doit être entre 1 et 10.");
        }

        // Vérifier unicité seulement si le nom change
        if (!matiere.getNom().equalsIgnoreCase(nom) && matiereRepository.nomExiste(nom)) {
            throw new IllegalArgumentException("Une matière nommée '" + nom + "' existe déjà.");
        }

        matiere.setNom(nom.trim());
        matiere.setDescription(description.trim());
        matiere.setCoefficient(coefficient);
    }

    // ─── Association Matière ↔ Professeur ────────────────────────────────────

    /**
     * Associe un professeur à une matière.
     */
    public void assignerProfesseur(int matiereId, int professeurId) {
        Matiere    matiere = trouverParId(matiereId);
        Professeur prof    = professeurService.trouverParId(professeurId);
        matiere.setProfesseur(prof);
    }

    /** Retire l'association professeur d'une matière. */
    public void retirerProfesseur(int matiereId) {
        Matiere matiere = trouverParId(matiereId);
        matiere.setProfesseur(null);
    }

    // ─── Suppression ──────────────────────────────────────────────────────────

    public void supprimerMatiere(int id) {
        if (!matiereRepository.supprimer(id)) {
            throw new IllegalArgumentException("Aucune matière trouvée avec l'ID : " + id);
        }
    }

    // ─── Lecture ──────────────────────────────────────────────────────────────

    public List<Matiere> obtenirToutesLesMatieres() {
        return matiereRepository.findAll();
    }

    public Matiere trouverParId(int id) {
        return matiereRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Matière introuvable (ID=" + id + ")"));
    }

    public int compter() {
        return matiereRepository.count();
    }
}
