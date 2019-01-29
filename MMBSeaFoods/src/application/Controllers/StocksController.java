/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controllers;

import com.jfoenix.controls.JFXButton;

import application.Models.Fish_Lot;
import application.Models.Vehicles;
import application.Services.Fish_LotServices;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class StocksController implements Initializable {

    @FXML
    private AnchorPane Stocks;
    /*
    @FXML
	private TableView<Fish_Lot> tableLots;
	
	@FXML
	private TableColumn<?,?> clmDate;
	
	@FXML
	private TableColumn<?,?> clmWeight;
	
	@FXML
	private TableColumn<?,?> clmLorry;
	
	@FXML
	private TableColumn<?,?> clmBprice;
	
	@FXML
	private TableColumn<?,?> clmsell;
	
	
	public ObservableList<Fish_Lot> list = FXCollections.observableArrayList();
    */
    AnchorPane lots,stoks,boats,buyers,fishtypes,newLots;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
    	/*list.clear();
    	Fish_LotServices service =new Fish_LotServices();
    	ArrayList<Fish_Lot> lots=null;
    	try {
		 lots =service.getUnslodLots();
		 
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	for(Fish_Lot lot : lots) {
			list.add(lot);
		}
    	
    	clmDate.setCellValueFactory(new PropertyValueFactory<>("Added_Date"));
    	clmWeight.setCellValueFactory(new PropertyValueFactory<>("Buying_Weight"));
    	clmLorry.setCellValueFactory(new PropertyValueFactory<>("Lorry_Number"));
    	clmBprice.setCellValueFactory(new PropertyValueFactory<>("buying_price"));
		
    	tableLots.setItems(list);*/

    }
    
	//Set selected node to a content holder
    void setNode(Node node) {
    	Stocks.getChildren().clear();
    	Stocks.setTopAnchor(node,0.0);
    	Stocks.setRightAnchor(node, 0.0);
    	Stocks.setLeftAnchor(node, 0.0);
    	Stocks.setBottomAnchor(node, 0.0);
    	Stocks.getChildren().addAll((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    public void switchStock(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Stocks.fxml"));
		setNode(lots);
		
	}
    
    public void AddStoks(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("../Views/Ftrade/NewLot.fxml"));
		setNode(lots);
		
	}
    
    public void switchLot(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Ftrade.fxml"));
		setNode(lots);
		
	}
    
    public void switchBoat(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Boats.fxml"));
		setNode(lots);
		
	}
    
    public void switchBuyers(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Buyers.fxml"));
		setNode(lots);
		
	}
    
    public void switchFishTypes(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("../Views/Ftrade/FishTypes.fxml"));
		setNode(lots);
		
	}
    
    

    

}
