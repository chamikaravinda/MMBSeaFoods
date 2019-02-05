package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.Buyers;
import application.Models.Fish_Lot;
import application.Services.BuyerService;
import application.Services.Fish_LotServices;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
    Fish_Lot lot = null;
    
	ObservableList<String> BuyersList =FXCollections.observableArrayList();
	ArrayList<Buyers> Blist=null; 
	
	
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
	
	
	public void SellLot() throws SQLException {
		
		//get the buyer id
		Buyers buyers = serviceB.getBuyers(cmbBuyer.getValue());
		Fish_Lot lot=new Fish_Lot();
		lot.setSold_price(Double.parseDouble(txtSellingPrice.getText()));
		lot.setSold_Weight(Double.parseDouble(txtWeight.getText()));
		lot.setSold_To(buyers.getID());
		lot.setID(Integer.parseInt(lblLotID.getText()));
		
		if(service.SellFishLot(lot)){
			
			//remove stock belong 
			
		}
		
		
	}
	

}
