package capitaly.player;

/**
 * "Óvatos" player: within a single lap it only spends up to half of its
 * capital on purchases. The budget is (re)computed at the start of each lap
 * as half the current cash, and every purchase debits what remains.
 */
public class CarefulPlayer extends BasePlayer {
    private int lapBudgetRemaining;

    public CarefulPlayer(String name) {
        super(name);
        this.lapBudgetRemaining = getCash() / 2;
    }

    @Override
    public boolean wantsToBuy(int cost) {
        return decide(cost);
    }

    @Override
    public boolean wantsToBuild(int cost) {
        return decide(cost);
    }

    @Override
    public void onStartOfTurn() {
        this.lapBudgetRemaining = getCash() / 2;
    }

    private boolean decide(int cost) {
        if (cost <= lapBudgetRemaining && canAfford(cost)) {
            lapBudgetRemaining -= cost;
            return true;
        }
        return false;
    }
}
