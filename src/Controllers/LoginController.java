package Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.prefs.Preferences;

import entities.GetUserFactory;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
	
	String pid = "";
	
	
    public String getPid() {
		return pid;
	}

	public void setPid(String id) {
		this.pid = id;
	}

	@FXML
    private AnchorPane mainStage;

    @FXML
    private Pane loginPane;

    @FXML
    private TextField txtfieldUsername;

    @FXML
    private Button btnLogin;

    @FXML
    private ComboBox<String> userSelector;
  
    @FXML
    void loginMethod(ActionEvent event) {
    	if (event.getSource() == btnLogin) {
			// Set verification here. If true then run the following codes
			
			GetUserFactory guf = new GetUserFactory();
			String id = userSelector.getValue().toString();
			if(id.equalsIgnoreCase("Lecturer")) {
				id="Lecturer";
			}else {
				id="Student";
			}
			User user = guf.GetUserFactory(id);
			if(user.login(txtfieldUsername.getText()) == true)
			{
				if(id=="Lecturer") {
					CreateText ct = new CreateText();
					ct.createText(txtfieldUsername.getText());
					try {
						Parent dashboard = FXMLLoader.load(getClass().getResource("/Templates/dashboard.fxml"));
						Scene dashboardScene = new Scene(dashboard);
						Stage dashboardStage = (Stage) ((Node) event.getSource()).getScene()
								.getWindow();
						dashboardStage.setScene(dashboardScene);
						dashboardStage.show();
						} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(id=="Student") {
					CreateText ct = new CreateText();
					ct.createText(txtfieldUsername.getText());
					try {
						Parent dashboard = FXMLLoader.load(getClass().getResource("/Templates/dashboard_student.fxml"));
						Scene dashboardScene = new Scene(dashboard);
						Stage dashboardStage = (Stage) ((Node) event.getSource()).getScene()
								.getWindow();
						dashboardStage.setScene(dashboardScene);
						dashboardStage.show();
						} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else {
				Stage newStage = new Stage();
				VBox comp = new VBox();
				Text error = new Text("Incorrect information!");
				comp.getChildren().add(error);

				Scene stageScene = new Scene(comp, 300, 300);
				newStage.setScene(stageScene);
				newStage.show();
			}
		}
    }
}
