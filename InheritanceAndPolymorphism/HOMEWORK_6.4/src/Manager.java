public class Manager extends AbstractEmployee {


    @Override
    public void setMonthSalary() {
        super.setMonthSalary();
        super.setMonthSalary((((Math.random() * 25000) + 115000) * 0.05));
    }

    @Override
    public String toString() {
        return "Manager";
    }

    /*
     зарплата складывается из фиксированной части и бонуса в виде 5% от заработанных для компании денег.
     Количество заработанных денег для компании генерируйте случайным образом от 115 000 до 140 000 рублей.
    */


}
