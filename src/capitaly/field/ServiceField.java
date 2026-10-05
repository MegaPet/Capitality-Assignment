package capitaly.field;

import capitaly.player.BasePlayer;

/**
 * The "szolgáltatás" (service) field: landing on it means paying the bank
 * the configured amount.
 */
public class ServiceField extends Field {
    private final int amount;

    public ServiceField(int amount) {
        this.amount = amount;
    }

    @Override
    public void step(BasePlayer player) {
        player.payBank(amount);
    }

    @Override
    public String label() {
        return "service";
    }
}
