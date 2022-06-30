package rectangle.UIForm.eventsForm;

import rectangle.JSON.JSONReader;
import rectangle.JSON.JSONWriter;
import rectangle.functional.ComputeOverLappingRectangle;
import rectangle.model.Rectangle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public final class EventsApplication {


    private final JSONWriter writer;
    private final JSONReader reader;
    private final List<Rectangle> rectangleList;
    private final String objectParseToJSON;

    private final String IOJSONFile;
    private final String IOResultFile;



    public EventsApplication(String IOJSONFile_, String IOResultFile, String objectParseToJSON) {


            this.IOJSONFile = IOJSONFile_;
            this.IOResultFile = IOResultFile;



        writer = new JSONWriter(IOJSONFile);
        reader = new JSONReader(IOJSONFile);
        rectangleList = new ArrayList<Rectangle>();
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
            return null;
        }

        Rectangle rectangle = new Rectangle(x1Coordinate, x2Coordinate, y1Coordinate, y2Coordinate);
        rectangleList.add(rectangle);
        writeJSON();
        return rectangle;

    }

    public boolean findOverLappingRectangle() {

        ComputeOverLappingRectangle compute = new ComputeOverLappingRectangle(rectangleList, IOResultFile);
        return compute.compute(objectParseToJSON);

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
        File file = new File(IOResultFile);

        if (file.exists()) {
            return new JSONReader(IOResultFile).readJSONToList(objectParseToJSON).get(0);
        } else {
            return null;
        }
    }

    public void clearDataAndJSONFile() {
        writer.clear();
        rectangleList.clear();
    }


}
