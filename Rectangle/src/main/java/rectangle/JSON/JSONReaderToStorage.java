package rectangle.JSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import rectangle.model.Rectangle;
import rectangle.storage.RectangleStorage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JSONReaderToStorage {


    private final Path pathToJSON;

    public JSONReaderToStorage(Path pathToJSON) {
        this.pathToJSON = pathToJSON;
    }


    public List<Rectangle> parseJSONToList() {
        List<Rectangle> result = new ArrayList<>();

        try {

            JSONObject object = (JSONObject) (new JSONParser()).parse(readJSONFile());

            Object obj = object.get("rects");

            if (!obj.equals("empty")) {

                JSONArray array = (JSONArray) obj;

                array.forEach(
                        el ->
                        {
                            JSONObject element = (JSONObject) el;
                            double x1 = (Double) element.get("x1");
                            double x2 = (Double) element.get("x2");
                            double y1 = (Double) element.get("y1");
                            double y2 = (Double) element.get("y2");
                            result.add(new Rectangle(x1, x2, y1, y2));
                        }
                );
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public void parseJSONToStorage() {
        RectangleStorage.addRectangleList(parseJSONToList());
    }

    private String readJSONFile() {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> rectangles = Files.readAllLines(pathToJSON);
            rectangles.forEach(builder::append);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return builder.toString();
    }

}



