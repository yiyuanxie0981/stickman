package stickman.model.level;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import stickman.config.ConfigurationProvider;
import stickman.config.LevelSettings;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.entity.impl.CloudEntity;
import stickman.model.entity.impl.HeroEntity;
import stickman.model.entity.spawner.EntitySpawner;
import stickman.model.entity.spawner.EntitySpawnerImpl;
import stickman.model.level.collision.CollisionHandler;

/** The implementation class of the Level interface. */
public class LevelImpl implements Level {

  private EntitySpawner entitySpawner;
  private CollisionHandler collisionHandler;

  private List<Entity> entities;
  private Controllable hero;

  private double width;
  private double floorHeight;
  private Instant startTime;

  private boolean finished;
  private int level;
  private double targetGameTime;
  private boolean gainScore;
  private ConfigurationProvider provider;
  private List<Entity> copyEntity;

  public LevelImpl(ConfigurationProvider provider) {

    this.entitySpawner = new EntitySpawnerImpl();
    this.collisionHandler = new CollisionHandler();
    this.entities = new ArrayList<>();

    LevelSettings levelData = provider.getLevelData();
    this.width = levelData.getWidth();
    this.floorHeight = levelData.getFloorHeight();
    this.finished = false;
    this.targetGameTime = levelData.getTargetGameTime();
    this.provider = provider;
  }

  @Override
  public boolean checkFinished(int level) {
    this.level = level;
    return this.finished;
  }

  @Override
  public void start(ConfigurationProvider provider) {
    spawnHero(provider);
    spawnEntities(provider);
    populateScene(provider);
    startTime = Instant.now();
  }
  @Override
  public void gainScore(boolean gained){
    gainScore = gained;
  }
  @Override
  public boolean getGainedScore(){
    return gainScore;
  }

  @Override
  public void finish(String outcome) {
    int repeat;
    if ("DEATH".equals(outcome.toUpperCase())) {
      System.out.println("\n=== YOU DIED IN LEVEL " + level + "!  ===");
      System.out.println("Oops! You don't have any life left.");
      System.out.println("Good luck next time.");
      repeat = 17;
      System.exit(0);
    } else {
      System.out.println("\n=== YOU WON IN LEVEL " + level + "! ===");
      System.out.println("Congratulations! You finished the level.\n");
      this.finished = true;// level will move to the next level.
      repeat = 16;
      return;
    }

    Duration elapsed = Duration.between(startTime, Instant.now());
    System.out.println("Your time: " + prettyTimeFormat(elapsed));
    System.out.println("=".repeat(repeat));
  }

  @Override
  public List<Entity> getEntities() {
    return this.entities;
  }
  @Override
  public void setEntities(List<Entity> entities){
    this.entities = entities;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getFloorHeight() {
    return this.floorHeight;
  }

  @Override
  public double getHeroX() {
    return hero.getXPos();
  }

  @Override
  public double getHeroY() {
    return hero.getYPos();
  }

  @Override
  public Instant getStartTime() {
    return startTime;
  }

  @Override
  public boolean jump() {
    return hero.setJumping(true);
  }

  @Override
  public boolean moveLeft() {
    return hero.setMovingLeft(true);
  }

  @Override
  public boolean moveRight() {
    return hero.setMovingRight(true);
  }

  @Override
  public boolean stopMoving() {
    hero.setMovingRight(false);
    hero.setMovingLeft(false);
    return true;
  }

  @Override
  public void tick() {
    entities.forEach(e -> e.move(this));
    collisionHandler.detectCollisions(this, hero);
  }

  @Override
  public double getTargetGameTime() {
    return targetGameTime;
  }

  private void spawnHero(ConfigurationProvider provider) {
    this.hero = entitySpawner.createHero(provider, this);
    entities.add(this.hero);
  }

  private void spawnEntities(ConfigurationProvider provider) {
    entities.addAll(entitySpawner.createEntities(provider, this));
  }

  private void populateScene(ConfigurationProvider provider) {
    entities.addAll(entitySpawner.createBackgroundEntities(provider, this));
  }

  private String prettyTimeFormat(Duration duration) {
    return duration.toString().substring(2).replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase();
  }

  @Override
  public void setHero(Controllable hero) {
      this.hero = hero;
  }
  @Override
  public void setCloudSpeed(double speed, CloudEntity entity){
    entity.setVelocity(speed);
  }
}
