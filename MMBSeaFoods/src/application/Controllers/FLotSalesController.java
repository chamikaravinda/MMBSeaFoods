package application.Controllers;

import java.awt.event.ItemEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.Buyers;
import application.Models.F_Fish_Buyers_Account;
import application.Models.F_Fish_Buyers_Account_Uncleard;
import application.Models.Fish_Lot;
import application.Models.Fish_stock;
import application.Models.ForeignSallingFish;
import application.Models.Foreign_Fish_Buyers;
import application.Models.Foreign_Fish_types;
import application.Models.Stock_Fish;
import application.Services.BuyerService;
import application.Services.Fish_LotServices;
import application.Services.Fish_stockService;
import application.Services.Foreign_Fish_Buyers_AccountServices;
import application.Services.Foreign_Fish_typesServices;
import application.Services.stock_FishService;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;

public class FLotSalesController implements Initializable{
	
	private StringProperty id;
	
	public void setID(String ID) {
		lblLotID.setText(ID);
	}

    @FXML
    private AnchorPane sellStock;

    @FXML
    private JFXButton btnSell;

    @FXML
    private JFXComboBox<String> cmbBuyer;

    @FXML
    private JFXTextField txtWeight;

    @FXML
    private Label lblAddedDate;

    @FXML
    private Label lblLorryNumber;

    @FXML
    private Label lblbuyingprice;

    @FXML
    private JFXTextField txtSellingPrice;

    @FXML
    private Label lblLotID;

    @FXML
    private TableView<ForeignSallingFish> tblLotFishes;

    @FXML
    private TableColumn<?, ?> clmtype;

    @FXML
    private TableColumn<?, ?> clmweight;

    @FXML
    private TableColumn<?, ?> clmprice;
    
    @FXML
    private TableColumn<?, ?> clmSellprice;
    
    @FXML
    private Label lbltotalsellingPrice;
    
    private double totalItemPrice=0;
    
    Fish_LotServices service =new Fish_LotServices();
    BuyerService serviceB =new BuyerService();
    Fish_LotServices serviceC=new Fish_LotServices();
    Fish_stockService seriveD=new Fish_stockService();
    Foreign_Fish_Buyers_AccountServices serviceE =new Foreign_Fish_Buyers_AccountServices();
    Foreign_Fish_typesServices serviceF=new Foreign_Fish_typesServices();
    stock_FishService serviceG =new stock_FishService();
    Fish_Lot lot = null;
    
	ObservableList<String> BuyersList =FXCollections.observableArrayList();
	ObservableList<ForeignSallingFish> LotFishItemsDisplayList =FXCollections.observableArrayList();
	ArrayList<ForeignSallingFish> LotFishItems =new ArrayList<ForeignSallingFish>();
	ArrayList<Buyers> Blist=null; 
	
