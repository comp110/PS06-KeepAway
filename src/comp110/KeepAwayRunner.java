package comp110;

import comp110.events.TickEvent;
import comp110.pieces.Ship;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Our game begins running in this class. It is responsible for setting up the
 * JavaFX window, creating a new instance of our Game class, and hooking up our
 * graphics.
 * 
 * The only modifications you will need to make in this class are hooking up
 * event handlers for key presses/releases.
 */
public class KeepAwayRunner extends Application {

  private Stage _stage;
  private GameEngine _gameEngine;
  private Game _game;
  private Button _restart;
  private Label _homeScore;
  private Label _awayScore;

  public static void main(String[] args) {
    Application.launch();
  }

  public void start(Stage stage) {
    _stage = stage;
    _stage.setTitle("COMP110 Keep Away");
    _stage.show();
    this.newGame();
  }

  private void handleTick(TickEvent event) {
    _awayScore.setText(String.format("%.1f", _game.getAwayTime()));
    _homeScore.setText(String.format("%.1f", _game.getHomeTime()));
  }

  private void handleRestart(ActionEvent event) {
    this.newGame();
  }

  private void newGame() {
    _gameEngine = new GameEngine();
    _game = new Game(new GameEngine());
    _stage.setScene(this.initScene());
    _gameEngine.setOnTick(this::handleTick);
    _gameEngine.play();
  }

  private Scene initScene() {
    VBox stack = new VBox(8.0);

    Group graphics = _gameEngine.getGraphics();
    stack.getChildren().add(this.initScoreboard(graphics));
    stack.getChildren().add(graphics);

    Scene scene = new Scene(stack);
    // TODO: Part 2.3

    return scene;
  }

  private Pane initScoreboard(Group graphics) {
    Pane scoreboard = new Pane();

    _awayScore = this.initLabel(Ship.AWAY_COLOR, 20.0);
    scoreboard.getChildren().add(_awayScore);

    _homeScore = this.initLabel(Ship.HOME_COLOR, 400.0);
    scoreboard.getChildren().add(_homeScore);

    _restart = new Button("Restart");
    _restart.setOnAction(this::handleRestart);

    _restart.setLayoutX((graphics.getLayoutBounds().getWidth() - 60.0) / 2.0);
    _restart.setLayoutY(8.0);
    scoreboard.getChildren().add(_restart);

    return scoreboard;
  }

  private Label initLabel(Color color, double offsetX) {
    Label label = new Label("0.0");
    label.setTextFill(color);
    label.setFont(new Font("sans", 48.0));
    label.setLayoutX(offsetX);
    return label;
  }

}