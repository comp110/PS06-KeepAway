package comp110.pieces;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;

import comp110.GameEngine;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 * The Puck is what each of the ships are trying to maintain control of.
 */
public class Puck extends Piece {

  public static final Color COLOR = Color.WHITE;

  private static final double RADIUS = 2.0; // m
  private static final double MASS = 0.5; // kg
  private static final double BOUNCINESS = 0.75; // % bounce back
  private static final double FRICTION = 0.1; // resistance against pieces

  private Circle _puck;

  public Puck(double centerX, double centerY, Color color) {
    super(centerX, centerY, color);
  }

  public void setColor(Color color) {
    super.setColor(color);
    _puck.setFill(color);
    _puck.setStroke(color.darker());
  }

  public Body initPhysics(double centerX, double centerY) {
    Body body = new Body();
    BodyFixture fixture = GameEngine.circle(RADIUS);
    double area = RADIUS * RADIUS;
    double density = MASS / area;
    fixture.setDensity(density);
    fixture.setRestitution(BOUNCINESS);
    fixture.setFriction(FRICTION);
    body.addFixture(fixture);
    body.setMass(MassType.NORMAL);
    body.translate(centerX, centerY);
    return body;
  }

  public Group initGraphics() {
    Group graphics = new Group();
    Circle circle = new Circle();
    circle.setRadius(RADIUS);
    circle.setFill(this.getColor());
    circle.setStroke(this.getColor().darker());
    circle.setStrokeWidth(0.3);
    circle.setStrokeType(StrokeType.INSIDE);
    _puck = circle;
    graphics.getChildren().add(circle);
    return graphics;
  }

}
