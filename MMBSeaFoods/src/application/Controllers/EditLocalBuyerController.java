package application.Controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import application.Models.LocalBuyers;
import application.Services.LocalBuyerService;
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

public class EditLocalBuyerController implements Initializable {

	@FXML
    private AnchorPane Buyers;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtMobile;

    @FXML
    private Label lblID;
    
    AnchorPane Vbuyer;
    
    
    
    LocalBuyerService service = new LocalBuyerService();
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
			
		
			Platform.runLater(()->{
				 
				try {
					
					LocalBuyers lBuyers= service.getLocalBoat(Integer.parseInt(lblID.getText()));
					txtName.setText(lBuyers.getName());
					txtMobile.setText(lBuyers.getMobile_No());
					
				} catch (NumberFormatException e) {
					
					e.printStackTrace();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				RequiredFieldValidator buyerName = new RequiredFieldValidator();
				RequiredFieldValidator mobile = new RequiredFieldValidator();
				
				txtName.getValidators().add(buyerName);
				buyerName.setMessage("Please input the Boat Name");
				
				buyerName.focusedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if(!newValue) {
							buyerName.validate();
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
    void updateLocalBuyer(ActionEvent event)throws SQLException, IOException {
    	
    	LocalBuyers localBuyer= new LocalBuyers();
    	localBuyer.setName(txtName.getText());
    	localBuyer.setMobile_No(txtMobile.getText());
    	localBuyer.setID(Integer.parseInt(lblID.getText()));
    	
    	
    	if(!localBuyer.getName().isEmpty() && !localBuyer.getMobile_No().isEmpty()) {
    		if(service.UpdateLocalBuyer(localBuyer)) {
    			Notifications notifications = Notifications.create();
				notifications.title("Succesfull");
				notifications.text("Local Buyer Updated succesfully");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showConfirm();
				
				Vbuyer=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LBuyers.fxml"));
				setNode(Vbuyer);
    			
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
	
	 @FXML
	    void back(ActionEvent event) throws IOException {

		 Vbuyer=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LBuyers.fxml"));
	        setNode(Vbuyer);
	    	
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


	public void setID(String id) {
		lblID.setText(id);
	}


    

}
