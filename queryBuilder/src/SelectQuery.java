public class SelectQuery extends Query {

    private int countWhere = 0;
    private int countFrom = 0;

    public SelectQuery(String... columns) {
        super();
        if (columns.length == 0) {
            throw new NullPointerException("Введите поля таблиц в конструктор select");
        } else {

            for (int i = 0; i < columns.length; i++) {
                columns[i] = columns[i].trim();
            }

            QUERY_SQL.append("SELECT")
                    .append(" ")
                    .append(String.join(", ", columns));
        }
    }

    public SelectQuery from(String table) {
        this.QUERY_SQL.append(" ")
                .append("FROM")
                .append(" ")
                .append(table);
        countFrom++;
        return this;
    }

    public SelectQuery where(QueryCriteria criteria) {
        this.QUERY_SQL.append(" ").append("WHERE").append(" ").append(criteria);
        countWhere++;
        return this;
    }


    @Override
    protected boolean isQueryValid(String query) {
        return (countFrom == 1 && countWhere <= 1);
    }
}