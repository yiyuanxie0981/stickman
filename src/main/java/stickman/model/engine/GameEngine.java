package stickman.model.engine;

import stickman.memento.Memento;
import stickman.model.entity.Entity;
import stickman.model.level.Level;

import java.util.List;
import java.util.Map;

/** The GameEngine interface */
public interface GameEngine {

  /**
   * Returns the currently active level.
   *
   * @return The current level.
   */
  Level getCurrentLevel();

  /** Loads and starts the current level */
  void startLevel();
  void setupProvider(String jsonFileName);
  /**
   * Calls the current level's jump method.
   *
   * @return True if the hero will jump, else false.
   */
  boolean jump();

  /**
   * Calls the current level's moveLeft method.
   *
   * @return True if the hero will move left, else false.
   */
  boolean moveLeft();

  /**
   * Calls the current level's moveRight method.
   *
   * @return True if the hero will move right, else false.
   */
  boolean moveRight();

  /**
   * Calls the current level's stopMoving method.
   *
   * @return True if the hero will cease all movement, else false.
   */
  boolean stopMoving();

  /** Calls the current level's tick method. */
  void tick();
  boolean checkNextLevel();
  void setCheckNextLevel(boolean nextLevel);
  void save();
  void restore();
  void setUpData();
  double getCurrentScore();
  double getTotalScore();
  double getGameTime();
  void setLevel(Level level);
  void setLevel(int num);
}
