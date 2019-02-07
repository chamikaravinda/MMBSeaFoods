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
import application.Services.BoatService;
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

public class EditBoatController implements Initializable {
	
	@FXML	
	private Label lblID;

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
    
    AnchorPane Vboat;
    
    BoatService service =new BoatService();

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	Platform.runLater(() -> {
		
				try {
					Boat boat = service.getBoat(Integer.parseInt(lblID.getText()));
					txtBoatName.setText(boat.getBoatNameorNumber());
					txtMobile.setText(boat.getMobile());
					txtOwner.setText(boat.getOwner());
					
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
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
				
				txtMobile.getValidators().add(mobile);
				mobile.setMessage("Please input correct values");
				
				txtMobile.focusedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if(!newValue) {
							txtMobile.validate();
						}
						
					}
					
				});
				
				
    	});
		
	}
    
    
    @FXML
    void UpdateBoat(ActionEvent event) throws SQLException, IOException{
    	
    	
    	Boat boat=new Boat();
		boat.setBoatNameorNumber(txtBoatName.getText());	
		boat.setMobile(txtMobile.getText());
		boat.setOwner(txtOwner.getText());
		boat.setID(Integer.parseInt(lblID.getText()));
		
		if(!boat.getBoatNameorNumber().isEmpty() && !boat.getMobile().isEmpty()) {
			if(service.UpdateBoat(boat)) {
				
				Notifications notifications = Notifications.create();
				notifications.title("Succesfull");
				notifications.text("Boat Updated succesfully");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showConfirm();
				
				Vboat=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Boats.fxml"));
				setNode(Vboat);
				
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
    }

    @FXML
    void back(ActionEvent event) throws IOException {
    	
    	Vboat=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Boats.fxml"));
		setNode(Vboat);

    }

	

	public void setID(String ID){
		
		lblID.setText(ID);
		
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
	    
}
