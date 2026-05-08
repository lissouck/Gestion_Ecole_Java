package repository;

import model.Classe;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Couche de données pour les classes.
 *
 * @author Module Classes
 * @version 1.0
 */
public class ClasseRepository {

    private final List<Classe> classes = new ArrayList<>();

    public void sauvegarder(Classe classe) {
        classes.add(classe);
    }

    public List<Classe> findAll() {
        return new ArrayList<>(classes);
    }

    public Optional<Classe> findById(int id) {
        return classes.stream()
                      .filter(c -> c.getId() == id)
                      .findFirst();
    }

    public Optional<Classe> findByNom(String nom) {
        return classes.stream()
                      .filter(c -> c.getNom().equalsIgnoreCase(nom))
                      .findFirst();
    }

    public List<Classe> findByNiveau(String niveau) {
        List<Classe> resultats = new ArrayList<>();
        for (Classe c : classes) {
            if (c.getNiveau().equalsIgnoreCase(niveau)) {
                resultats.add(c);
            }
        }
        return resultats;
    }

    public boolean supprimer(int id) {
        return classes.removeIf(c -> c.getId() == id);
    }

    public boolean nomExiste(String nom) {
        return classes.stream().anyMatch(c -> c.getNom().equalsIgnoreCase(nom));
    }

    public int count() {
        return classes.size();
    }
}
