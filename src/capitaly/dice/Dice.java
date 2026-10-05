package capitaly.dice;

/**
 * Facade over a {@link RollSource}. The game only ever talks to {@code Dice}
 * and never knows whether the rolls are predetermined or random.
 */
public class Dice {
    private final RollSource source;

    public Dice(RollSource source) {
        this.source = source;
    }

    /** Whether another roll is available (a finite source can run out). */
    public boolean hasNext() {
        return source.hasNext();
    }

    /** The next die value. */
    public int roll() {
        return source.next();
    }
}