	AnchorPane sell;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {

			try {
				int iD = Integer.parseInt(lblLotID.getText());
				lot=service.getTheLot(iD);
				lblAddedDate.setText(lot.getAdded_Date());
				String price =Double.toString(lot.getBuying_price());
				lblbuyingprice.setText(price+"0");
				lblLorryNumber.setText(lot.getLorry_Number());
				Blist=serviceB.getBuyers();
				
				for (Buyers buyer :Blist) {
					BuyersList.add(buyer.getName());
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			cmbBuyer.setItems(BuyersList);
						
			//Items in the table
			
			//initialize the item list
			try {
				ArrayList<Foreign_Fish_types> typelist = serviceF.getfishTypes();
				LotFishItems.clear();
				for(Foreign_Fish_types type : typelist) {
					
					ForeignSallingFish item = new ForeignSallingFish();
					item.setId(type.getID());
					item.setName(type.getName()+" Under 15kg");
					item.setPrice(0);
					item.setTotalWeigth(0);
					item.setWeightclass(1);
					LotFishItems.add(item);
					
					ForeignSallingFish item2 = new ForeignSallingFish();
					item2.setId(type.getID());
					item2.setName(type.getName()+" Between 15kg to 20kg");
					item2.setPrice(0);
					item2.setTotalWeigth(0);
					item2.setWeightclass(2);
					LotFishItems.add(item2);
					
					ForeignSallingFish item3 = new ForeignSallingFish();
					item3.setId(type.getID());
					item3.setName(type.getName()+ " above 20kg");
					item3.setPrice(0);
					item3.setTotalWeigth(0);
					item3.setWeightclass(3);
					LotFishItems.add(item3);
				}	
				
				ArrayList<Fish_stock> stockList=new ArrayList<>();
				stockList=seriveD.getLotStocks(Integer.parseInt(lblLotID.getText()));
				
				for(Fish_stock stocks : stockList) {
					
					ArrayList<Stock_Fish> fishes = serviceG.getStocksFish(stocks.getID());
					for(ForeignSallingFish items:LotFishItems) {
						for(Stock_Fish fish:fishes) {
							if(items.getId()==fish.getType()) {
								
								if( fish.getWeight()<15.0 && items.getWeightclass() == 1 ) {
								items.setPrice(items.getPrice()+fish.getPrice());
								items.setTotalWeigth(items.getTotalWeigth()+fish.getWeight());
								}else if(fish.getWeight()>15.0 && fish.getWeight()<20.0 && items.getWeightclass()==2) {
									items.setPrice(items.getPrice()+fish.getPrice());
									items.setTotalWeigth(items.getTotalWeigth()+fish.getWeight());
									}else if(fish.getWeight()>20.0 && items.getWeightclass()==3) {
									items.setPrice(items.getPrice()+fish.getPrice());
									items.setTotalWeigth(items.getTotalWeigth()+fish.getWeight());
									}else {
										System.out.println("Error");
									}
								
							}
							
						}
						
					}
					
				}
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			LotFishItemsDisplayList.clear();
			
			clmtype.setCellValueFactory(new PropertyValueFactory<>("name"));
			clmweight.setCellValueFactory(new PropertyValueFactory<>("totalWeigth"));
			clmprice.setCellValueFactory(new PropertyValueFactory<>("price"));

			for(ForeignSallingFish item :LotFishItems) {
				if(item.getTotalWeigth() !=0) {
					LotFishItemsDisplayList.add(item);
				}
			}
			tblLotFishes.setEditable(true);
		    
	        TableColumn<ForeignSallingFish, String> clmSellprice = 
	            new TableColumn<>("Selling Price(1 Kg)");
	        clmSellprice.setCellValueFactory(
	            new PropertyValueFactory<>("SunitePrice"));
	        
	        clmSellprice.setCellFactory(TextFieldTableCell.<ForeignSallingFish>forTableColumn());
	        clmSellprice.setOnEditCommit(event -> {
	        		ForeignSallingFish fish = event.getRowValue();
	        		fish.setSunitePrice(event.getNewValue());
	        		fish.setUnitePrice(Double.parseDouble(fish.getSunitePrice()));
	        		fish.setTotalSellingPrice(fish.getTotalWeigth()*fish.getUnitePrice());
	        		fish.setStotalSellingPrice(Double.toString(fish.getTotalSellingPrice()));
	        		fish.setSunitePrice(Double.toString(fish.getUnitePrice()));
	        		
	        		tblLotFishes.refresh();
	        		totalItemPrice=0;
	        		for(ForeignSallingFish fishprice :LotFishItemsDisplayList) {
	        			totalItemPrice=totalItemPrice +fishprice.getTotalSellingPrice(); 
	        		}
	        		lbltotalsellingPrice.setText(Double.toString(totalItemPrice)+"0");
	        		
	        });
	        
	        
	        TableColumn<ForeignSallingFish, String> clmTotalPrice= 
		            new TableColumn<>("Total Price");
	        clmTotalPrice.setCellValueFactory(
		            new PropertyValueFactory<>("StotalSellingPrice"));
		        
		        
	      
	        tblLotFishes.getColumns().addAll(clmSellprice,clmTotalPrice);
	        tblLotFishes.setItems(LotFishItemsDisplayList);
	    });
		
		
	}
		
	public void SellLot(ActionEvent event) throws SQLException, IOException {
		
		if(cmbBuyer.getValue()!=null) {
			//get the buyer id
			Buyers buyers = serviceB.getBuyers(cmbBuyer.getValue());
			Fish_Lot lot=new Fish_Lot();
			
			totalItemPrice=0;
			double totalWeight=0;
			for(ForeignSallingFish fishprice :LotFishItemsDisplayList) {
				totalItemPrice=totalItemPrice +fishprice.getTotalSellingPrice();
				totalWeight=totalWeight+fishprice.getTotalWeigth();
			}
	
			
			lot.setSold_price(totalItemPrice);
			lot.setSold_Weight(totalWeight);
			lot.setSold_To(buyers.getID());
			lot.setID(Integer.parseInt(lblLotID.getText()));
			Date date =new Date();
			F_Fish_Buyers_Account buyerAccount =new F_Fish_Buyers_Account();
			if(isPricesSet()) {
				if(service.SellFishLot(lot)){
					if(seriveD.sellStock(lot.getID())) {
						buyerAccount.setDate(date.toString());
						buyerAccount.setTo_Pay(lot.getSold_price());
						buyerAccount.setPaid(0);
						buyerAccount.setReason("Selling Lot purchased form lorry "+lot.getLorry_Number()+" on "+lot.getAdded_Date());
						buyerAccount.setBuyer_ID(buyers.getID());
							if(serviceE.addEntry(buyerAccount))	{	
									if(serviceE.addEntryUncleared(buyerAccount)){
									
										Notifications notifications = Notifications.create();
										notifications.title("Succesfull");
										notifications.text("Lot Sold succesfully");
										notifications.graphic(null);
										notifications.hideAfter(Duration.seconds(2));
										notifications.position(Pos.CENTER);
										notifications.showConfirm();
										
										sell=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Ftrade.fxml"));
										setNode(sell);
								}
							}
	
						}		
					}else {
						Notifications notifications = Notifications.create();
						notifications.title("Error");
						notifications.text("Selling Lot unsuccesfull.");
						notifications.graphic(null);
						notifications.hideAfter(Duration.seconds(2));
						notifications.position(Pos.CENTER);
						notifications.showError();
					}
					
			}else {
				Notifications notifications = Notifications.create();
				notifications.title("Error");
				notifications.text("Set all the selling prices.");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showError();
			}
	
		}else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Select the buyer");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}
	}	
		
	 void setNode(Node node) {
			
		 sellStock.getChildren().clear();
		 sellStock.setTopAnchor(node,0.0);
		 sellStock.setRightAnchor(node, 0.0);
		 sellStock.setLeftAnchor(node, 0.0);
		 sellStock.setBottomAnchor(node, 0.0);
		 sellStock.getChildren().addAll((Node) node);

	        FadeTransition ft = new FadeTransition(Duration.millis(1500));
	        ft.setNode(node);
	        ft.setFromValue(0.1);
	        ft.setToValue(1);
	        ft.setCycleCount(1);
	        ft.setAutoReverse(false);
	        ft.play();
	        
		
	    }
		
	
	public void back(ActionEvent event) throws IOException {
		sell=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Ftrade.fxml"));
		setNode(sell);
		
	}

	private boolean isPricesSet() {
		
		for(ForeignSallingFish fishprice :LotFishItemsDisplayList) {
			if(fishprice.getTotalSellingPrice() == 0 || fishprice.getStotalSellingPrice() == null ) {
				return false;
			} 
		}

		return true;
	}


}
