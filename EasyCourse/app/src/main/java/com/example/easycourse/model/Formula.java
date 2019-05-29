package com.example.easycourse.model;

public enum Formula {
    PROGRESSION ("progression"),
    SUPPORT ("support");

    private final String text;

    /**
     * @param text
     */
    Formula(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
