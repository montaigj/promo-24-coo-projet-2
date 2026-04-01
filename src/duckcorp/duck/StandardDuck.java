package duckcorp.duck;

/**
 * Canard en plastique standard, produit de volume de DuckCorp™.
 * <p>
 * Fabriqué par la <em>StandardPress</em>, il représente l'essentiel des commandes
 * de l'usine : fréquences de production élevées, marges modestes.
 * Son prix de base est fixé à {@value #BASE_PRICE} €.
 * </p>
 *
 * @author Julien Montaigu-Lancelin
 * @see Duck
 * @see DuckType#STANDARD
 */
public class StandardDuck extends Duck {

    /**
     * Prix de base d'un canard standard, en euros.
     * Utilisé par {@link #getBasePrice()} et exposé publiquement pour
     * permettre des calculs externes sans instanciation.
     */
    public static final double BASE_PRICE = 25.0;

    /**
     * Construit un canard standard avec le score de qualité fourni.
     * <p>
     * Délègue à {@link Duck#Duck(DuckType, int)} en passant le type
     * {@link DuckType#STANDARD}. Le score est automatiquement borné à [0, 100].
     * </p>
     *
     * @param qualityScore score de qualité calculé par la StandardPress (0–100)
     */
    public StandardDuck(int qualityScore) {
        super(DuckType.STANDARD, qualityScore);
    }

    /**
     * Retourne le prix de base d'un canard standard.
     *
     * @return {@link #BASE_PRICE} ({@value #BASE_PRICE} €)
     */
    @Override
    public double getBasePrice() { return BASE_PRICE; }

    /**
     * Retourne la description en français du canard standard.
     *
     * @return {@code "Canard Standard"}
     */
    @Override
    public String describe() { return "Canard Standard"; }
}