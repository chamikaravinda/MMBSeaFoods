/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controllers;

import com.jfoenix.controls.JFXButton;

import application.Models.Boat;
import application.Models.Buyers;
import application.Models.Vehicles;
import application.Services.BoatService;
import application.Services.VehiclesServices;

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

public class BoatsController implements Initializable {

    @FXML
    private AnchorPane ftrade;
    
    @FXML
	private TableView<Boat> tblboats;
    
    @FXML
	private TableColumn<?,?> clmName;
	
	@FXML
	private TableColumn<?,?> clmMobile;
	
	@FXML
	private TableColumn<?,?> clmOwner;
	
	ObservableList<Boat> list = FXCollections.observableArrayList();

    AnchorPane lots,stoks,boats,buyers,fishtypes,newLots;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
	    list.clear();
		BoatService service = new BoatService();
		
		ArrayList<Boat> Blist = null;
		try {
			Blist  = service.getBoat();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Boat sup : Blist) {
			list.add(sup);
		}	
		
		clmName.setCellValueFactory(new PropertyValueFactory<>("BoatNameorNumber"));
		clmMobile.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
		clmOwner.setCellValueFactory(new PropertyValueFactory<>("Owner"));
		
		tblboats.setItems(list);
    }
    
	//Set selected node to a content holder
    void setNode(Node node) {
        ftrade.getChildren().clear();
        ftrade.setTopAnchor(node,0.0);
        ftrade.setRightAnchor(node, 0.0);
        ftrade.setLeftAnchor(node, 0.0);
        ftrade.setBottomAnchor(node, 0.0);
        ftrade.getChildren().addAll((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    public void switchStock(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Stocks.fxml"));
		setNode(lots);
		
	}
    
    public void AddNewBoat(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/NewBoat.fxml"));
		setNode(lots);
		
	}
    
    public void switchLot(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Ftrade.fxml"));
		setNode(lots);
		
	}
    
    public void switchBoat(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Boats.fxml"));
		setNode(lots);
		
	}
    
    public void switchBuyers(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Buyers.fxml"));
		setNode(lots);
		
	}
    
    public void switchFishTypes(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/FishTypes.fxml"));
		setNode(lots);
		
	}
    
    public void editNewBoat(ActionEvent event) throws IOException {
    	
    	Boat boat=tblboats.getSelectionModel().getSelectedItem();
    	
    
        
    	if(boat != null) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Views/Ftrade/EditBoat.fxml"));
			Parent root = loader.load();
    		EditBoatController controller = loader.<EditBoatController>getController();
    		String id=Integer.toString(boat.getID());
			controller.setID(id); 
			
			setNode(root);
    	}
    	
    }

    

}
