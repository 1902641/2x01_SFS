
package Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.prefs.Preferences;

import entities.Component;
import entities.Lecturer;
import entities.Student;
import entities.User;

public class DashboardController implements Initializable {

	@FXML
	private AnchorPane mainStage;

	@FXML
	private VBox componentVB;

    @FXML
    private Button welcometxt;
    
	@FXML
	private Pane loginPane;

	@FXML
	private TextField txtfieldUsername;

	@FXML
	private TextField txtFieldPassword;

	@FXML
	private Button btnLogin;

	@FXML
	private Pane dashboardPane;

	@FXML
	private Pane lHomePagePane;

	@FXML
	private Pane lViewStudentsPane;

	@FXML
	private Pane lManageCPane;

	@FXML
	private Button btnAssWeightage;

	@FXML
	private Button btnHome;

	@FXML
	private Button btnManageCom;

	@FXML
	private Button btnViewStudent;

	@FXML
	private Button btnSignOut;

	@FXML
	private Button welcomeBtn;

	@FXML
	private Pane lsubComponentPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CreateText ct = new CreateText();
		String id = ct.getText();
		User ul = new Lecturer();
		String name = ul.getNameByID(id);
		welcomeBtn.setText("Welcome, " + name);
		Component comp = new Component();
		ArrayList<Component> compAL = comp.retrieveAllComponents();
		Node[] nodes = new Node[compAL.size()];
		for (int i = 0; i < nodes.length; i++) {
			try {

				final int j = i;
				nodes[i] = FXMLLoader.load(getClass().getResource("/Templates/component.fxml"));
				// give the items some effect
				nodes[i].setOnMouseEntered(event -> {
					nodes[j].setStyle("-fx-background-color : #E9E9E9");
				});
				nodes[i].setOnMouseExited(event -> {
					nodes[j].setStyle("-fx-background-color : #FFFFFF");
				});

				nodes[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
							if (mouseEvent.getClickCount() == 2) {
								Parent subComponent;
								try {
									subComponent = FXMLLoader.load(getClass().getResource("/Templates/subComponent.fxml"));
									Scene subComponentScene = new Scene(subComponent);
									Stage subComponentStage = (Stage) ((Node) mouseEvent.getSource()).getScene()
											.getWindow();
									subComponentStage.setScene(subComponentScene);
									subComponentStage.show();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}
					}

					public void submitAction(MouseEvent mouseEvent) {

					}
				});

				componentVB.getChildren().add(nodes[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	


	public void weightageMethod(ActionEvent actionEvent) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Templates/weightageComponent.fxml"));
			Parent root2 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(mainStage.getScene().getWindow());
			stage.setTitle("Assign Weightage");
			stage.setScene(new Scene(root2));
			stage.show();
		} catch (Exception e) {

		}
	}

	public void loginMethod(ActionEvent actionEvent) {
		if (actionEvent.getSource() == btnLogin) {
			// Set verification here. If true then run the following codes
			// Following codes ensure that the HomePage is visible and on top of StackPane
			lManageCPane.setVisible(false);
			lViewStudentsPane.setVisible(false);
			lHomePagePane.setVisible(true);
			lHomePagePane.toFront();

			// After Login
			loginPane.setVisible(false);
			dashboardPane.setVisible(true);
			dashboardPane.toFront();
		}
	}

	public void navBar(ActionEvent actionEvent) {
		if (actionEvent.getSource() == btnHome) {
			Parent dashboard;
			try {
				dashboard = FXMLLoader.load(getClass().getResource("/Templates/dashboard.fxml"));
				Scene dashboardScene = new Scene(dashboard);
				Stage dashboardStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
				dashboardStage.setScene(dashboardScene);
				dashboardStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (actionEvent.getSource() == btnManageCom) {
			Parent dashboard;
			try {
				dashboard = FXMLLoader.load(getClass().getResource("/Templates/manageComp.fxml"));
				Scene dashboardScene = new Scene(dashboard);
				Stage dashboardStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
				dashboardStage.setScene(dashboardScene);
				dashboardStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (actionEvent.getSource() == btnViewStudent) {
			Parent dashboard;
			try {
				dashboard = FXMLLoader.load(getClass().getResource("/Templates/viewStudent.fxml"));
				Scene dashboardScene = new Scene(dashboard);
				Stage dashboardStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
				dashboardStage.setScene(dashboardScene);
				dashboardStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (actionEvent.getSource() == btnSignOut) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Confirm logout? ", ButtonType.YES, ButtonType.CANCEL);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				Parent login;
				try {
					login = FXMLLoader.load(getClass().getResource("/Templates/login.fxml"));
					Scene loginScene = new Scene(login);
					Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
					loginStage.setScene(loginScene);
					loginStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
