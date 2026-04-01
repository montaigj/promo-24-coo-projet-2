package duckcorp.duck;

/**
 * Canard en plastique miniature, best-seller de DuckCorp™.
 * <p>
 * Fabriqué par la <em>MiniPress</em>, il est produit en très grande quantité
 * pour répondre à une demande soutenue. Son prix de base est fixé à
 * {@value #BASE_PRICE} €.
 * </p>
 *
 * @author Julien Montaigu-Lancelin
 * @see Duck
 * @see DuckType#MINI
 */
public class MiniDuck extends Duck {

    /**
     * Prix de base d'un mini canard, en euros.
     * Utilisé par {@link #getBasePrice()} et exposé publiquement pour
     * permettre des calculs externes sans instanciation.
     */
    public static final double BASE_PRICE = 12.0;

    /**
     * Construit un mini canard avec le score de qualité fourni.
     * <p>
     * Délègue à {@link Duck#Duck(DuckType, int)} en passant le type
     * {@link DuckType#MINI}. Le score est automatiquement borné à [0, 100].
     * </p>
     *
     * @param qualityScore score de qualité calculé par la MiniPress (0–100)
     */
    public MiniDuck(int qualityScore) {
        super(DuckType.MINI, qualityScore);
    }

    /**
     * Retourne le prix de base d'un mini canard.
     *
     * @return {@link #BASE_PRICE} ({@value #BASE_PRICE} €)
     */
    @Override
    public double getBasePrice() { return BASE_PRICE; }

    /**
     * Retourne la description en français du mini canard.
     *
     * @return {@code "Mini Canard"}
     */
    @Override
    public String describe() { return "Mini Canard"; }
}