package box.ascension.app.nb2.game.hq.buildings;

public enum EGen implements HqType {

    PETROL("Petrol", 1000, 1000,
        "oil go brrrr"),
    PETROL_II(PETROL, 1000, 1000, "II"),
    PETROL_III(PETROL, 1000, 1000, "III"),
    PETROL_IV(PETROL, 1000, 1000, "IV"),
    PETROL_V(PETROL, 1000, 1000, "V"),
    PETROL_VI(PETROL, 1000, 1000, "VI"),
    PETROL_VII(PETROL, 1000, 1000, "VII"),
    PETROL_VIII(PETROL, 1000, 1000, "VIII"),
    PETROL_IX(PETROL, 1000, 1000, "IX"),
    PETROL_X(PETROL, 1000, 1000, "X"),
    FISSION("Fission", 1000, 1000, 
        "Uses uranium to generate "
        + "immense amounts of power"),
    FISSION_II(FISSION, 1000, 1000, "II"),
    FISSION_III(FISSION, 1000, 1000, "III"),
    FISSION_IV(FISSION, 1000, 1000, "IV"),
    FISSION_V(FISSION, 1000, 1000, "V"),
    FISSION_VI(FISSION, 1000, 1000, "VI"),
    FISSION_VII(FISSION, 1000, 1000, "VII"),
    FISSION_VIII(FISSION, 1000, 1000, "VIII"),
    FISSION_IX(FISSION, 1000, 1000, "IX"),
    FISSION_X(FISSION, 1000, 1000, "X"),
    FUSION("Fusion", 1000, 1000,
        "Uses hydrogen-helium fuel cells to " 
        + "generate immense amounts of power, "
        + "without the inefficiencies of uranium"),
    FUSION_II(FUSION, 1000, 1000, "II"),
    FUSION_III(FUSION, 1000, 1000, "III"),
    FUSION_IV(FUSION, 1000, 1000, "IV"),
    FUSION_V(FUSION, 1000, 1000, "V"),
    FUSION_VI(FUSION, 1000, 1000, "VI"),
    FUSION_VII(FUSION, 1000, 1000, "VII"),
    FUSION_VIII(FUSION, 1000, 1000, "VIII"),
    FUSION_IX(FUSION, 1000, 1000, "IX"),
    FUSION_X(FUSION, 1000, 1000, "X"),
    RED_FUSION("Red Fusion", 1000, 1000,
        "Generates red electricity. I will add " 
        + "lore later"),
    RED_FUSION_II(RED_FUSION, 1000, 1000, "II"),
    RED_FUSION_III(RED_FUSION, 1000, 1000, "III"),
    RED_FUSION_IV(RED_FUSION, 1000, 1000, "IV"),
    RED_FUSION_V(RED_FUSION, 1000, 1000, "V"),
    RED_FUSION_VI(RED_FUSION, 1000, 1000, "VI"),
    RED_FUSION_VII(RED_FUSION, 1000, 1000, "VII"),
    RED_FUSION_VIII(RED_FUSION, 1000, 1000, "VIII"),
    RED_FUSION_IX(RED_FUSION, 1000, 1000, "IX"),
    RED_FUSION_X(RED_FUSION, 1000, 1000, "X"),
    ;

    public final int cost;
    public final long upgradeTime;
    public final String name;
    public final String description;

    private EGen(String name, int cost, long upgradeTime, String description) {
        this.cost = cost;
        this.upgradeTime = upgradeTime;
        this.name = name;
        this.description = description;
    }

    private EGen(EGen prev, int cost, long upgradeTime, String level) {
        this.cost = cost;
        this.upgradeTime = upgradeTime;
        this.name = prev.name + " " + level;
        this.description = prev.description;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public long getUpgradeTime() {
        return upgradeTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

}