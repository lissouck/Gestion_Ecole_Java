package main;

import service.*;

/**
 * Charge des données simulées pour tester l'application sans saisie manuelle.
 * Appelé au démarrage si l'utilisateur choisit de charger des données de test.
 *
 * @author Équipe
 * @version 1.0
 */
public class DonneesTest {

    /**
     * Insère des données de démonstration dans tous les services.
     */
    public static void charger(EleveService eleveService,
                                ProfesseurService professeurService,
                                MatiereService matiereService,
                                ClasseService classeService,
                                NoteService noteService) {
        try {
            System.out.println("  Chargement des données de test...");

            // ── Professeurs ──────────────────────────────────────────────────
            var p1 = professeurService.ajouterProfesseur(
                    "Ngono", "Marie", "m.ngono@ecole.fr", "0601010101", "Mathématiques");
            var p2 = professeurService.ajouterProfesseur(
                    "Mballa", "Jean", "j.mballa@ecole.fr", "0602020202", "Français");
            var p3 = professeurService.ajouterProfesseur(
                    "Bella", "Sophie", "s.bella@ecole.fr", "0603030303", "Sciences");

            // ── Matières ─────────────────────────────────────────────────────
            var math    = matiereService.ajouterMatiere("Mathématiques", "Algèbre et géométrie", 4);
            var francais= matiereService.ajouterMatiere("Français",       "Langue et littérature", 4);
            var sciences= matiereService.ajouterMatiere("Sciences",       "Physique-Chimie-Bio",   3);
            var histoire= matiereService.ajouterMatiere("Histoire-Géo",   "Histoire et Géographie",2);
            var anglais = matiereService.ajouterMatiere("Anglais",        "Langue vivante 1",      3);

            // Associer professeurs ↔ matières
            matiereService.assignerProfesseur(math.getId(),     p1.getId());
            matiereService.assignerProfesseur(francais.getId(), p2.getId());
            matiereService.assignerProfesseur(sciences.getId(), p3.getId());

            // ── Élèves ───────────────────────────────────────────────────────
            var e1 = eleveService.ajouterEleve("Lissouck", "Lucas",  15, "lucas.lissouck@eleve.fr",  "0611111111");
            var e2 = eleveService.ajouterEleve("Morel",    "Emma",   14, "emma.morel@eleve.fr",      "0622222222");
            var e3 = eleveService.ajouterEleve("Simon",    "Hugo",   16, "hugo.simon@eleve.fr",      "0633333333");
            var e4 = eleveService.ajouterEleve("Ekounda",  "Léa",   15, "lea.ekounda@eleve.fr",     "0644444444");
            var e5 = eleveService.ajouterEleve("Mohamed",   "Thomas", 14, "thomas.mohamed@eleve.fr",   "0655555555");
            var e6 = eleveService.ajouterEleve("nadia",    "Camille",16, "camille.nadia@eleve.fr",   "0666666666");

            // ── Classes ───────────────────────────────────────────────────────
            var classe3A = classeService.creerClasse("3ème A", "Collège");
            var classe3B = classeService.creerClasse("3ème B", "Collège");

            classeService.affecterProfesseurPrincipal(classe3A.getId(), p1.getId());
            classeService.affecterProfesseurPrincipal(classe3B.getId(), p2.getId());

            classeService.ajouterEleveDansClasse(classe3A.getId(), e1.getId());
            classeService.ajouterEleveDansClasse(classe3A.getId(), e2.getId());
            classeService.ajouterEleveDansClasse(classe3A.getId(), e3.getId());
            classeService.ajouterEleveDansClasse(classe3B.getId(), e4.getId());
            classeService.ajouterEleveDansClasse(classe3B.getId(), e5.getId());
            classeService.ajouterEleveDansClasse(classe3B.getId(), e6.getId());

            // ── Notes ─────────────────────────────────────────────────────────
            // Lucas
            noteService.ajouterNote(e1.getId(), math.getId(),     16.5, "Excellent",   "10/09/2024");
            noteService.ajouterNote(e1.getId(), francais.getId(), 14.0, "Bien",        "12/09/2024");
            noteService.ajouterNote(e1.getId(), sciences.getId(), 17.0, "Très bon",    "15/09/2024");
            noteService.ajouterNote(e1.getId(), histoire.getId(), 13.5, "Satisfaisant","17/09/2024");
            noteService.ajouterNote(e1.getId(), anglais.getId(),  15.0, "Bon travail", "19/09/2024");

            // Emma
            noteService.ajouterNote(e2.getId(), math.getId(),      9.5, "À retravailler","10/09/2024");
            noteService.ajouterNote(e2.getId(), francais.getId(),  18.0, "Excellent",     "12/09/2024");
            noteService.ajouterNote(e2.getId(), sciences.getId(),  11.0, "Passable",      "15/09/2024");
            noteService.ajouterNote(e2.getId(), anglais.getId(),   16.5, "Très bien",     "19/09/2024");

            // Hugo
            noteService.ajouterNote(e3.getId(), math.getId(),     12.0, "Passable",    "10/09/2024");
            noteService.ajouterNote(e3.getId(), francais.getId(), 10.5, "Passable",    "12/09/2024");
            noteService.ajouterNote(e3.getId(), sciences.getId(), 13.0, "Bien",        "15/09/2024");

            // Léa
            noteService.ajouterNote(e4.getId(), math.getId(),     19.0, "Parfait !",   "11/09/2024");
            noteService.ajouterNote(e4.getId(), francais.getId(), 17.5, "Très bien",   "13/09/2024");
            noteService.ajouterNote(e4.getId(), anglais.getId(),  18.5, "Excellent",   "20/09/2024");

            System.out.println("    Données de test chargées ("
                    + eleveService.compter() + " élèves, "
                    + professeurService.compter() + " professeurs, "
                    + matiereService.compter() + " matières, "
                    + classeService.compter() + " classes)");

        } catch (Exception e) {
            System.out.println("     Erreur lors du chargement des données : " + e.getMessage());
        }
    }
}