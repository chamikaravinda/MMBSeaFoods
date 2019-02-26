package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Models.Boat_Account;
import application.Models.F_Fish_Buyers_Account_Uncleard;
import application.Models.Local_Buyers_Account;
import application.Models.Locl_Buyers_Account_Uncleared;
import application.Services.AccountServices;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LocalAddBuyerRecievedController implements Initializable {
	
	@FXML
	private AnchorPane Accounts;
	
	AnchorPane add;
	
	@FXML
	private Label lblBuyerName;
	
	private StringProperty id;
	
	@FXML private TableView<Locl_Buyers_Account_Uncleared> tblvBuyersDetails;
	
	@FXML private TableColumn<?, ?> tblcDate;
	@FXML private TableColumn<?, ?> tblcReason;
	@FXML private TableColumn<?, ?> tblcTopay;

	
	AccountServices accountServices=new AccountServices();
	
	private ObservableList<Locl_Buyers_Account_Uncleared> buyerDetailsList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		
		Platform.runLater(() -> {
			
			System.out.println("hi"+lblBuyerName.getText());
			
			int id=accountServices.getBuyerIDByName(lblBuyerName.getText());
			
			System.out.println("id "+id);
			
			buyerDetailsList.clear();
			
			
			ArrayList<Locl_Buyers_Account_Uncleared> boat_list = accountServices.getAllBuyerListUncleared(id);
			
			for( Locl_Buyers_Account_Uncleared boat : boat_list ) {
				buyerDetailsList.add(boat);
			} 
			tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
			tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
			tblcTopay.setCellValueFactory(new PropertyValueFactory<>("To_Pay"));

			tblvBuyersDetails.setItems(buyerDetailsList);
		
		 });
		
		
	}
	
	
	void setNode(Node node) {
		
		Accounts.getChildren().clear();
		Accounts.setTopAnchor(node,0.0);
		Accounts.setRightAnchor(node, 0.0);
		Accounts.setLeftAnchor(node, 0.0);
		Accounts.setBottomAnchor(node, 0.0);
		Accounts.getChildren().addAll((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        
	
    }
	
	
	
	@FXML
    void done(ActionEvent event) throws IOException {


		String buyerName=lblBuyerName.getText();
		
		
		
		int id = accountServices.getBuyerIDByName(buyerName);
		
		
		
		for (Locl_Buyers_Account_Uncleared item : tblvBuyersDetails.getItems()) {
			
			Locl_Buyers_Account_Uncleared boat= new Locl_Buyers_Account_Uncleared();
			
			boat.setID(item.getID());
			boat.setReason(item.getReason());
			boat.setDate(item.getDate());
			boat.setTo_Pay(0);
			boat.setPaid(item.getTo_Pay());
			boat.setBuyer_ID(id);
			
			
			accountServices.AddNewRecieved(boat);
			System.out.println(boat.getBuyer_ID());
			accountServices.setUnclearedBuyerRecieved(boat.getBuyer_ID());
			
			
		}
		
		
    }

	public void getBuyerName(String text) {

		
		lblBuyerName.setText(text);
		
	}
	
	@FXML
    void back(ActionEvent event)throws IOException {
    	add=FXMLLoader.load(getClass().getResource("/application/Views/Accounts/Accounts.fxml"));
		setNode(add);
    	
    }
}
