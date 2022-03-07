package stickman.model.entity.impl;

import stickman.model.entity.Entity;
import stickman.model.entity.strategy.EnemyStrategy;
import stickman.model.level.Level;
import stickman.view.GameManager;

/** The cloud Entity type. */
public class CloudEntity extends AbstractEntity {

  private double velocity;
  private String imagePath;
  private double xPos;
  private double yPos;
  private double width;
  private double height;
  private Layer layer;
  public CloudEntity(String imagePath, double xPos, double yPos, double width, double height, Layer layer, double velocity) {
    super(imagePath, xPos, yPos, width, height, layer);
    this.velocity = (velocity / 1000) * GameManager.FRAMEFRATE_MS;
    this.imagePath = imagePath;
    this.xPos = xPos;
    this.yPos = yPos;
    this.width = width;
    this.height = height;
    this.layer = layer;
  }
  public double getVelocity(){
    return velocity;
  }
  public void setVelocity(double velocity){
    this.velocity = velocity;
  }
  @Override
  public void move(Level level) {
    this.setXPos(this.getXPos() + this.velocity);
  }
  @Override
  public Entity copy(){
    Entity entity = new CloudEntity(getImagePath(),getXPos(),getYPos(),getWidth(),getHeight(),getLayer(),velocity);
    return entity;
  }
}

