package ui;

import model.Matiere;
import service.MatiereService;
import service.ProfesseurService;
import util.Validateur;

import java.util.List;

/**
 * Interface utilisateur pour la gestion des matières.
 *
 * @author Module Matières
 * @version 1.0
 */
public class MenuMatiere {

    private final MatiereService   matiereService;
    private final MenuProfesseur   menuProfesseur;

    public MenuMatiere(MatiereService matiereService, MenuProfesseur menuProfesseur) {
        this.matiereService  = matiereService;
        this.menuProfesseur  = menuProfesseur;
    }

    public void afficherMenu() {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│          GESTION DES MATIÈRES       │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. Ajouter une matière             │");
            System.out.println("│  2. Afficher toutes les matières    │");
            System.out.println("│  3. Modifier une matière            │");
            System.out.println("│  4. Assigner un professeur          │");
            System.out.println("│  5. Retirer un professeur           │");
            System.out.println("│  6. Supprimer une matière           │");
            System.out.println("│  0. Retour au menu principal        │");
            System.out.println("└─────────────────────────────────────┘");

            int choix = Validateur.lireEntier("  Votre choix : ", 0, 6);

            switch (choix) {
                case 1 -> ajouterMatiere();
                case 2 -> afficherToutesLesMatieres();
                case 3 -> modifierMatiere();
                case 4 -> assignerProfesseur();
                case 5 -> retirerProfesseur();
                case 6 -> supprimerMatiere();
                case 0 -> continuer = false;
            }
        }
    }

    private void ajouterMatiere() {
        System.out.println("\n  ── Ajouter une matière ──");
        try {
            String nom         = Validateur.lireChaine("  Nom          : ");
            String description = Validateur.lireChaineOptionnelle("  Description  : ");
            int    coefficient = Validateur.lireEntier("  Coefficient (1-10) : ", 1, 10);

            Matiere m = matiereService.ajouterMatiere(nom, description, coefficient);
            System.out.println("    Matière ajoutée ! ID = " + m.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }

    void afficherToutesLesMatieres() {
        List<Matiere> matieres = matiereService.obtenirToutesLesMatieres();
        System.out.println("\n  ── Liste des matières (" + matieres.size() + ") ──");

        if (matieres.isEmpty()) {
            System.out.println("  Aucune matière enregistrée.");
            return;
        }

        System.out.printf("  %-4s %-18s %-6s %-20s%n", "ID", "NOM", "COEFF", "PROFESSEUR");
        System.out.println("  " + "─".repeat(55));

        for (Matiere m : matieres) {
            String prof = m.getProfesseur() != null ? m.getProfesseur().getNomComplet() : "—";
            System.out.printf("  %-4d %-18s %-6d %-20s%n",
                    m.getId(), m.getNom(), m.getCoefficient(), prof);
        }
    }

    private void modifierMatiere() {
        System.out.println("\n  ── Modifier une matière ──");
        afficherToutesLesMatieres();
        try {
            int id = Validateur.lireEntier("  ID à modifier : ");
            Matiere m = matiereService.trouverParId(id);

            String nom = Validateur.lireChaineOptionnelle("  Nouveau nom [" + m.getNom() + "] : ");
            if (nom.isEmpty()) nom = m.getNom();

            String desc = Validateur.lireChaineOptionnelle("  Description [" + m.getDescription() + "] : ");
            if (desc.isEmpty()) desc = m.getDescription();

            String coeffStr = Validateur.lireChaineOptionnelle("  Coefficient [" + m.getCoefficient() + "] : ");
            int coeff = coeffStr.isEmpty() ? m.getCoefficient() : Integer.parseInt(coeffStr);

            matiereService.modifierMatiere(id, nom, desc, coeff);
            System.out.println("    Matière modifiée !");
        } catch (IllegalArgumentException | NumberFormatException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }

    private void assignerProfesseur() {
        System.out.println("\n  ── Assigner un professeur à une matière ──");
        afficherToutesLesMatieres();
        menuProfesseur.afficherTousLesProfesseurs();
        try {
            int matiereId    = Validateur.lireEntier("  ID de la matière    : ");
            int professeurId = Validateur.lireEntier("  ID du professeur    : ");
            matiereService.assignerProfesseur(matiereId, professeurId);
            System.out.println("    Professeur assigné !");
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }

    private void retirerProfesseur() {
        System.out.println("\n  ── Retirer le professeur d'une matière ──");
        afficherToutesLesMatieres();
        try {
            int id = Validateur.lireEntier("  ID de la matière : ");
            matiereService.retirerProfesseur(id);
            System.out.println("    Professeur retiré.");
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }

    private void supprimerMatiere() {
        System.out.println("\n  ── Supprimer une matière ──");
        afficherToutesLesMatieres();
        try {
            int id = Validateur.lireEntier("  ID à supprimer : ");
            Matiere m = matiereService.trouverParId(id);
            if (Validateur.confirmer("  Supprimer '" + m.getNom() + "' ?")) {
                matiereService.supprimerMatiere(id);
                System.out.println("    Matière supprimée.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }
}
