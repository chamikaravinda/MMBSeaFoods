package application.Controllers;

import java.io.IOException;
import java.sql.SQLException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXTextField;

import application.Models.Buyers;
import application.Models.LocalBuyers;
import application.Services.LocalBuyerService;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddLocalBuyers {

    @FXML
    private AnchorPane Buyers;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtMobile;

    LocalBuyerService service= new LocalBuyerService();
    AnchorPane add;
    
    void setNode(Node node) {
    	Buyers.getChildren().clear();
    	Buyers.setTopAnchor(node,0.0);
    	Buyers.setRightAnchor(node, 0.0);
    	Buyers.setLeftAnchor(node, 0.0);
    	Buyers.setBottomAnchor(node, 0.0);
    	Buyers.getChildren().addAll((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        
        
        
    }
    
    
    
    
    @FXML
    void back(ActionEvent event)throws IOException{
    	
     	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LBuyers.fxml"));
        setNode(add);

    }
    
    @FXML
    void AddBoat(ActionEvent event)throws SQLException, IOException {

    	
    	LocalBuyers Lbuyers = new LocalBuyers();
    	Lbuyers.setName(txtName.getText());
    	Lbuyers.setMobile_No(txtMobile.getText());
		
		if(!txtName.getText().isEmpty() && !txtMobile.getText().isEmpty()) {
			service.addLocalBuyer(Lbuyers);
			
			Notifications notifications = Notifications.create();
			notifications.title("Succesfull");
			notifications.text("Buyer added succesfully");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showConfirm();
			
			
			add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LBuyers.fxml"));
	        setNode(add);
			
		}else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Buyer adding unsuccesfull");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}
		
    	
    	
    }

}
