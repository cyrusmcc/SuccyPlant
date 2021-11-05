package com.cm.contentmanagementapp.models;

public enum EnumTags {
    BLACK("Black"),
    GREEN("Green"),
    WHITE("White"),
    OOLONG("Oolong");

    private String text;

    EnumTags(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static EnumTags fromString(String text) {
        for (EnumTags b : EnumTags.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

}
