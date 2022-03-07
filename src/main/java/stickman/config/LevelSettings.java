package stickman.config;

import org.json.simple.JSONObject;

/** The configuration containing all user-defined settings. */
public class LevelSettings {

  private JSONObject entityData;
  private double width;
  private double floorHeight;
  private double targetGameTime;

  public LevelSettings(JSONObject entityData, double width, double floorHeight,double targetGameTime) {
    this.entityData = entityData;
    this.width = width;
    this.floorHeight = floorHeight;
    this.targetGameTime = targetGameTime;
  }

  /**
   * Get the stored Entity data.
   *
   * @return a JSONObject containing Entity data.
   */
  public JSONObject getEntityData() {
    return entityData;
  }

  /**
   * Get the level's floor height.
   *
   * @return the floor height.
   */
  public double getFloorHeight() {
    return floorHeight;
  }

  /**
   * Get the level's width.
   *
   * @return the width.
   */
  public double getWidth() {
    return width;
  }
  /**
   * Get this level's taget time read from json file.
   *
   * @return the targetGameTime.
   */
  public double getTargetGameTime(){
    return targetGameTime;
  }
}
