import java.util.ArrayList;
import java.util.List;

public class Classe {
    private int id;
    private String nom;
    private List<Eleve> eleves;
    private Professeur professeur;

    public Classe(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.eleves = new ArrayList<>();
    }

    public void ajouterEleve(Eleve e) {
        eleves.add(e);
    }

    public void setProfesseur(Professeur p) {
        this.professeur = p;
    }

    public List<Eleve> getEleves() {
        return eleves;
    }

    public String getNom() {
        return nom;
    }
}
