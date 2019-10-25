package lab3;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Menu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.control.MenuItem;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.text.Text;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
 
public class Main extends Application{
  private DrawingField currentField;

  public static void main(String[] args) {
      Application.launch(args);
  }

  @Override
  public void start(Stage stage) {
    StackPane drawingFieldContainer = new StackPane();
    ScrollPane scrollWrapper = new ScrollPane(drawingFieldContainer);
    drawingFieldContainer.minWidthProperty().bind(Bindings.createDoubleBinding(
          () -> scrollWrapper.getViewportBounds().getWidth(),
          scrollWrapper.viewportBoundsProperty()
    ));
    drawingFieldContainer.minHeightProperty().bind(Bindings.createDoubleBinding(
          () -> scrollWrapper.getViewportBounds().getHeight(),
          scrollWrapper.viewportBoundsProperty()
    ));
    
    VBox box = new VBox();
    MenuBar menu = new MenuBar();
   
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.valueProperty().addListener(new ChangeListener<Color>() {
      public void changed(ObservableValue<? extends Color> obs, Color oldVal, Color newVal) {
        currentField.setColor(newVal);
      }
    });
    Slider widthSlider = new Slider(1, 20, 3);
    widthSlider.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        currentField.setWidth(newVal.doubleValue());
      }
    });
    widthSlider.setBlockIncrement(1);
    widthSlider.setMajorTickUnit(1);
    widthSlider.setShowTickMarks(true);
    widthSlider.setShowTickLabels(true);

    MenuItem newItem = new MenuItem("New");
    MenuItem loadItem = new MenuItem("Load");
    MenuItem saveItem = new MenuItem("Save");
    CustomMenuItem colorItem = new CustomMenuItem(colorPicker);
    CustomMenuItem widthItem = new CustomMenuItem(widthSlider);
    newItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent evt) {
        Dialog<Point2D> dialog = new NewImageDialog();
        dialog.showAndWait();
        Point2D size = dialog.getResult();
        if (size == null) {
          return;
        }
        drawingFieldContainer.getChildren().clear();
        currentField = new DrawingField(size.getX(), size.getY());
        drawingFieldContainer.getChildren().add(currentField.getRoot());
        saveItem.setDisable(false);
        colorPicker.setDisable(false);
        widthSlider.setDisable(false);
      }
    });
    loadItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent evt) {
        File file = new FileChooser().showOpenDialog(stage);
        if (file == null) {
          return;
        }
        drawingFieldContainer.getChildren().clear();
        currentField = new DrawingField(file);
        drawingFieldContainer.getChildren().add(currentField.getRoot());
        saveItem.setDisable(false);
        colorPicker.setDisable(false);
        widthSlider.setDisable(false);
      }
    });
    saveItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent evt) {
        File file = new FileChooser().showSaveDialog(stage);
        if (file == null) {
          return;
        }
        currentField.save(file);
      }
    });
    saveItem.setDisable(true);
    colorPicker.setDisable(true);
    widthSlider.setDisable(true);
    colorItem.setHideOnClick(false);
    widthItem.setHideOnClick(false);
    menu.getMenus().addAll(new Menu("File", null, newItem, loadItem, saveItem), new Menu("Color", null, colorItem), new Menu("Width", null, widthItem));

    VBox.setVgrow(scrollWrapper, Priority.ALWAYS);
    box.getChildren().addAll(menu, scrollWrapper);
    Scene scene = new Scene(box);
    stage.setScene(scene);
    stage.setMinWidth(480);
    stage.setMinHeight(320);
    stage.setTitle("Paint");
    stage.show();
  }
}
