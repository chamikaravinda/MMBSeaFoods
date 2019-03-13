package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.Boat;
import application.Models.LocalBoat;
import application.Services.BoatService;
import application.Services.LocalBoatService;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class EditLocalBoatController implements Initializable {
	
    @FXML
    private AnchorPane Boats;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXTextField txtBoatName;

    @FXML
    private JFXTextField txtMobile;

    @FXML
    private JFXTextField txtOwner;
    
    @FXML
    private Label lblID;

    AnchorPane Vboat;
    
    LocalBoatService service =new LocalBoatService();
    
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		
    	Platform.runLater(() ->{
    	try {
			LocalBoat Lboat = service.getLocalBoat(Integer.parseInt(lblID.getText()));
			txtBoatName.setText(Lboat.getBoatNameorNumber());
			txtMobile.setText(Lboat.getMobile());
			txtOwner.setText(Lboat.getOwner());
			
			
			
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    	
    	
    	RequiredFieldValidator boatName = new RequiredFieldValidator();
		RequiredFieldValidator mobile = new RequiredFieldValidator();
		
		
		txtBoatName.getValidators().add(boatName);
		boatName.setMessage("Please input the Boat Name");
		
		txtBoatName.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtBoatName.validate();
				}
				
			}
			
		});
    	
    	});
    	
		
	}
    
    
    public void UpdateLocalBoat(ActionEvent event)throws SQLException, IOException {
    	
    	LocalBoat localBoat= new LocalBoat();
    	localBoat.setBoatNameorNumber(txtBoatName.getText());
    	localBoat.setMobile(txtMobile.getText());
    	localBoat.setOwner(txtOwner.getText());
    	localBoat.setID(Integer.parseInt(lblID.getText()));
    	
    	
    	if(!localBoat.getBoatNameorNumber().isEmpty() && !localBoat.getOwner().isEmpty()) {
    		if(service.UpdateLocalBoat(localBoat)) {
    			Notifications notifications = Notifications.create();
				notifications.title("Succesfull");
				notifications.text("Local Boat Updated succesfully");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showConfirm();
				
				Vboat=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LBoats.fxml"));
				setNode(Vboat);
    			
    		}
    		
    	}else {
    		Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Boat Updating unsuccesfull");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
    		
    		
    		
    		
    	}

    }
    
   
  
    @FXML
    void back(ActionEvent event) throws IOException {
    	
    	Vboat=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LBoats.fxml"));
		setNode(Vboat);

    }


	 void setNode(Node node) {
		 Boats.getChildren().clear();
		 Boats.setTopAnchor(node,0.0);
		 Boats.setRightAnchor(node, 0.0);
		 Boats.setLeftAnchor(node, 0.0);
		 Boats.setBottomAnchor(node, 0.0);
		 Boats.getChildren().addAll((Node) node);

	        FadeTransition ft = new FadeTransition(Duration.millis(1500));
	        ft.setNode(node);
	        ft.setFromValue(0.1);
	        ft.setToValue(1);
	        ft.setCycleCount(1);
	        ft.setAutoReverse(false);
	        ft.play();
	    }


	public void setID(String id) {
		lblID.setText(id);
	}


	
	    
}
