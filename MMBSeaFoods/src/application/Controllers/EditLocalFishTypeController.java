package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXTextField;

import application.Models.Local_Fish_types;
import application.Services.Local_Fish_typesServices;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.geometry.Pos;

public class EditLocalFishTypeController implements Initializable {

    @FXML
    private AnchorPane FishType;
    
    
    AnchorPane add;
  
    @FXML
    private JFXTextField txtFishtype;

    @FXML
    private JFXTextField txtUnitPrice;
    
    @FXML
    private Label lblID;
    
   
    
    Local_Fish_typesServices service = new Local_Fish_typesServices();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		

		Platform.runLater(()->{
			 
			try {
				
				Local_Fish_types lFtypes= service.getLocalfishTypes(Integer.parseInt(lblID.getText()));
				txtFishtype.setText(lFtypes.getName());
				txtUnitPrice.setText(Double.toString(lFtypes.getPrice()));
				
			} catch (NumberFormatException e) {
				
				e.printStackTrace();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		});
		
	}
	
	
	
	
	@FXML
    void updateLocalBuyer(ActionEvent event)throws SQLException, IOException {
    	
    	Local_Fish_types lFtypes= new Local_Fish_types();
    	lFtypes.setName(txtFishtype.getText());
    	lFtypes.setPrice(Double.parseDouble(txtUnitPrice.getText()));
    	lFtypes.setID(Integer.parseInt(lblID.getText()));
    	
    	
    	if(!lFtypes.getName().isEmpty()) {
    		if(service.UpdateLocal_Fish_Type(lFtypes)) {
    			Notifications notifications = Notifications.create();
				notifications.title("Succesfull");
				notifications.text("Local Buyer Updated succesfully");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showConfirm();
				
				add=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LFishTypes.fxml"));
				setNode(add);
    			
    		}
    		
    	}else {
    		Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Buyer Updating unsuccesfull");
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

    @FXML
    void back(ActionEvent event) throws IOException {

    	add=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LFishTypes.fxml"));
        setNode(add);
    	
    }

	public void setID(String id) {
		lblID.setText(id);
	}

	

}
