package service;

import model.Eleve;
import repository.EleveRepository;
import util.Validateur;

import java.util.List;
import java.util.Optional;

/**
 * Logique métier pour la gestion des élèves.
 * Valide les données avant de les envoyer au repository.
 *
 * @author Module Élèves
 * @version 1.0
 */
public class EleveService {

    private final EleveRepository eleveRepository;

    public EleveService(EleveRepository eleveRepository) {
        this.eleveRepository = eleveRepository;
    }

    // ─── Ajout ────────────────────────────────────────────────────────────────

    /**
     * Ajoute un nouvel élève après validation des données.
     * @throws IllegalArgumentException si les données sont invalides
     */
    public Eleve ajouterEleve(String nom, String prenom, int age,
                               String email, String telephone) {
        // Validation des champs obligatoires
        Validateur.verifierNonVide(nom,    "Le nom est obligatoire");
        Validateur.verifierNonVide(prenom, "Le prénom est obligatoire");
        Validateur.verifierNonVide(email,  "L'email est obligatoire");

        // Validation de l'âge (élève secondaire : 11–20 ans)
        if (age < 11 || age > 20) {
            throw new IllegalArgumentException("L'âge doit être compris entre 11 et 20 ans.");
        }

        // Vérification de l'unicité de l'email
        if (eleveRepository.emailExiste(email)) {
            throw new IllegalArgumentException("Un élève avec cet email existe déjà : " + email);
        }

        // Validation format email
        if (!Validateur.emailValide(email)) {
            throw new IllegalArgumentException("Format d'email invalide : " + email);
        }

        Eleve nouvelEleve = new Eleve(nom.trim(), prenom.trim(), age,
                                      email.trim().toLowerCase(), telephone.trim());
        eleveRepository.sauvegarder(nouvelEleve);
        return nouvelEleve;
    }

    // ─── Modification ─────────────────────────────────────────────────────────

    /**
     * Modifie les informations d'un élève existant.
     * @throws IllegalArgumentException si l'élève n'existe pas
     */
    public void modifierEleve(int id, String nom, String prenom,
                               int age, String email, String telephone) {
        Eleve eleve = trouverParId(id);

        Validateur.verifierNonVide(nom,    "Le nom est obligatoire");
        Validateur.verifierNonVide(prenom, "Le prénom est obligatoire");

        if (age < 11 || age > 20) {
            throw new IllegalArgumentException("L'âge doit être compris entre 11 et 20 ans.");
        }

        // Vérifier email uniquement s'il change
        if (!eleve.getEmail().equalsIgnoreCase(email)) {
            if (eleveRepository.emailExiste(email)) {
                throw new IllegalArgumentException("Cet email est déjà utilisé.");
            }
            if (!Validateur.emailValide(email)) {
                throw new IllegalArgumentException("Format d'email invalide.");
            }
            eleve.setEmail(email.trim().toLowerCase());
        }

        eleve.setNom(nom.trim());
        eleve.setPrenom(prenom.trim());
        eleve.setAge(age);
        eleve.setTelephone(telephone.trim());
    }

    // ─── Suppression ──────────────────────────────────────────────────────────

    /**
     * Supprime un élève par son ID.
     * @throws IllegalArgumentException si l'élève n'existe pas
     */
    public void supprimerEleve(int id) {
        if (!eleveRepository.supprimer(id)) {
            throw new IllegalArgumentException("Aucun élève trouvé avec l'ID : " + id);
        }
    }

    // ─── Recherche ────────────────────────────────────────────────────────────

    public List<Eleve> obtenirTousLesEleves() {
        return eleveRepository.findAll();
    }

    public Eleve trouverParId(int id) {
        return eleveRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Élève introuvable (ID=" + id + ")"));
    }

    public List<Eleve> rechercherParNom(String texte) {
        Validateur.verifierNonVide(texte, "Le texte de recherche ne peut pas être vide.");
        return eleveRepository.findByNom(texte);
    }

    public int compter() {
        return eleveRepository.count();
    }
}