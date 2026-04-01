package duckcorp.duck;

import java.util.Objects;

/**
 * Classe abstraite représentant un canard en plastique DuckCorp™.
 * Chaque canard possède un identifiant unique auto-généré, un type et un score de qualité.
 * Implémente Qualifiable : getQualityScore() satisfait le contrat de l'interface.
 *
 * equals() et hashCode() sont basés uniquement sur l'id :
 * deux canards sont identiques si et seulement si ils partagent le même identifiant.
 *
 * @author Julien Montaigu-Lancelin
 */
public abstract class Duck implements Qualifiable {

    /** Compteur partagé pour générer des identifiants uniques et séquentiels. */
    private static int counter = 0;

    /** Identifiant unique du canard, ex : "S0042" pour un Standard. */
    private final String   id;

    /** Type du canard (STANDARD, MINI, LUXURY). */
    private final DuckType type;

    /**
     * Score de qualité du canard, compris entre 0 et 100.
     * Plafonné à 100 et planché à 0 à la construction.
     */
    private final int qualityScore;

    /**
     * Constructeur protégé appelé par les sous-classes.
     * Génère automatiquement un identifiant unique à partir du type.
     *
     * @param type         le type de canard
     * @param qualityScore le score de qualité brut (sera borné entre 0 et 100)
     */
    protected Duck(DuckType type, int qualityScore) {
        this.id           = type.name().charAt(0) + String.format("%04d", ++counter);
        this.type         = type;
        this.qualityScore = Math.max(0, Math.min(100, qualityScore));
    }

    // --- Getters ---

    /** @return l'identifiant unique du canard */
    public String   getId()           { return id; }

    /** @return le type du canard */
    public DuckType getType()         { return type; }

    /**
     * Retourne le score de qualité du canard (0–100).
     * Satisfait automatiquement le contrat de Qualifiable.
     *
     * @return le score de qualité
     */
    public int getQualityScore() { return qualityScore; }

    // --- Méthodes abstraites ---

    /**
     * Retourne le prix de base du canard selon son type.
     *
     * @return le prix de base en euros
     */
    public abstract double getBasePrice();

    /**
     * Retourne une description en français du canard.
     *
     * @return le nom du canard (ex : "Canard Standard")
     */
    public abstract String describe();

    // --- equals et hashCode ---

    /**
     * Deux canards sont égaux si et seulement si ils ont le même identifiant.
     * Le type et la qualité ne sont pas pris en compte.
     *
     * @param o l'objet à comparer
     * @return true si les deux canards ont le même id
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Duck)) return false;
        return id.equals(((Duck) o).id);
    }

    /**
     * hashCode cohérent avec equals() : basé uniquement sur l'id.
     *
     * @return le hash de l'identifiant
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // --- toString ---

    @Override
    public String toString() {
        return String.format("[%s] %s — qualité : %d/100", id, describe(), qualityScore);
    }
}