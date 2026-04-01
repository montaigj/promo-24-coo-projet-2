package duckcorp.duck;

/**
 * Canard en plastique standard.
 * Produit par la StandardPress, c'est le canard de volume de DuckCorp™ :
 * commandes fréquentes, marges modestes, prix de base à 25 €.
 *
 * @author Julien Montaigu-Lancelin
 */
public class StandardDuck extends Duck {

    /** Prix de base d'un canard standard en euros. */
    public static final double BASE_PRICE = 25.0;

    /**
     * Construit un canard standard avec le score de qualité donné.
     * Délègue à Duck en passant le type STANDARD.
     *
     * @param qualityScore score de qualité calculé par la machine (0–100)
     */
    public StandardDuck(int qualityScore) {
        super(DuckType.STANDARD, qualityScore);
    }

    /**
     * Retourne le prix de base d'un canard standard.
     *
     * @return BASE_PRICE (25.0 €)
     */
    @Override
    public double getBasePrice() { return BASE_PRICE; }

    /**
     * Retourne la description en français du canard.
     *
     * @return "Canard Standard"
     */
    @Override
    public String describe() { return "Canard Standard"; }
}