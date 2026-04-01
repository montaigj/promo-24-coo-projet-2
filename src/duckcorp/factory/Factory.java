package duckcorp.factory;

import duckcorp.duck.Duck;
import duckcorp.machine.Machine;
import duckcorp.order.Order;
import duckcorp.stats.ProductionStats;
import duckcorp.stock.Stock;

import java.util.*;

/**
 * Cœur du jeu : orchestre les machines, le stock, le budget et la réputation.
 * Appelée par {@link duckcorp.Game} à chaque étape d'un tour.
 *
 * @author Roussille Philippe
 */
public class Factory {

    private double budget;
    private double reputation = 100.0;
    private final Stock<Duck> stock = new Stock<>();
    private final List<Machine> machines = new ArrayList<>();
    private final ProductionStats stats = new ProductionStats();

    /**
     * Crée une nouvelle usine avec le budget de départ donné.
     *
     * @param initialBudget budget initial en euros
     */
    public Factory(double initialBudget) { this.budget = initialBudget; }

    /** @return le budget actuel en euros */
    public double getBudget() { return budget; }

    /** @return la réputation actuelle (0–100) */
    public double getReputation() { return reputation; }

    /** @return le stock de canards de l'usine */
    public Stock<Duck> getStock() { return stock; }

    /** @return une vue non modifiable de la liste des machines */
    public List<Machine> getMachines() { return Collections.unmodifiableList(machines); }

    /** @return les statistiques de production et de vente */
    public ProductionStats getStats() { return stats; }

    /**
     * Signale qu'une commande a expiré.
     * Pénalise la réputation de 5 points et incrémente le compteur d'expirations.
     */
    public void notifyExpiredOrder() {
        reputation = Math.max(0, reputation - 5);
        stats.recordExpiredOrder();
    }

    /**
     * Calcule le score final du joueur.
     * Formule : budget + réputation×80 + commandesHonorées×200 − commandesExpirées×100
     *
     * @return le score final
     */
    public int computeScore() {
        return (int) (budget + reputation * 80 + stats.getTotalOrders() * 200 - stats.getOrdersExpired() * 100);
    }

    /**
     * Tente d'acheter une machine.
     * Vérifie que le budget est suffisant, déduit le coût et ajoute la machine.
     *
     * @param m la machine à acheter
     * @return {@code true} si l'achat a réussi, {@code false} si budget insuffisant
     */
    public boolean buyMachine(Machine m) {
        if (budget < m.getPurchaseCost()) return false;
        budget -= m.getPurchaseCost();
        machines.add(m);
        return true;
    }

    /**
     * Tente d'effectuer la maintenance d'une machine.
     * Vérifie que le budget est suffisant, déduit le coût et délègue à {@link Machine#maintain()}.
     *
     * @param m la machine à entretenir
     * @return {@code true} si la maintenance a réussi, {@code false} si budget insuffisant
     */
    public boolean maintainMachine(Machine m) {
        if (budget < m.getMaintenanceCost()) return false;
        budget -= m.getMaintenanceCost();
        m.maintain();
        return true;
    }

    /**
     * Déclenche la production de toutes les machines pour ce tour.
     * Chaque machine produit autant de canards que sa capacité via {@link Machine#produceDuck()}.
     * Les canards produits sont ajoutés au stock et enregistrés dans les stats.
     *
     * @return la liste complète des canards produits ce tour
     */
    public List<Duck> runProduction() {
        List<Duck> produced = new ArrayList<>();
        for (Machine m : machines)
            for (int i = 0; i < m.getCapacity(); i++) {
                Duck d = m.produceDuck();
                stock.add(d);
                produced.add(d);
            }
        stats.recordProduction(produced);
        return produced;
    }

    /**
     * Tente d'honorer une commande.
     * Si le stock est suffisant : retire les canards, crédite le budget,
     * met à jour la réputation selon la qualité moyenne (+3 si moy ≥ 70, +1 si moy ≥ 50),
     * marque la commande honorée et enregistre la vente dans les stats.
     *
     * @param order la commande à honorer
     * @return {@code true} si la commande a été honorée, {@code false} si stock insuffisant
     */
    public boolean fulfillOrder(Order order) {
        if (!order.canBeFulfilled(stock)) return false;
        List<Duck> shipped = stock.remove(order.getDuckType(), order.getQuantity());
        double avg = shipped.stream().mapToInt(Duck::getQualityScore).average().orElse(0);
        budget += order.getTotalValue();
        if (avg >= 70)      reputation = Math.min(100, reputation + 3);
        else if (avg >= 50) reputation = Math.min(100, reputation + 1);
        order.fulfill();
        stats.recordSale(order);
        return true;
    }

    /**
     * Effectue la fin de tour : dégrade toutes les machines.
     * Pour chaque machine dont la condition passe en dessous de 30 après dégradation,
     * pénalise la réputation de 5 points.
     */
    public void endTurn() {
        for (Machine m : machines) {
            m.degrade();
            if (m.needsMaintenance()) reputation = Math.max(0, reputation - 5);
        }
    }
}