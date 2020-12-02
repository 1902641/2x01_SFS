package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entities.Component;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class studentViewComp implements Initializable {

    @FXML
    private AnchorPane mainStage;

    @FXML
    private Pane dashboardPane;

    @FXML
    private Pane lManageCPane;

    @FXML
    private VBox componentVB;

    @FXML
    private Button welcomeTxt;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnSignOut;
    
    int comp_id;
    
    public int getComp_id() {
		return comp_id;
	}

	public void setComp_id(int comp_id) {
		this.comp_id = comp_id;
	}

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	String studentName = "Student A"; //change this to get dynamically
		welcomeTxt.setText(welcomeTxt.getText()+ studentName);
		
		ArrayList<Component> compAL = new ArrayList<Component>();
		ResultSet rs = null;
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		// Step 1 - connect to database
		db.getConnection();
		// step 2 - declare the SQL statement (select all components)
		dbQuery = "SELECT * FROM 2x01_db.component;";
		// step 3 - using DBControlle readRequest method
		rs = db.readRequest(dbQuery);
		try {
			while (rs.next()) {
				Component cp = new Component();
				Component component = cp.convertToComponent(rs);
				compAL.add(component);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// step 4 - close connection
		db.terminate();
		
		//calculate all components grade here
		
		Node[] nodes = new Node[compAL.size()];
		for (int i = 0; i < nodes.length; i++) {
			try {
				final int j = i;
				nodes[i] = FXMLLoader.load(getClass().getResource("/Templates/componentWithGrade.fxml"));
				Label lbl1 = (Label) nodes[i].lookup("#compNameLbl");
				//update grades in label here
				Label lbl2 = (Label) nodes[i].lookup("#compDescLbl");
				Label lbl3 = (Label) nodes[i].lookup("#weightLbl");
				if(lbl1 != null && lbl2!=null && lbl3 !=null) {
					lbl1.setText(compAL.get(i).getComp_name());
					//set grades here too
					lbl2.setText(compAL.get(i).getComp_desc());
					lbl3.setText(String.valueOf(compAL.get(i).getComp_weight()));
				}

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
								comp_id = j;
								try {
									subComponent = FXMLLoader.load(getClass().getResource("/Templates/studentViewSubComponent.fxml"));										
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

    @FXML
    void navBar(ActionEvent actionEvent) {
    	if (actionEvent.getSource() == btnHome) {
			Parent dashboard;
			try {
				dashboard = FXMLLoader.load(getClass().getResource("/Templates/dashboard_student.fxml"));
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
