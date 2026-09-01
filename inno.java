import java.util.ArrayList;
import java.util.List;

public class GestionEnseignants {
    private List<Enseignant> listeEnseignants;

    public GestionEnseignants() {
        this.listeEnseignants = new ArrayList<>();
    }

    // Ajouter un enseignant
    public void ajouterEnseignant(Enseignant e) {
        listeEnseignants.add(e);
        System.out.println("Enseignant ajouté avec succès.");
    }

    // Supprimer un enseignant par ID
    public void supprimerEnseignant(String id) {
        boolean trouve = false;
        for (int i = 0; i < listeEnseignants.size(); i++) {
            if (listeEnseignants.get(i).getId().equals(id)) {
                listeEnseignants.remove(i);
                System.out.println("Enseignant supprimé.");
                trouve = true;
                break;
            }
        }
        if (!trouve) System.out.println("Enseignant non trouvé.");
    }

    // Afficher tous les enseignants
    public void afficherEnseignants() {
        if (listeEnseignants.isEmpty()) {
            System.out.println("Aucun enseignant dans la liste.");
        } else {
            for (Enseignant e : listeEnseignants) {
                System.out.println(e);
            }
        }
    }
}
