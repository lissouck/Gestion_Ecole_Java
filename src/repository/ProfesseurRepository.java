package repository;

import model.Professeur;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Couche de données pour les professeurs.
 *
 * @author Module Enseignants
 * @version 1.0
 */
public class ProfesseurRepository {

    private final List<Professeur> professeurs = new ArrayList<>();

    public void sauvegarder(Professeur professeur) {
        professeurs.add(professeur);
    }

    public List<Professeur> findAll() {
        return new ArrayList<>(professeurs);
    }

    public Optional<Professeur> findById(int id) {
        return professeurs.stream()
                          .filter(p -> p.getId() == id)
                          .findFirst();
    }

    public List<Professeur> findByNom(String texte) {
        List<Professeur> resultats = new ArrayList<>();
        String recherche = texte.toLowerCase().trim();
        for (Professeur p : professeurs) {
            if (p.getNom().toLowerCase().contains(recherche)
             || p.getPrenom().toLowerCase().contains(recherche)) {
                resultats.add(p);
            }
        }
        return resultats;
    }

    public List<Professeur> findBySpecialite(String specialite) {
        List<Professeur> resultats = new ArrayList<>();
        for (Professeur p : professeurs) {
            if (p.getSpecialite().toLowerCase().contains(specialite.toLowerCase())) {
                resultats.add(p);
            }
        }
        return resultats;
    }

    public boolean supprimer(int id) {
        return professeurs.removeIf(p -> p.getId() == id);
    }

    public boolean emailExiste(String email) {
        return professeurs.stream().anyMatch(p -> p.getEmail().equalsIgnoreCase(email));
    }

    public int count() {
        return professeurs.size();
    }
}
