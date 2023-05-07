package uet.oop.bomberman;
import uet.oop.bomberman.Sound.Sound;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.Graphics.Sprite;
import uet.oop.bomberman.Input.KeyBoard;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class BombermanGame extends Application {

    public static GraphicsContext gc;
    public static Canvas canvas;
    public static GraphicsContext gcForPlayer;
    public static Scene gameScene;
    private java.util.List<Text> TextList = new ArrayList<>();
    public static Board board;
    public static KeyBoard keyBoard;
    private Text textScore;
    private Text textTime;
    private Text textLevel;
    private Text playerHealth;

    private static final int MENU_WIDTH = 992;
    private static final int MENU_HEIGHT = 480;
    private AnchorPane menuPane;
    private Stage menuStage;
    private Button continueButton;
    private Button startButton;

    public void initContinueGame() throws FileNotFoundException {
        board = new Board();
        keyBoard = new KeyBoard();
        board.getGameLevel().createMapLevel(board.getLevel());
    }

    public void initNewGame() throws FileNotFoundException {
        board = new Board();
        keyBoard = new KeyBoard();
        Board.score = 0;
        Board.getPlayer().setHealth(3);
        board.setLevel(0);
        board.getGameLevel().createMapLevel(0);
        System.out.println("DANG TAO GAME");

    }

    public void createTextScene() {
        Text textS = new Text(30,35,"Score: ");
        Text textT = new Text(230, 35, "Time: ");

        textS.setFill(Color.WHITE);
        textS.setFont(new Font(20));

        textT.setFill(Color.WHITE);
        textT.setFont(new Font(20));

        TextList.add(textS);
        TextList.add(textT);

        textScore = new Text(130, 35, String.valueOf(Board.score + Board.scorePrevious));
        textScore.setFill(Color.WHITE);
        textScore.setFont(new Font(20));

        TextList.add(textScore);

        textTime = new Text(290, 35, String.valueOf(Board.countDownTime / 60));
        textTime.setFill(Color.WHITE);
        textTime.setFont(new Font(20));

        TextList.add(textTime);

        Text textL = new Text(600, 35, "Level: ");
        textL.setFill(Color.WHITE);
        textL.setFont(new Font(20));
        TextList.add(textL);

        textLevel = new Text(670,35, String.valueOf(board.getLevel()));
        textLevel.setFill(Color.WHITE);
        textLevel.setFont(new Font(20));
        TextList.add(textLevel);

        Text textLive = new Text(400, 35, "Live: ");
        textLive.setFill(Color.WHITE);
        textLive.setFont(new Font(20));
        TextList.add(textLive);

        playerHealth = new Text(450, 35, String.valueOf(Board.getPlayer().getHealth()));
        playerHealth.setFill(Color.WHITE);
        playerHealth.setFont(new Font(20));
        TextList.add(playerHealth);

    }

    public void initializeStage() {
        canvas = new Canvas(Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2));
        gc = canvas.getGraphicsContext2D();


        Canvas canvasForPlayer = new Canvas(Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2));
        gcForPlayer = canvasForPlayer.getGraphicsContext2D();

        Group gameRoot = new Group();
        gameRoot.getChildren().add(canvas);
        gameRoot.getChildren().add(canvasForPlayer);

        gameRoot.getChildren().addAll(TextList);

        gameScene = new Scene(gameRoot, Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2), Color.BLACK);

    }

    public void update() {
        playerHealth.setText(String.valueOf(Board.getPlayer().getHealth()));
        textLevel.setText(String.valueOf(board.getLevel()));
        textScore.setText(String.valueOf(Board.score + Board.scorePrevious));
        textTime.setText(String.valueOf(board.countDown() / 60));
    }

    private void createStartButton() {
        InputStream input = getClass().getResourceAsStream("/button/start.png");

        javafx.scene.image.Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        startButton = new Button("", imageView);
        startButton.setStyle("-fx-background-color: rgba(0,0,0,0); ");

        menuPane.getChildren().add(startButton);
        startButton.setLayoutX(430);
        startButton.setLayoutY(350);
    }

    private void createBackGround() {
        Image back = new Image("/background/Background.png", MENU_WIDTH, MENU_HEIGHT, false, true);
        BackgroundImage backMenu = new BackgroundImage(back, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        menuPane.setBackground(new Background(backMenu));
    }

    private Scene gameFinalScene(boolean check) {
        Group gameRoot = new Group();
        Text textOver;
        if(!check)
            textOver = new Text(250, 240, "Game over!");
        else
            textOver = new Text(250, 240, "Win");
        textOver.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        textOver.setFill(Color.WHITE);

        gameRoot.getChildren().add(textOver);
        return new Scene(gameRoot, Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2), Color.BLACK);
    }

    private void createContinueButton() {
        InputStream input = getClass().getResourceAsStream("/button/continue.png");

        javafx.scene.image.Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        continueButton = new Button("", imageView);
        continueButton.setStyle("-fx-background-color: rgba(0,0,0,0); ");

        menuPane.getChildren().add(continueButton);
        continueButton.setLayoutX(406);
        continueButton.setLayoutY(400);
    }


    private void createMenu() {
        menuPane = new AnchorPane();
        Scene menuScene = new Scene(menuPane, MENU_WIDTH, MENU_HEIGHT);
        menuStage = new Stage();
        menuStage.setScene(menuScene);
        createBackGround();
        createContinueButton();
        createStartButton();
    }

    public void start(Stage stage) {
        createMenu();
        stage = menuStage;
        Stage finalStage = stage;
        stage.show();

        startButton.setOnMousePressed(event -> { //thao tác xử lý khi dùng chuột
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                try {
                    initNewGame();
                    createTextScene();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                initializeStage();
                finalStage.setScene(gameScene);
                finalStage.show();

                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long l) {
                        update();
                        board.render();
                        board.update();
                        if(Board.getPlayer().isDie() || Board.countDownTime < 0) {
                            finalStage.setScene(gameFinalScene(Board.getPlayer().isWin()));
                        }
                        if(Board.getPlayer().isWin()) {
                            Board.scorePrevious = 0;
                            finalStage.setScene(gameFinalScene(Board.getPlayer().isWin()));

                        }
                    }
                };

                timer.start();

                keyBoard.status(gameScene); //bat su kien
                Sound.play("Back");
                board.countDown();
            }
        });


        continueButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    try {
                        initContinueGame();
                        createTextScene();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    initializeStage();
                    finalStage.setScene(gameScene);
                    finalStage.show();

                    AnimationTimer timer= new AnimationTimer() {
                        @Override
                        public void handle(long l) {
                            update();
                            board.render();
                            board.update();
                            if(Board.getPlayer().isDie() || Board.countDownTime < 0) {
                                finalStage.setScene(gameFinalScene(Board.getPlayer().isWin()));
                            }
                            if(Board.getPlayer().isWin()) {
                                Board.scorePrevious = 0;
                                BombermanGame.board.setLevel(0);
                                Board.getPlayer().setHealth(3);
                                Board.getPlayer().updateStatus();
                                finalStage.setScene(gameFinalScene(Board.getPlayer().isWin()));
                            }
                        }
                    };

                    timer.start();

                    keyBoard.status(gameScene); // bat su kien
                    Sound.play("Back");
                    board.countDown();
                }

            }
        });

    }
}
