package repository;

import model.Matiere;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Couche de données pour les matières.
 *
 * @author Module Matières
 * @version 1.0
 */
public class MatiereRepository {

    private final List<Matiere> matieres = new ArrayList<>();

    public void sauvegarder(Matiere matiere) {
        matieres.add(matiere);
    }

    public List<Matiere> findAll() {
        return new ArrayList<>(matieres);
    }

    public Optional<Matiere> findById(int id) {
        return matieres.stream()
                       .filter(m -> m.getId() == id)
                       .findFirst();
    }

    public Optional<Matiere> findByNom(String nom) {
        return matieres.stream()
                       .filter(m -> m.getNom().equalsIgnoreCase(nom))
                       .findFirst();
    }

    public boolean supprimer(int id) {
        return matieres.removeIf(m -> m.getId() == id);
    }

    public boolean nomExiste(String nom) {
        return matieres.stream().anyMatch(m -> m.getNom().equalsIgnoreCase(nom));
    }

    public int count() {
        return matieres.size();
    }
}
