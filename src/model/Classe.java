import java.util.ArrayList;
import java.util.List;

public class Classe {

    private int        id;
    private String     nom;           // Ex: "3ème A", "Terminale S"
    private String     niveau;        // Ex: "Collège", "Lycée"
    private Professeur professeurPrincipal;
    private List<Eleve> eleves;

    private static int compteurId = 1;

    // ─── Constructeur ─────────────────────────────────────────────────────────

    public Classe(String nom, String niveau) {
        this.id                  = compteurId++;
        this.nom                 = nom;
        this.niveau              = niveau;
        this.professeurPrincipal = null;
        this.eleves              = new ArrayList<>();
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public int            getId()                   { return id; }
    public String         getNom()                  { return nom; }
    public String         getNiveau()               { return niveau; }
    public Professeur     getProfesseurPrincipal()  { return professeurPrincipal; }
    public List<Eleve>    getEleves()               { return eleves; }

    public void setNom(String nom)                          { this.nom                 = nom; }
    public void setNiveau(String niveau)                    { this.niveau              = niveau; }
    public void setProfesseurPrincipal(Professeur p)        { this.professeurPrincipal = p; }

    // ─── Méthodes métier ──────────────────────────────────────────────────────

    /**
     * Ajoute un élève à la classe s'il n'est pas déjà présent.
     * @return true si l'ajout a réussi, false si déjà présent
     */
    public boolean ajouterEleve(Eleve eleve) {
        for (Eleve e : eleves) {
            if (e.getId() == eleve.getId()) return false;
        }
        eleves.add(eleve);
        return true;
    }

    /**
     * Retire un élève de la classe.
     * @return true si la suppression a réussi
     */
    public boolean retirerEleve(int eleveId) {
        return eleves.removeIf(e -> e.getId() == eleveId);
    }

    /**
     * Vérifie si un élève appartient à cette classe.
     */
    public boolean contientEleve(int eleveId) {
        return eleves.stream().anyMatch(e -> e.getId() == eleveId);
    }

    /** Nombre d'élèves dans la classe. */
    public int getNombreEleves() {
        return eleves.size();
    }

    // ─── Affichage ────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        String prof = (professeurPrincipal != null) ? professeurPrincipal.getNomComplet() : "Non assigné";
        return String.format("Classe{id=%d, nom='%s', niveau='%s', prof='%s', nbEleves=%d}",
                id, nom, niveau, prof, eleves.size());
    }
}
