package ui;

import model.Classe;
import model.Eleve;
import service.ClasseService;
import util.Validateur;

import java.util.List;

/**
 * Interface utilisateur pour la gestion des classes.
 *
 * @author Module Classes
 * @version 1.0
 */
public class MenuClasse {

    private final ClasseService  classeService;
    private final MenuEleve      menuEleve;
    private final MenuProfesseur menuProfesseur;

    public MenuClasse(ClasseService classeService,
                      MenuEleve menuEleve,
                      MenuProfesseur menuProfesseur) {
        this.classeService  = classeService;
        this.menuEleve      = menuEleve;
        this.menuProfesseur = menuProfesseur;
    }

    public void afficherMenu() {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│          GESTION DES CLASSES        │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. Créer une classe                │");
            System.out.println("│  2. Afficher toutes les classes     │");
            System.out.println("│  3. Détails d'une classe            │");
            System.out.println("│  4. Ajouter un élève dans la classe │");
            System.out.println("│  5. Retirer un élève de la classe   │");
            System.out.println("│  6. Affecter un professeur principal│");
            System.out.println("│  7. Modifier une classe             │");
            System.out.println("│  8. Supprimer une classe            │");
            System.out.println("│  0. Retour au menu principal        │");
            System.out.println("└─────────────────────────────────────┘");

            int choix = Validateur.lireEntier("  Votre choix : ", 0, 8);

            switch (choix) {
                case 1 -> creerClasse();
                case 2 -> afficherToutesLesClasses();
                case 3 -> detailsClasse();
                case 4 -> ajouterEleveDansClasse();
                case 5 -> retirerEleveDeLaClasse();
                case 6 -> affecterProfesseur();
                case 7 -> modifierClasse();
                case 8 -> supprimerClasse();
                case 0 -> continuer = false;
            }
        }
    }

    private void creerClasse() {
        System.out.println("\n  ── Créer une classe ──");
        try {
            String nom    = Validateur.lireChaine("  Nom (ex: 3ème A)  : ");
            String niveau = Validateur.lireChaine("  Niveau (ex: Collège) : ");
            Classe c = classeService.creerClasse(nom, niveau);
            System.out.println("    Classe créée ! ID = " + c.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }

    void afficherToutesLesClasses() {
        List<Classe> classes = classeService.obtenirToutesLesClasses();
        System.out.println("\n  ── Liste des classes (" + classes.size() + ") ──");

        if (classes.isEmpty()) {
            System.out.println("  Aucune classe enregistrée.");
            return;
        }

        System.out.printf("  %-4s %-12s %-12s %-10s %-22s%n",
                "ID", "NOM", "NIVEAU", "NB ÉLÈVES", "PROF PRINCIPAL");
        System.out.println("  " + "─".repeat(65));

        for (Classe c : classes) {
            String prof = c.getProfesseurPrincipal() != null
                    ? c.getProfesseurPrincipal().getNomComplet() : "—";
            System.out.printf("  %-4d %-12s %-12s %-10d %-22s%n",
                    c.getId(), c.getNom(), c.getNiveau(), c.getNombreEleves(), prof);
        }
    }

    private void detailsClasse() {
        System.out.println("\n  ── Détails d'une classe ──");
        afficherToutesLesClasses();
        try {
            int id     = Validateur.lireEntier("  ID de la classe : ");
            Classe c   = classeService.trouverParId(id);

            System.out.println("\n  Classe      : " + c.getNom() + " (" + c.getNiveau() + ")");
            String prof = c.getProfesseurPrincipal() != null
                    ? c.getProfesseurPrincipal().getNomComplet() : "Non assigné";
            System.out.println("  Prof. princ.: " + prof);
            System.out.println("  Élèves (" + c.getNombreEleves() + ") :");

            if (c.getEleves().isEmpty()) {
                System.out.println("    Aucun élève dans cette classe.");
            } else {
                for (Eleve e : c.getEleves()) {
                    System.out.printf("    → [%d] %s (âge: %d)%n",
                            e.getId(), e.getNomComplet(), e.getAge());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }

    private void ajouterEleveDansClasse() {
        System.out.println("\n  ── Ajouter un élève dans une classe ──");
        afficherToutesLesClasses();
        menuEleve.afficherTousLesEleves();
        try {
            int classeId = Validateur.lireEntier("  ID de la classe : ");
            int eleveId  = Validateur.lireEntier("  ID de l'élève   : ");
            classeService.ajouterEleveDansClasse(classeId, eleveId);
            System.out.println("    Élève ajouté dans la classe !");
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }

    private void retirerEleveDeLaClasse() {
        System.out.println("\n  ── Retirer un élève d'une classe ──");
        afficherToutesLesClasses();
        try {
            int classeId = Validateur.lireEntier("  ID de la classe : ");
            Classe c = classeService.trouverParId(classeId);
            System.out.println("  Élèves de la classe " + c.getNom() + " :");
            c.getEleves().forEach(e -> System.out.printf("    → [%d] %s%n", e.getId(), e.getNomComplet()));
            int eleveId = Validateur.lireEntier("  ID de l'élève à retirer : ");
            classeService.retirerEleveDeLaClasse(classeId, eleveId);
            System.out.println("    Élève retiré.");
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }

    private void affecterProfesseur() {
        System.out.println("\n  ── Affecter un professeur principal ──");
        afficherToutesLesClasses();
        menuProfesseur.afficherTousLesProfesseurs();
        try {
            int classeId     = Validateur.lireEntier("  ID de la classe      : ");
            int professeurId = Validateur.lireEntier("  ID du professeur     : ");
            classeService.affecterProfesseurPrincipal(classeId, professeurId);
            System.out.println("  ✅ Professeur affecté !");
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }

    private void modifierClasse() {
        System.out.println("\n  ── Modifier une classe ──");
        afficherToutesLesClasses();
        try {
            int id     = Validateur.lireEntier("  ID à modifier : ");
            Classe c   = classeService.trouverParId(id);

            String nom    = Validateur.lireChaineOptionnelle("  Nouveau nom [" + c.getNom() + "] : ");
            if (nom.isEmpty()) nom = c.getNom();
            String niveau = Validateur.lireChaineOptionnelle("  Nouveau niveau [" + c.getNiveau() + "] : ");
            if (niveau.isEmpty()) niveau = c.getNiveau();

            classeService.modifierClasse(id, nom, niveau);
            System.out.println("    Classe modifiée !");
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }

    private void supprimerClasse() {
        System.out.println("\n  ── Supprimer une classe ──");
        afficherToutesLesClasses();
        try {
            int id = Validateur.lireEntier("  ID à supprimer : ");
            Classe c = classeService.trouverParId(id);
            if (Validateur.confirmer("  Supprimer la classe '" + c.getNom() + "' ?")) {
                classeService.supprimerClasse(id);
                System.out.println("    Classe supprimée.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }
}

