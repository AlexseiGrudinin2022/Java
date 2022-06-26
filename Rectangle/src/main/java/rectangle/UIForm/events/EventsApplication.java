package rectangle.UIForm.events;

import rectangle.JSON.JSONReader;
import rectangle.JSON.JSONWriter;
import rectangle.functional.ComputeOverLappingRectangle;
import rectangle.input.InputCoordinate;
import rectangle.model.Rectangle;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class EventsApplication {


    private final JSONWriter writer;
    private final JSONReader reader;
    private final List<Rectangle> rectangleList;
    private final String objectParseToJSON;

    private Path IOJSONFile;

    private final String JSON_RESULT_FILE_NAME = "result.json";


    private boolean inputIsValid(Double x1, Double x2, Double y1, Double y2) {
        return (x1 != null && x2 != null && y1 != null && y2 != null);
    }

    public EventsApplication(String IOJSONFile_, String objectParseToJSON) {

        try {
            this.IOJSONFile = Paths.get(IOJSONFile_);
        } catch (Exception ex) {
            System.err.println("такого файла нет");
        }


        writer = new JSONWriter(IOJSONFile);
        reader = new JSONReader(IOJSONFile);
        rectangleList = new ArrayList<>();
        this.objectParseToJSON = objectParseToJSON;

    }


    private void writeJSON() {
        writer.write(rectangleList, objectParseToJSON);
    }

    private List<Rectangle> readJSON() {
        return reader.readJSONToList(objectParseToJSON);
    }

    public int getCountRecord() {
        return rectangleList.size();
    }


    public Rectangle addRectangle(String x1, String x2, String y1, String y2) {
        Double x1Coordinate = InputCoordinate.parseCoordinate(x1);
        Double x2Coordinate = InputCoordinate.parseCoordinate(x2);
        Double y1Coordinate = InputCoordinate.parseCoordinate(y1);
        Double y2Coordinate = InputCoordinate.parseCoordinate(y2);

        if (inputIsValid(x1Coordinate, x2Coordinate, y1Coordinate, y2Coordinate)) {
            Rectangle rectangle = new Rectangle(x1Coordinate, x2Coordinate, y1Coordinate, y2Coordinate);
            rectangleList.add(rectangle);
            writeJSON();
            return rectangle;
        }
        return null;
    }

    public Rectangle findOverLappingRectangle() {
        Path resultJSON = Path.of(IOJSONFile.getParent() + "\\" + JSON_RESULT_FILE_NAME);

        ComputeOverLappingRectangle compute = new ComputeOverLappingRectangle(rectangleList);
        Rectangle rectangle = compute.compute();

        new JSONWriter(resultJSON).write(Collections.singletonList(rectangle), objectParseToJSON); //пишем json result

        return rectangle;
    }

    public List<Rectangle> getRectangleListFromJSON(boolean addToTmpGlobalStorage) {

        List<Rectangle> rectangles = readJSON();
        if (addToTmpGlobalStorage) {
            rectangleList.addAll(rectangles);
            return rectangleList;
        }
        return rectangles;
    }

    public Rectangle getResultFromJSON() {
        Path resultJSON = Path.of(IOJSONFile.getParent() + "\\" + JSON_RESULT_FILE_NAME);
        if (Files.exists(resultJSON)) {
            return new JSONReader(resultJSON).readJSONToList(objectParseToJSON).stream().findFirst().orElse(null);
        } else {
            return null;
        }
    }

    public void clearDataAndJSONFile() {
        writer.clear();
        rectangleList.clear();
    }


}
