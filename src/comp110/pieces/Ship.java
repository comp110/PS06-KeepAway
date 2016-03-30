package comp110.pieces;

import static java.lang.Math.abs;
import static java.lang.Math.min;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;

import comp110.GameEngine;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;

/**
 * The ships are the user controlled pieces of the game. Feel free to change the
 * design of the ship if you'd like.
 */
public class Ship extends Piece {

  /* Constants */

  public static final Color HOME_COLOR = Color.CORNFLOWERBLUE;
  public static final Color AWAY_COLOR = Color.DARKSEAGREEN;

  private static final double RADIUS = 3.0; // m
  private static final double MASS = 1.0; // kg
  private static final double BOUNCINESS = 0.75; // % bounce back
  private static final double FRICTION = 0.1; // resistance against other pieces
  private static final double START_ANGLE = 90.0;

  // RR: Rear Right
  // RL: Rear Left
  // FR: Front Right
  // FL: Front Left
  private Rectangle _flameFR, _flameRR, _flameFL, _flameRL;

  private double _thrust, _torque;

  public Ship(double centerX, double centerY, Color color) {
    super(centerX, centerY, color);
    _thrust = 0.0;
    _torque = 0.0;
  }

  public void setThrust(double thrust) {
    _thrust = Math.max(-1.0, Math.min(1.0, thrust));
  }

  public double getThrust() {
    return _thrust;
  }

  public void setTorque(double torque) {
    _torque = Math.max(-1.0, Math.min(1.0, torque));
  }

  public double getTorque() {
    return _torque;
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
    body.rotate(Piece.DEG_TO_RAD * START_ANGLE * -1.0);
    body.translate(centerX, centerY);
    return body;
  }

  /*
   * If we are thrusting or torquing we will conditionally show the flames being
   * emitted from the correct engines.
   */
  public void updateGraphics() {
    super.updateGraphics();
    if (_thrust > 0.0 || _torque < 0.0) {
      _flameRR.setOpacity(min(1.0, abs(_thrust) + abs(_torque)));
    } else {
      _flameRR.setOpacity(0.0);
    }

    if (_thrust > 0.0 || _torque > 0.0) {
      _flameRL.setOpacity(min(1.0, abs(_thrust) + abs(_torque)));
    } else {
      _flameRL.setOpacity(0.0);
    }

    if (_thrust < 0.0 || _torque > 0.0) {
      _flameFR.setOpacity(min(1.0, abs(_thrust) + abs(_torque)));
    } else {
      _flameFR.setOpacity(0.0);
    }

    if (_thrust < 0.0 || _torque < 0.0) {
      _flameFL.setOpacity(min(1.0, abs(_thrust) + abs(_torque)));
    } else {
      _flameFL.setOpacity(0.0);
    }
  }

  /*
   * The following code is responsible for creating the graphics of a ship. It's
   * broken down into a few different methods not too dissimilar from how we
   * constructed emoji.
   */
  public Group initGraphics() {
    Group graphics = new Group();

    Circle hull = this.initHull(RADIUS);

    double engineOffset = RADIUS * 0.7;

    Rectangle leftEngine = this.initEngine(RADIUS);
    leftEngine.setTranslateY(engineOffset);
    leftEngine.setTranslateX(leftEngine.getWidth() / -2.0);

    Rectangle rightEngine = this.initEngine(RADIUS);
    rightEngine.setTranslateY(-1.0 * engineOffset - rightEngine.getHeight());
    rightEngine.setTranslateX(rightEngine.getWidth() / -2.0);

    double engineWidth = leftEngine.getWidth();
    double engineHeight = leftEngine.getHeight();

    _flameRR = this.initFlame(RADIUS);
    double flameWidth = _flameRR.getWidth();
    double flameHeight = _flameRR.getHeight();
    double flameYOffset = (engineHeight - flameHeight) / 2.0;
    _flameRR.setTranslateX(-1.0 * engineWidth / 2.0 - flameWidth - 0.1);
    _flameRR.setTranslateY(engineOffset + flameYOffset);

    _flameRL = this.initFlame(RADIUS);
    _flameRL.setTranslateX(-1.0 * engineWidth / 2.0 - flameWidth - 0.1);
    _flameRL.setTranslateY(-1.0 * (engineOffset + engineHeight - flameYOffset));

    _flameFR = this.initFlame(RADIUS);
    _flameFR.setTranslateX(engineWidth / 2.0 + 0.1);
    _flameFR.setTranslateY(engineOffset + flameYOffset);

    _flameFL = this.initFlame(RADIUS);
    _flameFL.setTranslateX(engineWidth / 2.0 + 0.1);
    _flameFL.setTranslateY(-1.0 * (engineOffset + engineHeight - flameYOffset));

    Group windows = this.initWindows(RADIUS);

    graphics.getChildren().addAll(hull, leftEngine, rightEngine, _flameRR,
        _flameRL, _flameFR, _flameFL, windows);
    return graphics;
  }

  private Circle initHull(double radius) {
    Circle circle = new Circle();
    circle.setRadius(radius);
    circle.setFill(this.getColor());
    circle.setStroke(this.getColor().darker());
    circle.setStrokeWidth(0.2);
    circle.setStrokeType(StrokeType.INSIDE);
    return circle;
  }

  private Rectangle initEngine(double radius) {
    Rectangle engine = new Rectangle(radius * 1.5, radius * 0.4);
    engine.setFill(this.getColor().darker());
    engine.setArcHeight(engine.getHeight());
    engine.setArcWidth(engine.getHeight());
    return engine;
  }

  private Rectangle initFlame(double radius) {
    Rectangle flame = new Rectangle(radius * 0.6, radius * 0.2);
    flame.setFill(Color.color(0.89, 0.35, 0.13, 1.0));
    flame.setOpacity(0.0);
    flame.setArcHeight(10.0);
    flame.setArcWidth(2.0);
    return flame;
  }

  private Group initWindows(double radius) {
    Group group = new Group();

    Color windowColor = this.getColor().darker().darker();
    double windowRadius = radius * 0.7;

    Arc window = new Arc(0.0, 0.0, windowRadius, windowRadius, -30.0, 60.0);
    window.setType(ArcType.OPEN);
    window.setStroke(windowColor);
    window.setFill(Color.TRANSPARENT);
    window.setStrokeWidth(0.8);
    window.setStrokeLineCap(StrokeLineCap.ROUND);
    group.getChildren().addAll(window);

    return group;
  }

}