package capitaly.engine;

import capitaly.dice.Dice;
import capitaly.field.Field;
import capitaly.gameboard.BoardGame;
import capitaly.player.BasePlayer;

import java.util.List;

/**
 * Singleton coordinator of a Capitaly game. Owns the board, the ordered list
 * of players and the dice facade, runs the round-robin turn loop and prints
 * the per-turn log and final standings.
 */
public class GameService {
    private static final GameService INSTANCE = new GameService();

    private BoardGame board;
    private List<BasePlayer> players;
    private Dice dice;
    private int rounds;

    private GameService() {
    }

    public static GameService getInstance() {
        return INSTANCE;
    }

    public void init(BoardGame board, List<BasePlayer> players, Dice dice, int rounds) {
        this.board = board;
        this.players = players;
        this.dice = dice;
        this.rounds = rounds;
    }

    /** Plays up to {@code rounds} rounds (a round = one turn for each living player). */
    public void run() {
        int played = 0;
        for (int round = 1; round <= rounds && !isOver(); round++) {
            System.out.printf("%n--- Round %d ---%n", round);
            playRound();
            played = round;
        }
        announceStandings(played);
    }

    private void playRound() {
        for (BasePlayer player : players) {
            if (!player.isAlive()) {
                continue;
            }
            if (!dice.hasNext()) {
                return;
            }
            takeTurn(player);
            if (aliveCount() <= 1) {
                return;
            }
        }
    }

    private void takeTurn(BasePlayer player) {
        int steps = dice.roll();
        int cashBefore = player.getCash();

        Field field = board.advance(player, steps);
        field.step(player);

        int index = board.positionOf(player);
        int delta = player.getCash() - cashBefore;

        System.out.printf(
            "%s rolls %d -> field %d (%s): %s | cash: %d%n",
            player.getName(), steps, index, field.label(),
            describeOutcome(player, delta), player.getCash());

        if (!player.isAlive()) {
            board.releaseHoldings(player);
            System.out.printf("  %s is eliminated; their properties are freed.%n", player.getName());
        }
    }

    private String describeOutcome(BasePlayer player, int delta) {
        if (!player.isAlive()) {
            return "could not pay";
        }
        if (delta < 0) {
            return "paid " + (-delta);
        }
        if (delta > 0) {
            return "received " + delta;
        }
        return "nothing happened";
    }

    private boolean isOver() {
        return !dice.hasNext() || aliveCount() <= 1;
    }

    private long aliveCount() {
        return players.stream().filter(BasePlayer::isAlive).count();
    }

    private void announceStandings(int rounds) {
        System.out.println();
        System.out.printf("=== Standings after %d round(s) ===%n", rounds);
        players.stream()
            .sorted((a, b) -> Integer.compare(b.getCash(), a.getCash()))
            .forEach(p -> System.out.printf(
                "%-10s %s cash: %-7d properties: %s%n",
                p.getName(), p.isAlive() ? "[alive]" : "[out]  ",
                p.getCash(), board.holdingsOf(p)));
    }
}
