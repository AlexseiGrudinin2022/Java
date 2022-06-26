package rectangle.functional;

import rectangle.JSON.JSONWriter;
import rectangle.model.Rectangle;


import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class ComputeOverLappingRectangle implements RectangleFunctionality {

    private final List<Rectangle> rectangleList;
    private final Path pathResultFile;

    public ComputeOverLappingRectangle(List<Rectangle> rectangleList, Path pathResultFile) {
        this.rectangleList = rectangleList;
        this.pathResultFile = pathResultFile;
    }

    @Override
    public boolean compute(String objectParseToJSON) {

        JSONWriter writer = new JSONWriter(pathResultFile);

        if (rectangleList.size() > 1) {

            Rectangle startRectangle = rectangleList.get(0);
            double x1 = startRectangle.getX1();
            double x2 = startRectangle.getX2();
            double y1 = startRectangle.getY1();
            double y2 = startRectangle.getY2();

            for (int i = 1; i < rectangleList.size(); i++) {
                Rectangle temp = rectangleList.get(i);
                x1 = Math.max(x1, temp.getX1());
                x2 = Math.min(x2, temp.getX2());
                y1 = Math.max(y1, temp.getY1());
                y2 = Math.min(y2, temp.getY2());

                if (!(x1 < x2 && y1 < y2)) {
                    writer.write(objectParseToJSON);
                    return false;
                }
            }
            writer.write(
                    Collections.singletonList(new Rectangle(x1, x2, y1, y2)),
                    objectParseToJSON);
            return true;
        }
        writer.write(objectParseToJSON);
        return false;


    }


}
