package stickman.model.entity.impl;

import stickman.model.entity.Entity;

/** The flag Entity type. */
public class FlagEntity extends AbstractEntity {
  private String imagePath;
  private double xPos;
  private double yPos;
  private double width;
  private double height;
  private Layer layer;
  public FlagEntity(
      String imagePath, double xPos, double yPos, double width, double height, Layer layer) {
    super(imagePath, xPos, yPos, width, height, layer);
    this.imagePath = imagePath;
    this.xPos = xPos;
    this.yPos = yPos;
    this.width = width;
    this.height = height;
    this.layer = layer;
  }
  @Override
  public Entity copy(){
    Entity entity = new FlagEntity(getImagePath(),getXPos(),getYPos(),getWidth(),getHeight(),getLayer());
    return entity;
  }
}
