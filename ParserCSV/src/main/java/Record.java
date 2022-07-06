public class Record {


    private Double expense;
    private Double income;
    private String organizationPostingsName;


    public void setExpense(Double expense) {

        this.expense = expense;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public void setOrganizationPostingsName(String organizationPostingsName) {
        this.organizationPostingsName = organizationPostingsName.substring(organizationPostingsName.lastIndexOf(" ")).trim();

    }


    public double getExpense() {
        return (expense != null) ? expense : 0.0;
    }

    public double getIncome() {
        return (income != null) ? income : 0.0;
    }

    public String getOrganizationPostingsName() {
        return organizationPostingsName;
    }

    @Override
    public String toString() {
        return "Парсинг строки: \n" +
                "\tПриход: " + income + ";\n" +
                "\tРасход: " + expense + ";\n" +
                "\tПроводка: " + organizationPostingsName + ";";
    }


}
