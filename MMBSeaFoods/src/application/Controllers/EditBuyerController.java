package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

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
import application.Models.Buyers;
import application.Services.BuyerService;

public class EditBuyerController implements Initializable{

    @FXML
    private AnchorPane Buyers;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtMobile;

    @FXML
    private Label lblID;
    
    
    AnchorPane back;
    
    public void setID(String ID) {
    	lblID.setText(ID);
    }
    
    BuyerService service = new BuyerService();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Platform.runLater(() -> {
			
			try {
				Buyers buyer =service.getBuyers(Integer.parseInt(lblID.getText()));
				
				txtName.setText(buyer.getName());
				txtMobile.setText(buyer.getMobile_No());

				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			RequiredFieldValidator name = new RequiredFieldValidator();
			RequiredFieldValidator mobile = new RequiredFieldValidator();

			
			txtName.getValidators().add(name);
			name .setMessage("Please input the Buyer Name");
			
			txtName.focusedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(!newValue) {
						txtName.validate();
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
    void updateBuyer(ActionEvent event) throws SQLException {
    	
    	Buyers buyer=new Buyers();
		buyer.setName(txtName.getText());
		buyer.setMobile_No(txtMobile.getText());
		buyer.setID(Integer.parseInt(lblID.getText()));
		if(service.UpdateBuyer(buyer)) {
			
			Notifications notifications = Notifications.create();
			notifications.title("Succesfull");
			notifications.text("Buyer Updated succesfully");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showConfirm();
			
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

    @FXML
    void back(ActionEvent event) throws IOException {

    	back=FXMLLoader.load(getClass().getResource("../Views/Ftrade/Buyers.fxml"));
		setNode(back);
    	
    }

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

}
