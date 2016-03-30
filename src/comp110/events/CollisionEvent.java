package comp110.events;

import comp110.pieces.Piece;
import javafx.event.ActionEvent;

/**
 * Collision events occur when one game piece collides with another. This can
 * happen when a ship hits a ship or when a ship hits a puck.
 */

@SuppressWarnings("serial")
public class CollisionEvent extends ActionEvent {

  private Piece _a;
  private Piece _b;

  public CollisionEvent(Piece a, Piece b) {
    super();
    _a = a;
    _b = b;
  }

  public Piece getA() {
    return _a;
  }

  public Piece getB() {
    return _b;
  }

}