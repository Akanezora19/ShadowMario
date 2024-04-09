import bagel.*;

public class Entity {
    public int x;
    public int y;
    public int speed;
    public Image image;

    public Entity(int x, int y, int speed, String filename) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.image = new Image(filename);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void update(Input input) {
        if (input.isDown(Keys.LEFT)) {
            this.setX(this.getX() + this.speed);
        }
        if (input.isDown(Keys.RIGHT)) {
            this.setX(this.getX() - this.speed);
        }
        image.draw(this.x, this.y);
    }
}
