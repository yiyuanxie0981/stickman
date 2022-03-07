package stickman.model.level;

import java.time.Instant;
import java.util.List;
import stickman.config.ConfigurationProvider;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.entity.impl.CloudEntity;

/** The Level interface */
public interface Level {

  boolean checkFinished(int level);
  /**
   * Starts the level running
   *
   * @param provider the ConfigurationProvider
   */
  void start(ConfigurationProvider provider);

  /**
   * Stops and completes the level
   *
   * @param outcome how the player finished the level (e.g. DEATH, FLAG)
   */
  void finish(String outcome);
  void setCloudSpeed(double speed, CloudEntity entity);

  /**
   * Get a list of all Entities assigned to the level
   *
   * @return the level's entities
   */
  List<Entity> getEntities();

  void gainScore(boolean gained);
  boolean getGainedScore();

  /**
   * Get the level's size along the x-axis.
   *
   * @return the level's width
   */
  double getWidth();

  /**
   * Get the level's floor height.
   *
   * @return the level's floor height
   */
  double getFloorHeight();

  /**
   * Get the hero's current x-axis position.
   *
   * @return the hero's x value
   */
  double getHeroX();

  /**
   * Get the hero's current y-axis position.
   *
   * @return the hero's y value
   */
  double getHeroY();

  /**
   * Get the Instant in which start() was called
   *
   * @return the game's start Instant
   */
  Instant getStartTime();

  /**
   * Attempt to make the hero jump.
   *
   * @return True if the hero will jump, else false
   */
  boolean jump();

  /**
   * Attempt to move the hero left.
   *
   * @return True if the hero will move left, else false
   */
  boolean moveLeft();

  /**
   * Attempt to move the hero right.
   *
   * @return True if the hero will move right, else false
   */
  boolean moveRight();

  /**
   * Attempt to stop the hero's movement.
   *
   * @return True if the hero will cease all movement, else false
   */
  boolean stopMoving();

  /** Updates the position of all entities in the level. */
  void tick();

  double getTargetGameTime();
  void setEntities(List<Entity> entities);

  void setHero(Controllable hero);
}
