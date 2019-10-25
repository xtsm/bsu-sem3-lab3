package lab3;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DrawingField extends Pane {
  private static final int DEFAULT_BRUSH_SIZE = 5;

  private static final Color DEFAULT_BRUSH_COLOR = Color.BLACK;
  
  private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;

  private Canvas canvas;

  public DrawingField() {
    canvas = new Canvas();
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setLineWidth(DEFAULT_BRUSH_SIZE);
    gc.setStroke(DEFAULT_BRUSH_COLOR);
    Circle brushCursor = new Circle(DEFAULT_BRUSH_SIZE);
    Rectangle background = new Rectangle();
    background.setFill(DEFAULT_BACKGROUND_COLOR);

    this.getChildren().addAll(background, canvas, brushCursor);
    
    EventHandler<MouseEvent> updateBrushCursor = new EventHandler<MouseEvent>() {
      public void handle(MouseEvent evt) {
        brushCursor.setCenterX(evt.getSceneX()-DrawingField.this.getLayoutX());
        brushCursor.setCenterY(evt.getSceneY()-DrawingField.this.getLayoutY());
      }
    };
    
    this.addEventHandler(MouseEvent.MOUSE_MOVED, updateBrushCursor);
    //this.addEventHandler(MouseEvent.MOUSE_DRAGGED, updateBrushCursor);
    /*
    this.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
      public void handle(MouseEvent evt) {
        gc.lineTo(evt.getSceneX()-DrawingField.this.getLayoutX(), evt.getSceneY()-DrawingField.this.getLayoutY());
        gc.stroke();
        gc.moveTo(evt.getSceneX()-DrawingField.this.getLayoutX(), evt.getSceneY()-DrawingField.this.getLayoutY());
      }
    });
    */
    ChangeListener<Number> resizeListener = new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        double w = DrawingField.this.getWidth();
        double h = DrawingField.this.getHeight();
        canvas.setWidth(w);
        canvas.setHeight(h);
        background.setWidth(w);
        background.setHeight(h);
        DrawingField.this.setClip(new Rectangle(w, h));
      }
    };
    this.widthProperty().addListener(resizeListener);
    this.heightProperty().addListener(resizeListener);
  }

  public void Save(File file) {
    try {
      ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(new SnapshotParameters(), null), null), "png", file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
