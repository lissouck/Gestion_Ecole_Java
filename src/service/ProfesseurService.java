package service;

import model.Professeur;
import repository.ProfesseurRepository;
import util.Validateur;

import java.util.List;

/**
 * Logique métier pour la gestion des professeurs.
 *
 * @author Module Enseignants
 * @version 1.0
 */
public class ProfesseurService {

    private final ProfesseurRepository professeurRepository;

    public ProfesseurService(ProfesseurRepository professeurRepository) {
        this.professeurRepository = professeurRepository;
    }

    // ─── Ajout ────────────────────────────────────────────────────────────────

    public Professeur ajouterProfesseur(String nom, String prenom, String email,
                                         String telephone, String specialite) {
        Validateur.verifierNonVide(nom,        "Le nom est obligatoire");
        Validateur.verifierNonVide(prenom,     "Le prénom est obligatoire");
        Validateur.verifierNonVide(email,      "L'email est obligatoire");
        Validateur.verifierNonVide(specialite, "La spécialité est obligatoire");

        if (!Validateur.emailValide(email)) {
            throw new IllegalArgumentException("Format d'email invalide : " + email);
        }
        if (professeurRepository.emailExiste(email)) {
            throw new IllegalArgumentException("Un professeur avec cet email existe déjà.");
        }

        Professeur prof = new Professeur(nom.trim(), prenom.trim(),
                                         email.trim().toLowerCase(),
                                         telephone.trim(), specialite.trim());
        professeurRepository.sauvegarder(prof);
        return prof;
    }

    // ─── Modification ─────────────────────────────────────────────────────────

    public void modifierProfesseur(int id, String nom, String prenom,
                                    String email, String telephone, String specialite) {
        Professeur prof = trouverParId(id);

        Validateur.verifierNonVide(nom,        "Le nom est obligatoire");
        Validateur.verifierNonVide(specialite, "La spécialité est obligatoire");

        if (!prof.getEmail().equalsIgnoreCase(email)) {
            if (!Validateur.emailValide(email)) {
                throw new IllegalArgumentException("Format d'email invalide.");
            }
            if (professeurRepository.emailExiste(email)) {
                throw new IllegalArgumentException("Cet email est déjà utilisé.");
            }
            prof.setEmail(email.trim().toLowerCase());
        }

        prof.setNom(nom.trim());
        prof.setPrenom(prenom.trim());
        prof.setTelephone(telephone.trim());
        prof.setSpecialite(specialite.trim());
    }

    // ─── Suppression ──────────────────────────────────────────────────────────

    public void supprimerProfesseur(int id) {
        if (!professeurRepository.supprimer(id)) {
            throw new IllegalArgumentException("Aucun professeur trouvé avec l'ID : " + id);
        }
    }

    // ─── Lecture ──────────────────────────────────────────────────────────────

    public List<Professeur> obtenirTousLesProfesseurs() {
        return professeurRepository.findAll();
    }

    public Professeur trouverParId(int id) {
        return professeurRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Professeur introuvable (ID=" + id + ")"));
    }

    public List<Professeur> rechercherParNom(String texte) {
        Validateur.verifierNonVide(texte, "Le texte de recherche ne peut pas être vide.");
        return professeurRepository.findByNom(texte);
    }

    public List<Professeur> rechercherParSpecialite(String specialite) {
        return professeurRepository.findBySpecialite(specialite);
    }

    public int compter() {
        return professeurRepository.count();
    }
}