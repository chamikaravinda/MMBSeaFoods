package application.Controllers;

import java.io.IOException; 
import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.Boat;
import application.Models.Fish_Lot;
import application.Models.Foreign_Fish_types;
import application.Models.ProfiteAndLose;
import application.Models.Third_Party_Account;
import application.Models.Vehicles;
import application.Services.BoatService;
import application.Services.Fish_LotServices;
import application.Services.Foreign_Fish_typesServices;
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

public class AddFishTypeController implements Initializable {
	
	@FXML
	private AnchorPane FishType;
	
	@FXML
	private JFXTextField txtName;
	
	@FXML
	private JFXTextField txtU15;
	
	@FXML
	private JFXTextField txt15B20;
	
	@FXML
	private JFXTextField txtA20;
	

	
	AnchorPane back;
	
	Foreign_Fish_typesServices service =new Foreign_Fish_typesServices();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		RequiredFieldValidator Name = new RequiredFieldValidator();
		NumberValidator U15 = new NumberValidator();
		NumberValidator B1520 = new NumberValidator();
		NumberValidator A20 = new NumberValidator();

		
		txtName.getValidators().add(Name );
		Name.setMessage("Please input the Fish Type");
		
		txtName.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtName.validate();
				}
				
			}
			
		});
		
		txtU15.getValidators().add(U15);
		U15.setMessage("Please input correct values");
		
		txtU15.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtU15.validate();
				}
				
			}
			
		});
		
		txt15B20.getValidators().add(B1520);
		B1520.setMessage("Please input correct values");
		
		txt15B20.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txt15B20.validate();
				}
				
			}
			
		});
		
		txtA20.getValidators().add(A20);
		A20.setMessage("Please input correct values");
		
		txtA20.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtA20.validate();
				}
				
			}
			
		});

	
}
	
	public void AddFishType(ActionEvent event) throws SQLException, IOException {
		Foreign_Fish_types type =new Foreign_Fish_types();
		type.setName(txtName.getText());
		type.setPrice_15B(Double.parseDouble(txtU15.getText()));
		type.setPrice20_15(Double.parseDouble(txt15B20.getText()));
		type.setPrice_20P(Double.parseDouble(txtA20.getText()));
		
		if(service.addForeign_Fish_Type(type)) {
		Notifications notifications = Notifications.create();
		notifications.title("Succesfull");
		notifications.text("Fish Type added succesfully");
		notifications.graphic(null);
		notifications.hideAfter(Duration.seconds(2));
		notifications.position(Pos.CENTER);
		notifications.showConfirm();
		
		back=FXMLLoader.load(getClass().getResource("../Views/Ftrade/FishTypes.fxml"));
		setNode(back);
		
	}else {
		Notifications notifications = Notifications.create();
		notifications.title("Error");
		notifications.text("Fish Type unsuccesfull");
		notifications.graphic(null);
		notifications.hideAfter(Duration.seconds(2));
		notifications.position(Pos.CENTER);
		notifications.showError();
	}
		
		
	}

	
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
		
	
	public void back(ActionEvent event) throws IOException {
		back=FXMLLoader.load(getClass().getResource("../Views/Ftrade/FishTypes.fxml"));
		setNode(back);
		
	}
	
}
