package rectangle.functional;

import rectangle.model.Rectangle;


import java.util.List;

public class ActionImpl implements ActionCompute {

    private final List<Rectangle> rectangleList;

    public ActionImpl(List<Rectangle> rectangleList) {
        this.rectangleList = rectangleList;
    }


    @Override
    public Rectangle compute() {

        double x1 = 0;
        double x2 = 0;
        double y1 = 0;
        double y2 = 0;


        if (!rectangleList.isEmpty()) {
            Rectangle startRectangle = rectangleList.get(0);
            x1 = startRectangle.x1();
            x2 = startRectangle.x2();
            y1 = startRectangle.y1();
            y2 = startRectangle.y2();

            for (int i = 1; i < rectangleList.size(); i++) {
                Rectangle temp = rectangleList.get(i);
                x1 = Math.max(x1, temp.x1());
                x2 = Math.min(x2, temp.x2());
                y1 = Math.max(y1, temp.y1());
                y2 = Math.min(y2, temp.y2());

                if (!isValid(x1, x2, y1, y2)) {
                    return null;
                }
            }

        }
        return new Rectangle(x1, x2, y1, y2);
    }


    private boolean isValid(double x1, double x2, double y1, double y2) {
        return (x1 <= x2 && y1 <= y2);
    }


}
