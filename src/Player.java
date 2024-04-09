import bagel.*;

import java.util.ArrayList;

public class Player {
    private int x;
    private int y;
    private final int onPlatformY;
    private Image currentImage;
    private final Image faceRight;
    private Image faceLeft;
    private double radius;
    private double health;
    private int verticalSpeed;
    private boolean isJump;
    private boolean won;
    private int score;
    private final int coinCollisionSpeed = -10;
    private final int FONT_SIZE = 30; // score.fontSize replace with 30 (no hardcode)
    private final Font font = new Font("res/FSO8BITR.TTF", FONT_SIZE); // score.fontSize replace with 30 (no hardcode), also filename

    public Player(int x, int y, double radius, double health, String imageRight, String imageLeft) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.health = health;
        this.faceRight = new Image(imageRight);
        this.faceLeft = new Image(imageLeft);
        this.currentImage = faceRight;
        this.onPlatformY = y;
        this.isJump = false;
        this.verticalSpeed = 0;
        this.score = 0;
        this.won = false;
    }
    // two array enemies and coins
    public void update(Input input, ArrayList<Enemy> enemies, ArrayList<Coin> coins, EndFlag endFlag) {
        currentImage.draw(this.x, this.y);
        if (input.isDown(Keys.UP) && !isJump && health > 0) {
            isJump = true;
            verticalSpeed = -20;
        }
        jump();
        if (input.isDown(Keys.LEFT)) {
            currentImage = faceLeft;
        }
        if (input.isDown(Keys.RIGHT)) {
            currentImage = faceRight;
        }
        renderScorePoints();
        renderHealth();
        checkCollision(enemies, coins, endFlag);
    }

    public void jump() {
        if (isJump) {
            verticalSpeed++;
            this.y += verticalSpeed;
            // make sure the player is on the platform by comparing the y coordinates
            if (this.y >= onPlatformY) {
                this.y = onPlatformY;
                isJump = false;
                verticalSpeed = 0;
            }
        }
    }

    public void renderHealth() {
        font.drawString("Health" + health, 750, 35);
    }

    public void renderScorePoints() {
        font.drawString("Score" + score, 35, 35); // replace hardcode with app properties
    }

    private void checkCollision(ArrayList<Enemy> enemies, ArrayList<Coin> coins, EndFlag endFlag) {
        for (Enemy enemy : enemies) {
            if(enemy.isAppear()) {
                double distance = calculateDistance(this.x, this.y, enemy.getX(), enemy.getY());
                double range = this.radius + enemy.getRadius();
                if (distance <= range) {
                    this.health = enemy.getDamageSize();
                    enemy.setAppear(false);
                }
            }
        }

        for (Coin coin: coins) {
            if (coin.getIsAppear()) {
                double distance = calculateDistance(this.x, this.y, coin.getX(), coin.getY());
                double range = this.radius + coin.getRadius();
                if (distance <= range) {
                    if (!coin.getIsScored()) {
                        this.score += coin.getValue();
                        coin.setIsScored(true);
                    }
                    coin.setVerticalSpeed(coinCollisionSpeed);
                }
            }
        }
        double distance = calculateDistance(this.x, this.y, endFlag.getX(), endFlag.getY());
        double range = this.radius + endFlag.getRadius();
        if (distance <= range) {
            this.won = true;
        }
    }

    private double calculateDistance(int playerX, int playerY, int enemyX, int enemyY) {
        double distanceX = (playerX - enemyX) * (playerX - enemyX);
        double distanceY = (playerY - enemyY) * (playerY - enemyY);
        return Math.sqrt(distanceX + distanceY);
    }

    public boolean isWon() {
        return won;
    }

    public boolean isDead() {
        if (health <= 0) {
            verticalSpeed = 2;
            this.y += verticalSpeed;
            return this.y > 768;
        }
        return false;
    }

}
