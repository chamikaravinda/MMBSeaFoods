package application.Controllers;

import java.io.IOException; 
import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.Boat;
import application.Models.Fish_Lot;
import application.Models.ProfiteAndLose;
import application.Models.Third_Party_Account;
import application.Models.Vehicles;
import application.Services.BoatService;
import application.Services.Fish_LotServices;
import application.Services.ProfiteAndLossService;
import application.Services.Third_Party_AccountServices;
import application.Services.VehiclesServices;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddBoatController implements Initializable {
	
	@FXML
	private AnchorPane Boats;
	
	@FXML
	private JFXTextField txtBoatName;
	
	@FXML
	private JFXTextField txtMobile;
	

	
	AnchorPane back;
	
	BoatService service=new BoatService();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		RequiredFieldValidator boatName = new RequiredFieldValidator();
		RequiredFieldValidator mobile = new RequiredFieldValidator();

		
		txtBoatName.getValidators().add(boatName);
		boatName.setMessage("Please input the Lorry Number");
		
		txtBoatName.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtBoatName.validate();
				}
				
			}
			
		});
		
		txtMobile.getValidators().add(mobile);
		mobile.setMessage("Please input correct values");
		
		txtMobile.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtMobile.validate();
				}
				
			}
			
		});
		
	

	
}
	
	public void AddBoat(ActionEvent event) throws SQLException, IOException {

		Boat boat=new Boat();
		boat.setBoatNameorNumber(txtBoatName.getText());
		
		boat.setMobile(txtMobile.getText());
		
		if(service.addBoat(boat)) {
			
			Notifications notifications = Notifications.create();
			notifications.title("Succesfull");
			notifications.text("Boat added succesfully");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showConfirm();
			
		}else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Boat adding unsuccesfull");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}
		
		
	}

	
	 void setNode(Node node) {
			
			 Boats.getChildren().clear();
			 Boats.setTopAnchor(node,0.0);
			 Boats.setRightAnchor(node, 0.0);
			 Boats.setLeftAnchor(node, 0.0);
			 Boats.setBottomAnchor(node, 0.0);
			 Boats.getChildren().addAll((Node) node);

	        FadeTransition ft = new FadeTransition(Duration.millis(1500));
	        ft.setNode(node);
	        ft.setFromValue(0.1);
	        ft.setToValue(1);
	        ft.setCycleCount(1);
	        ft.setAutoReverse(false);
	        ft.play();
	        
		
	    }
		
	
	public void back(ActionEvent event) throws IOException {
		back=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Boats.fxml"));
		setNode(back);
		
	}
	
}
