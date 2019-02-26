package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.Foreign_Fish_types;
import application.Models.Local_Fish_types;
import application.Services.Foreign_Fish_typesServices;
import application.Services.Local_Fish_typesServices;
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

public class EditFishTypeController2Local implements Initializable{
	
	 @FXML
	    private AnchorPane FishType;

	    @FXML
	    private JFXButton btnAdd;

	    @FXML
	    private JFXTextField txtName;

	    @FXML
	    private JFXTextField txtPrice;
	    
	    @FXML
	    private Label lblID;
	    
	    AnchorPane types;
	    
	    Local_Fish_types type =null;
	    Local_Fish_typesServices service = new Local_Fish_typesServices();
	    
	   

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		Platform.runLater(() -> {
			
			try {
				
				type = service.getLocalfishTypes(Integer.parseInt(lblID.getText()));
				txtName.setText(type.getName());
				txtPrice.setText(Double.toString(type.getPrice())+"0");
				
				

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			RequiredFieldValidator Name = new RequiredFieldValidator();
			NumberValidator Price = new NumberValidator();

			
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
			
			txtPrice.getValidators().add(Price);
			Price.setMessage("Please input correct values");
			
			txtPrice.focusedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(!newValue) {
						txtPrice.validate();
					}
					
				}
				
			});
			
			
			
			
			
			
			
		});
		
	}
	
	
	
	
	 @FXML
	    void UpdateFishType(ActionEvent event) throws SQLException, IOException {
	    	
	    	Local_Fish_types type =new Local_Fish_types();
			type.setName(txtName.getText());
			type.setPrice(Double.parseDouble(txtPrice.getText()));
			type.setID(Integer.parseInt(lblID.getText()));
			
			if( type.getName().isEmpty() || txtPrice.getText().isEmpty()) {	
				
				
				Notifications notifications = Notifications.create();
				notifications.title("Error");
				notifications.text("One more filed is empty");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showError();
				
				
			}else {
				if(service.UpdateLocal_Fish_Type(type)) {
					Notifications notifications = Notifications.create();
					notifications.title("Succesfull");
					notifications.text("Fish Type updated succesfully");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showConfirm();
					
					types=FXMLLoader.load(getClass().getResource("/application/Views/Home/Home.fxml"));
					setNode(types);
				}else {
					Notifications notifications = Notifications.create();
					notifications.title("Error");
					notifications.text("Fish Type updat unsuccesfull");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showError();
				}
			}
			
	    }

	
	 public void setID(String ID) {
	    	lblID.setText(ID);
	 }
	 
	 @FXML
	 void back(ActionEvent event) throws IOException {

	    	types=FXMLLoader.load(getClass().getResource("/application/Views/Home/Home.fxml"));
			setNode(types);
			
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
}
