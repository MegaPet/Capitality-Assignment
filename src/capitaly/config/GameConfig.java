package capitaly.config;

import capitaly.gameboard.BoardGame;
import capitaly.player.BasePlayer;

import java.util.List;

/** The parsed result of a config file: the ready board and the ordered players. */
public record GameConfig(BoardGame board, List<BasePlayer> players) {
}
