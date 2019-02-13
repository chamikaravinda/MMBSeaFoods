package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.Models.Boat_Account;
import application.Models.LocalSales;
import application.Services.AccountServices;
import javafx.animation.FadeTransition;
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

public class AccountLocalSalesController implements Initializable{
	
    @FXML
    private AnchorPane Accounts;

    @FXML
    private Label BoatName;
    
    AnchorPane add;

	/*
	
	private ObservableList<LocalSales> boatDetailsList = FXCollections.observableArrayList();
	
	AccountServices accountServices=new AccountServices();

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		boatDetailsList.clear();
		
		
		ArrayList<LocalSales> boat_list = accountServices.getAllSaleList();
		
		for( LocalSales boat : boat_list ) {
			boat.setBuyerName(accountServices.getBuyerNameByID(boat.getBuyerID()));
			boatDetailsList.add(boat);
		} 
		
		
		tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		tblcBuyerName.setCellValueFactory(new PropertyValueFactory<>("BuyerName"));
		FishType.setCellValueFactory(new PropertyValueFactory<>("FishType"));
		TotalWeight.setCellValueFactory(new PropertyValueFactory<>("TotalWeight"));

		tblvSales.setItems(boatDetailsList);
		
	}
	
	*/
	

	
	
	
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
	    void back(ActionEvent event)throws IOException {
	    	
	    	add= FXMLLoader.load(getClass().getResource("../Views/Accounts/Accounts.fxml"));
	    	setNode(add);

	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

}