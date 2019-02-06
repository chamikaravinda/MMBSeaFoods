package application.Controllers;

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
import application.Models.Foreign_Fish_Buyers;
import application.Services.BuyerService;
import application.Services.Fish_LotServices;
import application.Services.Fish_stockService;
import application.Services.Foreign_Fish_Buyers_AccountServices;
import javafx.animation.FadeTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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

    Fish_LotServices service =new Fish_LotServices();
    BuyerService serviceB =new BuyerService();
    Fish_LotServices serviceC=new Fish_LotServices();
    Fish_stockService seriveD=new Fish_stockService();
    Foreign_Fish_Buyers_AccountServices serviceE =new Foreign_Fish_Buyers_AccountServices();
    
    Fish_Lot lot = null;
    
	ObservableList<String> BuyersList =FXCollections.observableArrayList();
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
				lblbuyingprice.setText(price);
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
			
			NumberValidator weightValidator =new NumberValidator();
			
			txtWeight.getValidators().add(weightValidator);
			weightValidator.setMessage("Please input Fish Weight Correctly");
			
			txtWeight.focusedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(!newValue) {
						txtWeight.validate();
					}
					
				}
				
			});
			
			
			
			NumberValidator priceValidator =new NumberValidator();
			
			txtSellingPrice.getValidators().add(priceValidator);
			priceValidator.setMessage("Please input Fish Price Correctly");
			
			txtSellingPrice.focusedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(!newValue) {
						txtSellingPrice.validate();
					}
					
				}
				
			});
			
	    });
		
	}
	
	
	public void SellLot(ActionEvent event) throws SQLException {
		
		//get the buyer id
		Buyers buyers = serviceB.getBuyers(cmbBuyer.getValue());
		Fish_Lot lot=new Fish_Lot();
		lot.setSold_price(Double.parseDouble(txtSellingPrice.getText()));
		lot.setSold_Weight(Double.parseDouble(txtWeight.getText()));
		lot.setSold_To(buyers.getID());
		lot.setID(Integer.parseInt(lblLotID.getText()));
		Date date =new Date();
		F_Fish_Buyers_Account buyerAccount =new F_Fish_Buyers_Account();
		F_Fish_Buyers_Account_Uncleard buyerAccountU =new F_Fish_Buyers_Account_Uncleard();
		
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
		sell=FXMLLoader.load(getClass().getResource("../Views/Vehicles/Vehicles.fxml"));
		setNode(sell);
		
	}
	

}
