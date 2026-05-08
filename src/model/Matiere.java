public class Matiere {

    private int        id;
    private String     nom;
    private String     description;
    private int        coefficipent;   // Coefficient pour le calcul de la moyenne
    private Professeur professeur;    // Enseignant responsable (peut être null)

    private static int compteurId = 1;

    // ─── Constructeur ─────────────────────────────────────────────────────────

    public Matiere(String nom, String description, int coefficient) {
        this.id          = compteurId++;
        this.nom         = nom;
        this.description = description;
        this.coefficient = coefficient;
        this.professeur  = null;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public int        getId()          { return id; }
    public String     getNom()         { return nom; }
    public String     getDescription() { return description; }
    public int        getCoefficient() { return coefficient; }
    public Professeur getProfesseur()  { return professeur; }

    public void setNom(String nom)               { this.nom         = nom; }
    public void setDescription(String desc)      { this.description = desc; }
    public void setCoefficient(int coeff)        { this.coefficient = coeff; }
    public void setProfesseur(Professeur p)      { this.professeur  = p; }

    // ─── Affichage ────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        String prof = (professeur != null) ? professeur.getNomComplet() : "Non assigné";
        return String.format("Matiere{id=%d, nom='%s', coef=%d, prof='%s'}",
                id, nom, coefficient, prof);
    }
}