import bagel.*;

public class Platform extends Entity {
    public Platform(int x, int y, int speed, String filename) {
        super(x, y, speed, filename);
    }


    public void update(Input input) {
        if (input.isDown(Keys.LEFT) && this.getX() < 3000) {
            this.setX(this.getX() + this.speed);
        }
        if (input.isDown(Keys.RIGHT)) {
            this.setX(this.getX() - this.speed);
        }
        getImage().draw(this.x, this.y);
    }
}
