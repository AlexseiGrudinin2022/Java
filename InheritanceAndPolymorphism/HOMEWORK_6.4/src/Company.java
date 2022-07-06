import java.util.*;
import java.util.stream.Collectors;


public class Company {


    private final List<Employee> employees = new ArrayList<>();
    private final List<Employee> fireEmployee = new ArrayList<>();
    private final static double INCOME_ALL_DAY = 15000000;
    private final static double INCOME_TODAY = 10000000;

    private List<Employee> runActionSalaryStaff(int count, Comparator<Employee> comparator) {

        if (count <= this.getCountSotr() && count > 0) {
            var copyList = employees;
            copyList.sort(comparator);
            return copyList.subList(0, count);
        } else {
            System.out.println("Лист не может быть сформирован, т.к. записей в массиве недостаточно для вашего количества!");
            return Collections.emptyList();
        }
    }


    public <T> List<Employee> createTypeList(Class<T> clazz) {

        return
                employees.stream()
                        .filter(emp -> clazz.isAssignableFrom(emp.getClass()))
                        .collect(Collectors.toList());
    }


    private List<Employee> removes(int procent, List<Employee> empl) {
        int countEmployeeProcent = empl.size();
        if (countEmployeeProcent > 0) {
            countEmployeeProcent -= ((countEmployeeProcent * procent) / 100);

            for (int i = 0; i < countEmployeeProcent; i++) {

                Employee tmp = empl.get(i);
                fireEmployee.add(tmp);
                empl.remove(tmp);

            }
        }
        return empl;
    }

    public void fire(int procentFireOperator, int procentFireManager, int procentFireTopManager) {

        var operator = removes(procentFireOperator, createTypeList(Operator.class));
        var manager = removes(procentFireManager, createTypeList(Manager.class));
        var topManager = removes(procentFireTopManager, createTypeList(TopManager.class));

        employees.clear();
        this.hireAll(operator);
        this.hireAll(manager);
        this.hireAll(topManager);

    }


    public void hire(Employee employee) {
        System.out.println("Добавлен новый сотрудник - " + employee.toString() + "\n");
        this.employees.add(employee);
    }

    public void hireAll(List<Employee> employee) {
        this.employees.addAll(employee);
    }

    public void listAllRecord() {
        if (this.getCountSotr() > 0) {
            System.out.println("\nСписок всех зарплат: ");

            for (Employee s : employees) {
                System.out.println((employees.indexOf(s) + 1) + ") " + s.toString() + " - " + String.format("%.2f", s.getMonthSalary()));
            }
        }
    }

    public List<Employee> getAllRecords() {
        return employees;
    }

    public List<Employee> getFireRecords() {
        return fireEmployee;
    }

    public void listFireRecord() {
        if (this.fireEmployee.size() > 0) {
            System.out.println("Уволенные ");

            for (Employee s : fireEmployee) {
                System.out.println((fireEmployee.indexOf(s) + 1) + ") " + s.toString() + " - " + String.format("%.2f", s.getMonthSalary()));

            }
        }
    }

    public List<Employee> getTopSalaryStaff(int count) {

        return runActionSalaryStaff(count, Comparator.comparingDouble(Employee::getMonthSalary).reversed());
    }

    public List<Employee> getLowestSalaryStaff(int count) {
        return runActionSalaryStaff(count, Comparator.comparingDouble(Employee::getMonthSalary));
    }

    public int getCountSotr() {
        return this.employees.size();
    }

    public static double getIncome() {
        return INCOME_ALL_DAY;
    }

    public static double getIncomeToday() {
        return INCOME_TODAY;
    }
}
