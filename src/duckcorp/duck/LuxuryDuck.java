package duckcorp.duck;

/**
 * Canard en plastique de luxe, produit haut de gamme de DuckCorp™.
 * <p>
 * Fabriqué par le <em>LuxuryMold</em>, il est peu demandé mais génère une
 * forte marge. Sa production est conditionnée à une réputation d'usine
 * supérieure ou égale à 80.
 * Son prix de base est fixé à {@value #BASE_PRICE} €.
 * </p>
 *
 * @author Julien Montaigu-Lancelin
 * @see Duck
 * @see DuckType#LUXURY
 */
public class LuxuryDuck extends Duck {

    /**
     * Prix de base d'un canard de luxe, en euros.
     * Utilisé par {@link #getBasePrice()} et exposé publiquement pour
     * permettre des calculs externes sans instanciation.
     */
    public static final double BASE_PRICE = 80.0;

    /**
     * Construit un canard de luxe avec le score de qualité fourni.
     * <p>
     * Délègue à {@link Duck#Duck(DuckType, int)} en passant le type
     * {@link DuckType#LUXURY}. Le score est automatiquement borné à [0, 100].
     * </p>
     *
     * @param qualityScore score de qualité calculé par le LuxuryMold (0–100)
     */
    public LuxuryDuck(int qualityScore) {
        super(DuckType.LUXURY, qualityScore);
    }

    /**
     * Retourne le prix de base d'un canard de luxe.
     *
     * @return {@link #BASE_PRICE} ({@value #BASE_PRICE} €)
     */
    @Override
    public double getBasePrice() { return BASE_PRICE; }

    /**
     * Retourne la description en français du canard de luxe.
     *
     * @return {@code "Canard de Luxe"}
     */
    @Override
    public String describe() { return "Canard de Luxe"; }
}