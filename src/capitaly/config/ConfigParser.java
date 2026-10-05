package capitaly.config;

import capitaly.field.Field;
import capitaly.field.LuckField;
import capitaly.field.RealEstateField;
import capitaly.field.ServiceField;
import capitaly.gameboard.BoardGame;
import capitaly.player.BasePlayer;
import capitaly.player.CarefulPlayer;
import capitaly.player.GreedyPlayer;
import capitaly.player.TacticianPlayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Reads a Capitaly configuration file:
 * <pre>
 *   &lt;field count&gt;
 *   &lt;field defs: PROPERTY | SERVICE n | LUCKY n&gt;
 *   &lt;player count&gt;
 *   &lt;player defs: Name greedy|careful|tactician&gt;
 * </pre>
 */
public class ConfigParser {

    public GameConfig parse(Path path) throws IOException {
        Iterator<String> lines = readNonBlankLines(path).iterator();

        int fieldCount = Integer.parseInt(lines.next().trim());
        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < fieldCount; i++) {
            fields.add(parseField(lines.next().trim()));
        }

        int playerCount = Integer.parseInt(lines.next().trim());
        List<BasePlayer> players = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            players.add(parsePlayer(lines.next().trim()));
        }

        BoardGame board = new BoardGame(fields, players);
        return new GameConfig(board, players);
    }

    private Field parseField(String line) {
        String[] parts = line.split("\\s+");
        String type = parts[0].toUpperCase();
        return switch (type) {
            case "PROPERTY", "REAL_ESTATE" -> new RealEstateField();
            case "SERVICE" -> new ServiceField(parseAmount(parts, line));
            case "LUCKY" -> new LuckField(parseAmount(parts, line));
            default -> throw new IllegalArgumentException("Ismeretlen mezőtípus: " + parts[0]);
        };
    }

    private int parseAmount(String[] parts, String line) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Hiányzó pénzdíj a mezőnél: " + line);
        }
        return Integer.parseInt(parts[1]);
    }

    private BasePlayer parsePlayer(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Hiányzó név vagy stratégia a sorban: " + line);
        }
        String name = parts[0];
        String strategy = parts[1].toLowerCase();
        return switch (strategy) {
            case "greedy" -> new GreedyPlayer(name);
            case "careful" -> new CarefulPlayer(name);
            case "tactician" -> new TacticianPlayer(name);
            default -> throw new IllegalArgumentException("Ismeretlen stratégia: " + parts[1]);
        };
    }

    private List<String> readNonBlankLines(Path path) throws IOException {
        return Files.readAllLines(path)
            .stream()
            .filter(line -> !line.isBlank())
            .toList();
    }
}
