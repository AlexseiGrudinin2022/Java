public class TopManager extends AbstractEmployee {

    /* зарплата складывается из фиксированной части и бонуса в виде 150% от заработной платы,
    если доход компании более 10 млн рублей.*/


    @Override
    public void setMonthSalary() {
        super.setMonthSalary();
        if (Company.getIncome() > Company.getIncomeToday()) {
            super.setMonthSalary(super.getMonthSalary() * 1.5);
        }
    }

    @Override
    public String toString() {
        return "TopManager";
    }


}
