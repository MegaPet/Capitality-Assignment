package capitaly.dice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A finite, predetermined roll source read from a whitespace-separated file
 * of integers (e.g. {@code resources/dice.txt}). When the rolls run out,
 * {@link #hasNext()} returns {@code false}, which ends the game.
 */
public class FileRollSource implements RollSource {
    private final List<Integer> rolls;
    private int index;

    public FileRollSource(Path path) throws IOException {
        this.rolls = new ArrayList<>();
        this.index = 0;
        for (String line : Files.readAllLines(path)) {
            for (String token : line.trim().split(" ")) {
                if (!token.isEmpty()) {
                    rolls.add(Integer.parseInt(token));
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        return index < rolls.size();
    }

    @Override
    public int next() {
        return rolls.get(index++);
    }
}
