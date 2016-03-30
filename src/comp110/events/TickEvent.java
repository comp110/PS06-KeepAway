package comp110.events;

import javafx.event.ActionEvent;

/**
 * A Tick event is emitted each time the game progresses by a single frame.
 */

@SuppressWarnings("serial")
public class TickEvent extends ActionEvent {

  private double _time;

  public TickEvent(double time) {
    _time = time;
  }

  public double getTime() {
    return _time;
  }

}