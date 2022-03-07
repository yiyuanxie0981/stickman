package stickman.model.entity.impl;

import stickman.model.entity.Damageable;
import stickman.model.entity.Entity;
import stickman.model.entity.strategy.EnemyStrategy;
import stickman.model.level.Level;
import stickman.model.level.collision.CollisionDirection;

/** The enemy Entity type. */
public class EnemyEntity extends AbstractEntity implements Damageable {

  private EnemyStrategy strategy;
  private int lives;
  private String imagePath;
  private double xPos;
  private double yPos;
  private double width;
  private double height;
  private Layer layer;

  public EnemyEntity(String imagePath, double xPos, double yPos, double width, double height, Layer layer, EnemyStrategy strategy) {
    super(imagePath, xPos, yPos, width, height, layer);
    this.imagePath = imagePath;
    this.xPos = xPos;
    this.yPos = yPos;
    this.width = width;
    this.height = height;
    this.layer = layer;
    this.strategy = strategy;
    this.lives = 1;
  }

  @Override
  public void move(Level level) {
    strategy.think(level, this);
  }

  @Override
  public void die(Level level) {
    level.getEntities().remove(this);
    level.gainScore(true);
  }

  @Override
  public void takeDamage(Level level) {

    int newLives = lives - 1;

    if (newLives <= 0) {
      die(level);
    } else {
      this.lives = newLives;
    }
  }

  @Override
  public int getLives() {
    return lives;
  }
  @Override
  public Entity copy(){
    EnemyStrategy strategy1 = getStrategy().copy();
    Entity entity = new EnemyEntity(imagePath,xPos,yPos,width,height,layer,strategy1);
    return entity;
  }

  @Override
  public boolean handleCollision(Entity other, CollisionDirection direction, Level level) {

    if (other instanceof HeroEntity) {

      if (!direction.equals(CollisionDirection.BOTTOM)) {
        return false;
      }

      takeDamage(level);
      return true;
    }
    return false;
  }

  // exposed for testing
  public EnemyStrategy getStrategy() {
    return strategy;
  }
}
