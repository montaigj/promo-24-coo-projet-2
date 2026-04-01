package duckcorp.duck;

/**
 * Canard en plastique de luxe.
 * Produit par le LuxuryMold, peu demandé mais à forte marge.
 * Disponible uniquement si la réputation de l'usine est >= 80.
 * Prix de base à 80 €.
 *
 * @author Julien Montaigu-Lancelin
 */
public class LuxuryDuck extends Duck {

    /** Prix de base d'un canard de luxe en euros. */
    public static final double BASE_PRICE = 80.0;

    /**
     * Construit un canard de luxe avec le score de qualité donné.
     * Délègue à Duck en passant le type LUXURY.
     *
     * @param qualityScore score de qualité calculé par la machine (0–100)
     */
    public LuxuryDuck(int qualityScore) {
        super(DuckType.LUXURY, qualityScore);
    }

    /**
     * Retourne le prix de base d'un canard de luxe.
     *
     * @return BASE_PRICE (80.0 €)
     */
    @Override
    public double getBasePrice() { return BASE_PRICE; }

    /**
     * Retourne la description en français du canard.
     *
     * @return "Canard de Luxe"
     */
    @Override
    public String describe() { return "Canard de Luxe"; }
}