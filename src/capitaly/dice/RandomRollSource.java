package capitaly.dice;

import java.util.Random;

/**
 * An endless source of random 1–6 rolls. Interchangeable with
 * {@link FileRollSource} behind the {@link Dice} facade.
 */
public class RandomRollSource implements RollSource {
    private static final int SIDES = 6;
    private final Random random = new Random();

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public int next() {
        return random.nextInt(SIDES) + 1;
    }
}
