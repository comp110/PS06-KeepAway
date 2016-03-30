package comp110.pieces;

import org.dyn4j.dynamics.Body;

import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * Each game Piece has two representations: a physical representation and a
 * graphical representation. We are providing the game pieces for you. If you'd
 * like to modify the graphics or the physics of them for your game, feel
 * encouraged to.
 * 
 * You should not need to modify any code here for grading purposes.
 * 
 * Pieces make use of some advance object-oriented programming concepts like
 * inheritance and polymorphism. You'll learn more about these in 401.
 */
public class Piece {

  public final static double RAD_TO_DEG = 57.2958;
  public final static double DEG_TO_RAD = 1 / 57.2958;

  private Group _graphics;
  private Body _physics;
  private Color _color;

  public Piece(double centerX, double centerY, Color color) {
    _color = color;
    _physics = this.initPhysics(centerX, centerY);
    _graphics = this.initGraphics();
    this.updateGraphics();
  }

  public void updateGraphics() {
    Body physics = this.getPhysics();
    Group graphics = this.getGraphics();
    graphics.setTranslateX(physics.getTransform().getTranslationX());
    graphics.setTranslateY(physics.getTransform().getTranslationY());
    graphics.setRotate(physics.getTransform().getRotation() * RAD_TO_DEG);
  }

  /*
   * In 401 you'll learn that the following methods should be marked as
   * "abstract" and the class should be abstract, as well. They should also be
   * "protected" rather than "public". This is a minor detail at this stage,
   * however. By declaring them here and then overriding them in the subclasses
   * we can call them in the Piece constructor.
   */
  public Group initGraphics() {
    return null;
  }

  public Body initPhysics(double centerX, double centerY) {
    return null;
  }

  public Color getColor() {
    return _color;
  }

  public void setColor(Color color) {
    _color = color;
  }

  public Group getGraphics() {
    return _graphics;
  }

  public Body getPhysics() {
    return _physics;
  }

}
