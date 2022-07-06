
public class InsertQuery extends Query {

    public InsertQuery() {
        super();
        QUERY_SQL.append("INSERT ");
    }

    private int nameColumnsValuesCount = 0;
    private int expectedAddValuesColumn = 0;
    private boolean isNullValues = false;

    public InsertQuery into(String table, String... nameInsertColumns) {
        QUERY_SQL
                .append("INTO ")
                .append(table)
                .append(" (")
                .append(String.join(",", nameInsertColumns))
                .append(") ");
        nameColumnsValuesCount = nameInsertColumns.length;
        return this;
    }

    private String getValuesObjectStr(Object... values) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < expectedAddValuesColumn; i++) {

            Object currentIterationValue = values[i];

            if (currentIterationValue == null) {
                isNullValues = true;
                break;
            }
            result.append(values[i].toString());

            if (i != expectedAddValuesColumn - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }

    public InsertQuery values(Object... values) {
        //здесь можно достать список колонок таблицы

        expectedAddValuesColumn = values.length;
            QUERY_SQL
                    .append("VALUES (")
                    .append(getValuesObjectStr(values))
                    .append(")");
        return this;
    }

    @Override
    protected boolean isQueryValid(String query) {
        return (expectedAddValuesColumn == nameColumnsValuesCount && !isNullValues);

    }
}