package duckcorp.machine;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;
import duckcorp.duck.MiniDuck;

/**
 * Presse produisant des {@link MiniDuck}, machine haute cadence de DuckCorp™.
 * <p>
 * La Mini-Presse est l'équipement de grande série : sa capacité est la plus élevée
 * du parc ({@value #CAPACITY} canards/tour), pour un coût d'achat faible
 * ({@value #PURCHASE_COST} €) et une maintenance peu coûteuse ({@value #MAINTENANCE_COST} €).
 * </p>
 *
 * @author Julien Montaigu-Lancelin
 * @see Machine
 * @see MiniDuck
 * @see DuckType#MINI
 */
public class MiniPress extends Machine {

    /**
     * Coût d'achat de la Mini-Presse, en euros.
     *
     * @see #getPurchaseCost()
     */
    public static final int PURCHASE_COST = 300;

    /**
     * Nombre maximum de canards produits par tour.
     */
    public static final int CAPACITY = 8;

    /**
     * Coût d'une opération de maintenance de la Mini-Presse, en euros.
     */
    public static final int MAINTENANCE_COST = 30;

    /**
     * Construit une Mini-Presse opérationnelle.
     * <p>
     * Délègue à {@link Machine#Machine(DuckType, int, int)} en passant
     * {@link DuckType#MINI}, {@link #CAPACITY} et {@link #MAINTENANCE_COST}.
     * L'état initial de la machine est fixé à 100.
     * </p>
     */
    public MiniPress() {
        super(DuckType.MINI, CAPACITY, MAINTENANCE_COST);
    }

    /**
     * Produit un {@link MiniDuck} dont la qualité dépend de l'état courant
     * de la machine.
     * <p>
     * Appelle {@link Machine#computeQuality()} pour calculer le score de qualité.
     * </p>
     *
     * @return un nouveau {@link MiniDuck} avec un score de qualité calculé
     */
    @Override
    public Duck produceDuck() {
        return new MiniDuck(computeQuality());
    }

    /**
     * Retourne le coût d'achat de la Mini-Presse.
     *
     * @return {@link #PURCHASE_COST} ({@value #PURCHASE_COST} €)
     */
    @Override
    public int getPurchaseCost() {
        return PURCHASE_COST;
    }

    /**
     * Retourne le nom lisible de la machine.
     *
     * @return {@code "Mini-Presse"}
     */
    @Override
    public String getName() {
        return "Mini-Presse";
    }
}