public class Enseignant {
    private String id;
    private String nom;
    private String matiere;

    public Enseignant(String id, String nom, String matiere) {
        this.id = id;
        this.nom = nom;
        this.matiere = matiere;
    }

    // Getters et Setters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }

    @Override
    public String toString() {
        return "ID: " + id + " | Nom: " + nom + " | Matière: " + matiere;
    }
}