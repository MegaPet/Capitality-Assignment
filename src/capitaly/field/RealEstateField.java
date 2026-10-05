package capitaly.field;

import capitaly.player.BasePlayer;

public class RealEstateField extends Field {
    static final int COST_OF_REAL_ESTATE = 1_000;
    static final int COST_OF_HOUSE_BUILDING = 4_000;
    static final int COST_OF_RENT_WITHOUT_BUILDING = 500;
    static final int COST_OF_RENT_WITH_BUILDING = 2_000;

    private BasePlayer owner;
    private boolean hasHouse;

    public RealEstateField() {
        this.owner = null;
        this.hasHouse = false;
    }

    @Override
    public void step(BasePlayer player) {
        if (owner == null) {
            tryBuy(player);
        } else if (owner.equals(player)) {
            tryBuild(player);
        } else {
            payRent(player);
        }
    }

    private void tryBuy(BasePlayer player) {
        if (player.wantsToBuy(COST_OF_REAL_ESTATE)) {
            player.payBank(COST_OF_REAL_ESTATE);
            if (player.isAlive()) {
                owner = player;
            }
        }
    }

    private void tryBuild(BasePlayer player) {
        if (!hasHouse && player.wantsToBuild(COST_OF_HOUSE_BUILDING)) {
            player.payBank(COST_OF_HOUSE_BUILDING);
            if (player.isAlive()) {
                hasHouse = true;
            }
        }
    }

    private void payRent(BasePlayer player) {
        int rent = hasHouse ? COST_OF_RENT_WITH_BUILDING : COST_OF_RENT_WITHOUT_BUILDING;
        player.payTo(owner, rent);
    }

    @Override
    public String label() {
        return "real estate";
    }

    public boolean isOwnedBy(BasePlayer player) {
        return owner != null && owner.equals(player);
    }

    public boolean hasHouse() {
        return hasHouse;
    }

    public void reset() {
        this.owner = null;
        this.hasHouse = false;
    }
}
