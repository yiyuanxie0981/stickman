package stickman.view;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import stickman.model.engine.GameEngine;
import stickman.memento.Memento;
import stickman.model.entity.Entity;

/** A class for drawing objects onto the game window. */
public class GameDrawer {

  private GameEngine model;
  private Pane pane;
  private final int width;
  private final int height;

  private BackgroundDrawer backgroundDrawer;

  private double xViewportOffset = 0.0;
  private double yViewportOffset = 0.0;
  private static final double Y_VIEWPORT_MARGIN = 200.0;
  private static final double VIEWPORT_MARGIN = 280.0;

  private List<EntityView> entityViews;

  Font front = Font.font( "Verdana", FontWeight.THIN, 15 );
  Canvas canvas = new Canvas(1000.0, 1100.0);
  private GraphicsContext gc = canvas.getGraphicsContext2D();

  public GameDrawer(GameEngine model, Pane pane, BackgroundDrawer backgroundDrawer, int width, int height) {

    this.model = model;
    this.pane = pane;
    this.backgroundDrawer = backgroundDrawer;
    this.width = width;
    this.height = height;
    this.entityViews = new ArrayList<>();
    pane.getChildren().add(canvas);
  }

  /** Draws and updates all currently displayed EntityView objects. */
  public void drawText(){
    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    gc.setFont(front);
    gc.fillText( "Time: "+ (int)model.getGameTime()+" s", 20, 28 );
    gc.fillText( "Total Score: "+ (int)(model.getTotalScore()+model.getCurrentScore()), 120, 28 );
    gc.fillText( "Score: "+ (int)model.getCurrentScore(), 280, 28 );

  }
  public void draw() {
    model.tick();
    drawText();//unfinished

    List<Entity> entities = model.getCurrentLevel().getEntities();

    for (EntityView entityView : entityViews) {
      entityView.markForDelete();
    }

    double heroXPos = model.getCurrentLevel().getHeroX();
    double heroYPos = model.getCurrentLevel().getHeroY();

    heroXPos -= xViewportOffset;

    if (heroXPos < VIEWPORT_MARGIN) {
      if (xViewportOffset >= 0) { // Don't go further left than the start of the level
        xViewportOffset -= VIEWPORT_MARGIN - heroXPos;
        if (xViewportOffset < 0) {
          xViewportOffset = 0;
        }
      }
    } else if (heroXPos > width - VIEWPORT_MARGIN) {
      xViewportOffset += heroXPos - (width - VIEWPORT_MARGIN);
    }

    if (heroYPos <= Y_VIEWPORT_MARGIN) {
      yViewportOffset = heroYPos - Y_VIEWPORT_MARGIN;
    } else {
      yViewportOffset = 0;
    }

    backgroundDrawer.update(xViewportOffset);

    for (Entity entity : entities) {
      boolean notFound = true;
      for (EntityView view : entityViews) {
        if (view.matchesEntity(entity)) {
          notFound = false;
          view.updateView(xViewportOffset, yViewportOffset);
          break;
        }
      }
      if (notFound) {
        EntityView entityView = new EntityViewImpl(entity);
        entityViews.add(entityView);
        pane.getChildren().add(entityView.getNode());
      }
    }

    for (EntityView entityView : entityViews) {
      if (entityView.isMarkedForDelete()) {
        pane.getChildren().remove(entityView.getNode());
      }
    }
    entityViews.removeIf(EntityView::isMarkedForDelete);
  }

  public void removeView(Entity e) {
    entityViews.removeIf(v -> v.matchesEntity(e));
  }

  // exposed for testing
  public double getxViewportOffset() {
    return xViewportOffset;
  }

  // exposed for testing
  public double getyViewportOffset() {
    return yViewportOffset;
  }
}
