package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Models.F_BoatEntryCatogries;
import application.Models.LFish_stock;
import application.Models.LocalBoat;
import application.Models.LocalPurchase;
import application.Models.Local_Fish_types;
import application.Models.Stock_Fish;
import application.Services.LFish_stockService;
import application.Services.LocalBoatAccountService;
import application.Services.LocalBoatService;
import application.Services.Local_Fish_typesServices;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.fxml.Initializable;

public class AddLocalStockController implements Initializable  {  

	 @FXML
	    private AnchorPane NewStocks;

	    @FXML
	    private JFXTextField Lfweight;

	    @FXML
	    private JFXComboBox<String> cmbLftype;
	    
	    @FXML
	    private JFXComboBox<String> cmbLsBoat;
	    
	    // TableView
	    
	    @FXML
	    private TableView<LFish_stock> clmFishTable;

	    @FXML
	    private TableColumn<?, ?> clmfishtype;

	    @FXML
	    private TableColumn<?, ?> clmTotalWeight;

	    @FXML
	    private TableColumn<?, ?> clmTotalPrice;
	    
	    
	    @FXML
		private JFXButton btnremove;

	    @FXML
	    private JFXButton AddLFish;

	    
	    AnchorPane add;
    
    ObservableList<String> LocalFishTypeList =FXCollections.observableArrayList();
    ObservableList<String> LocalBoatList =FXCollections.observableArrayList();
    
    Local_Fish_typesServices serviceC= new Local_Fish_typesServices();
    LFish_stockService serviceB= new LFish_stockService();
    LocalBoatAccountService serviceD = new LocalBoatAccountService();
    LocalBoatService serviceE=new LocalBoatService();
    ArrayList<Local_Fish_types> local_fishtype=null;
    ArrayList<LocalBoat> local_boat=null;
    
    
    ObservableList<LFish_stock> local_fishStock = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		local_fishStock.clear();
		//Load DataS to the Combo Boxs
		try {
			local_fishtype=serviceC.getLocalfishTypes();
			
			for(Local_Fish_types Ltyp:local_fishtype) {
				
				
				LocalFishTypeList.add(Ltyp.getName());
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			local_boat= serviceE.getLocalBoat();
			
			for(LocalBoat lboat:local_boat) {
				
				LocalBoatList.add(lboat.getBoatNameorNumber());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		cmbLsBoat.setItems(LocalBoatList);
		cmbLftype.setItems(LocalFishTypeList);
		// end the Data to Combo Box
		
		
		clmfishtype.setCellValueFactory(new PropertyValueFactory<>("FishName"));
		clmTotalWeight.setCellValueFactory(new PropertyValueFactory<>("Total_Weight"));
		clmTotalPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
		
		clmFishTable.setItems(local_fishStock);
		
		btnremove.setOnAction(e -> {
			LFish_stock local_Fish = clmFishTable.getSelectionModel().getSelectedItem();
			clmFishTable.getItems().remove(local_Fish);
			clmFishTable.refresh();
			
		});
	
		
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
		Local_Fish_types types =serviceC.getLocalfishTypes(cmbLftype.getValue());
		
		if(cmbLftype.getSelectionModel().getSelectedItem() != null && Lfweight.getText() != null) {
		String Local_Fish_Type=	cmbLftype.getValue();
		
			local_Fish.setFishName(cmbLftype.getValue());
			local_Fish.setTotal_Weight(Double.parseDouble(Lfweight.getText()));
			local_Fish.setPrice(local_Fish.getTotal_Weight()*types.getPrice());
			local_Fish.setFish_Type(types.getID());
			local_fishStock.add(local_Fish);
			
			Lfweight.clear();
			
		}
		
		

    }
	
	public void AddFinalizeStock(ActionEvent event)throws SQLException, IOException {
		
			String boatname =cmbLsBoat.getValue();
			LocalBoat boat=serviceE.getLocalBoat(boatname);
			
			if(boatname != null) {
				if(!local_fishStock.isEmpty()) {
						if(serviceB.newStock(local_fishStock)) {
							if(serviceD.addEntries(local_fishStock, boat.getID())) {
								if(serviceD.addEntriesUncleard(local_fishStock, boat.getID())) {
								
									Notifications notifications = Notifications.create();
									notifications.title("Succesfull");
									notifications.text("Fish stock added succesfully");
									notifications.graphic(null);
									notifications.hideAfter(Duration.seconds(2));
									notifications.position(Pos.CENTER);
									notifications.showConfirm();
									
									add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LStocks.fxml"));
									setNode(add);
									
								}
							}
							
						}else {
							Notifications notifications = Notifications.create();
							notifications.title("Error");
							notifications.text("Fish stock adding  unsuccesfull");
							notifications.graphic(null);
							notifications.hideAfter(Duration.seconds(2));
							notifications.position(Pos.CENTER);
							notifications.showError();
					
				   
						}
					}else {
						Notifications notifications = Notifications.create();
						notifications.title("Error");
						notifications.text("Add Fishes to the List");
						notifications.graphic(null);
						notifications.hideAfter(Duration.seconds(2));
						notifications.position(Pos.CENTER);
						notifications.showError();
					}
				}else {
				Notifications notifications = Notifications.create();
				notifications.title("Error");
				notifications.text("Select a boat");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showError();
	}
}

	  @FXML
	   public void back(ActionEvent event)throws IOException {
		  
		    add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LStocks.fxml"));
	        setNode(add);

	    }

 

}

