package util;

import java.util.Scanner;

/**
 * Classe utilitaire regroupant les méthodes de validation et de saisie.
 * Méthodes statiques utilisables depuis n'importe où dans l'application.
 *
 * @author Équipe
 * @version 1.0
 */
public class Validateur {

    private static final Scanner scanner = new Scanner(System.in);

    // ─── Validation des données ───────────────────────────────────────────────

    /**
     * Lève une exception si le texte est null ou vide.
     */
    public static void verifierNonVide(String valeur, String messageErreur) {
        if (valeur == null || valeur.trim().isEmpty()) {
            throw new IllegalArgumentException(messageErreur);
        }
    }

    /**
     * Vérifie qu'une adresse email a un format valide (simple@domaine.ext).
     */
    public static boolean emailValide(String email) {
        if (email == null) return false;
        return email.matches("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");
    }

    // ─── Lecture sécurisée des entrées utilisateur ────────────────────────────

    /**
     * Lit une chaîne non vide depuis la console.
     * Redemande si l'utilisateur entre une valeur vide.
     */
    public static String lireChaine(String invite) {
        String valeur;
        do {
            System.out.print(invite);
            valeur = scanner.nextLine().trim();
            if (valeur.isEmpty()) {
                System.out.println("  ⚠️  Cette valeur ne peut pas être vide. Veuillez réessayer.");
            }
        } while (valeur.isEmpty());
        return valeur;
    }

    /**
     * Lit une chaîne qui peut être vide (champ optionnel).
     */
    public static String lireChaineOptionnelle(String invite) {
        System.out.print(invite);
        return scanner.nextLine().trim();
    }

    /**
     * Lit un entier dans un intervalle [min, max].
     * Redemande si la valeur est hors intervalle ou non numérique.
     */
    public static int lireEntier(String invite, int min, int max) {
        int valeur;
        while (true) {
            System.out.print(invite);
            try {
                valeur = Integer.parseInt(scanner.nextLine().trim());
                if (valeur >= min && valeur <= max) return valeur;
                System.out.printf("  ⚠️  Entrez un nombre entre %d et %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Saisie invalide. Entrez un nombre entier.");
            }
        }
    }

    /**
     * Lit un entier sans contrainte de plage.
     */
    public static int lireEntier(String invite) {
        return lireEntier(invite, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Lit un nombre décimal (double) entre min et max.
     */
    public static double lireDouble(String invite, double min, double max) {
        double valeur;
        while (true) {
            System.out.print(invite);
            try {
                valeur = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
                if (valeur >= min && valeur <= max) return valeur;
                System.out.printf("  ⚠️  Entrez une valeur entre %.1f et %.1f.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Saisie invalide. Entrez un nombre décimal.");
            }
        }
    }

    /**
     * Demande une confirmation oui/non.
     * @return true si l'utilisateur répond 'o' ou 'oui'
     */
    public static boolean confirmer(String question) {
        System.out.print(question + " (o/n) : ");
        String reponse = scanner.nextLine().trim().toLowerCase();
        return reponse.equals("o") || reponse.equals("oui");
    }
}
