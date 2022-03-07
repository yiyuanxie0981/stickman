package stickman.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import stickman.memento.Memento;
import stickman.model.engine.GameEngine;

import java.util.ArrayList;
import java.util.List;

/** A "manager" type class for operating critical game functions */
public class GameManager {

  public static final int FRAMEFRATE_MS = 17;
  private final int width;
  private final int height;

  private Scene scene;
  private GameDrawer entityDrawer;

  public GameManager(GameEngine model, int width, int height) {
    Pane pane = new Pane();

    this.width = width;
    this.height = height;
    this.scene = new Scene(pane, width, height);
    // register input handler
    KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(model);
    scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
    scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

    // start drawers
    BackgroundDrawer backgroundDrawer = new BlockedBackground();
    this.entityDrawer = new GameDrawer(model, pane, backgroundDrawer, width, height);

    backgroundDrawer.draw(model, pane);


  }

  /**
   * Get the Window's current Scene object
   *
   * @return the scene object
   */
  public Scene getScene() {
    return this.scene;
  }

  /** Initiates the game's display update sequence. */
  public void run() {

    Timeline timeline =
        new Timeline(new KeyFrame(Duration.millis(FRAMEFRATE_MS), t -> entityDrawer.draw()));

    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  // exposed for testing
  public GameDrawer getEntityDrawer() {
    return entityDrawer;
  }

}
