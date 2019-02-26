package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Models.User;
import application.Services.SettingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SettingsController implements Initializable{

    @FXML
    private AnchorPane home;

    @FXML
    private JFXTextField txtusername;

    @FXML
    private JFXPasswordField txtpass;
    @FXML
    private JFXPasswordField txtpassnew;
    @FXML
    private JFXPasswordField txtpassconfirm;
    
    @FXML
    private Label lblMsg;

    @FXML
    private JFXButton btnAdd;
    
    SettingService service=new SettingService(); 

    User user =null;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			user =service.getUser();
			txtusername.setText(user.getUsername());
			//txtpass.setText(user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	

    @FXML
    void updateUser(ActionEvent event) throws SQLException {
    	
    	
    	
    	user =service.getUser();
    	
    	
    	
    	String UN=txtusername.getText();
		String CUR_PW=txtpass.getText();
		String NEW_PW=txtpassnew.getText();
		String CON_PW=txtpassconfirm.getText();

		if(txtusername.getText().isEmpty()) {
					
			lblMsg.setText("Enter Username First...");
					
		}else if(txtpass.getText().isEmpty()) {
			
			
			lblMsg.setText("Enter Current Password...");
			
		}else if(txtpassnew.getText().isEmpty()) {
			
			
			lblMsg.setText("Enter New Password...");
			
		}else if(txtpassconfirm.getText().isEmpty()) {
			
			
			lblMsg.setText("Enter New Password Again...");
			
		}else if(!CUR_PW.equals(user.getPassword())) {
			
			
			lblMsg.setText("Incorrect Password...");
			
		}else if(!NEW_PW.equals(CON_PW) ) {
			
			lblMsg.setText("New Passwords Are Not Matching...");
			
		}else if(CUR_PW.equals(user.getPassword()) && NEW_PW.equals(CON_PW)){
			
			user.setUsername(UN);
			user.setPassword(CON_PW);
			
			
			if(service.updateUser(user)) {
	    		
	    		Notifications notifications = Notifications.create();
				notifications.title("Succesfull");
				notifications.text("User detailes update succesfully");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showConfirm();
				
				
				txtpass.setText("");
				txtpassnew.setText("");
				txtpassconfirm.setText("");
				txtusername.setText(service.getUser().getUsername());
				
			}else {
				Notifications notifications = Notifications.create();
				notifications.title("Error");
				notifications.text("User detailes update unsuccesfull");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showError();
			}
				
			}
			
			
			
    	
    	
    	
    	
    }
		
    

}

