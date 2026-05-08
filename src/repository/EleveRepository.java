package repository;

import model.Eleve;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Couche de données pour les élèves.
 * Stocke et récupère les données en mémoire avec ArrayList.
 * Aucune logique métier ici — uniquement les opérations CRUD de base.
 *
 * @author Module Élèves
 * @version 1.0
 */
public class EleveRepository {

    // Stockage en mémoire (remplace une base de données)
    private final List<Eleve> eleves = new ArrayList<>();

    // ─── CRUD ─────────────────────────────────────────────────────────────────

    /** Sauvegarde un nouvel élève. */
    public void sauvegarder(Eleve eleve) {
        eleves.add(eleve);
    }

    /** Retourne tous les élèves. */
    public List<Eleve> findAll() {
        return new ArrayList<>(eleves);   // Copie défensive
    }

    /** Cherche un élève par son ID. */
    public Optional<Eleve> findById(int id) {
        return eleves.stream()
                     .filter(e -> e.getId() == id)
                     .findFirst();
    }

    /** Cherche des élèves dont le nom ou prénom contient le texte (insensible à la casse). */
    public List<Eleve> findByNom(String texte) {
        List<Eleve> resultats = new ArrayList<>();
        String recherche = texte.toLowerCase().trim();
        for (Eleve e : eleves) {
            if (e.getNom().toLowerCase().contains(recherche)
             || e.getPrenom().toLowerCase().contains(recherche)) {
                resultats.add(e);
            }
        }
        return resultats;
    }

    /** Supprime un élève par ID. Retourne true si trouvé et supprimé. */
    public boolean supprimer(int id) {
        return eleves.removeIf(e -> e.getId() == id);
    }

    /** Vérifie si l'email est déjà utilisé par un autre élève. */
    public boolean emailExiste(String email) {
        return eleves.stream().anyMatch(e -> e.getEmail().equalsIgnoreCase(email));
    }

    /** Nombre total d'élèves enregistrés. */
    public int count() {
        return eleves.size();
    }
}
