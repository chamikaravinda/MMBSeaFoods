package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Models.Local_Fish_types;
import application.Models.LFish_stock;
import application.Models.LocalBoat;
import application.Models.LocalBuyers;
import application.Services.LFish_stockService;
import application.Services.LocalBuyerAccountService;
import application.Services.LocalBuyerService;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LocalSellController implements Initializable {

	 	@FXML
	    private AnchorPane Stocks;

	    @FXML
	    private TableView<LFish_stock> clmFishTable;

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
	    ArrayList<LFish_stock> currentStock = null;
	    
	    Local_Fish_typesServices serviceC= new Local_Fish_typesServices();
	    LocalBuyerService serviceD =new LocalBuyerService();
	    LFish_stockService serviceE = new LFish_stockService();
	    LocalBuyerAccountService serviceF=new LocalBuyerAccountService();
	    
	    
	    ObservableList<LFish_stock> local_fishStock = FXCollections.observableArrayList();
	    
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
			
	    	try {
				local_fishtype=serviceC.getLocalfishTypes();
				LocalBuyers=serviceD.getLocalBuyer();
				currentStock=serviceE.getUnsoldLocalStocks();
				
				for(Local_Fish_types Ltyp:local_fishtype) {
					LocalFishTypeList.add(Ltyp.getName());
				}
				
				for(LocalBuyers buyer:LocalBuyers) {
					LocalBuyersList.add(buyer.getName());
				}
				
				cmbLftype.setItems(LocalFishTypeList);
				cmbLBuyers.setItems(LocalBuyersList);
				
				
				
				clmfishtype.setCellValueFactory(new PropertyValueFactory<>("FishName"));
				clmTotalWeight.setCellValueFactory(new PropertyValueFactory<>("Total_Weight"));
				clmTotalPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
				
				clmFishTable.setItems(local_fishStock);
				
				btnremove.setOnAction(e -> {
					LFish_stock local_Fish = clmFishTable.getSelectionModel().getSelectedItem();
					clmFishTable.getItems().remove(local_Fish);
					clmFishTable.refresh();
					
				});
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}


	    @FXML
	    void AddLocalFishActions(ActionEvent event) throws SQLException {

	    	LFish_stock local_Fish = new LFish_stock();
			Local_Fish_types types =serviceC.getLocalfishTypes(cmbLftype.getValue());
			
			if(cmbLftype.getSelectionModel().getSelectedItem() != null && Lfweight.getText() != null) {
			String Local_Fish_Type=	cmbLftype.getValue();
			
				local_Fish.setFishName(cmbLftype.getValue());
				local_Fish.setTotal_Weight(Double.parseDouble(Lfweight.getText()));
				local_Fish.setPrice(local_Fish.getTotal_Weight()*types.getPrice());
				local_Fish.setFish_Type(types.getID());
				
				for(LFish_stock fish : currentStock) {
					if(fish.getFish_Type()== local_Fish.getFish_Type()) {
						if(fish.getTotal_Weight()>= local_Fish.getTotal_Weight()) {
							local_fishStock.add(local_Fish);
							fish.setTotal_Weight(fish.getTotal_Weight()-local_Fish.getTotal_Weight());
						}else {
							Notifications notifications = Notifications.create();
							notifications.title("Error");
							notifications.text("Not enough stocks");
							notifications.graphic(null);
							notifications.hideAfter(Duration.seconds(2));
							notifications.position(Pos.CENTER);
							notifications.showError();
						}
					}
				}
				Lfweight.clear();
				
			}
			
	    }
	    
	    
	    @FXML
	    void AddFinalizeStock(ActionEvent event) throws IOException {
	    	try {
	    		String Buyername =cmbLBuyers.getValue();
				LocalBuyers buyer=serviceD.getLocalBoat(Buyername);
				if(Buyername !=null && !local_fishStock.isEmpty()) {
					if(serviceE.sellStock(local_fishStock)) {
						if(serviceF.addEntries(local_fishStock, buyer.getID())) {
							if(serviceF.addEntriesUncleard(local_fishStock, buyer.getID())) {
								if(serviceF.addLocalSale(local_fishStock, buyer.getID())){
									
									Notifications notifications = Notifications.create();
									notifications.title("Succesfull");
									notifications.text("Sold succesfully");
									notifications.graphic(null);
									notifications.hideAfter(Duration.seconds(2));
									notifications.position(Pos.CENTER);
									notifications.showConfirm();
									
									back=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LStocks.fxml"));
									setNode(back);
									
								}
								
								
							}
						}
					}else {
						Notifications notifications = Notifications.create();
						notifications.title("Error");
						notifications.text("Selling  unsuccesfull");
						notifications.graphic(null);
						notifications.hideAfter(Duration.seconds(2));
						notifications.position(Pos.CENTER);
						notifications.showError();
					}
				}else {
					Notifications notifications = Notifications.create();
					notifications.title("Error");
					notifications.text("Buyer Not selected or Items are empty");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showError();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
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
