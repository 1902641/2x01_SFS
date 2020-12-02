package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Component;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class componentController{

    @FXML 
    Label compNameLbl;

    @FXML
    Label compDescLbl;

    @FXML
    Label weightLbl;
    
	private componentController componentController;
    
    public componentController() {
    	super();
    }
    

}
