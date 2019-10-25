package lab3;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
 
public class Main extends Application{
  private Circle brushCursor;
  public static void main(String[] args) {
      Application.launch(args);
  }

  @Override
  public void start(Stage stage) {
    DrawingField group = new DrawingField();
    Scene scene = new Scene(group);
    scene.setFill(Color.MAGENTA);
    group.setLayoutX(200);
    stage.setScene(scene);
    stage.setTitle("Paint");
    stage.setMinWidth(800);
    stage.setMaxWidth(800);
    stage.setMinHeight(600);
    stage.setMaxHeight(600);
    stage.show();
  }
}
