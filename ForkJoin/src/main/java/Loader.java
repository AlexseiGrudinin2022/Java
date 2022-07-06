public class Loader {

    public static final String ROOT_URL = "https://skillbox.ru/";

    public static void main(String[] args) {

        new PrintMapOfCite(ROOT_URL).saveMapOfSiteFromFile("src/mapOfSite.txt");

    }

}
