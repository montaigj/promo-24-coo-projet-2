package duckcorp.duck;

/**
 * Énumération des types de canards produits par DuckCorp™.
 * <p>
 * Chaque constante est associée à un libellé lisible en français,
 * utilisé notamment pour l'affichage et les rapports de production.
 * </p>
 *
 * @author Roussille Philippe &lt;roussille@3il.fr&gt;
 */
public enum DuckType {

    /** Canard de volume, produit en grande série par la StandardPress. */
    STANDARD("Standard"),

    /** Canard miniature, très demandé et fabriqué en grande quantité par la MiniPress. */
    MINI("Mini"),

    /** Canard haut de gamme, peu produit mais à forte marge, issu du LuxuryMold. */
    LUXURY("Luxe");

    /**
     * Libellé lisible en français associé au type de canard.
     * Utilisé pour l'affichage et les rapports.
     */
    private final String label;

    /**
     * Construit une constante d'énumération avec le libellé français associé.
     *
     * @param label le libellé lisible du type (ex : {@code "Standard"}, {@code "Mini"}, {@code "Luxe"})
     */
    DuckType(String label) {
        this.label = label;
    }

    /**
     * Retourne le libellé lisible en français du type de canard.
     *
     * @return le libellé associé à cette constante (ex : {@code "Standard"})
     */
    public String getLabel() {
        return label;
    }
}