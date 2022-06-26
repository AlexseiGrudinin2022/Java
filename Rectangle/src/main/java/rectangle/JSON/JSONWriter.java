package rectangle.JSON;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import rectangle.model.Rectangle;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JSONWriter {
    private final Path pathToJSON;

    private final Logger logger = (Logger) LogManager.getRootLogger();

    public JSONWriter(Path pathToJSON) {
        this.pathToJSON = pathToJSON;
    }

    public void write(List<Rectangle> storage, String objectParseToJSON) {

        if (storage.isEmpty()) {
            return;
        }

        JSONObject rectanglesJSON = new JSONObject();
        JSONArray rectangleArr = new JSONArray();

        if (storage.get(0) == null) {

            rectanglesJSON.put(objectParseToJSON, "empty");

        } else {

            storage.forEach(
                    s ->
                    {
                        JSONObject rectangleParam = new JSONObject();
                        rectangleParam.put("x1", s.getX1());
                        rectangleParam.put("x2", s.getX2());
                        rectangleParam.put("y1", s.getY1());
                        rectangleParam.put("y2", s.getY2());
                        rectangleArr.add(rectangleParam);
                    }
            );
            rectanglesJSON.put(objectParseToJSON, rectangleArr);
        }


        try (FileWriter fileWriter = new FileWriter(pathToJSON.toString())) {
            fileWriter.write(rectanglesJSON.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public void write(String objectParseToJSON) {

        JSONObject rectanglesJSON = new JSONObject();
        rectanglesJSON.put(objectParseToJSON, "empty");

        try (FileWriter fileWriter = new FileWriter(pathToJSON.toString())) {
            fileWriter.write(rectanglesJSON.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void clear() {
        try {
            if (Files.isExecutable(pathToJSON)) {
                Files.delete(pathToJSON);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
