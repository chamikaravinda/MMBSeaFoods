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
import application.Models.Buyers;
import application.Services.BoatService;
import application.Services.BuyerService;
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

public class AddBuyerController implements Initializable {
	
	@FXML
	private AnchorPane Buyers;
	
	@FXML
	private JFXTextField txtName;
	
	@FXML
	private JFXTextField txtMobile;
	

	
	AnchorPane back;
	
	BuyerService service=new BuyerService();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		RequiredFieldValidator name = new RequiredFieldValidator();
		RequiredFieldValidator mobile = new RequiredFieldValidator();

		
		txtName.getValidators().add(name);
		name .setMessage("Please input the Buyer Name");
		
		txtName.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtName.validate();
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

		Buyers buyer=new Buyers();
		buyer.setName(txtName.getText());
		buyer.setMobile_No(txtMobile.getText());
		
		if( !txtName.getText().isEmpty() && !txtMobile.getText().isEmpty() ) {	
			
			if(service.addBuyer(buyer)) {
				
				Notifications notifications = Notifications.create();
				notifications.title("Succesfull");
				notifications.text("Buyer added succesfully");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showConfirm();
				
			}else {
				Notifications notifications = Notifications.create();
				notifications.title("Error");
				notifications.text("Buyer adding unsuccesfull");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showError();
			}
			
		}	
	}

	
	 void setNode(Node node) {
			
		 Buyers.getChildren().clear();
		 Buyers.setTopAnchor(node,0.0);
		 Buyers.setRightAnchor(node, 0.0);
		 Buyers.setLeftAnchor(node, 0.0);
		 Buyers.setBottomAnchor(node, 0.0);
		 Buyers.getChildren().addAll((Node) node);

	        FadeTransition ft = new FadeTransition(Duration.millis(1500));
	        ft.setNode(node);
	        ft.setFromValue(0.1);
	        ft.setToValue(1);
	        ft.setCycleCount(1);
	        ft.setAutoReverse(false);
	        ft.play();
	        
		
	    }
		
	
	public void back(ActionEvent event) throws IOException {
		back=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Buyers.fxml"));
		setNode(back);
		
	}
	
}
