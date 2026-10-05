import capitaly.config.ConfigParser;
import capitaly.config.GameConfig;
import capitaly.dice.Dice;
import capitaly.dice.FileRollSource;
import capitaly.dice.RandomRollSource;
import capitaly.engine.GameService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    static final String CAPITALITY_LOGO_PATH = "assets/logo.txt";
    static final String CONFIG_PATH = "resources/config.txt";
    static final String DICE_PATH = "resources/dice.txt";

    static final boolean useFileRoll = true;
    static final int ROUNDS = 10;

    public static void main() {
        try {
            displayLogo(Path.of(CAPITALITY_LOGO_PATH));

            GameConfig config = new ConfigParser().parse(Path.of(CONFIG_PATH));
            Dice dice = new Dice(
                useFileRoll ? new FileRollSource(Path.of(DICE_PATH)) : new RandomRollSource());

            GameService game = GameService.getInstance();
            game.init(config.board(), config.players(), dice, ROUNDS);
            game.run();
        } catch (IOException e) {
            System.err.println("A bemeneti fájl nem olvasható: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Hibás konfiguráció: " + e.getMessage());
        }
    }

    private static void displayLogo(Path logoPath) throws IOException {
        Files.readAllLines(logoPath).forEach(System.out::println);
    }
}
