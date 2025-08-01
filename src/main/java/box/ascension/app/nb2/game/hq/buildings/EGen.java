package box.ascension.app.nb2.game.hq.buildings;

public enum EGen implements HqType {

    PETROL("Petrol", 
        "Faster than coal, but still "
        + "smoky"),
    PETROL_II(PETROL, "II"),
    PETROL_III(PETROL, "III"),
    PETROL_IV(PETROL, "IV"),
    PETROL_V(PETROL, "V"),
    PETROL_VI(PETROL, "VI"),
    PETROL_VII(PETROL, "VII"),
    PETROL_VIII(PETROL, "VIII"),
    PETROL_IX(PETROL, "IX"),
    PETROL_X(PETROL, "X"),
    FISSION("Fission", 
        "Uses uranium to generate "
        + "immense amounts of power"),
    FISSION_II(FISSION, "II"),
    FISSION_III(FISSION, "III"),
    FISSION_IV(FISSION, "IV"),
    FISSION_V(FISSION, "V"),
    FISSION_VI(FISSION, "VI"),
    FISSION_VII(FISSION, "VII"),
    FISSION_VIII(FISSION, "VIII"),
    FISSION_IX(FISSION, "IX"),
    FISSION_X(FISSION, "X"),
    FUSION("Fusion", 
        "Uses hydrogen-helium fuel cells to " 
        + "generate immense amounts of power, "
        + "without the inefficiencies of uranium"),
    FUSION_II(FUSION, "II"),
    FUSION_III(FUSION, "III"),
    FUSION_IV(FUSION, "IV"),
    FUSION_V(FUSION, "V"),
    FUSION_VI(FUSION, "VI"),
    FUSION_VII(FUSION, "VII"),
    FUSION_VIII(FUSION, "VIII"),
    FUSION_IX(FUSION, "IX"),
    FUSION_X(FUSION, "X"),
    RED_FUSION("Red Fusion",
        "Generates red electricity. I will add " 
        + "lore later"),
    RED_FUSION_II(RED_FUSION, "II"),
    RED_FUSION_III(RED_FUSION, "III"),
    RED_FUSION_IV(RED_FUSION, "IV"),
    RED_FUSION_V(RED_FUSION, "V"),
    RED_FUSION_VI(RED_FUSION, "VI"),
    RED_FUSION_VII(RED_FUSION, "VII"),
    RED_FUSION_VIII(RED_FUSION, "VIII"),
    RED_FUSION_IX(RED_FUSION, "IX"),
    RED_FUSION_X(RED_FUSION, "X");
    ;

    public final String name;
    public final String description;

    private EGen(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private EGen(EGen prev, String level) {
        this.name = prev.name + " " + level;
        this.description = prev.description;
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