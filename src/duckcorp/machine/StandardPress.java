package duckcorp.machine;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;
import duckcorp.duck.StandardDuck;

/**
 * Presse produisant des canards {@link StandardDuck}, machine de volume de DuckCorp™.
 * <p>
 * La Presse Standard représente l'essentiel de la production : capacité intermédiaire
 * ({@value #CAPACITY} canards/tour), coût d'achat modéré ({@value #PURCHASE_COST} €)
 * et maintenance régulière ({@value #MAINTENANCE_COST} €).
 * </p>
 *
 * @author Julien Montaigu-Lancelin
 * @see Machine
 * @see StandardDuck
 * @see DuckType#STANDARD
 */
public class StandardPress extends Machine {

    /**
     * Coût d'achat de la Presse Standard, en euros.
     *
     * @see #getPurchaseCost()
     */
    public static final int PURCHASE_COST = 500;

    /**
     * Nombre maximum de canards produits par tour.
     */
    public static final int CAPACITY = 5;

    /**
     * Coût d'une opération de maintenance de la Presse Standard, en euros.
     */
    public static final int MAINTENANCE_COST = 50;

    /**
     * Construit une Presse Standard opérationnelle.
     * <p>
     * Délègue à {@link Machine#Machine(DuckType, int, int)} en passant
     * {@link DuckType#STANDARD}, {@link #CAPACITY} et {@link #MAINTENANCE_COST}.
     * L'état initial de la machine est fixé à 100.
     * </p>
     */
    public StandardPress() {
        super(DuckType.STANDARD, CAPACITY, MAINTENANCE_COST);
    }

    /**
     * Produit un canard {@link StandardDuck} dont la qualité dépend de l'état
     * courant de la machine.
     * <p>
     * Appelle {@link Machine#computeQuality()} pour calculer le score de qualité.
     * </p>
     *
     * @return un nouveau {@link StandardDuck} avec un score de qualité calculé
     */
    @Override
    public Duck produceDuck() {
        return new StandardDuck(computeQuality());
    }

    /**
     * Retourne le coût d'achat de la Presse Standard.
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
     * @return {@code "Presse Standard"}
     */
    @Override
    public String getName() {
        return "Presse Standard";
    }
}