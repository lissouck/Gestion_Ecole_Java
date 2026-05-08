package ui;

import model.Eleve;
import service.EleveService;
import util.Validateur;

import java.util.List;

/**
 * Interface utilisateur pour la gestion des élèves.
 * Affiche les menus et interagit avec l'utilisateur.
 *
 * @author Module Élèves
 * @version 1.0
 */
public class MenuEleve {

    private final EleveService eleveService;

    public MenuEleve(EleveService eleveService) {
        this.eleveService = eleveService;
    }

    // ─── Menu principal ───────────────────────────────────────────────────────

    public void afficherMenu() {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│          GESTION DES ÉLÈVES         │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. Ajouter un élève                │");
            System.out.println("│  2. Afficher tous les élèves        │");
            System.out.println("│  3. Rechercher un élève             │");
            System.out.println("│  4. Modifier un élève               │");
            System.out.println("│  5. Supprimer un élève              │");
            System.out.println("│  0. Retour au menu principal        │");
            System.out.println("└─────────────────────────────────────┘");

            int choix = Validateur.lireEntier("  Votre choix : ", 0, 5);

            switch (choix) {
                case 1 -> ajouterEleve();
                case 2 -> afficherTousLesEleves();
                case 3 -> rechercherEleve();
                case 4 -> modifierEleve();
                case 5 -> supprimerEleve();
                case 0 -> continuer = false;
            }
        }
    }

    // ─── Ajouter ──────────────────────────────────────────────────────────────

    private void ajouterEleve() {
        System.out.println("\n  ── Ajouter un élève ──");
        try {
            String nom       = Validateur.lireChaine("  Nom        : ");
            String prenom    = Validateur.lireChaine("  Prénom     : ");
            int    age       = Validateur.lireEntier("  Âge        : ", 11, 20);
            String email     = Validateur.lireChaine("  Email      : ");
            String telephone = Validateur.lireChaineOptionnelle("  Téléphone  : ");

            Eleve eleve = eleveService.ajouterEleve(nom, prenom, age, email, telephone);
            System.out.println("    Élève ajouté avec succès ! ID = " + eleve.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }

    // ─── Afficher ─────────────────────────────────────────────────────────────

    private void afficherTousLesEleves() {
        List<Eleve> eleves = eleveService.obtenirTousLesEleves();
        System.out.println("\n  ── Liste des élèves (" + eleves.size() + ") ──");

        if (eleves.isEmpty()) {
            System.out.println("  Aucun élève enregistré.");
            return;
        }

        System.out.printf("  %-4s %-15s %-15s %-4s %-25s %-13s%n",
                "ID", "NOM", "PRÉNOM", "ÂGE", "EMAIL", "TÉLÉPHONE");
        System.out.println("  " + "─".repeat(80));

        for (Eleve e : eleves) {
            System.out.printf("  %-4d %-15s %-15s %-4d %-25s %-13s%n",
                    e.getId(), e.getNom(), e.getPrenom(),
                    e.getAge(), e.getEmail(), e.getTelephone());
        }
    }

    // ─── Rechercher ───────────────────────────────────────────────────────────

    private void rechercherEleve() {
        System.out.println("\n  ── Rechercher un élève ──");
        String texte = Validateur.lireChaine("  Nom ou prénom : ");

        List<Eleve> resultats = eleveService.rechercherParNom(texte);
        if (resultats.isEmpty()) {
            System.out.println("  Aucun élève trouvé pour : " + texte);
        } else {
            System.out.println("  " + resultats.size() + " résultat(s) :");
            for (Eleve e : resultats) {
                System.out.println("    → " + e);
            }
        }
    }

    // ─── Modifier ─────────────────────────────────────────────────────────────

    private void modifierEleve() {
        System.out.println("\n  ── Modifier un élève ──");
        afficherTousLesEleves();

        try {
            int id = Validateur.lireEntier("  ID de l'élève à modifier : ");
            Eleve eleve = eleveService.trouverParId(id);

            System.out.println("  Élève actuel : " + eleve);
            System.out.println("  (Appuyez Entrée pour conserver la valeur actuelle)");

            String nom = Validateur.lireChaineOptionnelle("  Nouveau nom [" + eleve.getNom() + "] : ");
            if (nom.isEmpty()) nom = eleve.getNom();

            String prenom = Validateur.lireChaineOptionnelle("  Nouveau prénom [" + eleve.getPrenom() + "] : ");
            if (prenom.isEmpty()) prenom = eleve.getPrenom();

            String ageStr = Validateur.lireChaineOptionnelle("  Nouvel âge [" + eleve.getAge() + "] : ");
            int age = ageStr.isEmpty() ? eleve.getAge() : Integer.parseInt(ageStr);

            String email = Validateur.lireChaineOptionnelle("  Nouvel email [" + eleve.getEmail() + "] : ");
            if (email.isEmpty()) email = eleve.getEmail();

            String tel = Validateur.lireChaineOptionnelle("  Nouveau téléphone [" + eleve.getTelephone() + "] : ");
            if (tel.isEmpty()) tel = eleve.getTelephone();

            eleveService.modifierEleve(id, nom, prenom, age, email, tel);
            System.out.println("    Élève modifié avec succès !");
        } catch (IllegalArgumentException | NumberFormatException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }

    // ─── Supprimer ────────────────────────────────────────────────────────────

    private void supprimerEleve() {
        System.out.println("\n  ── Supprimer un élève ──");
        afficherTousLesEleves();

        try {
            int id = Validateur.lireEntier("  ID de l'élève à supprimer : ");
            Eleve eleve = eleveService.trouverParId(id);

            System.out.println("  Élève à supprimer : " + eleve.getNomComplet());
            if (Validateur.confirmer("  Êtes-vous sûr ?")) {
                eleveService.supprimerEleve(id);
                System.out.println("    Élève supprimé.");
            } else {
                System.out.println("  Suppression annulée.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("    Erreur : " + e.getMessage());
        }
    }
}
