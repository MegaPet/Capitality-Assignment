package capitaly.field;

import capitaly.player.BasePlayer;

/**
 * The "szerencse" (luck) field: landing on it gives the player the
 * configured amount from the bank.
 */
public class LuckField extends Field {
    private final int amount;

    public LuckField(int amount) {
        this.amount = amount;
    }

    @Override
    public void step(BasePlayer player) {
        player.receiveFromBank(amount);
    }

    @Override
    public String label() {
        return "luck";
    }
}
