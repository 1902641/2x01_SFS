package Controllers;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import entities.Student;
import entities.User;

public class viewStudentController implements Initializable {

    @FXML
    private Button welcomeBtn;

	@FXML
	private AnchorPane mainStage;
	@FXML
	private VBox componentVB;
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
	private Pane lsubComponentPane;
    @FXML
    private TableView<Student> studentListTable;
    @FXML
    private TableColumn<Student, String> adminNoCol;
    @FXML
    private TableColumn<Student, String> fNameCol;
    @FXML
    private TableColumn<Student, String> lNameCol;
    @FXML
    private TableColumn<Student, String> emailCol;
    @FXML
    private TableColumn<Student, String> mobileCol;
   
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Student> obs = FXCollections.observableArrayList();
		User user = new Student();
		obs = user.retrieveAllStudentForTable();
		
		adminNoCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStu_id()));
		fNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStu_fname()));
		lNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStu_lname()));
		emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStu_email()));
		mobileCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStu_mobile()));
//		fNameCol.setCellValueFactory(new PropertyValueFactory<>("stu_fname"));
//		lNameCol.setCellValueFactory(new PropertyValueFactory<>("stu_lname"));
//		emailCol.setCellValueFactory(new PropertyValueFactory<>("stu_email"));
//		mobileCol.setCellValueFactory(new PropertyValueFactory<>("stu_mobile"));
		studentListTable.setItems(obs);
		
//		studentListTable.setRowFactory( tv -> {
//		    TableRow<User> row = new TableRow<>();
//		    row.setOnMouseClicked(event -> {
//		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
//		            User rowData = row.getItem();
//		            System.out.println(rowData.getStu_id());
//		        }
//		    });
//		    
//		});
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
