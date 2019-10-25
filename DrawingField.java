package lab3;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class DrawingField {
  private static final int DEFAULT_BRUSH_SIZE = 5;

  private static final Color DEFAULT_BRUSH_COLOR = Color.BLACK;
  
  private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;

  private Pane root;

  private Canvas canvas;

  private Point2D prevDragPoint;

  private Circle brushCursor;

  private Rectangle background;

  public DrawingField(double w, double h) {
    this();
    this.resize(w, h);
  }

  public DrawingField(File file) {
    this();
    try {
      Image img = SwingFXUtils.toFXImage(ImageIO.read(file), null);
      this.resize(img.getWidth(), img.getHeight());
      canvas.getGraphicsContext2D().drawImage(img, 0, 0);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private DrawingField() {
    root = new Pane();
    canvas = new Canvas();
    GraphicsContext gctx = canvas.getGraphicsContext2D();
    gctx.setLineWidth(DEFAULT_BRUSH_SIZE);
    gctx.setStroke(DEFAULT_BRUSH_COLOR);
    gctx.setLineCap(StrokeLineCap.ROUND);
    brushCursor = new Circle(DEFAULT_BRUSH_SIZE);
    background = new Rectangle();
    background.setFill(DEFAULT_BACKGROUND_COLOR);

    root.getChildren().addAll(background, canvas, brushCursor);

    EventHandler<MouseEvent> updateBrushCursor = new EventHandler<MouseEvent>() {
      public void handle(MouseEvent evt) {
        brushCursor.setCenterX(evt.getX());
        brushCursor.setCenterY(evt.getY());
      }
    };
    EventHandler<MouseEvent> continueLine = new EventHandler<MouseEvent>() {
      public void handle(MouseEvent evt) {
        gctx.strokeLine(prevDragPoint.getX(), prevDragPoint.getY(), evt.getX(), evt.getY());
        prevDragPoint = new Point2D(evt.getX(), evt.getY());
      }
    };
    
    root.addEventHandler(MouseEvent.MOUSE_MOVED, updateBrushCursor);
    root.addEventHandler(MouseEvent.MOUSE_DRAGGED, updateBrushCursor);
    root.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      public void handle(MouseEvent evt) {
        prevDragPoint = new Point2D(evt.getX(), evt.getY());
      }
    });
    root.addEventHandler(MouseEvent.MOUSE_DRAGGED, continueLine);
    root.addEventHandler(MouseEvent.MOUSE_RELEASED, continueLine);
  }

  public Pane getRoot() {
    return root;
  }

  public void save(File file) {
    try {
      ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(new SnapshotParameters(), null), null), "png", file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void resize(double w, double h) {
    canvas.setWidth(w);
    canvas.setHeight(h);
    background.setWidth(w);
    background.setHeight(h);
    root.setMinSize(w, h);
    root.setMaxSize(w, h);
    root.setClip(new Rectangle(w, h));
  }
}
