package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entities.Component;
import entities.Student;
import entities.SubComponent;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class studentViewSubComponent implements Initializable{
    
	@FXML
    private AnchorPane mainStage;

    @FXML
    private Pane dashboardPane;

    @FXML
    private Pane lsubComponentPane;

    @FXML
    private Text moduleLbl;

    @FXML
    private TextArea feedbackTxtArea;
    
    @FXML
    private TableView<SubComponent> subCompTable;
    @FXML
    private TableColumn<SubComponent, String> scNameCol;
    @FXML
    private TableColumn<SubComponent, String> marksCol;
    @FXML
    private TableColumn<SubComponent, String> weightageCol;
    @FXML
    private TableColumn<SubComponent, String> descCol;

    @FXML
    private Button welcomeTxt;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnComponents;

    @FXML
    private Button btnSignOut;
    
    @FXML
    void navBar(ActionEvent actionEvent) {
    	
    	//go to home scene
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
    	
    	//go to components scene
    	if (actionEvent.getSource() == btnComponents) {
			Parent dashboard;
			try {
				dashboard = FXMLLoader.load(getClass().getResource("/Templates/studentViewComp.fxml"));
				Scene dashboardScene = new Scene(dashboard);
				Stage dashboardStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
				dashboardStage.setScene(dashboardScene);
				dashboardStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    	//signing out
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
    
    //things to do when init subcomp page
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	
		studentViewComp svc = new studentViewComp();
		
		
		//*****getting comp_id not working****** currently id is 1
		int id = svc.getComp_id() + 5;
		
		ObservableList<SubComponent> scOL = FXCollections.observableArrayList();
		ResultSet rs = null;
		
		MySQLConnection db = new MySQLConnection();
		String dbQuery;
		
		
		//*****getting of the subcomp details
		// Step 1 - connect to database
		db.getConnection();
		
		// step 2 - declare the SQL statement
		System.out.println("this is the COMP ID" + id);
		
		dbQuery = "SELECT * FROM 2x01_db.subcomponent WHERE comp_id="+id+";";
		// step 3 - using DBControlle readRequest method
		rs = db.readRequest(dbQuery);
		try {
			while (rs.next()) {
				SubComponent sc = new SubComponent();
				SubComponent subComponent = sc.convertToSubComponent(rs);
				scOL.add(subComponent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// step 4 - close connection
		db.terminate();
		
		scNameCol.setCellValueFactory(new PropertyValueFactory<>("subcomp_name"));
		
		//marks required to be gotten from separate table which will require 
		//stu_id, subcomp_id and comp_id
		marksCol.setCellValueFactory(new PropertyValueFactory<>("subcomp_weight"));
		
		
		weightageCol.setCellValueFactory(new PropertyValueFactory<>("subcomp_weight"));
		descCol.setCellValueFactory(new PropertyValueFactory<>("subcomp_desc"));
		subCompTable.setItems(scOL);
		
		subCompTable.setRowFactory( tv -> {
		    TableRow<SubComponent> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            SubComponent rowData = row.getItem();
		            System.out.println(rowData.getSubcomp_id());
		        }
		    });
		    return row ;
		});
	}
    
    


}
