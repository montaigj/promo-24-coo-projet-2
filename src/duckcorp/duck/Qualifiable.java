package duckcorp.duck;

/**
 * Interface représentant un objet dont on peut évaluer la qualité.
 * <p>
 * Implémentée par {@link Duck} pour permettre au code de l'usine de vérifier
 * la qualité d'un canard sans connaître son type exact (principe de
 * <em>programmation par interface</em>).
 * </p>
 * <p>
 * Fournit deux méthodes {@code default} ({@link #isDefective()} et
 * {@link #getQualityLabel()}) qui s'appuient sur {@link #getQualityScore()}
 * sans accéder aux champs privés de l'implémenteur.
 * </p>
 *
 * @author Julien Montaigu-Lancelin
 */
public interface Qualifiable {

    /**
     * Retourne le score de qualité de l'objet.
     * <p>
     * Les implémenteurs doivent garantir que la valeur retournée est comprise
     * entre 0 (qualité nulle / rebut) et 100 (qualité parfaite) inclus.
     * </p>
     *
     * @return le score de qualité, dans l'intervalle [0, 100]
     */
    int getQualityScore();

    /**
     * Indique si l'objet est défectueux.
     * <p>
     * Un objet est considéré défectueux si son score de qualité est
     * <strong>strictement inférieur à 20</strong>.
     * Cette implémentation par défaut s'appuie uniquement sur
     * {@link #getQualityScore()} et peut être surchargée si nécessaire.
     * </p>
     *
     * @return {@code true} si {@link #getQualityScore()} {@code < 20},
     *         {@code false} sinon
     */
    default boolean isDefective() {
        return getQualityScore() < 20;
    }

    /**
     * Retourne un libellé lisible décrivant le niveau de qualité de l'objet.
     * <p>
     * Les seuils utilisés sont les suivants :
     * </p>
     * <ul>
     *   <li>score &ge; 80 &rarr; {@code "Excellent"}</li>
     *   <li>score &ge; 50 &rarr; {@code "Bon"}</li>
     *   <li>score &ge; 20 &rarr; {@code "Médiocre"}</li>
     *   <li>score &lt; 20 &rarr; {@code "Défectueux"}</li>
     * </ul>
     * <p>
     * Cette implémentation par défaut s'appuie uniquement sur
     * {@link #getQualityScore()} et peut être surchargée si nécessaire.
     * </p>
     *
     * @return le libellé de qualité correspondant au score courant
     */
    default String getQualityLabel() {
        int score = getQualityScore();
        if (score >= 80) return "Excellent";
        if (score >= 50) return "Bon";
        if (score >= 20) return "Médiocre";
        return "Défectueux";
    }
}