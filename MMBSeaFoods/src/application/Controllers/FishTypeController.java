/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controllers;

import com.jfoenix.controls.JFXButton;

import application.Models.Boat;
import application.Models.Foreign_Fish_types;
import application.Services.Foreign_Fish_typesServices;
import application.Services.ProfiteAndLossService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.TabableView;

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

public class FishTypeController implements Initializable {

    @FXML
    private AnchorPane FishType;

    @FXML
	private TableView<Foreign_Fish_types> tblFishType;

    @FXML
  	private TableColumn<?,?> clmName;
  	
  	@FXML
  	private TableColumn<?,?> clmB15;
  	
  	@FXML
  	private TableColumn<?,?> clm15B20;
  	
  	@FXML
  	private TableColumn<?,?> clmA20;
  	
	 ObservableList<Foreign_Fish_types> list = FXCollections.observableArrayList();

    
    AnchorPane lots,stoks,boats,buyers,fishtypes,newLots;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
    	list.clear();
    	Foreign_Fish_typesServices service =new Foreign_Fish_typesServices();
    	
    	ArrayList<Foreign_Fish_types> ftypes =null;
    	
    	try {
			ftypes=service.getfishTypes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	for(Foreign_Fish_types sup : ftypes) {
			list.add(sup);
		}	
    	
    	clmName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    	clmB15.setCellValueFactory(new PropertyValueFactory<>("price_15B"));
    	clm15B20.setCellValueFactory(new PropertyValueFactory<>("price20_15"));
    	clmA20.setCellValueFactory(new PropertyValueFactory<>("price_20P"));
    	
    	tblFishType.setItems(list);
    }
    
	//Set selected node to a content holder
    void setNode(Node node) {
    	FishType.getChildren().clear();
    	FishType.setTopAnchor(node,0.0);
        FishType.setRightAnchor(node, 0.0);
        FishType.setLeftAnchor(node, 0.0);
        FishType.setBottomAnchor(node, 0.0);
        FishType.getChildren().addAll((Node) node);

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
    
    public void AddNewFishType(ActionEvent event) throws IOException {
    	lots=FXMLLoader.load(getClass().getResource("../Views/Ftrade/NewFishType.fxml"));
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
