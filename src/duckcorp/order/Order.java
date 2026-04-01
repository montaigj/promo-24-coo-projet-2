package duckcorp.order;

import java.util.Objects;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;
import duckcorp.stock.Stock;

/**
 * Représente une commande client dans le système DuckCorp™.
 * <p>
 * Une commande porte sur un nombre précis de canards d'un type donné,
 * à un prix unitaire fixé, et doit être honorée avant l'expiration du délai.
 * </p>
 * <p>
 * Le cycle de vie d'une commande suit l'énumération {@link OrderStatus} :
 * {@code PENDING} (en attente) → {@code FULFILLED} (honorée) ou {@code EXPIRED}
 * (délai dépassé). La transition vers {@code EXPIRED} est gérée automatiquement
 * par {@link #tick()}.
 * </p>
 * <p>
 * {@link #equals(Object)} et {@link #hashCode()} sont basés <strong>uniquement</strong>
 * sur l'identifiant : deux commandes sont identiques si et seulement si elles
 * partagent le même {@code id}.
 * </p>
 *
 * @author Roussille Philippe &lt;roussille@3il.fr&gt;
 * @see OrderStatus
 * @see DuckType
 */
public class Order {

    /**
     * Compteur statique partagé par toutes les instances d'{@code Order}.
     * Incrémenté à chaque construction pour garantir l'unicité des identifiants.
     */
    private static int counter = 0;

    /**
     * Identifiant unique de la commande, au format {@code CMD-<numéro_3_chiffres>}.
     * <p>Exemple : {@code "CMD-007"} pour la 7e commande créée.</p>
     */
    private final String id;

    /**
     * Type de canard demandé par cette commande.
     *
     * @see DuckType
     */
    private final DuckType duckType;

    /**
     * Nombre de canards demandés.
     */
    private final int quantity;

    /**
     * Prix unitaire proposé par le client, en euros.
     */
    private final double pricePerUnit;

    /**
     * Nombre de tours restants avant expiration automatique de la commande.
     * Décrémenté à chaque appel de {@link #tick()}.
     */
    private int turnsRemaining;

    /**
     * Statut courant de la commande ({@code PENDING}, {@code FULFILLED} ou {@code EXPIRED}).
     *
     * @see OrderStatus
     */
    private OrderStatus status;

    /**
     * Construit une nouvelle commande client.
     * <p>
     * Génère automatiquement un identifiant unique au format {@code CMD-<numéro>}.
     * La commande est initialisée avec le statut {@link OrderStatus#PENDING}.
     * </p>
     *
     * @param duckType       le type de canard demandé ; ne doit pas être {@code null}
     * @param quantity       le nombre de canards demandés ; doit être &gt; 0
     * @param pricePerUnit   le prix unitaire proposé par le client, en euros ; doit être &gt; 0
     * @param turnsRemaining le nombre de tours accordés pour honorer la commande ; doit être &gt; 0
     */
    public Order(DuckType duckType, int quantity, double pricePerUnit, int turnsRemaining) {
        this.id             = String.format("CMD-%03d", ++counter);
        this.duckType       = duckType;
        this.quantity       = quantity;
        this.pricePerUnit   = pricePerUnit;
        this.turnsRemaining = turnsRemaining;
        this.status         = OrderStatus.PENDING;
    }

    // -------------------------------------------------------------------------
    // Getters fournis
    // -------------------------------------------------------------------------

    /**
     * Retourne l'identifiant unique de la commande.
     *
     * @return l'identifiant au format {@code "CMD-<numéro>"} (ex : {@code "CMD-001"})
     */
    public String getId() { return id; }

    /**
     * Retourne le type de canard demandé par cette commande.
     *
     * @return la constante {@link DuckType} associée à cette commande
     */
    public DuckType getDuckType() { return duckType; }

    /**
     * Retourne le nombre de canards demandés.
     *
     * @return la quantité de canards à livrer
     */
    public int getQuantity() { return quantity; }

    /**
     * Retourne le prix unitaire proposé par le client, en euros.
     *
     * @return le prix par canard
     */
    public double getPricePerUnit() { return pricePerUnit; }

    /**
     * Retourne le nombre de tours restants avant expiration de la commande.
     *
     * @return le délai restant en tours ; vaut 0 ou moins si la commande a expiré
     */
    public int getTurnsRemaining() { return turnsRemaining; }

