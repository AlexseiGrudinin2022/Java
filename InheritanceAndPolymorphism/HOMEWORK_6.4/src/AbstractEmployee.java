public class AbstractEmployee implements Employee {

    private double salary = 39500;

    @Override
    public double getMonthSalary() {
        return salary;
    }

    @Override
    public void setMonthSalary() {
        salary = (int) (Math.random() * ((salary - 10000) + 1)) + 10000;
        System.out.println("Фиксированная часть оклада компании для этого сотрудника - " + salary);
    }

    public void setMonthSalary(double salary) {
        this.salary += salary;
        System.out.println("Проценты - " + String.format("%.2f", salary));
        System.out.println("Сумма с процентами - " + String.format("%.2f", this.salary));
    }

}
