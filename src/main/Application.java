package main;

import repository.*;
import service.*;
import ui.*;
import util.Validateur;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║         SYSTÈME DE GESTION D'ÉCOLE SECONDAIRE                ║
 * ║         Projet académique — Java POO                         ║
 * ║         Lycee de DOMAYO                                      ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * Point d'entrée principal de l'application.
 * Instancie tous les composants et lance le menu interactif.
 *
 * @author Équipe complète
 * @version 1.0
 */
public class Application {

    public static void main(String[] args) {

        // ── 1. Création des repositories (couche données) ─────────────────────
        EleveRepository      eleveRepo      = new EleveRepository();
        ProfesseurRepository professeurRepo = new ProfesseurRepository();
        MatiereRepository    matiereRepo    = new MatiereRepository();
        ClasseRepository     classeRepo     = new ClasseRepository();
        NoteRepository       noteRepo       = new NoteRepository();

        // ── 2. Création des services (couche logique métier) ──────────────────
        EleveService      eleveService      = new EleveService(eleveRepo);
        ProfesseurService professeurService = new ProfesseurService(professeurRepo);
        MatiereService    matiereService    = new MatiereService(matiereRepo, professeurService);
        ClasseService     classeService     = new ClasseService(classeRepo, eleveService, professeurService);
        NoteService       noteService       = new NoteService(noteRepo, eleveService, matiereService);

        // ── 3. Création des menus (couche interface utilisateur) ──────────────
        MenuEleve      menuEleve      = new MenuEleve(eleveService);
        MenuProfesseur menuProfesseur = new MenuProfesseur(professeurService);
        MenuMatiere    menuMatiere    = new MenuMatiere(matiereService, menuProfesseur);
        MenuClasse     menuClasse     = new MenuClasse(classeService, menuEleve, menuProfesseur);
        MenuNote       menuNote       = new MenuNote(noteService, menuEleve, menuMatiere);

        // ── 4. Écran de bienvenue ─────────────────────────────────────────────
        afficherBienvenue();

        // ── 5. Charger des données de test (optionnel) ────────────────────────
        System.out.println("\n  Voulez-vous charger des données de démonstration ?");
        if (Validateur.confirmer("  Charger les données de test")) {
            DonneesTest.charger(eleveService, professeurService,
                                matiereService, classeService, noteService);
        }

        // ── 6. Boucle principale du menu ──────────────────────────────────────
        boolean continuer = true;
        while (continuer) {
            afficherMenuPrincipal(eleveService, professeurService,
                                  matiereService, classeService, noteService);

            int choix = Validateur.lireEntier("  Votre choix : ", 0, 5);

            switch (choix) {
                case 1 -> menuEleve.afficherMenu();
                case 2 -> menuProfesseur.afficherMenu();
                case 3 -> menuClasse.afficherMenu();
                case 4 -> menuNote.afficherMenu();
                case 5 -> menuMatiere.afficherMenu();
                case 0 -> {
                    System.out.println("\n  👋 Au revoir ! Merci d'avoir utilisé GestionÉcole.");
                    continuer = false;
                }
            }
        }
    }

    // ─── Affichage des menus ──────────────────────────────────────────────────

    private static void afficherBienvenue() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║                                                      ║");
        System.out.println("║          GESTION D'ÉCOLE SECONDAIRE                ║");
        System.out.println("║              DU LYCEE DE DOMAYO                      ║");
        System.out.println("║               Projet Java POO                        ║");
        System.out.println("║                                                      ║");
        System.out.println("║                                                      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    private static void afficherMenuPrincipal(EleveService es, ProfesseurService ps,
                                               MatiereService ms, ClasseService cs,
                                               NoteService ns) {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│                 MENU PRINCIPAL                      │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.printf ("│  1.      Gestion des Élèves       (%3d enregistrés)  │%n", es.compter());
        System.out.printf ("│  2.      Gestion des Enseignants   (%3d enregistrés)  │%n", ps.compter());
        System.out.printf ("│  3.      Gestion des Classes       (%3d enregistrées) │%n", cs.compter());
        System.out.printf ("│  4.      Gestion des Notes         (%3d enregistrées) │%n", ns.compter());
        System.out.printf ("│  5.      Gestion des Matières      (%3d enregistrées) │%n", ms.compter());
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.println("│  0.    Quitter l'application                        │");
        System.out.println("└─────────────────────────────────────────────────────┘");
    }
}
