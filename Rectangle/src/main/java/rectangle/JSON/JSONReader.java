package rectangle.JSON;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import rectangle.model.Rectangle;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class JSONReader {


    private final String pathToJSON;

    public JSONReader(String pathToJSON) {
        this.pathToJSON = pathToJSON;
    }


    public List<Rectangle> readJSONToList(String objectParseFromJSON) {

        String readJSONFile_ = readJSONFile();


        if (!readJSONFile_.trim().isEmpty()) {

            List<Rectangle> result = new ArrayList<Rectangle>();

            JSONObject object = null;

            try {
                object = (JSONObject) (new JSONParser()).parse(readJSONFile_);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            Object obj = object.get(objectParseFromJSON);

            if (!(obj == null || obj.toString().length() < 10)) {

                JSONArray array = (JSONArray) obj;

                for (Object el : array) {
                    JSONObject element = (JSONObject) el;
                    double x1 = (Double) element.get("x1");
                    double x2 = (Double) element.get("x2");
                    double y1 = (Double) element.get("y1");
                    double y2 = (Double) element.get("y2");
                    result.add(new Rectangle(x1, x2, y1, y2));
                }

            }
            return result;
        }


        return Collections.emptyList();
    }


    private String readJSONFile() {

        StringBuilder builder = new StringBuilder();

        if (new File(pathToJSON).exists()) {

            try {
                FileInputStream file = new FileInputStream(pathToJSON);
                Scanner sc = new Scanner(file);
                while (sc.hasNext()) {
                    builder.append(sc.next());
                }
                file.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return builder.toString();
    }

}



