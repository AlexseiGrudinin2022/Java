package UI;

public enum ButtonNames {
    NAME_COLLAPSE("Collapse"),
    NAME_EXPAND("Expand");

    private final String text;

    ButtonNames(final String text) {
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
