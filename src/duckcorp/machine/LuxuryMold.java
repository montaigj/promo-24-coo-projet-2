package duckcorp.machine;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;
import duckcorp.duck.LuxuryDuck;

/**
 * Moule produisant des {@link LuxuryDuck}, équipement haut de gamme de DuckCorp™.
 * <p>
 * Le Moule de Luxe est la machine la plus coûteuse du parc ({@value #PURCHASE_COST} €),
 * avec la capacité la plus faible ({@value #CAPACITY} canards/tour) mais la marge
 * la plus élevée par canard. Sa disponibilité est conditionnée à une réputation
 * d'usine supérieure ou égale à 80.
 * </p>
 *
 * @author Julien Montaigu-Lancelin
 * @see Machine
 * @see LuxuryDuck
 * @see DuckType#LUXURY
 */
public class LuxuryMold extends Machine {

    /**
     * Coût d'achat du Moule de Luxe, en euros.
     *
     * @see #getPurchaseCost()
     */
    public static final int PURCHASE_COST = 800;

    /**
     * Nombre maximum de canards de luxe produits par tour.
     */
    public static final int CAPACITY = 2;

    /**
     * Coût d'une opération de maintenance du Moule de Luxe, en euros.
     */
    public static final int MAINTENANCE_COST = 80;

    /**
     * Construit un Moule de Luxe opérationnel.
     * <p>
     * Délègue à {@link Machine#Machine(DuckType, int, int)} en passant
     * {@link DuckType#LUXURY}, {@link #CAPACITY} et {@link #MAINTENANCE_COST}.
     * L'état initial de la machine est fixé à 100.
     * </p>
     */
    public LuxuryMold() {
        super(DuckType.LUXURY, CAPACITY, MAINTENANCE_COST);
    }

    /**
     * Produit un {@link LuxuryDuck} dont la qualité dépend de l'état courant
     * de la machine.
     * <p>
     * Appelle {@link Machine#computeQuality()} pour calculer le score de qualité.
     * </p>
     *
     * @return un nouveau {@link LuxuryDuck} avec un score de qualité calculé
     */
    @Override
    public Duck produceDuck() {
        return new LuxuryDuck(computeQuality());
    }

    /**
     * Retourne le coût d'achat du Moule de Luxe.
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
     * @return {@code "Moule de Luxe"}
     */
    @Override
    public String getName() {
        return "Moule de Luxe";
    }
}