package capitaly.player;

/**
 * "Mohó" player: buys an ownerless property or builds on its own house-less
 * property whenever it can afford to. Stateless.
 */
public class GreedyPlayer extends BasePlayer {
    public GreedyPlayer(String name) {
        super(name);
    }

    @Override
    public boolean wantsToBuy(int cost) {
        return canAfford(cost);
    }

    @Override
    public boolean wantsToBuild(int cost) {
        return canAfford(cost);
    }
}
