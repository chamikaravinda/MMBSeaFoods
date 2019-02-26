package application.Controllers;

import java.io.IOException;
import java.sql.SQLException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.Models.LocalBoat;
import application.Services.LocalBoatService;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddLocalBoatController {


    @FXML
    private AnchorPane Boats;

    @FXML
    private JFXButton btnAdd;
    
    
    @FXML
    private JFXTextField txtBoatName;

    @FXML
    private JFXTextField txtOwner;

    @FXML
    private JFXTextField txtMobile;
    
    AnchorPane add;
    
    LocalBoatService service= new LocalBoatService();
    
    @FXML
    void AddBoat(ActionEvent event)throws SQLException, IOException{
    	
    	LocalBoat localboat=new LocalBoat();
    	localboat.setBoatNameorNumber(txtBoatName.getText());	
    	localboat.setMobile(txtMobile.getText());
    	localboat.setOwner(txtOwner.getText());
		
		if(!txtBoatName.getText().isEmpty() && !txtMobile.getText().isEmpty() && !txtOwner.getText().isEmpty()   ) {
			
			service.addLocalBoat(localboat);
			Notifications notifications = Notifications.create();
			notifications.title("Succesfull");
			notifications.text("Boat added succesfully");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showConfirm();
			
			add=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LBoats.fxml"));
			setNode(add);
			
		}else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Boat adding unsuccesfull");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}
		

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

    @FXML
    void back(ActionEvent event) throws IOException {

    	add=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LBoats.fxml"));
        setNode(add);
    }

    
    
}