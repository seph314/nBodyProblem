public class Quad {
    private double x;
    private double y;
    private double sideLength;

    public Quad(double x, double y, double sideLength) {
        this.x = x;
        this.y = y;
        this.sideLength = sideLength;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    public boolean contains(double x, double y) {
        if (x <= this.x + sideLength / 2.0 &&
                x >= this.x - sideLength / 2.0	&&
                y <= this.y + sideLength / 2.0 &&
                y >= this.y - sideLength / 2.0)
            return true;
        return false;
    }

    public boolean contains(Body b) {
        return contains(b.getPositionX(), b.getPositionY());
    }

    public Quad NW() {
        return new Quad(
                x - sideLength / 4.0,
                y + sideLength / 4.0,
                sideLength / 2.0);
    }

    public Quad NE() {
        return new Quad(
                x + sideLength / 4.0,
                y + sideLength / 4.0,
                sideLength / 2.0);
    }

    public Quad SW() {
        return new Quad(
                x - sideLength / 4.0,
                y - sideLength / 4.0,
                sideLength / 2.0);
    }
    public Quad SE() {
        return new Quad(
                x + sideLength / 4.0,
                y - sideLength / 4.0,
                sideLength / 2.0);
    }






}
