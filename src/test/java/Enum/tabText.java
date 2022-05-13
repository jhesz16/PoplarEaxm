package Enum;

public enum tabText {
    JUST_ME("Just Me"),
    WITH_OTHERS("With Others");

    public final String label;

    tabText(String label) {
        this.label = label;
    }

    public String gettext()
    {
        return label;
    }

}