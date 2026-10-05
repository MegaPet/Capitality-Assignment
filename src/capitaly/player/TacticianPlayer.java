package capitaly.player;

/**
 * "Taktikus" player: skips every second buying opportunity. The toggle flips
 * only when an opportunity is actually actionable (buyable/buildable AND
 * affordable) — mandatory payments never touch it. Acts on the first
 * actionable opportunity, skips the next, and so on.
 */
public class TacticianPlayer extends BasePlayer {
    private boolean actThisTime = true;

    public TacticianPlayer(String name) {
        super(name);
    }

    @Override
    public boolean wantsToBuy(int cost) {
        return decide(cost);
    }

    @Override
    public boolean wantsToBuild(int cost) {
        return decide(cost);
    }

    private boolean decide(int cost) {
        if (!canAfford(cost)) {
            return false; // not actionable: the toggle does not advance
        }
        boolean act = actThisTime;
        actThisTime = !actThisTime;
        return act;
    }
}
