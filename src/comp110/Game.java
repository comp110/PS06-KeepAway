package comp110;

/*
* Author: YOUR NAME
*
* ONYEN: YOUR ONYEN
*
* Collaborators: ONYENS
*
* UNC Honor Pledge: I certify that no unauthorized assistance has been 
* received or given in the completion of this work.
*/

import comp110.events.CollisionEvent;
import comp110.events.TickEvent;
import comp110.pieces.Puck;
import comp110.pieces.Ship;

/**
 * The Game class is responsible for maintaining the game's pieces and score.
 * 
 * This is where you will be writing your project code.
 */
public class Game {

  /* Instance Variables */

  // TODO: Parts 1.1 and 3.1

  // GameEngine is responsible for handling physics and graphics.
  private GameEngine _gameEngine;

  /* Constructor */

  public Game(GameEngine gameEngine) {
    _gameEngine = gameEngine;
    _gameEngine.setOnCollision(this::handleCollision);
    _gameEngine.setOnTick(this::handleTick);

    // TODO: Parts 1.2, 1.3, 3.1

  }

  /* Event Handlers */

  // TODO: Part 2 - handleKeyPressed, handleKeyReleased

  public void handleCollision(CollisionEvent collision) {
    System.out.println("BOOM!");
    // TODO: Part 3.2
  }

  public void handleTick(TickEvent tick) {
    // TODO: Parts 3.3 and 3.4
  }

  /* Accessor Methods */

  // TODO Parts 1.1, 3.1

  public Ship getHome() {
    return null;
  }

  public Ship getAway() {
    return null;
  }

  public Puck getPuck() {
    return null;
  }

  public double getAwayTime() {
    return 0.0;
  }

  public double getHomeTime() {
    return 0.0;
  }

}
