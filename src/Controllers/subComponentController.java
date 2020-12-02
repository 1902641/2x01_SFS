package Controllers;

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
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.Component;
import entities.Lecturer;
import entities.SubComponent;
import entities.User;

public class subComponentController implements Initializable {

    @FXML
    private Text titletxt;
    
    @FXML
    private Button welcomeBtn;

	@FXML
	private AnchorPane mainStage;

	@FXML
	private VBox subComponentVB;

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
	private Button btnaddSubcomponentMethod;

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
    private Button btnSaveWeightage;
	
    @FXML
    Label idlbl;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		CreateText ct = new CreateText();
		String isd = ct.getText();
		User ul = new Lecturer();
		String name = ul.getNameByID(isd);
		welcomeBtn.setText("Welcome, " + name);
		manageCompController mcc = new manageCompController();
		String id = ct.getCompID();
		Component c = new Component();
		titletxt.setText(c.retrieveCompNameByID(id));
		executableInit(id);
	}
	
	void executableInit(String cpid) {
		SubComponent sc = new SubComponent();
		ArrayList<String> modifiedID = new ArrayList<String>();
		ArrayList<Double> modifiedWeightage = new ArrayList<Double>();
		ArrayList<SubComponent> scAL = sc.retrieveSubComponentByComponentID(cpid);
		Node[] nodes = new Node[scAL.size()];
		
		for (int i = 0; i < nodes.length; i++) {
			try {
				final int j = i;
				nodes[i] = FXMLLoader.load(getClass().getResource("/Templates/sComponent.fxml"));
				Label lbl1 = (Label) nodes[i].lookup("#subcomp_name");
				Label lbl2 = (Label) nodes[i].lookup("#subcomp_desc");
				ComboBox<String> cmbs = (ComboBox<String>) nodes[i].lookup("#subcomp_weightage");
				if(lbl1 != null && lbl2!=null) {
					lbl1.setText(scAL.get(i).getSubcomp_name());
					lbl2.setText(scAL.get(i).getSubcomp_desc());
					System.out.println(scAL.get(i).getSubcomp_weight());
					cmbs.getSelectionModel().select(Double.toString(scAL.get(i).getSubcomp_weight())+"%");
				}
				cmbs.valueProperty().addListener((obs, oldItem, newItem) -> {
					if(oldItem != newItem) {
						modifiedID.add(scAL.get(j).getSubcomp_id());
						modifiedWeightage.add(Double.parseDouble(newItem.toString().replaceAll("[-+.^:,%]","")));
					}
				});
				btnaddSubcomponentMethod.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						Node[] nodesCreate  = new Node[3];
						try {
							nodesCreate[0] = FXMLLoader.load(getClass().getResource("/Templates/dialogModifyDelete.fxml"));
							Text createtitlelbl = (Text) nodesCreate[0].lookup("#titlelbl");
							Button createBtn = (Button) nodesCreate[0].lookup("#createUpdateBtn");
							Button createdeleteSubCompBtn = (Button) nodesCreate[0].lookup("#deleteSubCompBtn");
							TextField createsubcomp_nametxt = (TextField) nodesCreate[0].lookup("#subcomp_nametxt");
							TextField createsubcomp_desctxt = (TextField) nodesCreate[0].lookup("#subcomp_desctxt");
							ComboBox<String> createsubcomp_weightcmb = (ComboBox<String>) nodesCreate[0].lookup("#subcomp_weightcmb");
							createsubcomp_weightcmb.setOnShown(event -> createsubcomp_weightcmb.hide());
							createdeleteSubCompBtn.setVisible(false);
							createtitlelbl.setText("Create SubComponent");
							createBtn.setText("Create");
							createBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent arg0) {
									// TODO Auto-generated method stub
									Alert alert = new Alert(AlertType.CONFIRMATION, "Create SubComponent? ", ButtonType.YES, ButtonType.CANCEL);
									alert.showAndWait();
									if (alert.getResult() == ButtonType.YES) {
										SubComponent scasd = new SubComponent();
										String subcomp_name = createsubcomp_nametxt.getText();
										String subcomp_desc = createsubcomp_desctxt.getText();
										String comp_id = cpid;
										if(scasd.createSubComponent(comp_id, subcomp_name, subcomp_desc)!= true) {
											System.out.println("Failed to create Subcomponent!");
										}else{
										    Stage stage = (Stage) createBtn.getScene().getWindow();
										    stage.close();
										    Alert a = new Alert(AlertType.INFORMATION, "Successfully create subcomponent!");
									        a.setContentText("Successfully create subcomponent!");
									        a.showAndWait();
											subComponentVB.getChildren().clear();
									        executableInit(comp_id);
										}
									}
								}
							});
							Stage stage = new Stage();
							stage.initModality(Modality.APPLICATION_MODAL);
							stage.initOwner(mainStage.getScene().getWindow());
							stage.setScene(new Scene((Parent) nodesCreate[0]));
							stage.show();
						}catch(Exception e) {

							System.out.println(e);
							e.printStackTrace();  // This will give line number
						}
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
								Node[] nodes  = new Node[3];
								try {
									nodes[0] = FXMLLoader.load(getClass().getResource("/Templates/dialogModifyDelete.fxml"));
									Text titlelbl = (Text) nodes[0].lookup("#titlelbl");
									Button createUpdateBtn = (Button) nodes[0].lookup("#createUpdateBtn");
									Button deleteSubCompBtn = (Button) nodes[0].lookup("#deleteSubCompBtn");
									TextField subcomp_nametxt = (TextField) nodes[0].lookup("#subcomp_nametxt");
									TextField subcomp_desctxt = (TextField) nodes[0].lookup("#subcomp_desctxt");
									ComboBox<String> subcomp_weightcmb = (ComboBox<String>) nodes[0].lookup("#subcomp_weightcmb");
									subcomp_weightcmb.setOnShown(event -> subcomp_weightcmb.hide());
									
									
									if(titlelbl !=null) {
										titlelbl.setText("Modify Or Delete SubComponent");
										createUpdateBtn.setText("Update");
										subcomp_nametxt.setText(scAL.get(j).getSubcomp_name());
										subcomp_desctxt.setText(scAL.get(j).getSubcomp_desc());
										subcomp_weightcmb.getSelectionModel().select(Double.toString(scAL.get(j).getSubcomp_weight())+"%");
										subcomp_weightcmb.setOnShown(event -> subcomp_weightcmb.hide());
										createUpdateBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
											@Override
											public void handle(MouseEvent arg0) {
												// TODO Auto-generated method stub
												Alert alert = new Alert(AlertType.CONFIRMATION, "Delete SubComponent? ", ButtonType.YES, ButtonType.CANCEL);
												alert.showAndWait();

												if (alert.getResult() == ButtonType.YES) {
													String subcomp_name = subcomp_nametxt.getText();
													String subcomp_desc = subcomp_desctxt.getText();
													String subcomp_id = scAL.get(j).getSubcomp_id();
													SubComponent scasd = new SubComponent();
													if(scasd.updateSubComponent(subcomp_id, subcomp_name, subcomp_desc)!= true) {
														System.out.println("Failed to update Subcomponent!");
													}else{
													    Stage stage = (Stage) createUpdateBtn.getScene().getWindow();
													    stage.close();
													    Alert a = new Alert(AlertType.INFORMATION, "Successfully updated subcomponent!");
														a.showAndWait();
												        a.setContentText("Successfully updated subcomponent!");
												        a.show();
														subComponentVB.getChildren().clear();
												        executableInit(cpid);
													}
												}
											}
											
										});
										deleteSubCompBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

											@Override
											public void handle(MouseEvent arg0) {
												Alert alert = new Alert(AlertType.CONFIRMATION, "Delete SubComponent? ", ButtonType.YES, ButtonType.CANCEL);
												alert.showAndWait();
												if (alert.getResult() == ButtonType.YES) {
													String subcomp_id = scAL.get(j).getSubcomp_id();
													SubComponent scasd = new SubComponent();
													if(scasd.deleteSubComp(subcomp_id)!=true) {
														System.out.println("Failed to delete SubComponent!");
													}else{
													    Stage stage = (Stage) createUpdateBtn.getScene().getWindow();
													    stage.close();
													    Alert a = new Alert(AlertType.INFORMATION, "Successfully deleted subcomponent!");
														a.showAndWait();
												        a.setContentText("Successfully deleted subcomponent!");
												        a.show();
														subComponentVB.getChildren().clear();
												        executableInit(cpid);
													}
												}
												
												
											}
										});
									}else {
										System.out.println("title is empty");
									}
									Stage stage = new Stage();
									stage.initModality(Modality.APPLICATION_MODAL);
									stage.initOwner(mainStage.getScene().getWindow());
									stage.setScene(new Scene((Parent) nodes[0]));
									stage.show();
								} catch (Exception e) {
									System.out.println(e);
									e.printStackTrace();  // This will give line number
								}
							}
						}
					}
				});
				subComponentVB.getChildren().add(nodes[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		btnSaveWeightage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					Alert alert = new Alert(AlertType.CONFIRMATION, "Save Weightage? ", ButtonType.YES, ButtonType.CANCEL);
					alert.showAndWait();
					if (alert.getResult() == ButtonType.YES) {
						Double totalWeight = 10.0;
						Double dummyWeight = 0.0;
						for (int i = 0; i < nodes.length ; i++) {
							ComboBox<String> cmbSave =(ComboBox<String>) nodes[i].lookup("#subcomp_weightage");
							dummyWeight += Double.parseDouble(cmbSave.getValue().toString().replaceAll("[-+.^:,%]","").substring(0, 1));
							
						}
						System.out.println("Dummy weight:"+dummyWeight);
						if(dummyWeight == 10.0) {
							for(int i = 0; i<modifiedID.size(); i++) {
								String subcomp_id = (modifiedID.get(i));
								Double weightage = (modifiedWeightage.get(i));
								SubComponent scomp = new SubComponent();
								System.out.println(weightage);
								System.out.println(subcomp_id);
								scomp.updateSubComponentWeight(subcomp_id, weightage);
							}
						}else {
							Alert alerts = new Alert(AlertType.ERROR, "Weightage must add up to 100%!");
							alerts.showAndWait();
							subComponentVB.getChildren().clear();
					        executableInit(cpid);
						}
					}
				}
			}
		});
		
	}

	@FXML
	void backToComponentMethod(ActionEvent event) {
		Parent dashboard;
		try {
			dashboard = FXMLLoader.load(getClass().getResource("/Templates/manageComp.fxml"));
			Scene dashboardScene = new Scene(dashboard);
			Stage dashboardStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			dashboardStage.setScene(dashboardScene);
			dashboardStage.show();
			dashboardPane.toFront();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
					CreateText ct = new CreateText();
					ct.logout();
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