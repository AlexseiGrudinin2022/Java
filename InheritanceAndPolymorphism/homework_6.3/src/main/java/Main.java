public class Main {

    public static void main(String[] args) {
        PhysicalPerson physicalPerson = new PhysicalPerson();
        LegalPerson legalPerson = new LegalPerson();
        IndividualBusinessman individualBusinessman = new IndividualBusinessman();

        System.out.println(physicalPerson.toString());
        physicalPerson.put(1000);
        physicalPerson.take(500);
        physicalPerson.take(505);
        System.out.println("");
        System.out.println(legalPerson.toString());
        legalPerson.put(1000);
        legalPerson.take(500);
        legalPerson.take(505);
        System.out.println("");
        System.out.println(individualBusinessman.toString());
        individualBusinessman.put(1000);
        individualBusinessman.take(500);
        individualBusinessman.put(999);
        individualBusinessman.take(1484.02);

    }
}
