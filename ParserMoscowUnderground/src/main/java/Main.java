import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        try {


            Path pathToJSONFile = ParserUndergroundJSON.getJSONMoscowMap("src\\main\\resources", "moscowMap");


            if (pathToJSONFile != null) {
                JSONReader jsonReader = new JSONReader(pathToJSONFile);
                jsonReader.parseJSONFile();
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
