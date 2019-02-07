package application.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Models.Commition;
import application.Models.Fish_Lot;
import application.Models.LocalSales;
import application.Services.AccountServices;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccountFSalesController implements Initializable{
	
	
	@FXML
	private AnchorPane Accounts;
	
	AnchorPane add;
	
	@FXML private TableView<Fish_Lot> tblcFSales;
	
	@FXML private TableColumn<?, ?> tblcAddedDate;
	@FXML private TableColumn<?, ?> tblcLorry;
	@FXML private TableColumn<?, ?> tblcBuyPrice;
	@FXML private TableColumn<?, ?> tblcSellWeight;
	@FXML private TableColumn<?, ?> tblcSellPrice;
	
	
	private ObservableList<Fish_Lot> boatDetailsList = FXCollections.observableArrayList();
	
	AccountServices accountServices=new AccountServices();
	
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {



		boatDetailsList.clear();
		
		
		ArrayList<Fish_Lot> boat_list = accountServices.getAllSaleListForeign();
		
		for( Fish_Lot boat : boat_list ) {
			boatDetailsList.add(boat);
		} 
		
		
		
	    
	    
		
		tblcAddedDate.setCellValueFactory(new PropertyValueFactory<>("Added_Date"));
		tblcLorry.setCellValueFactory(new PropertyValueFactory<>("Lorry_Number"));
		tblcBuyPrice.setCellValueFactory(new PropertyValueFactory<>("Buying_Weight"));
		tblcSellWeight.setCellValueFactory(new PropertyValueFactory<>("Sold_Weight"));
		tblcSellPrice.setCellValueFactory(new PropertyValueFactory<>("Sold_price"));

		
		tblcFSales.setItems(boatDetailsList);
		
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
}
