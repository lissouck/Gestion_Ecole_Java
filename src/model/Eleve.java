package model;

/**
 * Représente un élève dans l'école secondaire.
 * Contient les informations personnelles de l'élève.
 *
 * @author Module Élèves
 * @version 1.0
 */
public class Eleve {

    // ─── Attributs ───────────────────────────────────────────────────────────

    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String email;
    private String telephone;

    // Compteur statique pour générer des IDs uniques
    private static int compteurId = 1;

    // ─── Constructeurs ────────────────────────────────────────────────────────

    /**
     * Crée un nouvel élève avec génération automatique de l'ID.
     */
    public Eleve(String nom, String prenom, int age, String email, String telephone) {
        this.id        = compteurId++;
        this.nom       = nom;
        this.prenom    = prenom;
        this.age       = age;
        this.email     = email;
        this.telephone = telephone;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public int    getId()        { return id; }
    public String getNom()       { return nom; }
    public String getPrenom()    { return prenom; }
    public int    getAge()       { return age; }
    public String getEmail()     { return email; }
    public String getTelephone() { return telephone; }

    public void setNom(String nom)             { this.nom       = nom; }
    public void setPrenom(String prenom)       { this.prenom    = prenom; }
    public void setAge(int age)                { this.age       = age; }
    public void setEmail(String email)         { this.email     = email; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    /** Nom complet (prénom + nom). */
    public String getNomComplet() {
        return prenom + " " + nom;
    }

    // ─── Affichage ────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("Eleve{id=%d, nom='%s %s', age=%d, email='%s', tel='%s'}",
                id, prenom, nom, age, email, telephone);
    }
}
