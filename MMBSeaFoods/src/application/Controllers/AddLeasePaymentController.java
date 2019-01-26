package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import application.Models.ProfiteAndLose;
import application.Models.Vehicles;
import application.Models.Vehicles_Leased_Payments;
import application.Services.ProfiteAndLossService;
import application.Services.VehiclesServices;

public class AddLeasePaymentController implements Initializable{
	
	@FXML
	private AnchorPane Vehicles;
	
	@FXML 
	private JFXComboBox<String> cmbVehicles;
	
	@FXML
	private JFXTextField txtPaidAmount;

	ObservableList<String> vehiclesList =FXCollections.observableArrayList();
	
	VehiclesServices services =new VehiclesServices();
	ProfiteAndLossService entryService=new ProfiteAndLossService();
	
	
	
	AnchorPane add;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		NumberValidator paidAmount = new NumberValidator();
		
		txtPaidAmount.getValidators().add(paidAmount);
		paidAmount.setMessage("Please input the Vehicle Number");
		
		paidAmount.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtPaidAmount.validate();
				}
				
			}
			
		});
		
		vehiclesList.clear();
		try {
			ArrayList<Vehicles> vlist = services.getVehicles();
			for(Vehicles vehicles:vlist) {
				vehiclesList.add(vehicles.getVehicle_No());			
			}
			
			cmbVehicles.setItems(vehiclesList);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void AddPayment(ActionEvent event) throws SQLException{
		
		String VNo=cmbVehicles.getValue();
		String payAmount=txtPaidAmount.getText();
		
		Vehicles  vehicle =services.getVehicle(VNo);
		Vehicles_Leased_Payments payment =new Vehicles_Leased_Payments();
		payment.setVehicle_ID(vehicle.getID());
		payment.setPaid_Amount(Double.parseDouble(payAmount));
		Date date = new Date();
		payment.setPayment_Date(date);
		
		if(services.Vehicles_Leased_Payment_Add(payment)) {
		
			vehicle.setLast_Payment(date.toString());
		
			Double paidAmt = vehicle.getPaid_Amount()+(Double.parseDouble(payAmount));
			vehicle.setPaid_Amount(paidAmt);
			
			double ToPay = vehicle.getTo_Pay() - (Double.parseDouble(payAmount));
			vehicle.setTo_Pay(ToPay);
			
			//enter entry for the profite and lose account 
			
			ProfiteAndLose newEntry =new ProfiteAndLose();
			
			newEntry.setReason("Leasing Premiume Payment For the Vehicel "+vehicle.getVehicle_No());
			newEntry.setDate(date.toString());
			newEntry.setPaid(payment.getPaid_Amount());
			newEntry.setTo_Pay(0);
			entryService.addProfitAndLossEntry(newEntry);
			
			if(services.updateVehicle(vehicle)) {
			
				Notifications notifications = Notifications.create();
				notifications.title("Succesfull");
				notifications.text("Payment added succesfully");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showConfirm();
			}
			
		
		}else {
			
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Vehicle adding unsuccesfull.Vehicle may be already in the database");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
			
			
		}
		
	}
	//switch windows 

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
