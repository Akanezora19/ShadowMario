import bagel.*;
public class Enemy extends Entity {
    private double radius;
    private double damageSize;
    private int enemyCount;
    private final int speed;
    private boolean isAppear;

    public Enemy(int x, int y, int speed, String filename) {
        super(x, y, speed, filename);
        this.radius = radius;
        this.damageSize = damageSize;
        this.enemyCount = enemyCount;
        this.speed = speed;
        this.isAppear = true;
    }

    public double getRadius() {
        return radius;
    }

    public double getDamageSize() {
        return damageSize;
    }

    public boolean isAppear() {
        return isAppear;
    }

    public void setAppear(boolean appear) {
        isAppear = appear;
    }

    public void update(Input input) {
        if (isAppear) {
            super.update(input);
        }
    }
}
