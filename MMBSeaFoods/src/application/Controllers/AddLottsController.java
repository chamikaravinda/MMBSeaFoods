package application.Controllers;

import java.io.IOException;  
import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;


import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.Fish_Lot;
import application.Models.ProfiteAndLose;
import application.Models.Third_Party_Account;
import application.Models.Vehicles;
import application.Services.Fish_LotServices;
import application.Services.ProfiteAndLossService;
import application.Services.Third_Party_AccountServices;
import application.Services.VehiclesServices;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddLottsController implements Initializable {
	
	@FXML
	private AnchorPane Vehicles;
	
	@FXML
	private JFXTextField txtLorryNo;
	
	@FXML
	private JFXTextField txtIceFee;
	
	@FXML
	private JFXTextField txtLorryFee;
	
	@FXML
	private JFXTextField txtOtherFee;
	
	@FXML 
	private CheckBox chkIce;
	
	@FXML 
	private CheckBox chkLorry;
	
	
	@FXML 
	private CheckBox chkOther;
	
	AnchorPane back;
	
	Fish_LotServices service =new Fish_LotServices();
	
	Third_Party_AccountServices service2=new Third_Party_AccountServices();
	
	ProfiteAndLossService ProfitAndLossService=new ProfiteAndLossService();
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		RequiredFieldValidator LorryNo = new RequiredFieldValidator();
		NumberValidator IceFee = new NumberValidator();
		NumberValidator LorryFee = new NumberValidator();
		NumberValidator OtherFee = new NumberValidator();
		
		txtLorryNo.getValidators().add(LorryNo);
		LorryNo.setMessage("Please input the Lorry Number");
		
		txtLorryNo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtLorryNo.validate();
				}
				
			}
			
		});
		
		txtIceFee.getValidators().add(IceFee);
		IceFee.setMessage("Please input correct values");
		
		txtIceFee.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtIceFee.validate();
				}
				
			}
			
		});
		
	
	
	txtLorryFee.getValidators().add(LorryFee);
	LorryFee.setMessage("Please input correct values");
	
	txtLorryFee.focusedProperty().addListener(new ChangeListener<Boolean>() {

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if(!newValue) {
				txtLorryFee.validate();
			}
			
		}
		
	});
	
	txtOtherFee.getValidators().add(OtherFee);
	OtherFee.setMessage("Please input correct values");
	
	txtOtherFee.focusedProperty().addListener(new ChangeListener<Boolean>() {

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if(!newValue) {
				txtOtherFee.validate();
			}
			
		}
		
	});
	
}
	
	public void AddLots(ActionEvent event) throws SQLException, IOException, ParseException {
		
		Fish_Lot newLot =new Fish_Lot();
		newLot.setAdded_Date(format1.format( new Date()   ));
		newLot.setLorry_Number(txtLorryNo.getText());
		newLot.setLorry_fee(Double.parseDouble(txtLorryFee.getText()));
		newLot.setIce_fee(Double.parseDouble(txtIceFee.getText()));
		newLot.setOther_fees(Double.parseDouble(txtOtherFee.getText()));
		
		if(service.addLot(newLot)) {
			if(newLot.getIce_fee() >0.0) {
				if(chkIce.isSelected()) {
					Third_Party_Account entry =new Third_Party_Account();
					entry.setDate(newLot.getAdded_Date());
					entry.setTo_Pay(newLot.getIce_fee());
					entry.setPaid(0);
					entry.setReason("Ice fee for Lot form Lorry "+newLot.getLorry_Number());
					
					service2.addEntry_Uncleared(entry);
					service2.addEntry(entry);
					
				}else {
					
					ProfiteAndLose entry=new ProfiteAndLose();
					entry.setDate(newLot.getAdded_Date());
					entry.setTo_Pay(0);
					entry.setPaid(newLot.getIce_fee());
					entry.setReason("Ice fee for Lot form Lorry "+newLot.getLorry_Number());
					
					ProfitAndLossService.addProfitAndLossEntry(entry);
				}
			}
			if(newLot.getLorry_fee() > 0.0) {	
				if(chkLorry.isSelected()) {
					Third_Party_Account entry =new Third_Party_Account();
					entry.setDate(newLot.getAdded_Date());
					entry.setTo_Pay(newLot.getLorry_fee());
					entry.setPaid(0);
					entry.setReason("Lorry fee for Lot form  Lorry "+newLot.getLorry_Number());
					
					service2.addEntry_Uncleared(entry);
					service2.addEntry(entry);
				}else {
					ProfiteAndLose entry=new ProfiteAndLose();
					entry.setDate(newLot.getAdded_Date());
					entry.setTo_Pay(0);
					entry.setPaid(newLot.getLorry_fee());
					entry.setReason("Lorry fee for Lot form Lorry "+newLot.getLorry_Number());
					ProfitAndLossService.addProfitAndLossEntry(entry);
				}
			}
			if(newLot.getOther_fees()>0.0) {
				if(chkOther.isSelected()) {
					Third_Party_Account entry =new Third_Party_Account();
					entry.setDate(newLot.getAdded_Date());
					entry.setTo_Pay(newLot.getLorry_fee());
					entry.setPaid(0);
					entry.setReason("Other expences for Lot form  Lorry  "+newLot.getLorry_Number());
					
					service2.addEntry_Uncleared(entry);
					service2.addEntry(entry);
				}else {
					ProfiteAndLose entry=new ProfiteAndLose();
					entry.setDate(newLot.getAdded_Date());
					entry.setTo_Pay(0);
					entry.setPaid(newLot.getLorry_fee());
					entry.setReason("Other expences for Lot form  Lorry "+newLot.getLorry_Number());
					ProfitAndLossService.addProfitAndLossEntry(entry);
				}
			}
			
			
			
			Notifications notifications = Notifications.create();
			notifications.title("Succesfull");
			notifications.text("Lot added succesfully");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showConfirm();
			
			back=FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Ftrade.fxml"));
			setNode(back);
			
		}else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Lot adding unsuccesfull");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}

		
	}

	
	 void setNode(Node node) {
			
			Vehicles.getChildren().clear();
			Vehicles.setTopAnchor(node,0.0);
			Vehicles.setRightAnchor(node, 0.0);
			Vehicles.setLeftAnchor(node, 0.0);
			Vehicles.setBottomAnchor(node, 0.0);
			Vehicles.getChildren().addAll((Node) node);

	        FadeTransition ft = new FadeTransition(Duration.millis(1500));
	        ft.setNode(node);
	        ft.setFromValue(0.1);
	        ft.setToValue(1);
	        ft.setCycleCount(1);
	        ft.setAutoReverse(false);
	        ft.play();
	        
		
	    }
		
	
	public void back(ActionEvent event) throws IOException {
		back=FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Ftrade.fxml"));
		setNode(back);
		
	}
	
}
