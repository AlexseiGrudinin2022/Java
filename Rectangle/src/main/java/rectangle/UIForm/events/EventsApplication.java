package rectangle.UIForm.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import rectangle.JSON.JSONReader;
import rectangle.JSON.JSONWriter;
import rectangle.functional.ComputeOverLappingRectangle;
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
    private Path IOResultFile;

    private final Logger logger = (Logger) LogManager.getRootLogger();


    public EventsApplication(String IOJSONFile_, String IOResultFile, String objectParseToJSON) {

        try {
            this.IOJSONFile = Paths.get(IOJSONFile_);
            this.IOResultFile = Paths.get(IOResultFile);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
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

        double x1Coordinate, x2Coordinate, y1Coordinate, y2Coordinate;

        try {

            x1Coordinate = Double.parseDouble(x1);
            x2Coordinate = Double.parseDouble(x2);
            y1Coordinate = Double.parseDouble(y1);
            y2Coordinate = Double.parseDouble(y2);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }


        Rectangle rectangle = new Rectangle(x1Coordinate, x2Coordinate, y1Coordinate, y2Coordinate);
        rectangleList.add(rectangle);
        writeJSON();
        return rectangle;

    }

    public Rectangle findOverLappingRectangle() {

        ComputeOverLappingRectangle compute = new ComputeOverLappingRectangle(rectangleList);
        Rectangle rectangle = compute.compute();

        new JSONWriter(IOResultFile).write(Collections.singletonList(rectangle), objectParseToJSON); //пишем json result

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
        if (Files.exists(IOResultFile)) {
            return new JSONReader(IOResultFile).readJSONToList(objectParseToJSON).stream().findFirst().orElse(null);
        } else {
            return null;
        }
    }

    public void clearDataAndJSONFile() {
        writer.clear();
        rectangleList.clear();
    }


}
