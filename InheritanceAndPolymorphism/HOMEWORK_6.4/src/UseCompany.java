import java.util.Random;

public class UseCompany {

    final private static int countPrintLowestSalaries = 6;
    final static private int procentFireOperator = 50;
    final static private int procentFireManager = 50;
    final static private int procentFireTopManager = 50;
    public Company company;



    public UseCompany (Company company){
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void migrationToCompany(Company newCompany) {
        int randomCount = new Random().nextInt(this.company.getFireRecords().size());
        newCompany.hireAll(this.company.getFireRecords().subList(0, randomCount));
        System.out.println("\nМиграция "+randomCount+" уволенных людей в новую компанию...");
    }


    public void printLowestSalaries() {
        System.out.println("Самые низкие зарплаты: ");

        for (Employee employee :this.company.getLowestSalaryStaff(countPrintLowestSalaries)) {
            System.out.println(employee.toString() + " - " + String.format("%.2f", employee.getMonthSalary()));
        }
    }

    public void printHighestSalaries() {
        System.out.println("Самые высокие зарплаты: ");
        for (Employee employee : this.company.getTopSalaryStaff(countPrintLowestSalaries)) {
            System.out.println(employee.toString() + " - " + String.format("%.2f", employee.getMonthSalary()));
        }
    }

    public void hireEmployees() {
        double salaryAllOperator = 0;
        double salaryAllManager = 0;
        double salaryAllTopManager = 0;
        for (int i = 0; i < 180; i++) {
            Employee operator = new Operator();
            operator.setMonthSalary();
            this.company.hire(operator);
            salaryAllOperator += operator.getMonthSalary();
        }
        for (int i = 0; i < 80; i++) {
            Employee manager = new Manager();
            manager.setMonthSalary();
            this.company.hire(manager);
            salaryAllManager += manager.getMonthSalary();
        }
        for (int i = 0; i < 10; i++) {
            Employee topManager = new TopManager();
            topManager.setMonthSalary();
            this.company.hire(topManager);
            salaryAllTopManager += topManager.getMonthSalary();
        }
        System.out.println("Зп оператора: " + salaryAllOperator);
        System.out.println("Зп менеджера: " + salaryAllManager);
        System.out.println("Зп топменеджера: " + salaryAllTopManager);
    }

    public void fireEmployees() {
        this.company.fire(procentFireOperator, procentFireManager, procentFireTopManager);
    }

    public void showAllListCompany()
    {
        this.company.listAllRecord();
    }

    public void showFireListCompany()
    {
        this.company.listFireRecord();
    }
}
