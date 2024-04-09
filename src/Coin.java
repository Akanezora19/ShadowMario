import bagel.*;
public class Coin extends Entity {

    private int value;
    private int coinCount;
    private int verticalSpeed;
    private boolean isAppear;
    private boolean isScored;
    private final double radius;

    public Coin(int x, int y, int speed, String filename, int value, int coinCount, int verticalSpeed,
                boolean isAppear, boolean isScored, double radius) {
        super(x, y, speed, filename);
        this.value = value;
        this.coinCount = coinCount;
        this.verticalSpeed = 0;
        this.isAppear = true;
        this.isScored = false;
        this.radius = radius;
    }

    public int getValue() {
        return value;
    }

    public boolean getIsAppear() {
        return isAppear;
    }

    public boolean getIsScored() {
        return isScored;
    }

    public double getRadius() {
        return radius;
    }

    public void setVerticalSpeed(int verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public void setIsAppear(boolean appear) {
        isAppear = appear;
    }

    public void setIsScored(boolean scored) {
        isScored = scored;
    }
    @Override
    public void update(Input input) {
        if (isAppear) {
            this.setY(this.getY() + verticalSpeed);
            super.update(input);
        }
        if (this.getY() <= 0) {
            this.setIsAppear(false);
        }
    }
}
