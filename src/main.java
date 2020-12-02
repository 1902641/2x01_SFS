import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

public class main extends Application {
	private double x, y;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane homepane = FXMLLoader.load(main.class.getResource("/Templates/login.fxml"));
		primaryStage.setOnCloseRequest(e -> {
			e.consume();
			closeProgram(primaryStage);
		});
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(new Scene(homepane));
		// drag it here
		homepane.setOnMousePressed(event -> {
			x = event.getSceneX();
			y = event.getSceneY();
		});
		homepane.setOnMouseDragged(event -> {

			primaryStage.setX(event.getScreenX() - x);
			primaryStage.setY(event.getScreenY() - y);

		});
		primaryStage.show();
	}

	public void closeProgram(Stage primaryStage) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Exit the program? ", ButtonType.YES, ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			primaryStage.close();
		}
	}
}
