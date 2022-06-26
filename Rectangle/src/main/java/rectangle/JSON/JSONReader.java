package rectangle.JSON;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import rectangle.model.Rectangle;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JSONReader {


    private final Logger logger = (Logger) LogManager.getRootLogger();
    private final Path pathToJSON;

    public JSONReader(Path pathToJSON) {
        this.pathToJSON = pathToJSON;
    }


    public List<Rectangle> readJSONToList(String objectParseFromJSON) {

        String readJSONFile_ = readJSONFile();

        if (!readJSONFile_.trim().isEmpty()) {

            List<Rectangle> result = new ArrayList<>();
            try {
                JSONObject object = (JSONObject) (new JSONParser()).parse(readJSONFile());
                Object obj = object.get(objectParseFromJSON);

                if (!(obj == null || obj.toString().length() < 10)) {

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
                logger.error(ex.getMessage());
                throw new RuntimeException(ex);
            }
            return result;
        }
        return Collections.emptyList();
    }


    private String readJSONFile() {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> rectangles = Files.readAllLines(pathToJSON);
            rectangles.forEach(builder::append);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return builder.toString();
    }

}



