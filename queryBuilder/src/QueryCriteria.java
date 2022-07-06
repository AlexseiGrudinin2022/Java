public class QueryCriteria {

    String criteries ="";

    public QueryCriteria(Object argument) {
        this.criteries += argument.toString();
    }

    public QueryCriteria and(QueryCriteria argCriteriaAND) {
        this.criteries += " AND " + argCriteriaAND;
        return this;
    }

    public QueryCriteria or(QueryCriteria argCriteriaOR) {
        this.criteries += " OR " + argCriteriaOR;
        return this;
    }


    @Override
    public String toString() {
        return criteries;
    }
}