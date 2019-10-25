package lab3;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.geometry.Point2D;

public class NewImageDialog extends Dialog<Point2D> {
  public NewImageDialog() {
    this.setTitle("New image");
    this.setHeaderText("Specify image size:");
    this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    GridPane grid = new GridPane();
    TextField widthEdit = new TextField();
    TextField heightEdit = new TextField();
    grid.add(new Label("Width"), 0, 0);
    grid.add(widthEdit, 1, 0);
    grid.add(new Label("Height"), 0, 1);
    grid.add(heightEdit, 1, 1);
    this.getDialogPane().setContent(grid);
    this.setResultConverter(buttonType -> {
      if (buttonType == ButtonType.OK) {
        return new Point2D(Integer.parseInt(widthEdit.getText()), Integer.parseInt(heightEdit.getText()));
      } else {
        return null;
      }
    });
  }
}
