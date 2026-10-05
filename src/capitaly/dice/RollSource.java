package capitaly.dice;

/**
 * Supplies die rolls. Implementations may be scripted (read from a file) or
 * random; the {@link Dice} facade hides which one is in use.
 */
public interface RollSource {
    boolean hasNext();

    int next();
}
