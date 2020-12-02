package Controllers;
import javafx.application.Platform;
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
import javafx.scene.control.ComboBox;
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
import java.util.Arrays;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entities.Component;
import entities.Lecturer;
import entities.User;

public class manageCompController implements Initializable {
	
	  
	 	@FXML
	    private AnchorPane mainStage;

	    @FXML
	    private Pane dashboardPane;

	    @FXML
	    private Pane lManageCPane;

	    @FXML
	    private Button btnSaveChanges;

	    @FXML
	    private VBox componentVB;
	    
	    @FXML
	    private Button btnHome;

	    @FXML
	    private Button welcomeBtn;

	    @FXML
	    private Button btnManageCom;

	    @FXML
	    private Button btnViewStudent;

	    @FXML
	    private Button btnSignOut;

		public manageCompController() {
			super();
			// TODO Auto-generated constructor stub
		}
		

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			CreateText ct = new CreateText();
			String id = ct.getText();
			User ul = new Lecturer();
			String name = ul.getNameByID(id);
			welcomeBtn.setText("Welcome, " + name);
			Component comp = new Component();
			ArrayList<String> modifiedID = new ArrayList<String>();
			ArrayList<Boolean> modifiedVisibility = new ArrayList<Boolean>();
			ArrayList<Component> compAL = comp.retrieveAllComponents();
			Node[] nodes = new Node[compAL.size()];
			for (int i = 0; i < nodes.length; i++) {
				try {
					final int j = i;
					nodes[i] = FXMLLoader.load(getClass().getResource("/Templates/component.fxml"));
					Label lbl1 = (Label) nodes[i].lookup("#comp_name");
					Label lbl2 = (Label) nodes[i].lookup("#comp_desc");
					Label lbl3 = (Label) nodes[i].lookup("#comp_weight");
					@SuppressWarnings("unchecked")
					ComboBox<String> cmb1 = (ComboBox<String>) nodes[i].lookup("#comp_visibility");
					String visibility = "Hidden";
					if(Boolean.TRUE.equals(compAL.get(i).getComp_visible())) {
						visibility = "Shown";
					}else {
						visibility = "Hidden";
					}
					if(lbl1 != null && lbl2!=null && lbl3 !=null) {
						lbl1.setText(compAL.get(i).getComp_name());
						lbl2.setText(compAL.get(i).getComp_desc());
						lbl3.setText(String.valueOf(compAL.get(i).getComp_weight()));
						cmb1.getSelectionModel().select(visibility);
					}
					
					cmb1.valueProperty().addListener((obs, oldItem, newItem) -> {
						if(oldItem != newItem) {
							boolean visible = false;
							if(newItem.equalsIgnoreCase("Hidden")) {
								visible = false;
							}
							else {
								visible = true;
							}
							modifiedID.add(compAL.get(j).getComp_id());
							modifiedVisibility.add(visible);
						}
					});
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
									Integer comp_id = j+1;
									try {
										CreateText ct = new CreateText();
										ct.createCompID(Integer.toString(comp_id));
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
					});
					btnSaveChanges.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent mouseEvent) {
							if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
								for(int i = 0; i<modifiedID.size(); i++) {
									String comp_id = (modifiedID.get(i));
									boolean visibility = (modifiedVisibility.get(i));
									Component comp = new Component();
									comp.setVisibility(comp_id, visibility);
								}
							}
						}
					});
					componentVB.getChildren().add(nodes[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		

//
//	public void weightageMethod(ActionEvent actionEvent) {
//		try {
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Templates/weightageComponent.fxml"));
//			Parent root2 = (Parent) fxmlLoader.load();
//			Stage stage = new Stage();
//			stage.initModality(Modality.APPLICATION_MODAL);
//			stage.initOwner(mainStage.getScene().getWindow());
//			stage.setTitle("Assign Weightage");
//			stage.setScene(new Scene(root2));
//			stage.show();
//		} catch (Exception e) {
//
//		}
//	}

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