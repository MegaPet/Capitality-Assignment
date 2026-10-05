package capitaly.player;

public abstract class BasePlayer {
    private static final int STARTING_CASH_AMOUNT = 10_000;

    private final String name;

    private int cash;
    private boolean alive;

    public BasePlayer(String name) {
        this.name = name;
        this.cash = STARTING_CASH_AMOUNT;
        this.alive = true;
    }

    /* ---- buy-decision hooks (filled in by the strategy subclasses) ---- */

    /** Whether this player chooses to buy an ownerless property for {@code cost}. */
    public abstract boolean wantsToBuy(int cost);

    /** Whether this player chooses to build a house on its own property for {@code cost}. */
    public abstract boolean wantsToBuild(int cost);

    /** Called by the board when the player completes a lap. No-op by default. */
    public void onStartOfTurn() {};

    /* ---- money primitives ---- */

    public boolean canAfford(int amount) {
        return cash >= amount;
    }

    /** Pay the bank; a player who cannot afford a mandatory payment is eliminated. */
    public void payBank(int amount) {
        if (!canAfford(amount)) {
            eliminate();
            return;
        }
        cash -= amount;
    }

    /** Receive money from the bank (luck fields). */
    public void receiveFromBank(int amount) {
        cash += amount;
    }

    /** Pay rent to another player; if unaffordable this player is eliminated. */
    public void payTo(BasePlayer other, int amount) {
        if (!canAfford(amount)) {
            eliminate();
            return;
        }
        cash -= amount;
        other.cash += amount;
    }

    protected void eliminate() {
        this.alive = false;
    }

    /* ---- accessors ---- */

    public String getName() {
        return name;
    }

    public int getCash() {
        return cash;
    }

    public boolean isAlive() {
        return alive;
    }
}
