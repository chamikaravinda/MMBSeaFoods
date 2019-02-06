/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controllers;

import com.jfoenix.controls.JFXButton;

import application.Models.Boat;
import application.Models.Buyers;
import application.Services.BuyerService;

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
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class BuyersController implements Initializable {

    @FXML
    private AnchorPane Buyers;
    
    @FXML
	private TableView<Buyers> tblBuyers;
    
    @FXML
	private TableColumn<?,?> clmName;
	
	@FXML
	private TableColumn<?,?> clmMobile;
	
	ObservableList<Buyers> list = FXCollections.observableArrayList();

    AnchorPane lots,stoks,boats,buyers,fishtypes,newLots;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
    	list.clear();
    	BuyerService service =new BuyerService();
    	ArrayList<Buyers> Blist = null;
    	
    	try {
			Blist=service.getBuyers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	for (Buyers  buyer :Blist) {
    		list.add(buyer);
    	}
    	
    	clmName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    	clmMobile.setCellValueFactory(new PropertyValueFactory<>("Mobile_No"));
    	
    	tblBuyers.setItems(list);
    }
    
	//Set selected node to a content holder

    public void switchStock(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Stocks.fxml"));
		setNode(lots);
		
	}
    
    public void AddNewBuyer(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("../Views/Ftrade/NewBuyer.fxml"));
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
    
    
    public void editType(ActionEvent envent) throws IOException {
    	
    	Buyers buyers =tblBuyers.getSelectionModel().getSelectedItem();
    	
    	if(buyers != null) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Ftrade/EditBuyer.fxml"));
			Parent root = loader.load();
    		EditBuyerController controller = loader.<EditBuyerController>getController();
    		String id=Integer.toString(buyers.getID());
			controller.setID(id); 
			
			setNode(root);
    	}
    	
    }
    
    void setNode(Node node) {
    	Buyers.getChildren().clear();
    	Buyers.setTopAnchor(node,0.0);
    	Buyers.setRightAnchor(node, 0.0);
    	Buyers.setLeftAnchor(node, 0.0);
    	Buyers.setBottomAnchor(node, 0.0);
    	Buyers.getChildren().addAll((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    

}
