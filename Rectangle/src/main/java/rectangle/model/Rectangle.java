package rectangle.model;

public record Rectangle(double x1, double x2, double y1, double y2) {

    @Override
    public boolean equals(Object obj) {
        Rectangle rectangle = (Rectangle) obj;
        return (this.x1() == rectangle.x1() &&
                this.x2() == rectangle.x2() &&
                this.y1() == rectangle.y1() &&
                this.y2() == rectangle.y2());
    }

    @Override
    public String toString() {
        return "x1: " + x1 + ", " + "x2: " + x2 + ", " + "y1: " + y1 + ", " + "y2: " + y2 + "\n";
    }


}
