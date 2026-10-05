package capitaly.field;

import capitaly.player.BasePlayer;

public abstract class Field {

    /**
     * Applies this field's effect to the player who just landed on it.
     * Polymorphic dispatch: each field subtype defines its own behaviour,
     * so no caller ever has to switch on a field-type discriminator.
     */
    public abstract void step(BasePlayer player);

    /** Short human-readable name of this field kind, for logging. */
    public abstract String label();
}
