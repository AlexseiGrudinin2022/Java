package rectangle.JSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import rectangle.model.Rectangle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONWriter {
    private final String pathToJSON;


    public JSONWriter(String pathToJSON) {
        this.pathToJSON = pathToJSON;
    }

    private void writeFile(String json) {

        if (new File(pathToJSON).getParentFile().mkdirs()) ;
        {
            try {
                FileWriter fileWriter = new FileWriter(pathToJSON);
                fileWriter.write(json);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void write(List<Rectangle> storage, String objectParseToJSON) {
        JSONObject rectanglesJSON = new JSONObject();
        if (storage.isEmpty()) {
            rectanglesJSON.put(objectParseToJSON, "empty");
        } else {
            JSONArray rectangleArr = new JSONArray();
            for (Rectangle s : storage) {
                JSONObject rectangleParam = new JSONObject();
                rectangleParam.put("x1", s.getX1());
                rectangleParam.put("x2", s.getX2());
                rectangleParam.put("y1", s.getY1());
                rectangleParam.put("y2", s.getY2());
                rectangleArr.add(rectangleParam);
            }
            rectanglesJSON.put(objectParseToJSON, rectangleArr);
        }
        writeFile(rectanglesJSON.toJSONString());
    }


    public void clear() {
        File file = new File(pathToJSON);
        if (file.exists()) {
            System.out.println(file.delete());
        }
    }

}
