package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
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
import sun.misc.Cleaner;
import application.Models.Vehicles;
import application.Services.VehiclesServices;

public class EditVehiclesController implements Initializable{

    @FXML
    private AnchorPane Vehicles;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXTextField txtVno;

    @FXML
    private JFXTextField txtTotalLease;

    @FXML
    private JFXTextField txtPaidAmount;

    @FXML
    private JFXTextField txtPremiumeAmount;

    @FXML
    private JFXComboBox<String> cmbstatus;

    private int vehicleId;
    
    AnchorPane edit;
    
    protected void setID(int id ) {
    	vehicleId=id;
    }
    
    ObservableList<String> status =FXCollections.observableArrayList();
    
    VehiclesServices service =new VehiclesServices();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	  	Platform.runLater(() ->{
			
			Vehicles vehicle =new Vehicles();
			try {
				vehicle =service.getVehiclebyId(vehicleId);
				txtVno.setText(vehicle.getVehicle_No());
				txtTotalLease.setText(String.format("%2.2f",vehicle.getTotal_Lease()));
				txtPaidAmount.setText(String.format("%2.2f",vehicle.getPaid_Amount()));
				txtPremiumeAmount.setText(String.format("%2.2f",vehicle.getPremium_Amount()));
				
				status.clear();
				
				status.add("Incomplete");
				status.add("Complete");
				
				cmbstatus.setItems(status);
				
				if(vehicle.getPayment_status().equals("Incomplete")) {
				cmbstatus.getSelectionModel().select(0);
				}else {
					cmbstatus.getSelectionModel().select(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	   	});
	}
	

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void updateVehicel(ActionEvent event) throws SQLException, IOException {
    	
    	Vehicles vehicle =new Vehicles();
    	
    	vehicle.setTotal_Lease(Double.parseDouble(txtTotalLease.getText()));
    	vehicle.setPaid_Amount(Double.parseDouble(txtPaidAmount.getText()));
    	vehicle.setPremium_Amount(Double.parseDouble(txtPremiumeAmount.getText()));
    	vehicle.setVehicle_No(txtVno.getText());
    	vehicle.setPayment_status(cmbstatus.getValue());
    	vehicle.setID(vehicleId);
    	vehicle.setTo_Pay(vehicle.getTotal_Lease()-vehicle.getPaid_Amount());
    	

    	if(service.editVehicleUpdate(vehicle)) {
    		Notifications notifications = Notifications.create();
			notifications.title("Succesfull");
			notifications.text("Vehicle update succesfully");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showConfirm();
			
			edit=FXMLLoader.load(getClass().getResource("/application/Views/Vehicles/Vehicles.fxml"));
			setNode(edit);
    	}else {
			
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Vehicle updating unsuccesfull");
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
}
