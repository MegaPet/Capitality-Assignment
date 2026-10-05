package capitaly.gameboard;

import capitaly.field.Field;
import capitaly.field.RealEstateField;
import capitaly.player.BasePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The circular board. It owns both the fixed track of fields and the mutable
 * bookkeeping of where each player currently stands.
 */
public class BoardGame {
    private final List<Field> fields;
    private final Map<BasePlayer, Integer> positions;

    public BoardGame(List<Field> fields, List<BasePlayer> players) {
        this.fields = fields;
        this.positions = new HashMap<>();
        for (BasePlayer player : players) {
            positions.put(player, 0);
        }
    }

    public int size() {
        return fields.size();
    }

    public Field fieldAt(int index) {
        return fields.get(index);
    }

    public int positionOf(BasePlayer player) {
        return positions.get(player);
    }

    /**
     * Moves the player {@code steps} fields forward around the circular board,
     * notifying it of a completed lap, and returns the field it landed on.
     */
    public Field advance(BasePlayer player, int steps) {
        player.onStartOfTurn();
        int raw = positions.get(player) + steps;
        int position = raw % size();
        positions.put(player, position);
        return fieldAt(position);
    }

    /**
     * A human-readable list of the real-estate fields owned by the player,
     * marking which carry a house (e.g. {@code "#1 (house), #4"}).
     */
    public String holdingsOf(BasePlayer player) {
        List<String> owned = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i) instanceof RealEstateField realEstate && realEstate.isOwnedBy(player)) {
                owned.add("#" + i + (realEstate.hasHouse() ? " (house)" : ""));
            }
        }
        return owned.isEmpty() ? "no properties" : String.join(", ", owned);
    }

    /**
     * Frees every property owned by an eliminated player (also demolishing its
     * houses) so the fields become buyable again.
     */
    public void releaseHoldings(BasePlayer dead) {
        for (Field field : fields) {
            if (field instanceof RealEstateField realEstate && realEstate.isOwnedBy(dead)) {
                realEstate.reset();
            }
        }
    }
}
