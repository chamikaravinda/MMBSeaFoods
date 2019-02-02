package application.Controllers;

import java.io.IOException; 
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.crypto.Data;

import application.Models.Vehicles;
import application.Services.VehiclesServices;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class VehiclesController implements Initializable{
	
	@FXML
	private AnchorPane Vehicles;
	
	@FXML
	private TableView<Vehicles> vehicles;
	
	@FXML
	private TableColumn<?,?> clmVno;
	
	@FXML
	private TableColumn<?,?> clmTotalLease;
	
	@FXML
	private TableColumn<?,?> clmPaidAmt;
	
	@FXML
	private TableColumn<?,?> clmRemainAmt;
	
	@FXML
	private TableColumn<?,?> clmPremiumAmt;
	
	@FXML
	private TableColumn<?,?> clmLastPayment;
	
	public ObservableList<Vehicles> list = FXCollections.observableArrayList();
	
	AnchorPane add;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
			list.clear();
			VehiclesServices service = new VehiclesServices();
			ArrayList<Vehicles> Vlist = null;
			try {
				Vlist = service.getVehicles();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			for(Vehicles sup : Vlist) {
				sup.setSTotal_Lease(String.format ("%2.0f", sup.getTotal_Lease()));
				sup.setSPaid_Amount(String.format ("%2.0f", sup.getPaid_Amount()));
				sup.setSTo_Pay(String.format ("%2.0f", sup.getTo_Pay()));
				sup.setSPremium_Amount(String.format("%2.0f", sup.getPremium_Amount()));
				sup.setLast_Payment(sup.getLast_Payment());
			
				
				
				list.add(sup);
			}
			
			clmVno.setCellValueFactory(new PropertyValueFactory<>("Vehicle_No"));
			clmTotalLease.setCellValueFactory(new PropertyValueFactory<>("STotal_Lease"));
			clmPaidAmt.setCellValueFactory(new PropertyValueFactory<>("SPaid_Amount"));
			clmRemainAmt.setCellValueFactory(new PropertyValueFactory<>("STo_Pay"));
			clmPremiumAmt.setCellValueFactory(new PropertyValueFactory<>("SPremium_Amount"));
			clmLastPayment.setCellValueFactory(new PropertyValueFactory<>("Last_Payment"));
			
			vehicles.setItems(list);
	}
	
	
	//----------- New Vehicles -----------
	
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
		
	
	public void NewVehicle(ActionEvent event) throws IOException {
		add=FXMLLoader.load(getClass().getResource("../Views/Vehicles/Add.fxml"));
		setNode(add);
		
	}
	
	public void NewPayment(ActionEvent event) throws IOException {
		add=FXMLLoader.load(getClass().getResource("../Views/Vehicles/NewLeasePayment.fxml"));
		setNode(add);
		
	}
	
	

}
