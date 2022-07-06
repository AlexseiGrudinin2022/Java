import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static final String STAFF_TXT = "data/staff.txt";
    private static final List<Employee> staff = Employee.loadStaffFromFile(STAFF_TXT);

    public static void main(String[] args) {
        showSortedRecord(staff);

    }

    public static void showSortedRecord(List<Employee> list) {

        sortBySalaryAndAlphabet(list).forEach(System.out::println);

    }

    public static List<Employee> sortBySalaryAndAlphabet(List<Employee> staff_sort) {
        staff_sort.sort(Comparator.comparing(Employee::getSalary).thenComparing(Employee::getName));
        return new ArrayList<>(staff_sort);
    }
}