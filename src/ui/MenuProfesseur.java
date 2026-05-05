package ui;

import model.Professeur;
import service.ProfesseurService;
import util.Validateur;

import java.util.List;

/**
 * Interface utilisateur pour la gestion des professeurs.
 *
 * @author Module Enseignants
 * @version 1.0
 */
public class MenuProfesseur {

    private final ProfesseurService professeurService;

    public MenuProfesseur(ProfesseurService professeurService) {
        this.professeurService = professeurService;
    }

    public void afficherMenu() {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│     👨‍🏫  GESTION DES ENSEIGNANTS      │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. Ajouter un professeur           │");
            System.out.println("│  2. Afficher tous les professeurs   │");
            System.out.println("│  3. Rechercher un professeur        │");
            System.out.println("│  4. Modifier un professeur          │");
            System.out.println("│  5. Supprimer un professeur         │");
            System.out.println("│  0. Retour au menu principal        │");
            System.out.println("└─────────────────────────────────────┘");

            int choix = Validateur.lireEntier("  Votre choix : ", 0, 5);

            switch (choix) {
                case 1 -> ajouterProfesseur();
                case 2 -> afficherTousLesProfesseurs();
                case 3 -> rechercherProfesseur();
                case 4 -> modifierProfesseur();
                case 5 -> supprimerProfesseur();
                case 0 -> continuer = false;
            }
        }
    }

    private void ajouterProfesseur() {
        System.out.println("\n  ── Ajouter un professeur ──");
        try {
            String nom        = Validateur.lireChaine("  Nom         : ");
            String prenom     = Validateur.lireChaine("  Prénom      : ");
            String email      = Validateur.lireChaine("  Email       : ");
            String telephone  = Validateur.lireChaineOptionnelle("  Téléphone   : ");
            String specialite = Validateur.lireChaine("  Spécialité  : ");

            Professeur prof = professeurService.ajouterProfesseur(
                    nom, prenom, email, telephone, specialite);
            System.out.println("  ✅ Professeur ajouté ! ID = " + prof.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Erreur : " + e.getMessage());
        }
    }

    void afficherTousLesProfesseurs() {
        List<Professeur> profs = professeurService.obtenirTousLesProfesseurs();
        System.out.println("\n  ── Liste des professeurs (" + profs.size() + ") ──");

        if (profs.isEmpty()) {
            System.out.println("  Aucun professeur enregistré.");
            return;
        }

        System.out.printf("  %-4s %-15s %-15s %-20s %-20s%n",
                "ID", "NOM", "PRÉNOM", "SPÉCIALITÉ", "EMAIL");
        System.out.println("  " + "─".repeat(78));

        for (Professeur p : profs) {
            System.out.printf("  %-4d %-15s %-15s %-20s %-20s%n",
                    p.getId(), p.getNom(), p.getPrenom(),
                    p.getSpecialite(), p.getEmail());
        }
    }

    private void rechercherProfesseur() {
        System.out.println("\n  ── Rechercher un professeur ──");
        System.out.println("  1. Par nom/prénom");
        System.out.println("  2. Par spécialité");
        int choix = Validateur.lireEntier("  Choix : ", 1, 2);

        if (choix == 1) {
            String texte = Validateur.lireChaine("  Nom ou prénom : ");
            List<Professeur> res = professeurService.rechercherParNom(texte);
            afficherResultats(res);
        } else {
            String spec = Validateur.lireChaine("  Spécialité : ");
            List<Professeur> res = professeurService.rechercherParSpecialite(spec);
            afficherResultats(res);
        }
    }

    private void afficherResultats(List<Professeur> resultats) {
        if (resultats.isEmpty()) {
            System.out.println("  Aucun résultat.");
        } else {
            resultats.forEach(p -> System.out.println("    → " + p));
        }
    }

    private void modifierProfesseur() {
        System.out.println("\n  ── Modifier un professeur ──");
        afficherTousLesProfesseurs();
        try {
            int id = Validateur.lireEntier("  ID du professeur à modifier : ");
            Professeur prof = professeurService.trouverParId(id);

            System.out.println("  Actuel : " + prof);

            String nom = Validateur.lireChaineOptionnelle("  Nouveau nom [" + prof.getNom() + "] : ");
            if (nom.isEmpty()) nom = prof.getNom();

            String prenom = Validateur.lireChaineOptionnelle("  Nouveau prénom [" + prof.getPrenom() + "] : ");
            if (prenom.isEmpty()) prenom = prof.getPrenom();

            String email = Validateur.lireChaineOptionnelle("  Nouvel email [" + prof.getEmail() + "] : ");
            if (email.isEmpty()) email = prof.getEmail();

            String tel = Validateur.lireChaineOptionnelle("  Nouveau téléphone [" + prof.getTelephone() + "] : ");
            if (tel.isEmpty()) tel = prof.getTelephone();

            String spec = Validateur.lireChaineOptionnelle("  Nouvelle spécialité [" + prof.getSpecialite() + "] : ");
            if (spec.isEmpty()) spec = prof.getSpecialite();

            professeurService.modifierProfesseur(id, nom, prenom, email, tel, spec);
            System.out.println("  ✅ Professeur modifié !");
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Erreur : " + e.getMessage());
        }
    }

    private void supprimerProfesseur() {
        System.out.println("\n  ── Supprimer un professeur ──");
        afficherTousLesProfesseurs();
        try {
            int id = Validateur.lireEntier("  ID à supprimer : ");
            Professeur prof = professeurService.trouverParId(id);
            System.out.println("  À supprimer : " + prof.getNomComplet());
            if (Validateur.confirmer("  Confirmer ?")) {
                professeurService.supprimerProfesseur(id);
                System.out.println("  ✅ Professeur supprimé.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Erreur : " + e.getMessage());
        }
    }
}
