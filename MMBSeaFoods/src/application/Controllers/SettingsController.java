package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.User;
import application.Services.SettingService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class SettingsController implements Initializable {
	
	SettingService settingServices=new SettingService();
	
	@FXML private JFXTextField txtbUsername;
	@FXML private JFXTextField txtbCurrentPassword;
	@FXML private JFXTextField txtbNewPassword;
	@FXML private JFXTextField txtbConfirmPassword;
	@FXML private Label lblMsg;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		RequiredFieldValidator UNvalidator =new RequiredFieldValidator();
		RequiredFieldValidator CUvalidator =new RequiredFieldValidator();
		RequiredFieldValidator NWvalidator =new RequiredFieldValidator();
		RequiredFieldValidator COvalidator =new RequiredFieldValidator();
		
		txtbUsername.getValidators().add(UNvalidator);
		UNvalidator.setMessage("Please input the Username");
		
		txtbUsername.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtbUsername.validate();
				}
				
			}
			
		});
		
		txtbCurrentPassword.getValidators().add(CUvalidator);
		CUvalidator.setMessage("Please input the Current Password");
		
		txtbCurrentPassword.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtbCurrentPassword.validate();
				}
				
			}		
		});
		
		
		
		
		
		
		
		txtbNewPassword.getValidators().add(NWvalidator);
		NWvalidator.setMessage("Please input the Current Password");
		
		txtbNewPassword.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtbNewPassword.validate();
				}
				
			}		
		});
		
		
		txtbConfirmPassword.getValidators().add(COvalidator);
		COvalidator.setMessage("Please input the Current Password");
		
		txtbConfirmPassword.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtbConfirmPassword.validate();
				}
				
			}		
		});
		
		
		
	}
	
	public void changePassword(ActionEvent event) {
		
		
		
		try {
			
			String UN=txtbUsername.getText();
			String CUR_PW=txtbCurrentPassword.getText();
			String NEW_PW=txtbNewPassword.getText();
			String CON_PW=txtbConfirmPassword.getText();
			
			
			User user=settingServices.getUser(UN);

			if(txtbUsername.getText().isEmpty()) {
						
				lblMsg.setText("Enter Username First...");
						
			}else if(txtbCurrentPassword.getText().isEmpty()) {
				
				
				lblMsg.setText("Enter Current Password...");
				
			}else if(txtbNewPassword.getText().isEmpty()) {
				
				
				lblMsg.setText("Enter New Password...");
				
			}else if(txtbConfirmPassword.getText().isEmpty()) {
				
				
				lblMsg.setText("Enter New Password Again...");
				
			}else if(!UN.equalsIgnoreCase(user.getUsername())) {
				
				
				lblMsg.setText("Incorrect Username...");
				
			}else if(!CUR_PW.equals(user.getPassword())) {
				
				
				lblMsg.setText("Incorrect Password...");
				
			}else if(!NEW_PW.equals(CON_PW) ) {
				
				lblMsg.setText("New Passwords Are Not Matching...");
				
			}else if(UN.equalsIgnoreCase(user.getUsername()) && CUR_PW.equals(user.getPassword()) && NEW_PW.equals(CON_PW)){
				
				
				Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("Are You Sure To Update Password ?");
				Optional <ButtonType> action= alert.showAndWait();
				
				if(action.get()==ButtonType.OK) {
					
					
					settingServices.updatePassword(UN, CON_PW);
					
					
				
					
					
					
				}
					
				clear(event);
				
				

				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public void clear(ActionEvent event) {
		
		txtbUsername.setText("");
		txtbCurrentPassword.setText("");
		txtbNewPassword.setText("");
		txtbConfirmPassword.setText("");
		
	}
	
	
	

}
