package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Models.Vehicles;
import application.Models.Vehicles_Leased_Payments;
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

public class VehicleAllPaymentsController implements Initializable{

    @FXML
    private AnchorPane Vehicles;

    @FXML
    private TableView<Vehicles_Leased_Payments> leasePayments;

    @FXML
    private TableColumn<?, ?> clmVno;

    @FXML
    private TableColumn<?, ?> clmamount;

    @FXML
    private TableColumn<?, ?> clmPayemntdate;
    
    AnchorPane add;

	public ObservableList<Vehicles_Leased_Payments> list = FXCollections.observableArrayList();
	
	VehiclesServices service =new VehiclesServices();
	
    @FXML
    void AllPayments(ActionEvent event)  throws IOException {

    	add=FXMLLoader.load(getClass().getResource("/application/Views/Vehicles/payments.fxml"));
		setNode(add);

    }

    @FXML
    void AllVehicles(ActionEvent event) throws IOException {

    	add=FXMLLoader.load(getClass().getResource("/application/Views/Vehicles/Vehicles.fxml"));
		setNode(add);
		
    }
   
	@FXML
	void NewPayment(ActionEvent event) throws IOException {
		add=FXMLLoader.load(getClass().getResource("/application/Views/Vehicles/NewLeasePayment.fxml"));
		setNode(add);
		
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		list.clear();
		try {
			ArrayList <Vehicles_Leased_Payments > payments= service.getPayments();
			
			for(Vehicles_Leased_Payments payment: payments) {
				
				Vehicles paymentVno =service.getVehiclebyId(payment.getVehicle_ID());
				payment.setVno(paymentVno.getVehicle_No());
				payment.setSPaid_Amount("Rs." + String.format("%2.2f", payment.getPaid_Amount()));
				list.add(payment);
			}
			
			clmVno.setCellValueFactory(new PropertyValueFactory<>("Vno"));
			clmamount.setCellValueFactory(new PropertyValueFactory<>("SPaid_Amount"));
			clmPayemntdate.setCellValueFactory(new PropertyValueFactory<>("Payment_Date"));
			
			leasePayments.setItems(list);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
