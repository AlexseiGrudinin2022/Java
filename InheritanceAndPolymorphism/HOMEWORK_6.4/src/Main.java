public class Main {



    public static void main(String[] args) {


        UseCompany uses = new UseCompany(new Company());
        UseCompany newUseCompany = new UseCompany(new Company());

        uses.hireEmployees(); //наймем много сотрудников
        uses.showAllListCompany(); //выведем всех сотрудников
        uses.fireEmployees(); // уволим 50% сотрудников
        uses.showFireListCompany(); //выведем всех уволенных сотрудников
        System.out.println();
        uses.printHighestSalaries(); //выведем топ высоких зп
        System.out.println();
        uses.printLowestSalaries(); // выведем топ низких зп*/

        //Уволенные сотрудники ищут работу...и переходят в новую компанию
        uses.migrationToCompany(newUseCompany.getCompany());
        newUseCompany.showAllListCompany();


    }


}
