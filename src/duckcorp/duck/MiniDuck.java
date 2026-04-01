package duckcorp.duck;

/**
 * Canard en plastique miniature.
 * Produit par la MiniPress, très demandé et produit en grande quantité.
 * Prix de base à 12 €.
 *
 * @author Julien Montaigu-Lancelin
 */
public class MiniDuck extends Duck {

    /** Prix de base d'un mini canard en euros. */
    public static final double BASE_PRICE = 12.0;

    /**
     * Construit un mini canard avec le score de qualité donné.
     * Délègue à Duck en passant le type MINI.
     *
     * @param qualityScore score de qualité calculé par la machine (0–100)
     */
    public MiniDuck(int qualityScore) {
        super(DuckType.MINI, qualityScore);
    }

    /**
     * Retourne le prix de base d'un mini canard.
     *
     * @return BASE_PRICE (12.0 €)
     */
    @Override
    public double getBasePrice() { return BASE_PRICE; }

    /**
     * Retourne la description en français du canard.
     *
     * @return "Mini Canard"
     */
    @Override
    public String describe() { return "Mini Canard"; }
}