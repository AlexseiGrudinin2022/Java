public class QueryBuilder {

    public SelectQuery select(String... columns) {
        return new SelectQuery(columns);
    }

    public InsertQuery insert() {
        return new InsertQuery();
    }

    public UpdateQuery update(String tableName) {
        return new UpdateQuery(tableName);
    }

}