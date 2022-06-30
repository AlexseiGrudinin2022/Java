package rectangle.model;

import static sun.swing.MenuItemLayoutHelper.max;

public class Rectangle {


    private double x1;
    private double x2;
    private double y1;
    private double y2;

    public Rectangle(double x1, double x2, double y1, double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }


    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Rectangle rectangle = (Rectangle) obj;

        return (this.x1 == rectangle.x1 &&
                this.x2 == rectangle.x2 &&
                this.y1 == rectangle.y1 &&
                this.y2 == rectangle.y2);
    }

    @Override
    public String toString() {
        return "x1: " + x1 + ", " + "x2: " + x2 + ", " + "y1: " + y1 + ", " + "y2: " + y2 + "\n";
    }


}
