package com.example.easycourse.model;

public enum SchoolLevel {
    MIDDLE_SCHOOL("Middle school"),
    HIGH_SCHOOL("High school"),
    UNDERGRADUTE("Undergraduate"),
    POSTGRADUTE("Postgraduate");

    private final String text;

    /**
     * @param text
     */
    SchoolLevel(final String text) {
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
