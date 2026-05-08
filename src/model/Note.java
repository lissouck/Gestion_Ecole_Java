package model;

/**
 * Représente une note attribuée à un élève pour une matière donnée.
 *
 * @author Module Notes
 * @version 1.0
 */
public class Note {

    private int     id;
    private Eleve   eleve;
    private Matiere matiere;
    private double  valeur;        // Note entre 0 et 20
    private String  commentaire;
    private String  dateEvaluation;  // Format "JJ/MM/AAAA"

    private static int compteurId = 1;

    // ─── Constructeur ─────────────────────────────────────────────────────────

    public Note(Eleve eleve, Matiere matiere, double valeur,
                String commentaire, String dateEvaluation) {
        this.id             = compteurId++;
        this.eleve          = eleve;
        this.matiere        = matiere;
        this.valeur         = valeur;
        this.commentaire    = commentaire;
        this.dateEvaluation = dateEvaluation;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public int     getId()              { return id; }
    public Eleve   getEleve()           { return eleve; }
    public Matiere getMatiere()         { return matiere; }
    public double  getValeur()          { return valeur; }
    public String  getCommentaire()     { return commentaire; }
    public String  getDateEvaluation()  { return dateEvaluation; }

    public void setValeur(double valeur)              { this.valeur         = valeur; }
    public void setCommentaire(String commentaire)    { this.commentaire    = commentaire; }
    public void setDateEvaluation(String date)        { this.dateEvaluation = date; }

    /**
     * Retourne la mention correspondant à la note.
     */
    public String getMention() {
        if (valeur >= 16) return "Très Bien";
        if (valeur >= 14) return "Bien";
        if (valeur >= 12) return "Assez Bien";
        if (valeur >= 10) return "Passable";
        return "Insuffisant";
    }

    // ─── Affichage ────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("Note{id=%d, eleve='%s', matiere='%s', valeur=%.2f, mention='%s', date='%s'}",
                id, eleve.getNomComplet(), matiere.getNom(),
                valeur, getMention(), dateEvaluation);
    }
}
