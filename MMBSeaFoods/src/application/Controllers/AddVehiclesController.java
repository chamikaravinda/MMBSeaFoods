package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.Vehicles;
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
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddVehiclesController implements Initializable {
	
	@FXML
	private AnchorPane Vehicles;
	
	@FXML
	private JFXTextField txtPremiumeAmount;
	
	@FXML
	private JFXTextField txtPaidAmount;
	
	@FXML
	private JFXTextField txtVno;
	
	@FXML
	private JFXTextField txtTotalLease;
	
	AnchorPane add;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		RequiredFieldValidator VnumValidator = new RequiredFieldValidator();
		NumberValidator PaidAmtValidator = new NumberValidator();
		NumberValidator PremiumeAmtValidator = new NumberValidator();
		NumberValidator TotalLeaseAmtValidator = new NumberValidator();
		
		txtVno.getValidators().add(VnumValidator);
		VnumValidator.setMessage("Please input the Vehicle Number");
		
		txtVno.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtVno.validate();
				}
				
			}
			
		});
		
		txtPaidAmount.getValidators().add(PaidAmtValidator);
		PaidAmtValidator.setMessage("Please input correct values");
		
		txtPaidAmount.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtPaidAmount.validate();
				}
				
			}
			
		});
		
	
	
	txtPremiumeAmount.getValidators().add(PremiumeAmtValidator);
	PremiumeAmtValidator.setMessage("Please input correct values");
	
	txtPremiumeAmount.focusedProperty().addListener(new ChangeListener<Boolean>() {

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if(!newValue) {
				txtPremiumeAmount.validate();
			}
			
		}
		
	});
	
	txtTotalLease.getValidators().add(TotalLeaseAmtValidator);
	TotalLeaseAmtValidator.setMessage("Please input correct values");
	
	txtTotalLease.focusedProperty().addListener(new ChangeListener<Boolean>() {

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if(!newValue) {
				txtTotalLease.validate();
			}
			
		}
		
	});
	
}
	
	public void AddVehicle(ActionEvent event) throws SQLException, IOException {
		
		Vehicles vehicel=new Vehicles();
		VehiclesServices service =new VehiclesServices();
		
		vehicel.setVehicle_No(txtVno.getText());
		vehicel.setTotal_Lease(Double.parseDouble(txtTotalLease.getText()));
		vehicel.setPaid_Amount(Double.parseDouble(txtPaidAmount.getText()));
		vehicel.setPremium_Amount(Double.parseDouble(txtPremiumeAmount.getText()));
		vehicel.setTo_Pay(vehicel.getTotal_Lease()-vehicel.getPaid_Amount());
		
		if(service.addVehicles(vehicel)) {
			
			Notifications notifications = Notifications.create();
			notifications.title("Succesfull");
			notifications.text("Vehicle added succesfully");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showConfirm();
			
			add=FXMLLoader.load(getClass().getResource("../Views/Vehicles/Vehicles.fxml"));
			setNode(add);
			
		}
		else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Vehicle adding unsuccesfull.Vehicle may be already in the database");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}
		
	}

	
	 void setNode(Node node) {
			
			Vehicles.getChildren().clear();
			Vehicles.setTopAnchor(node,0.0);
			Vehicles.setRightAnchor(node, 0.0);
			Vehicles.setLeftAnchor(node, 0.0);
			Vehicles.setBottomAnchor(node, 0.0);
			Vehicles.getChildren().addAll((Node) node);

	        FadeTransition ft = new FadeTransition(Duration.millis(1500));
	        ft.setNode(node);
	        ft.setFromValue(0.1);
	        ft.setToValue(1);
	        ft.setCycleCount(1);
	        ft.setAutoReverse(false);
	        ft.play();
	        
		
	    }
		
	
	public void back(ActionEvent event) throws IOException {
		add=FXMLLoader.load(getClass().getResource("../Views/Vehicles/Vehicles.fxml"));
		setNode(add);
		
	}
	
}
