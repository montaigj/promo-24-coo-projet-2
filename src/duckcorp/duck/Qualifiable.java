package duckcorp.duck;

/**
 * Interface représentant un objet dont on peut évaluer la qualité.
 * Implémentée par Duck pour permettre au code de l'usine de vérifier
 * la qualité d'un canard sans connaître son type exact.
 *
 * @author Julien Montaigu-Lancelin
 */
public interface Qualifiable {

    /**
     * Retourne le score de qualité de l'objet, entre 0 et 100.
     *
     * @return le score de qualité
     */
    int getQualityScore();

    /**
     * Indique si l'objet est défectueux.
     * Un objet est défectueux si son score de qualité est strictement inférieur à 20.
     * S'appuie sur getQualityScore() sans accéder aux champs privés de l'implémenteur.
     *
     * @return true si le score est < 20, false sinon
     */
    default boolean isDefective() {
        return getQualityScore() < 20;
    }

    /**
     * Retourne un libellé lisible décrivant le niveau de qualité :
     * <ul>
     *   <li>score >= 80 → "Excellent"</li>
     *   <li>score >= 50 → "Bon"</li>
     *   <li>score >= 20 → "Médiocre"</li>
     *   <li>score <  20 → "Défectueux"</li>
     * </ul>
     *
     * @return le libellé de qualité
     */
    default String getQualityLabel() {
        int score = getQualityScore();
        if (score >= 80) return "Excellent";
        if (score >= 50) return "Bon";
        if (score >= 20) return "Médiocre";
        return "Défectueux";
    }
}