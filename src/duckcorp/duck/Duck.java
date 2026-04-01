package duckcorp.duck;

import java.util.Objects;

/**
 * Classe abstraite représentant un canard en plastique DuckCorp™.
 * <p>
 * Chaque canard possède un identifiant unique auto-généré à la construction,
 * un type ({@link DuckType}) et un score de qualité borné entre 0 et 100.
 * </p>
 * <p>
 * Implémente {@link Qualifiable} : {@link #getQualityScore()} satisfait le contrat
 * de l'interface, et les méthodes {@code default} ({@code isDefective()},
 * {@code getQualityLabel()}) sont héritées sans surcharge.
 * </p>
 * <p>
 * {@link #equals(Object)} et {@link #hashCode()} sont basés <strong>uniquement</strong>
 * sur l'identifiant : deux canards sont identiques si et seulement si ils partagent
 * le même {@code id}.
 * </p>
 *
 * @author Julien Montaigu-Lancelin
 * @see Qualifiable
 * @see DuckType
 */
public abstract class Duck implements Qualifiable {

    /**
     * Compteur statique partagé par toutes les instances de {@code Duck}.
     * Incrémenté à chaque construction pour garantir l'unicité des identifiants.
     */
    private static int counter = 0;

    /**
     * Identifiant unique du canard, composé de la première lettre du type
     * suivie d'un numéro séquentiel sur 4 chiffres.
     * <p>Exemple : {@code "S0042"} pour le 42e canard Standard créé.</p>
     */
    private final String id;

    /**
     * Type du canard ({@code STANDARD}, {@code MINI} ou {@code LUXURY}).
     *
     * @see DuckType
     */
    private final DuckType type;

    /**
     * Score de qualité du canard, compris entre 0 et 100 inclus.
     * Toute valeur fournie au constructeur est automatiquement bornée
     * (plancher à 0, plafond à 100).
     */
    private final int qualityScore;

    /**
     * Constructeur protégé appelé par les sous-classes concrètes.
     * <p>
     * Génère automatiquement un identifiant unique de la forme
     * {@code <lettre_type><numéro_4_chiffres>} (ex : {@code "L0003"} pour le
     * 3e {@code LuxuryDuck} créé, quel que soit l'ordre de création global).
     * </p>
     *
     * @param type         le type de canard ; ne doit pas être {@code null}
     * @param qualityScore le score de qualité brut fourni par la machine de production ;
     *                     sera automatiquement ramené à l'intervalle [0, 100]
     */
    protected Duck(DuckType type, int qualityScore) {
        this.id           = type.name().charAt(0) + String.format("%04d", ++counter);
        this.type         = type;
        this.qualityScore = Math.max(0, Math.min(100, qualityScore));
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    /**
     * Retourne l'identifiant unique du canard.
     * <p>
     * L'identifiant est généré une seule fois à la construction et ne change
     * jamais au cours de la vie de l'objet.
     * </p>
     *
     * @return l'identifiant du canard (ex : {@code "S0001"}, {@code "M0042"})
     */
    public String getId() { return id; }

    /**
     * Retourne le type du canard.
     *
     * @return la constante {@link DuckType} associée à ce canard
     */
    public DuckType getType() { return type; }

    /**
     * Retourne le score de qualité du canard, compris entre 0 et 100 inclus.
     * <p>
     * Cette méthode satisfait le contrat de l'interface {@link Qualifiable}
     * et est utilisée par les méthodes {@code default} {@code isDefective()}
     * et {@code getQualityLabel()}.
     * </p>
     *
     * @return le score de qualité (0 = rebut total, 100 = qualité parfaite)
     */
    @Override
    public int getQualityScore() { return qualityScore; }

    // -------------------------------------------------------------------------
    // Méthodes abstraites
    // -------------------------------------------------------------------------

    /**
     * Retourne le prix de base du canard selon son type, en euros.
     * <p>
     * Chaque sous-classe expose sa constante {@code BASE_PRICE} et la retourne ici.
     * </p>
     *
     * @return le prix de base en euros (ex : {@code 25.0} pour un {@link StandardDuck})
     */
    public abstract double getBasePrice();

    /**
     * Retourne une description lisible en français du canard.
     *
     * @return le nom du canard (ex : {@code "Canard Standard"}, {@code "Mini Canard"})
     */
    public abstract String describe();

    // -------------------------------------------------------------------------
    // equals / hashCode
    // -------------------------------------------------------------------------

    /**
     * Détermine l'égalité entre deux canards sur la base de leur identifiant.
     * <p>
     * Le type et le score de qualité ne sont <strong>pas</strong> pris en compte :
     * deux canards sont égaux si et seulement si leurs {@code id} sont identiques.
     * </p>
     *
     * @param o l'objet à comparer à ce canard
     * @return {@code true} si {@code o} est un {@code Duck} avec le même {@code id}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Duck)) return false;
        return id.equals(((Duck) o).id);
    }

    /**
     * Retourne un code de hachage cohérent avec {@link #equals(Object)}.
     * <p>
     * Le hash est calculé uniquement à partir de l'identifiant {@code id}.
     * </p>
     *
     * @return le hash de l'identifiant du canard
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    /**
     * Retourne une représentation textuelle du canard au format :
     * {@code [<id>] <description> — qualité : <score>/100}.
     * <p>Exemple : {@code [S0001] Canard Standard — qualité : 74/100}</p>
     *
     * @return la représentation lisible du canard
     */
    @Override
    public String toString() {
        return String.format("[%s] %s — qualité : %d/100", id, describe(), qualityScore);
    }
}