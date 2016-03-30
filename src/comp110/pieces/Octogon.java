package comp110.pieces;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.MassType;

import comp110.GameEngine;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * The Octogon piece represents the playing field.
 */
public class Octogon extends Piece {

  public Octogon(double centerX, double centerY, Color color) {
    super(centerX, centerY, color);
  }

  public Group initGraphics() {
    Group graphics = new Group();

    Path wall = new Path();
    wall.getElements().add(new MoveTo(42.0, 0.0));
    wall.getElements().add(new LineTo(18.0, 0.0));
    wall.getElements().add(new LineTo(0.0, 18.0));
    wall.getElements().add(new LineTo(0.0, 42.0));
    wall.getElements().add(new LineTo(18.0, 60.0));
    wall.getElements().add(new LineTo(42.0, 60.0));
    wall.getElements().add(new LineTo(60.0, 42.0));
    wall.getElements().add(new LineTo(60.0, 18.0));
    wall.getElements().add(new ClosePath());
    wall.setStroke(Color.BLACK);
    wall.setStrokeLineJoin(StrokeLineJoin.ROUND);
    wall.setFill(Color.gray(0.2));
    wall.setStrokeWidth(2.0);
    wall.setStrokeType(StrokeType.OUTSIDE);
    graphics.getChildren().add(wall);

    Text hashtag = new Text("#comp110");
    hashtag.setFont(Font.font("Verdana", FontWeight.NORMAL, 6.0));
    hashtag.setFill(Color.WHITE);
    hashtag.setTranslateX(13.0);
    hashtag.setTranslateY(50.0);
    hashtag.setOpacity(0.2);
    graphics.getChildren().add(hashtag);

    return graphics;
  }

  public Body initPhysics(double centerX, double centerY) {
    Body body = new Body();
    body.addFixture(GameEngine.segment(42.0, 0.0, 18.0, 0.0));
    body.addFixture(GameEngine.segment(18.0, 0.0, 0.0, 18.0));
    body.addFixture(GameEngine.segment(0.0, 18.0, 0.0, 42.0));
    body.addFixture(GameEngine.segment(0.0, 42.0, 18.0, 60.0));
    body.addFixture(GameEngine.segment(18.0, 60.0, 42.0, 60.0));
    body.addFixture(GameEngine.segment(42.0, 60.0, 60.0, 42.0));
    body.addFixture(GameEngine.segment(60.0, 42.0, 60.0, 18.0));
    body.addFixture(GameEngine.segment(60.0, 18.0, 42.0, 0.0));
    body.setMass(MassType.INFINITE);
    return body;
  }

}
