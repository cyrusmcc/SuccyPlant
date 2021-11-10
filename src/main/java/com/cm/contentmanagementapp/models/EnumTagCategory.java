package com.cm.contentmanagementapp.models;

public enum EnumTagCategory {
    LIGHT("Light"),
    WATER("Water"),
    DIFFICULTY("Difficulty"),
    TYPE("Type"),
    GENUS("Genus");

    private String text;

    EnumTagCategory(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static EnumTagCategory fromString(String text) {
        for (EnumTagCategory b : EnumTagCategory.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

}
