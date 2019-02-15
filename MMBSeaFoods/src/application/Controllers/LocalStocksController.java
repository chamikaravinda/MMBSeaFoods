
package application.Controllers;

import com.jfoenix.controls.JFXButton;

import application.Models.*;
import application.Services.*;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jws.soap.SOAPBinding.Use;

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

public class LocalStocksController implements Initializable {
	
		@FXML
	    private AnchorPane LocalStocks;

	    @FXML
	    private TableView<LFish_stock> LstockTable;

	    @FXML
	    private TableColumn<?, ?> FishName;

	    @FXML
	    private TableColumn<?, ?> TotalWeight;

	    @FXML
	    private JFXButton switchNewStocks;

	    @FXML
	    private JFXButton switchStocks;

	    @FXML
	    private JFXButton switchBoats;

	    @FXML
	    private JFXButton switchBuyers;

	    @FXML
	    private JFXButton switchFishTypes;
	    
	    //if you want to load to date to table use that below method
	    public ObservableList<LFish_stock> list = FXCollections.observableArrayList();
	    
	    AnchorPane add,LocalNewStocks; //handle buttons that contains in Anchorpane 
	    
	    LFish_stockService service= new LFish_stockService(); //Database handling
	    Local_Fish_typesServices serviceB= new Local_Fish_typesServices();//Data base handling
	    ArrayList<LFish_stock> Llots=null;
	    @Override
	    public void initialize(URL url, ResourceBundle rb) {
	    	
	    	list.clear();//to remove temporary values
	    	try {
				Llots=service.getUnsoldLocalStocks();
				
				for(LFish_stock Llot: Llots) {
		    		
			    	  Local_Fish_types localfishtypes  = serviceB.getLocalfishTypes(Llot.getFish_Type());
			    	  Llot.setFishName(localfishtypes.getName());
			    	  
			    	  list.add(Llot);
				}		
			FishName.setCellValueFactory(new PropertyValueFactory<>("FishName"));
	    	TotalWeight.setCellValueFactory(new PropertyValueFactory<>("Total_Weight"));
	    	
	    	LstockTable.setItems(list);
		    	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	
	    	//use to change the FXML 
	    	
	    	
	    	
	    }
	    
	    
	    void setNode(Node node) {
	    	LocalStocks.getChildren().clear();
	    	LocalStocks.setTopAnchor(node,0.0);
	    	LocalStocks.setRightAnchor(node, 0.0);
	    	LocalStocks.setLeftAnchor(node, 0.0);
	    	LocalStocks.setBottomAnchor(node, 0.0);
	    	LocalStocks.getChildren().addAll((Node) node);

	        FadeTransition ft = new FadeTransition(Duration.millis(1500));
	        ft.setNode(node);
	        ft.setFromValue(0.1);
	        ft.setToValue(1);
	        ft.setCycleCount(1);
	        ft.setAutoReverse(false);
	        ft.play();
	    }

	    @FXML
	    private void switchNewStocks(ActionEvent event) throws IOException {
	    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LNewStock.fxml"));
	        setNode(add);
	    }
	    
	    
	    @FXML
	    public void switchLocalBoats(ActionEvent event)throws IOException {
	    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LBoats.fxml"));
	        setNode(add);

	    }
	    
	   
	    @FXML
	    public void switchLocalBuyers(ActionEvent event) throws IOException {
	    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LBuyers.fxml"));
	        setNode(add);

	    }
	    
	    @FXML
	    void switchFishTypes(ActionEvent event) throws IOException {
	    	
	    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LFishTypes.fxml"));
	        setNode(add);


	    }
	    
	    @FXML
	    void switchLocalSell(ActionEvent event) throws IOException {
	    	
	    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LocalSell.fxml"));
	        setNode(add);


	    }
	    
	    
    
}
