package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import application.Models.Vehicles;
import application.Services.VehiclesServices;

public class AddLeasePayment implements Initializable{
	
	@FXML
	private AnchorPane Vehicles;
	
	@FXML 
	private JFXComboBox<String> cmbVehicles;

	ObservableList<String> vehiclesList =FXCollections.observableArrayList();
	
	VehiclesServices services =new VehiclesServices();
	
	
	
	AnchorPane add;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	
	public void AddPayment(){
		
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
