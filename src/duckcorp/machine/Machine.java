package duckcorp.machine;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;

import java.util.Random;

/**
 * Classe abstraite représentant une machine de production de canards DuckCorp™.
 * <p>
 * Chaque machine est caractérisée par le type de canard qu'elle produit,
 * sa capacité de production par tour, son état de fonctionnement (0–100)
 * et son coût de maintenance.
 * </p>
 * <p>
 * L'état se dégrade automatiquement à chaque tour via {@link #degrade()} et peut
 * être restauré partiellement par {@link #maintain()}. La qualité des canards
 * produits est directement influencée par l'état courant de la machine, calculée
 * par la méthode finale {@link #computeQuality()}.
 * </p>
 * <p>
 * Implémente {@link Maintainable} : {@link #getCondition()} et {@link #maintain()}
 * satisfont le contrat de l'interface.
 * </p>
 *
 * @author Roussille Philippe &lt;roussille@3il.fr&gt;
 * @see Maintainable
 * @see DuckType
 */
public abstract class Machine implements Maintainable {

    /**
     * Générateur de nombres aléatoires partagé par toutes les machines.
     * Utilisé dans {@link #computeQuality()} pour introduire une variation
     * dans le score de qualité des canards produits.
     */
    private static final Random RANDOM = new Random();

    /**
     * Type de canard produit par cette machine.
     *
     * @see DuckType
     */
    private final DuckType producedType;

    /**
     * Nombre maximum de canards que cette machine peut produire par tour.
     */
    private final int capacity;

    /**
     * État courant de la machine, compris entre 0 (hors service) et 100 (parfait état).
     * Initialisé à 100 à la construction, dégradé de 10 points par tour via {@link #degrade()}.
     */
    private int condition;

    /**
     * Coût d'une opération de maintenance, en euros.
     * Débité sur le budget de l'usine à chaque appel de {@link #maintain()}.
     */
    private final int maintenanceCost;

    /**
     * Constructeur protégé appelé par les sous-classes concrètes.
     * <p>
     * La machine est initialisée avec un état de 100 (condition parfaite).
     * </p>
     *
     * @param producedType  le type de canard que cette machine est capable de produire ;
     *                      ne doit pas être {@code null}
     * @param capacity      le nombre maximum de canards produits par tour ; doit être &gt; 0
     * @param maintenanceCost le coût d'une opération de maintenance, en euros ; doit être &ge; 0
     */
    protected Machine(DuckType producedType, int capacity, int maintenanceCost) {
        this.producedType    = producedType;
        this.capacity        = capacity;
        this.condition       = 100;
        this.maintenanceCost = maintenanceCost;
    }

    // -------------------------------------------------------------------------
    // Getters fournis
    // -------------------------------------------------------------------------

    /**
     * Retourne le type de canard produit par cette machine.
     *
     * @return la constante {@link DuckType} associée à cette machine
     */
    public DuckType getProducedType() { return producedType; }

    /**
     * Retourne la capacité de production de la machine, en nombre de canards par tour.
     *
     * @return la capacité maximale par tour
     */
    public int getCapacity() { return capacity; }

    /**
     * Retourne le coût d'une opération de maintenance pour cette machine, en euros.
     *
     * @return le coût de maintenance
     */
    public int getMaintenanceCost() { return maintenanceCost; }

    /**
     * Retourne l'état courant de la machine.
     * <p>
     * Satisfait le contrat de {@link Maintainable#getCondition()}.
     * La valeur est toujours comprise entre 0 et 100 inclus.
     * </p>
     *
     * @return l'état de la machine dans l'intervalle [0, 100]
     */
    @Override
    public int getCondition() { return condition; }

    // -------------------------------------------------------------------------
    // maintain()
    // -------------------------------------------------------------------------

    /**
     * Effectue une opération de maintenance sur la machine.
     * <p>
     * Augmente l'état ({@code condition}) de <strong>40 points</strong>,
     * dans la limite de 100. Une machine déjà à 100 ne bénéficie d'aucun effet.
     * </p>
     * <p>
     * Satisfait le contrat de {@link Maintainable#maintain()}.
     * </p>
     */
    @Override
    public void maintain() {
        condition = Math.min(100, condition + 40);
    }

    // -------------------------------------------------------------------------
    // Méthodes fournies
    // -------------------------------------------------------------------------

    /**
     * Dégrade l'état de la machine de <strong>10 points</strong>, avec un plancher à 0.
     * <p>
     * Appelée automatiquement par {@code Factory.endTurn()} à chaque fin de tour.
     * <strong>Ne pas modifier.</strong>
     * </p>
     */
    public void degrade() {
        condition = Math.max(0, condition - 10);
    }

    /**
     * Calcule le score de qualité d'un canard produit par cette machine
     * en fonction de son état courant.
     * <p>
     * La formule appliquée est :
     * </p>
     * <pre>
     *   base      = (int)(condition × 0.7)
     *   variation = entier aléatoire dans [0, 30]
     *   qualité   = min(100, base + variation)
     * </pre>
     * <p>
     * Plus l'état de la machine est élevé, plus la qualité de base est haute,
     * mais une part d'aléatoire subsiste toujours. Cette méthode est
     * {@code final} et ne peut pas être surchargée.
     * <strong>Ne pas modifier.</strong>
     * </p>
     *
     * @return le score de qualité calculé, dans l'intervalle [0, 100]
     */
    protected final int computeQuality() {
        int base      = (int) (condition * 0.7);
        int variation = RANDOM.nextInt(31);
        return Math.min(100, base + variation);
    }

    // -------------------------------------------------------------------------
    // Méthodes abstraites à implémenter dans les sous-classes
    // -------------------------------------------------------------------------

    /**
     * Produit un canard du type géré par cette machine.
     * <p>
     * L'implémentation doit appeler {@link #computeQuality()} pour calculer
     * le score de qualité du canard produit.
     * </p>
     *
     * @return un nouveau {@link Duck} dont le type correspond à {@link #getProducedType()}
     */
    public abstract Duck produceDuck();

    /**
     * Retourne le coût d'achat de cette machine, en euros.
     * <p>
     * Chaque sous-classe expose sa constante {@code PURCHASE_COST} et la retourne ici.
     * </p>
     *
     * @return le coût d'achat de la machine
     */
    public abstract int getPurchaseCost();

    /**
     * Retourne le nom lisible de la machine.
     *
     * @return le nom de la machine (ex : {@code "Presse Standard"}, {@code "Moule de Luxe"})
     */
    public abstract String getName();

    // -------------------------------------------------------------------------
    // toString fourni
    // -------------------------------------------------------------------------

    /**
     * Retourne une représentation textuelle de la machine au format :
     * {@code <nom> [capacité : <n>/tour, état : <e>%]}.
     * <p>Exemple : {@code Presse Standard [capacité : 5/tour, état : 80%]}</p>
     *
     * @return la représentation lisible de la machine
     */
    @Override
    public String toString() {
        return String.format("%s [capacité : %d/tour, état : %d%%]",
                getName(), capacity, condition);
    }
}