package application.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;



public class DashboardHomeController implements Initializable{
	
	@FXML
	private Label orders;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		orders.setText("200");
		
	}

}
