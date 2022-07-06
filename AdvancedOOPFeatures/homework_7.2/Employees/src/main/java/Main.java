
import java.util.Comparator;
import java.util.List;

public class Main {

    private static final String STAFF_TXT = "data/staff.txt";

    public static void main(String[] args) {
        List<Employee> staff = Employee.loadStaffFromFile(STAFF_TXT);
        System.out.println(findEmployeeWithHighestSalary(staff, 1988));
    }

    public static Employee findEmployeeWithHighestSalary(List<Employee> staff, int year) {
        return staff.stream()
                .filter(d -> (d.getWorkStart().getYear() + 1900) == year)
                .max(Comparator.comparing(Employee::getSalary)).orElse(null);
    }
}