package stickman.model.engine;

import stickman.config.ConfigurationProvider;
import stickman.memento.CareTaker;
import stickman.memento.Originator;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.entity.impl.*;
import stickman.model.level.Level;
import stickman.model.level.LevelImpl;

import java.io.File;
import java.util.*;
import java.util.List;



/** The implementation class of the GameEngine interface. */
public class GameEngineImpl implements GameEngine {
  //  File folder = new File("/Users/apple/Desktop/soft2412/assign_3/src/main/resources/config");
//  File[] listOfFiles = folder.listFiles();
  List<String> listOfFiles = Arrays.asList("config.json","config_2.json","config_3.json");

  private List<Entity> copyEntity = new ArrayList<Entity>();
  private String fileName;
  private ConfigurationProvider provider;
  private Level currentLevel;
  private boolean nextLevel;
  private Originator originator = new Originator();
  private CareTaker careTaker = new CareTaker();

  private double gameTime = 0;
  private double currentScore = 0;
  private double totalScore = 0;
  private boolean saved = false;
  private int levelNum;

  public GameEngineImpl() {
    startLevel();
    currentScore = getCurrentLevel().getTargetGameTime();
  }

  @Override
  public Level getCurrentLevel() {
    return this.currentLevel;
  }

  @Override
  public void startLevel() {
//    setupProvider("config/"+listOfFiles[0].getName());
//    currentLevel.start(provider);
//    fileName = listOfFiles[0].getName();
    setupProvider("config/"+listOfFiles.get(0));
    currentLevel.start(provider);
    fileName = listOfFiles.get(0);
  }

  @Override
  public void setupProvider(String jsonFileName) {
    provider = new ConfigurationProvider(jsonFileName);
    currentLevel = new LevelImpl(provider);
  }

  @Override
  public boolean jump() {
    return currentLevel.jump();
  }

  @Override
  public boolean moveLeft() {
    return currentLevel.moveLeft();
  }

  @Override
  public boolean moveRight() {
    return currentLevel.moveRight();
  }
  @Override
  public boolean stopMoving() {
    return currentLevel.stopMoving();
  }

  @Override
  public void setCheckNextLevel(boolean nextLevel){
    this.nextLevel = nextLevel;
  }
  @Override
  public boolean checkNextLevel(){
    return this.nextLevel;
  }
  @Override
  public void setUpData(){
    gameTime += 0.017/2;
    if(currentScore > 0.017){
      currentScore -= 0.017/2;
    }
    if(getCurrentLevel().getGainedScore()){
      currentScore += 100;
      getCurrentLevel().gainScore(false);
    }
    if(checkNextLevel()){
      totalScore += currentScore;
      currentScore = getCurrentLevel().getTargetGameTime();
    }
  }
  @Override
  public double getTotalScore(){
    return totalScore;
  }
  @Override
  public double getCurrentScore(){
    return currentScore;
  }
  @Override
  public double getGameTime(){
    return gameTime;
  }
  @Override
  public void save(){
    Map<String,List> data = new HashMap<String,List>();
    List<Double> settingData = new ArrayList<Double>();
    settingData.add(totalScore);
    settingData.add(currentScore);
    settingData.add(gameTime);
    settingData.add((double)levelNum);
    data.put("settingEntity",settingData);
    Level level = getCurrentLevel();
    List<Entity> entities = level.getEntities();
    copyEntity.clear();
    for(Entity i : entities){
      copyEntity.add(i.copy());
      if(i instanceof CloudEntity){
        settingData.add(((CloudEntity) i).getVelocity());
      }
    }
    System.out.println("=== YOU HAVE SAVED GAME IN LEVEL "+(levelNum+1)+"! ===");
    System.out.println("Please press Q to restore last saved game.\n");
    originator.setState(data);
    careTaker.add(originator.saveStateToMemento());
    saved = true;
  }
  @Override
  public void restore(){
    if(saved){
      originator.getStateFromMemento(careTaker.get(careTaker.getListSize()-1));
      Map<String,List> data = originator.getState();
      List<Entity> entityList = getCurrentLevel().getEntities();
      entityList.clear();
      for (Entity i : copyEntity){
        Entity entity= i.copy();
        if(i instanceof HeroEntity){
          currentLevel.setHero((Controllable) entity);
        }else if(i instanceof CloudEntity){
          currentLevel.setCloudSpeed((Double)data.get("settingEntity").get(4),(CloudEntity)entity);
        }
        entityList.add(entity);
      }
      totalScore = (Double)data.get("settingEntity").get(0);
      currentScore = (Double)data.get("settingEntity").get(1);
      gameTime = (Double)data.get("settingEntity").get(2);
      double number = (Double) data.get("settingEntity").get(3);
      levelNum = (int) number;
      System.out.println("=== YOU HAVE RESTORED GAME IN LEVEL "+(levelNum+1)+"! ===\n");
    }else{
      System.out.println("=== TRY TO PRESS S TO SAVE BEFORE RESTORING ===\n");
    }
  }
  @Override
  public void setLevel(Level level){
    this.currentLevel = level;
  }
  @Override
  public void setLevel(int num){
    levelNum = num;
    if(levelNum >= listOfFiles.size()){
      System.out.println("Congratualation and see you next time! ");
      System.out.printf("=== Your total score is: %d points. ===\n", (int)totalScore);
      System.exit(0);
    }
    fileName = listOfFiles.get(levelNum);
    setupProvider("config/"+fileName);
    currentLevel.start(provider);
    setCheckNextLevel(true);
  }

  @Override
  public void tick() {
    currentLevel.tick();
    setCheckNextLevel(false);
    if (currentLevel.checkFinished(levelNum+1)){
      setLevel(levelNum+1);
    }
    setUpData();
  }
}

