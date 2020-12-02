package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class modifyComponentController {

	@FXML
	private AnchorPane modifyStage;

	@FXML
	private Button btnDelete;

	@FXML
	private Button btnModify;

	@FXML
	private Button btnCancel;

	@FXML
	void cancelModify(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Exit modification? ", ButtonType.YES, ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			// get a handle to the stage
			Stage stage = (Stage) btnCancel.getScene().getWindow();
			// do what you have to do
			stage.close();
		}
	}
}
