public class Main {
    public static void main(String[] args) {

        System.out.println("Пример 1");

        QueryBuilder queryBuilder = new QueryBuilder();
        QueryCriteria criteria = new QueryCriteria("a = b");
        QueryCriteria criteria1 = new QueryCriteria("b = c");
        QueryCriteria criteria2 = new QueryCriteria("c = 5");


        System.out.println(queryBuilder.select("    a  ", "  b  ", "  c  ")
                .from("tables1")
                .where(criteria
                        .and(criteria1)
                        .or(criteria2))
                .buildQuery());


        System.out.println(queryBuilder.insert().into("table", "col1", "col2")
                .values(1, 2)
                .buildQuery()
        );
        System.out.println(queryBuilder
                .update("update_table")
                .set(new QueryCriteria("price = price * 9000"))
                .where(new QueryCriteria("id = 999"))
                .buildQuery()
        );

        //********************************************************************************

        System.out.println("Пример 2");
        SelectQuery selectQuery = new SelectQuery("col1,col2");
        selectQuery
                .from("table1")
                .where(new QueryCriteria("false != true"));
        InsertQuery insertQuery = new InsertQuery();
        insertQuery
                .into("a1", "col1", "col2", "col3")
                .values(1, "2", 3.56);

        UpdateQuery updateQuery = new UpdateQuery("tableName");
        updateQuery.where(new QueryCriteria("bingo = null")).set(new QueryCriteria("values = 0"));

        System.out.println("select:\n\t" + selectQuery);
        System.out.println("insert:\n\t" + insertQuery);
        System.out.println("update:\n\t" + updateQuery);


    }
}
