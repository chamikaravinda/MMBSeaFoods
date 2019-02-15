package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Models.Local_Fish_types;
import application.Models.LocalBuyers;
import application.Services.LocalBuyerService;
import application.Services.Local_Fish_typesServices;
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
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LocalSellController implements Initializable {

	 	@FXML
	    private AnchorPane Stocks;

	    @FXML
	    private TableView<?> clmFishTable;

	    @FXML
	    private TableColumn<?, ?> clmfishtype;

	    @FXML
	    private TableColumn<?, ?> clmTotalWeight;

	    @FXML
	    private TableColumn<?, ?> clmTotalPrice;

	    @FXML
	    private JFXComboBox<String> cmbLftype;

	    @FXML
	    private JFXTextField Lfweight;

	    @FXML
	    private JFXComboBox<String> cmbLBuyers;

	    @FXML
	    private JFXButton btnremove;
	    
	    AnchorPane back;
	    
	    ObservableList<String> LocalFishTypeList =FXCollections.observableArrayList();
	    ObservableList<String> LocalBuyersList =FXCollections.observableArrayList();
	    
	    ArrayList<Local_Fish_types> local_fishtype=null;
	    ArrayList<LocalBuyers> LocalBuyers=null;
	    
	    Local_Fish_typesServices serviceC= new Local_Fish_typesServices();
	    LocalBuyerService serviceD =new LocalBuyerService();
	    
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
			
	    	try {
				local_fishtype=serviceC.getLocalfishTypes();
				LocalBuyers=serviceD.getLocalBuyer();

				for(Local_Fish_types Ltyp:local_fishtype) {
					LocalFishTypeList.add(Ltyp.getName());
				}
				
				for(LocalBuyers buyer:LocalBuyers) {
					LocalBuyersList.add(buyer.getName());
				}
				
				cmbLftype.setItems(LocalFishTypeList);
				cmbLBuyers.setItems(LocalBuyersList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}


	    @FXML
	    void AddLocalFishActions(ActionEvent event) {

	    }
	    
	    
	    @FXML
	    void AddFinalizeStock(ActionEvent event) {

	    }

    
    
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

	  @FXML
	   public void back(ActionEvent event)throws IOException {
		  
		    back=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LStocks.fxml"));
	        setNode(back);

	    }
    
}
