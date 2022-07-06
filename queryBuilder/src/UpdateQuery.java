public class UpdateQuery extends Query {
    public UpdateQuery(String tableName) {
        super();
        QUERY_SQL.append("UPDATE ").append(tableName);
    }


    private int countWhere = 0;

    public UpdateQuery set(QueryCriteria criteria) {
        QUERY_SQL
                .append(" ")
                .append("SET ")
                .append(criteria);
        return this;
    }


    public UpdateQuery where(QueryCriteria criteria) {
        this.QUERY_SQL.append(" ").append("WHERE").append(" ").append(criteria);
        countWhere++;
        return this;
    }

    @Override
    protected boolean isQueryValid(String query) {


        return (countWhere <= 1 );

    }
}
