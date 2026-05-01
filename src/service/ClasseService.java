public class ClasseService {
    private final ClasseRepository  classeRepository;
    private final EleveService      eleveService;
    private final ProfesseurService professeurService;

    public ClasseService(ClasseRepository classeRepository,
                         EleveService eleveService,
                         ProfesseurService professeurService) {
        this.classeRepository  = classeRepository;
        this.eleveService      = eleveService;
        this.professeurService = professeurService;
    }

    // ─── Création ─────────────────────────────────────────────────────────────

    public Classe creerClasse(String nom, String niveau) {
        Validateur.verifierNonVide(nom,    "Le nom de la classe est obligatoire");
        Validateur.verifierNonVide(niveau, "Le niveau est obligatoire");

        if (classeRepository.nomExiste(nom)) {
            throw new IllegalArgumentException("Une classe nommée '" + nom + "' existe déjà.");
        }

        Classe classe = new Classe(nom.trim(), niveau.trim());
        classeRepository.sauvegarder(classe);
        return classe;
    }

    // ─── Modification ─────────────────────────────────────────────────────────

    public void modifierClasse(int id, String nom, String niveau) {
        Classe classe = trouverParId(id);
        Validateur.verifierNonVide(nom, "Le nom est obligatoire");

        if (!classe.getNom().equalsIgnoreCase(nom) && classeRepository.nomExiste(nom)) {
            throw new IllegalArgumentException("Une classe nommée '" + nom + "' existe déjà.");
        }

        classe.setNom(nom.trim());
        classe.setNiveau(niveau.trim());
    }

    // ─── Gestion des élèves dans la classe ───────────────────────────────────

    /**
     * Ajoute un élève à une classe.
     * @throws IllegalArgumentException si l'élève est déjà dans la classe
     */
    public void ajouterEleveDansClasse(int classeId, int eleveId) {
        Classe classe = trouverParId(classeId);
        Eleve  eleve  = eleveService.trouverParId(eleveId);

        if (!classe.ajouterEleve(eleve)) {
            throw new IllegalArgumentException(
                eleve.getNomComplet() + " est déjà dans la classe " + classe.getNom());
        }
    }

    public void retirerEleveDeLaClasse(int classeId, int eleveId) {
        Classe classe = trouverParId(classeId);
        if (!classe.retirerEleve(eleveId)) {
            throw new IllegalArgumentException("Cet élève n'appartient pas à cette classe.");
        }
    }

    // ─── Affectation du professeur principal ─────────────────────────────────

    public void affecterProfesseurPrincipal(int classeId, int professeurId) {
        Classe     classe = trouverParId(classeId);
        Professeur prof   = professeurService.trouverParId(professeurId);
        classe.setProfesseurPrincipal(prof);
    }

    public void retirerProfesseurPrincipal(int classeId) {
        Classe classe = trouverParId(classeId);
        classe.setProfesseurPrincipal(null);
    }

    // ─── Suppression ──────────────────────────────────────────────────────────

    public void supprimerClasse(int id) {
        if (!classeRepository.supprimer(id)) {
            throw new IllegalArgumentException("Aucune classe trouvée avec l'ID : " + id);
        }
    }

    // ─── Lecture ──────────────────────────────────────────────────────────────

    public List<Classe> obtenirToutesLesClasses() {
        return classeRepository.findAll();
    }

    public Classe trouverParId(int id) {
        return classeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Classe introuvable (ID=" + id + ")"));
    }

    public List<Classe> rechercherParNiveau(String niveau) {
        return classeRepository.findByNiveau(niveau);
    }

    public int compter() {
        return classeRepository.count();
    }
}