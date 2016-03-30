package comp110;

import java.util.ArrayList;
import java.util.HashMap;

import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionAdapter;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Segment;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import comp110.events.CollisionEvent;
import comp110.events.TickEvent;
import comp110.pieces.Octogon;
import comp110.pieces.Piece;
import comp110.pieces.Ship;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * The GameEngine is responsible for setting up the physics and graphics
 * elements of the game.
 * 
 * You should not need to modify anything in this class.
 */
public class GameEngine extends CollisionAdapter {

  private static final double FRAME_LENGTH = 1.0 / 60.0;

  private World _world;
  private EventHandler<CollisionEvent> _collisionHandler;
  private ArrayList<EventHandler<TickEvent>> _tickHandlers;

  private ArrayList<Piece> _pieces;
  private HashMap<Body, Piece> _bodyToPieceMap;

  private Timeline _timeline;
  private boolean _running;

  public GameEngine() {
    _bodyToPieceMap = new HashMap<Body, Piece>();
    _pieces = new ArrayList<Piece>();
    _world = this.initWorld();
    this.addPiece(new Octogon(0.0, 0.0, Color.BLACK));
    _timeline = this.initTimeline();
    _running = false;
    _tickHandlers = new ArrayList<EventHandler<TickEvent>>();
  }

  private World initWorld() {
    World world = new World();
    world.setGravity(new Vector2(0.0, 0.0));
    world.addListener(this);
    return world;
  }

  private Timeline initTimeline() {
    Timeline timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);

    KeyFrame interval =
        new KeyFrame(Duration.seconds(FRAME_LENGTH), this::tick);
    timeline.getKeyFrames().add(interval);

    return timeline;
  }

  public boolean isRunning() {
    return _running;
  }

  public void play() {
    _timeline.play();
    _running = true;
  }

  public void stop() {
    _timeline.stop();
    _running = false;
  }

  /**
   * This is where we progress the game state by one frame. It will be called
   * around 60 times per second. At each stage we are letting the dyn4j physics
   * library run the calculations on each of its bodies. We then apply any
   * thrust and torque being applied to each ship. Finally we notify any
   * listeners that the game has progressed by one tick. In COMP401 you will
   * learn about the observer/observable pattern which is at play here.
   */
  public void tick(ActionEvent e) {
    _world.update(FRAME_LENGTH);

    for (Piece piece : _pieces) {
      if (piece instanceof Ship) {

        Ship ship = (Ship) piece;
        double thrust = ship.getThrust();
        double torque = ship.getTorque();
        Transform transform = ship.getPhysics().getTransform();
        double rotation = transform.getRotation();

        Vector2 thrustV;
        if (thrust != 0.0) {
          thrustV = new Vector2(rotation).product(thrust * 100.0);
        } else {
          // Slow down the ship if player is not thrusting
          thrustV = ship.getPhysics().getLinearVelocity().product(-5.0);
        }
        ship.getPhysics().applyForce(thrustV);

        if (torque == 0.0) {
          // Slow down the spin if player is not torquing
          torque = ship.getPhysics().getAngularVelocity() * -0.5;
        }

        ship.getPhysics().applyTorque(torque * 100.0);
      }
      piece.updateGraphics();
    }

    if (_tickHandlers != null) {
      for (EventHandler<TickEvent> handler : _tickHandlers) {
        handler.handle(new TickEvent(FRAME_LENGTH));
      }
    }
  }

  public void setOnTick(EventHandler<TickEvent> handler) {
    _tickHandlers.add(handler);
  }

  public void addPiece(Piece piece) {
    Body body = piece.getPhysics();
    _world.addBody(body);
    _bodyToPieceMap.put(body, piece);
    _pieces.add(piece);
    piece.updateGraphics();
  }

  public boolean collision(Body bodyA, BodyFixture fixtureA, Body bodyB,
      BodyFixture fixtureB, Penetration penetration) {
    if (_collisionHandler != null) {
      Piece a = _bodyToPieceMap.get(bodyA);
      Piece b = _bodyToPieceMap.get(bodyB);
      CollisionEvent event = new CollisionEvent(a, b);
      _collisionHandler.handle(event);
    }
    return true;
  }

  public void setOnCollision(EventHandler<CollisionEvent> handler) {
    _collisionHandler = handler;
  }

  public Group getGraphics() {
    Group container = new Group();

    Group gameBoard = new Group();
    for (Piece piece : _pieces) {
      gameBoard.getChildren().add(piece.getGraphics());
    }
    Bounds bounds = gameBoard.getChildren().get(0).getLayoutBounds();
    gameBoard.setTranslateX(bounds.getWidth() * 3.5);
    gameBoard.setTranslateY(bounds.getHeight() * 3.5);
    gameBoard.setScaleX(8.0);
    gameBoard.setScaleY(8.0);
    container.getChildren().add(gameBoard);

    return container;
  }

  /* Static Utility Methods */

  public static Segment segment(double x1, double y1, double x2, double y2) {
    return new Segment(new Vector2(x1, y1), new Vector2(x2, y2));
  }

  public static BodyFixture circle(double radius) {
    Circle circle = new Circle(radius);
    BodyFixture fixture = new BodyFixture(circle);
    return fixture;
  }

}