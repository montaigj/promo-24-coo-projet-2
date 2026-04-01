package duckcorp.machine;

/**
 * Interface représentant une machine pouvant être entretenue.
 * <p>
 * Implémentée par {@link Machine} pour permettre au code de l'usine de gérer
 * la maintenance des équipements sans connaître leur type exact.
 * </p>
 * <p>
 * Fournit deux méthodes {@code default} ({@link #needsMaintenance()} et
 * {@link #getConditionLabel()}) qui s'appuient sur {@link #getCondition()}
 * sans accéder aux champs privés de l'implémenteur.
 * </p>
 *
 * @author Roussille Philippe &lt;roussille@3il.fr&gt;
 * @see Machine
 */
public interface Maintainable {

    /**
     * Retourne l'état courant de la machine.
     * <p>
     * Les implémenteurs doivent garantir que la valeur retournée est comprise
     * entre 0 (machine hors service) et 100 (machine en parfait état) inclus.
     * </p>
     *
     * @return l'état de la machine dans l'intervalle [0, 100]
     */
    int getCondition();

    /**
     * Effectue une opération de maintenance sur la machine.
     * <p>
     * L'effet concret dépend de l'implémentation : par convention,
     * la maintenance augmente l'état de la machine, dans la limite de 100.
     * </p>
     *
     * @see Machine#maintain()
     */
    void maintain();

    /**
     * Indique si la machine nécessite une maintenance.
     * <p>
     * Une machine est considérée en état critique si son état est
     * <strong>strictement inférieur à 30</strong>.
     * Cette implémentation par défaut s'appuie uniquement sur
     * {@link #getCondition()} et peut être surchargée si nécessaire.
     * </p>
     *
     * @return {@code true} si {@link #getCondition()} {@code < 30},
     *         {@code false} sinon
     */
    default boolean needsMaintenance() {
        return getCondition() < 30;
    }

    /**
     * Retourne un libellé lisible décrivant l'état courant de la machine.
     * <p>
     * Les seuils utilisés sont les suivants :
     * </p>
     * <ul>
     *   <li>état &ge; 80 &rarr; {@code "Parfait"}</li>
     *   <li>état &ge; 50 &rarr; {@code "Correct"}</li>
     *   <li>état &ge; 30 &rarr; {@code "Usé"}</li>
     *   <li>état &lt; 30 &rarr; {@code "Critique"}</li>
     * </ul>
     * <p>
     * Cette implémentation par défaut s'appuie uniquement sur
     * {@link #getCondition()} et peut être surchargée si nécessaire.
     * </p>
     *
     * @return le libellé d'état correspondant à la condition courante
     */
    default String getConditionLabel() {
        int c = getCondition();
        if (c >= 80) return "Parfait";
        if (c >= 50) return "Correct";
        if (c >= 30) return "Usé";
        return "Critique";
    }
}