    /**
     * Retourne le statut courant de la commande.
     *
     * @return {@link OrderStatus#PENDING}, {@link OrderStatus#FULFILLED}
     *         ou {@link OrderStatus#EXPIRED}
     */
    public OrderStatus getStatus() { return status; }

    // -------------------------------------------------------------------------
    // Méthode fournie
    // -------------------------------------------------------------------------

    /**
     * Décrémente le délai restant d'un tour et expire la commande si nécessaire.
     * <p>
     * Si la commande est {@link OrderStatus#PENDING} et que {@code turnsRemaining}
     * atteint 0 après décrémentation, le statut passe automatiquement à
     * {@link OrderStatus#EXPIRED}. Les commandes déjà {@code FULFILLED} ou
     * {@code EXPIRED} ne sont pas affectées.
     * </p>
     * <p>
     * Appelée automatiquement par {@code Game} à chaque fin de tour.
     * <strong>Ne pas modifier.</strong>
     * </p>
     */
    public void tick() {
        if (status == OrderStatus.PENDING) {
            turnsRemaining--;
            if (turnsRemaining <= 0) {
                status = OrderStatus.EXPIRED;
            }
        }
    }

    // -------------------------------------------------------------------------
    // Méthodes à implémenter (Ex4)
    // -------------------------------------------------------------------------

    /**
     * Calcule et retourne la valeur totale de la commande.
     * <p>
     * La valeur totale est le produit du prix unitaire par la quantité demandée :
     * {@code pricePerUnit × quantity}.
     * </p>
     *
     * @return la valeur totale de la commande en euros
     */
    public double getTotalValue() {
        return quantity * pricePerUnit;
    }

    /**
     * Détermine si le stock fourni contient suffisamment de canards du bon type
     * pour honorer cette commande.
     * <p>
     * La méthode accepte tout {@link Stock} dont le type générique est une
     * sous-classe de {@link Duck} (wildcard {@code ? extends Duck}), afin de
     * respecter le principe PECS et d'éviter une restriction inutile à
     * {@code Stock<Duck>}.
     * </p>
     *
     * @param stock le stock à interroger ; ne doit pas être {@code null}
     * @return {@code true} si {@code stock.count(duckType) >= quantity},
     *         {@code false} sinon
     */
    public boolean canBeFulfilled(Stock<? extends Duck> stock) {
        return stock.count(duckType) >= quantity;
    }

    /**
     * Marque cette commande comme honorée en passant son statut à
     * {@link OrderStatus#FULFILLED}.
     * <p>
     * Doit être appelée par {@code Factory.fulfillOrder()} <strong>après</strong>
     * le retrait effectif des canards du stock.
     * </p>
     */
    public void fulfill() {
        this.status = OrderStatus.FULFILLED;
    }

    // -------------------------------------------------------------------------
    // equals / hashCode
    // -------------------------------------------------------------------------

    /**
     * Détermine l'égalité entre deux commandes sur la base de leur identifiant.
     * <p>
     * Le type, la quantité, le prix et le statut ne sont <strong>pas</strong>
     * pris en compte : deux commandes sont égales si et seulement si leurs
     * {@code id} sont identiques.
     * </p>
     *
     * @param o l'objet à comparer à cette commande
     * @return {@code true} si {@code o} est une {@code Order} avec le même {@code id}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        return id.equals(((Order) o).id);
    }

    /**
     * Retourne un code de hachage cohérent avec {@link #equals(Object)}.
     * <p>
     * Le hash est calculé uniquement à partir de l'identifiant {@code id}.
     * </p>
     *
     * @return le hash de l'identifiant de la commande
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // -------------------------------------------------------------------------
    // toString fourni
    // -------------------------------------------------------------------------

    /**
     * Retourne une représentation textuelle de la commande au format :
     * {@code [<id>] <qté>x <type> → <prix>/u (<total>€) — <tours> tour(s) — <statut>}.
     * <p>Exemple : {@code [CMD-001] 3x Standard → 30€/u (90€) — 2 tour(s) — PENDING}</p>
     *
     * @return la représentation lisible de la commande
     */
    @Override
    public String toString() {
        return String.format("[%s] %dx %s → %.0f€/u (%.0f€) — %d tour(s) — %s",
                id, quantity, duckType.getLabel(), pricePerUnit,
                pricePerUnit * quantity, turnsRemaining, status);
    }
}