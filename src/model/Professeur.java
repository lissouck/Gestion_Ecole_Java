public class Professeur {

    private int    id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String specialite;   // Matière principale enseignée

    private static int compteurId = 1;

    // ─── Constructeur ─────────────────────────────────────────────────────────

    public Professeur(String nom, String prenom, String email,
                      String telephone, String specialite) {
        this.id         = compteurId++;
        this.nom        = nom;
        this.prenom     = prenom;
        this.email      = email;
        this.telephone  = telephone;
        this.specialite = specialite;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public int    getId()          { return id; }
    public String getNom()         { return nom; }
    public String getPrenom()      { return prenom; }
    public String getEmail()       { return email; }
    public String getTelephone()   { return telephone; }
    public String getSpecialite()  { return specialite; }

    public void setNom(String nom)               { this.nom        = nom; }
    public void setPrenom(String prenom)         { this.prenom     = prenom; }
    public void setEmail(String email)           { this.email      = email; }
    public void setTelephone(String telephone)   { this.telephone  = telephone; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public String getNomComplet() {
        return "Prof. " + prenom + " " + nom;
    }

    // ─── Affichage ────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("Professeur{id=%d, nom='%s %s', specialite='%s', email='%s'}",
                id, prenom, nom, specialite, email);
    }
}

