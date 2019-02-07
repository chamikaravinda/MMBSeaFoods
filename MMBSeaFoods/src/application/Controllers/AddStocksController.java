package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.Boat;
import application.Models.Boat_Account;
import application.Models.Boat_Account_UnCleared;
import application.Models.Fish_Lot;
import application.Models.Fish_stock;
import application.Models.Foreign_Fish_types;
import application.Models.Stock_Fish;
import application.Models.Third_Party_Account;
import application.Models.Vehicles;
import application.Services.BoatService;
import application.Services.Boat_AccountServices;
import application.Services.Fish_LotServices;
import application.Services.Fish_stockService;
import application.Services.Foreign_Fish_typesServices;
import application.Services.ProfiteAndLossService;
import application.Services.Third_Party_AccountServices;
import application.Services.stock_FishService;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class AddStocksController implements Initializable{
	
	//toatal price,weight and fish labels
	
	@FXML 
	private Label lbltotalWeight;
	
	@FXML 
	private Label lbltotalPrice;
	
	@FXML 
	private Label lblNoOfFish;
	
	double currentTotalWeigth;
	double currentTotalPrice;
	int totalNoofFish;
	
	//Add fish detail text box
	
	@FXML
	private JFXTextField txtFishweigth;
	
	
	//Stock Form 
	@FXML
	private JFXComboBox<String> cmbLot;
	
	@FXML
	private JFXComboBox<String> cmbBoat;
	
	@FXML
	private JFXComboBox<String> cmbFtype;
	
	@FXML
	private AnchorPane NewStocks;
	
	ObservableList<String> lotList =FXCollections.observableArrayList();
	ObservableList<String> boatList =FXCollections.observableArrayList();
	ObservableList<String> FishTypeList =FXCollections.observableArrayList();
	
	//fish table
	@FXML
	private TableView<Stock_Fish> tblFish;
	
	@FXML
	private TableColumn<?,?> clmWeight;
	
	@FXML
	private TableColumn<?,?> clmPrice;
	
	public ObservableList<Stock_Fish> list = FXCollections.observableArrayList();
	
	@FXML
	private JFXTextField txtHabour; 
	
	Fish_LotServices service=new Fish_LotServices();
	BoatService serviceB =new BoatService();
	Foreign_Fish_typesServices serviceC=new Foreign_Fish_typesServices();
	Fish_stockService serviceD =new Fish_stockService();
	stock_FishService serviceE = new stock_FishService();
	Third_Party_AccountServices serviceF=new Third_Party_AccountServices();
	ProfiteAndLossService serviceG =new ProfiteAndLossService();
	Boat_AccountServices serviceH=new Boat_AccountServices();
	
	ArrayList<Fish_Lot> lots=null;
	ArrayList<Boat> boats=null;
	ArrayList<Foreign_Fish_types> fishtype=null;
	
	AnchorPane add;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		NumberValidator weigthValidator = new NumberValidator();
		RequiredFieldValidator hobourValidator =new RequiredFieldValidator();
		
		txtFishweigth.getValidators().add(weigthValidator);
		weigthValidator.setMessage("Please input Fish Weight Correctly");
		
		txtFishweigth.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtFishweigth.validate();
				}
				
			}
			
		});
		
		
		txtHabour.getValidators().add(hobourValidator);
		hobourValidator.setMessage("Please input Habour ");
		
		txtHabour.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtHabour.validate();
				}
				
			}
			
		});
		
		lotList.clear();
		
		try {
			lots = service.getUnslodLots();
			boats =serviceB.getBoat();
			fishtype=serviceC.getfishTypes();
			
			for(Fish_Lot lot:lots) {
				lotList.add(lot.getDisplay_Name());
			}
			
			for(Boat bts:boats) {
				boatList.add(bts.getBoatNameorNumber());
			}
			for(Foreign_Fish_types typ:fishtype) {
				FishTypeList.add(typ.getName());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cmbLot.setItems(lotList);
		cmbBoat.setItems(boatList);
		cmbFtype.setItems(FishTypeList);
		
		clmWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		clmPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		
		tblFish.setItems(list);
		
		
		
	}
	
	public void FishListAdd(ActionEvent event) throws SQLException {
		
		Stock_Fish fish =new Stock_Fish();
		if(cmbFtype.getSelectionModel().getSelectedItem() != null && txtFishweigth.getText() != null) {
		String TypeName=cmbFtype.getValue();
		Foreign_Fish_types fishtype=serviceC.getfishTypes(TypeName);
		
		fish.setType(fishtype.getID());
		fish.setWeight(Double.parseDouble(txtFishweigth.getText()));
		
		if(fish.getWeight()>20.0) {	
				fish.setPrice(fish.getWeight()*fishtype.getPrice_20P());
		}else if(fish.getWeight()>15.0) {
				fish.setPrice(fish.getWeight()*fishtype.getPrice20_15());
		}else {
			fish.setPrice(fish.getWeight()*fishtype.getPrice_15B());
		}
		
		list.add(fish);
		
		
		totalNoofFish++;
		currentTotalWeigth += fish.getWeight();
		currentTotalPrice  += fish.getPrice();
		
		lblNoOfFish.setText(Integer.toString(totalNoofFish));
		lbltotalPrice.setText(Double.toString(currentTotalPrice)+"0");
		lbltotalWeight.setText(Double.toString(currentTotalWeigth)+"0 Kg");
		
		txtFishweigth.clear();
		}
		
	}
	
	public void FinaliseStock(ActionEvent event) throws SQLException, IOException {
		
		if(cmbBoat.getSelectionModel().getSelectedItem() !=null && cmbLot.getSelectionModel().getSelectedItem() !=null ) {
			if(!txtHabour.getText().isEmpty()){
				if(!list.isEmpty()) {
					
					Fish_stock stock = new Fish_stock();
					Boat stockboat=serviceB.getBoat(cmbBoat.getValue());
					Fish_Lot lot=service.getTheLot(cmbLot.getValue());
					
					stock.setAdded_Date(format1.format( new Date()   ));
					stock.setBoat_ID(stockboat.getID());
					stock.setHarbour(txtHabour.getText());
					stock.setNoofFishes(totalNoofFish);
					stock.setTotal_Weight(currentTotalWeigth);
					stock.setFishprice(currentTotalPrice);
					stock.setCommition_price(currentTotalWeigth*20);
					stock.setTotalBuying_price(stock.getFishprice()+stock.getCommition_price());
					stock.setLot_ID(lot.getID());
					
					long stockID=serviceD.addFish_Stock(stock);
					if(stockID != 0) {
						
						for(Stock_Fish entry : list) {
							serviceE.addFish(entry);						
						}
						
						Third_Party_Account entry =new Third_Party_Account();
						entry.setDate(format1.format( new Date()   ));
						entry.setTo_Pay(stock.getCommition_price());
						entry.setPaid(0);
						entry.setReason("Commition for stock from boat" +stockboat.getBoatNameorNumber());
						
						serviceF.addEntry(entry);
						serviceF.addEntry_Uncleared(entry);
						
						lot.setBuying_Weight(lot.getBuying_Weight()+stock.getTotal_Weight());
						lot.setBrokerFee(stock.getCommition_price());
						lot.setBuying_price(lot.getBuying_price()+stock.getTotalBuying_price());
							if(service.UpdateFish_Lot(lot)) {
							
								Boat_Account boatEntry =new Boat_Account();
								boatEntry.setDate(format1.format( new Date()   ));
								boatEntry.setBoat_ID(stock.getBoat_ID());
								boatEntry.setReason("Fish Puchase");
								boatEntry.setTo_Pay(stock.getFishprice());
								boatEntry.setPaid(0);
								
								Boat_Account_UnCleared boatEntryU =new Boat_Account_UnCleared();
								boatEntryU.setDate(format1.format( new Date()   ));
								boatEntryU.setBoat_ID(stock.getBoat_ID());
								boatEntryU.setReason("Fish Puchase");
								boatEntryU.setTo_Pay(stock.getFishprice());
								boatEntryU.setPaid(0);
								
								
								serviceH.addEntries(boatEntry);
								if(serviceH.addEntries_Uncleard(boatEntryU)) {
									
									Notifications notifications = Notifications.create();
									notifications.title("Succesfull");
									notifications.text("Stock added succesfully");
									notifications.graphic(null);
									notifications.hideAfter(Duration.seconds(2));
									notifications.position(Pos.CENTER);
									notifications.showConfirm();
									
									add=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Stocks.fxml"));
									setNode(add);
								}
						
						}
											
					}
				
					
				}else {
					Notifications notifications = Notifications.create();
					notifications.title("Error");
					notifications.text("Add fishes to the stock");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showError();
				}			
			}else {
				Notifications notifications = Notifications.create();
				notifications.title("Error");
				notifications.text("Add Harbour");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showError();
			}
		}
		
		
	}
	
	//switch windows 

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
			
		
		public void back(ActionEvent event) throws IOException {
			add=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Stocks.fxml"));
			setNode(add);
			
		}
}
