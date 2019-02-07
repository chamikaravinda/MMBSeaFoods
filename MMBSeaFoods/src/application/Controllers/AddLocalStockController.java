package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Models.LFish_stock;
import application.Models.Local_Fish_types;
import application.Services.LFish_stockService;
import application.Services.Local_Fish_typesServices;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddLocalStockController implements Initializable  {  //

	 @FXML
	    private AnchorPane NewStocks;

	    @FXML
	    private JFXTextField Lfweight;

	    @FXML
	    private JFXComboBox<String> cmbLftype;

	    @FXML
	    private JFXButton AddLFish;

	    
	    AnchorPane add;
    
    ObservableList<String> LocalFishTypeList =FXCollections.observableArrayList();

    
    Local_Fish_typesServices serviceC= new Local_Fish_typesServices();
    LFish_stockService serviceB= new LFish_stockService();
    ArrayList<Local_Fish_types> local_fishtype=null;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		try {
			local_fishtype=serviceC.getLocalfishTypes();
			
			for(Local_Fish_types Ltyp:local_fishtype) {
				
				
				LocalFishTypeList.add(Ltyp.getName());
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		cmbLftype.setItems(LocalFishTypeList);
		
	}
	
	
	void setNode(Node node) {
		
		 NewStocks.getChildren().clear();
		 NewStocks.setTopAnchor(node,0.0);
		 NewStocks.setRightAnchor(node, 0.0);
		 NewStocks.setLeftAnchor(node, 0.0);
		 NewStocks.setBottomAnchor(node, 0.0);
		 NewStocks.getChildren().addAll((Node) node);

	        FadeTransition ft = new FadeTransition(Duration.millis(1500));
	        ft.setNode(node);
	        ft.setFromValue(0.1);
	        ft.setToValue(1);
	        ft.setCycleCount(1);
	        ft.setAutoReverse(false);
	        ft.play();
	        
		
	    }


	
	
	public void AddLocalFishActions(ActionEvent event)throws SQLException {
		
		
		LFish_stock local_Fish = new LFish_stock();
		
		if(cmbLftype.getSelectionModel().getSelectedItem() != null && Lfweight.getText() != null) {
		String Local_Fish_Type=	cmbLftype.getValue();
		
		Local_Fish_types local_Fish_type=serviceC.getLocalfishTypes(Local_Fish_Type);
		
		local_Fish.setFish_Type(local_Fish_type.getID());
		local_Fish.setTotal_Weight(Double.parseDouble(Lfweight.getText()));

		
		serviceB.addFish_Stock(local_Fish);
		
		System.out.println(local_Fish);
		}
		
		

    }
	
	
	  @FXML
	   public void back(ActionEvent event)throws IOException {
		  
		    add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LStocks.fxml"));
	        setNode(add);

	    }
	  

}
