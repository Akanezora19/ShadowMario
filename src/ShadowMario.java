import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2024
 * Please enter your name below
 * @LiamZefuLi
 */
public class ShadowMario extends AbstractGame {
    private final Image BACKGROUND_IMAGE;
    private boolean gameStart;
    private boolean gameEnd;
    private boolean gameWin;
    private Player player;
    private EndFlag endFlag;
    private Platform platform;
    private ArrayList<Coin> coins = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    // Messages
    private final String TITLE_MESSAGE;
    private final String START_MESSAGE;
    private final String SCORE_MESSAGE;
    private final String HEALTH_MESSAGE;
    private final String END_MESSAGE;
    private final String WON_MESSAGE;
    // Font and their respective Font size use for messages
    private final Font TITLE_FONT;
    private final Font START_FONT;
    private final Font SCORE_FONT;
    private final Font HEALTH_FONT;
    private final Font END_FONT;
    private final Font WON_FONT;

    /**
     * The constructor
     */
    public ShadowMario(Properties game_props, Properties message_props) {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
              Integer.parseInt(game_props.getProperty("windowHeight")),
              message_props.getProperty("title"));

        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));

        // you can initialise other values from the property files here
        platform = new Platform(3000,745 ,
                Integer.parseInt(game_props.getProperty("gameObjects.platform.speed")),
                game_props.getProperty("gameObjects.platform.image"));

        TITLE_MESSAGE = message_props.getProperty("title");
        START_MESSAGE = message_props.getProperty("instruction");
        SCORE_MESSAGE = message_props.getProperty("score");
        HEALTH_MESSAGE = message_props.getProperty("health");
        END_MESSAGE = message_props.getProperty("gameOver");
        WON_MESSAGE = message_props.getProperty("gameWon");

        TITLE_FONT = new Font(game_props.getProperty("font"),
                                Integer.parseInt(game_props.getProperty("title.fontSize")));
        START_FONT = new Font(game_props.getProperty("font"),
                                Integer.parseInt(game_props.getProperty("instruction.fontSize")));
        SCORE_FONT = new Font(game_props.getProperty("font"),
                                Integer.parseInt(game_props.getProperty("score.fontSize")));
        HEALTH_FONT = new Font(game_props.getProperty("font"),
                                Integer.parseInt(game_props.getProperty("health.fontSize")));
        END_FONT = new Font(game_props.getProperty("font"),
                                Integer.parseInt(game_props.getProperty("message.fontSize")));
        WON_FONT = new Font(game_props.getProperty("font"),
                                Integer.parseInt(game_props.getProperty("message.fontSize")));

        readCSV(game_props);
        gameStart = false;
        gameEnd = false;
        gameWin = false;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");

        ShadowMario game = new ShadowMario(game_props, message_props);
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        // close window
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        if (!gameStart) drawStartScreen(input);
        if (gameEnd) drawEndScreen(input);
        if (gameWin) drawWonScreen(input);

        if (gameStart && !gameEnd && !gameWin) {
            for (Coin coin: coins) coin.update(input);
            for (Enemy enemy: enemies) enemy.update(input);

            endFlag.update(input);
            player.update(input, enemies, coins, endFlag);
            platform.update(input);

            if (player.isWon()) gameWin = true;
            if (player.isDead()) gameEnd = true;
        }
    }

    private void drawStartScreen(Input input) {
        START_FONT.drawString(START_MESSAGE, 220, 250); // magic num
        if (input.wasPressed(Keys.SPACE)) {
            gameStart = true;
        }
    }

    private void drawEndScreen(Input input) {
        END_FONT.drawString(END_MESSAGE, 220, 250);
        if (input.wasPressed(Keys.SPACE)) {
            resetGame();
        }
    }
    private void drawWonScreen(Input input) {
        WON_FONT.drawString(WON_MESSAGE, 220, 250);
        if (input.wasPressed(Keys.SPACE)) {
            resetGame();
        }
    }

    private void resetGame() {
        gameStart = false;
        gameEnd = false;
        gameWin = false;
    }

    private void readCSV(Properties game_props) {
        String filename = game_props.getProperty("levelFile");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] sections = line.split(",");
                if (sections[0].equals("PLAYER")) {
                    player = new Player(Integer.parseInt(sections[1]),
                            Integer.parseInt(sections[2]),
                            Double.parseDouble(game_props.getProperty("gameObjects.player.radius")),
                            Double.parseDouble(game_props.getProperty("gameObjects.player.health")),
                            game_props.getProperty("gameObjects.player.imageRight"),
                            game_props.getProperty("gameObjects.player.imageLeft")
                    );
                }
                if (sections[0].equals("COIN")) {
                    coins.add(new Coin(Integer.parseInt(sections[1]),
                            Integer.parseInt(sections[2]),
                            Integer.parseInt(game_props.getProperty("gameObjects.coin.speed")),
                            game_props.getProperty("gameObjects.coin.image"),
                            Integer.parseInt(game_props.getProperty("gameObjects.coin.value")),
                            Integer.parseInt(game_props.getProperty("gameObjects.coin.coinCount")),
                            0,
                            true,
                            false,
                            Double.parseDouble(game_props.getProperty("gameObjects.coin.radius"))
                    ));
                }
                if (sections[0].equals("ENEMY")) {
                    enemies.add(new Enemy(Integer.parseInt(sections[1]),
                            Integer.parseInt(sections[2]),
                            Integer.parseInt(game_props.getProperty("gameObjects.enemy.speed")),
                            game_props.getProperty("gameObjects.enemy.image")
                    ));
                }
                if (sections[0].equals("END_FLAG")) {
                    endFlag = new EndFlag(Integer.parseInt(sections[1]),
                            Integer.parseInt(sections[2]),
                            Integer.parseInt(game_props.getProperty("gameObjects.endFlag.speed")),
                            Double.parseDouble(game_props.getProperty("gameObjects.endFlag.radius")),
                            game_props.getProperty("gameObjects.endFlag.image")
                    );
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
