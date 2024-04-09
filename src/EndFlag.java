public class EndFlag extends Entity {
    private final double radius;
    public EndFlag(int x, int y, int speed, double radius, String filename) {
        super(x, y, speed, filename);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}


