public abstract class Query {

    protected final StringBuilder QUERY_SQL = new StringBuilder();

    public String buildQuery() {
        QUERY_SQL.append(";");
        String query = QUERY_SQL.toString();
        if (!isQueryValid(query)) {
            String exceptionMessage = QUERY_SQL.toString();
            QUERY_SQL.delete(0, QUERY_SQL.length());
            throw new IllegalArgumentException("Неверный запрос!\n" + exceptionMessage);
        }
        return this.toString();
    }

    protected abstract boolean isQueryValid(String query);

    @Override
    public String toString() {
        return QUERY_SQL.toString().replaceAll("\\s+", " ").trim();
    }
}