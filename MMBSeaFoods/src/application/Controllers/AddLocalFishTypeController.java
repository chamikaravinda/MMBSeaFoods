package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXTextField;

import application.Models.Foreign_Fish_types;
import application.Models.Local_Fish_types;
import application.Services.Local_Fish_typesServices;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddLocalFishTypeController {

    @FXML
    private AnchorPane FishType;
    
    AnchorPane add;
    

    @FXML
    private JFXTextField txtFishtype;

    @FXML
    private JFXTextField txtUnitPrice;
   
    
    Local_Fish_typesServices service= new Local_Fish_typesServices();
	
    public void addLFishType(ActionEvent event) throws SQLException, IOException {
    	Local_Fish_types Lftype =new Local_Fish_types();
		
    	Lftype.setName(txtFishtype.getText());
    	Lftype.setPrice(Double.parseDouble(txtUnitPrice.getText()));
    	
		
	if(!txtFishtype.getText().isEmpty() && !txtUnitPrice.getText().isEmpty() ) {
		
		service.addLocal_Fish_Type(Lftype);
		Notifications notifications = Notifications.create();
		notifications.title("Succesfull");
		notifications.text("Fish Type added succesfully");
		notifications.graphic(null);
		notifications.hideAfter(Duration.seconds(2));
		notifications.position(Pos.CENTER);
		notifications.showConfirm();
		
		
		add=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LFishTypes.fxml"));
		setNode(add);
		
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

    @FXML
    public void back(ActionEvent event)throws IOException {


	    add=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LFishTypes.fxml"));
        setNode(add);

    	
    }


    
}
