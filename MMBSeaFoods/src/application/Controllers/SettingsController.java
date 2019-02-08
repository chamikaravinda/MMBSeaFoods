package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.Models.User;
import application.Services.SettingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SettingsController implements Initializable{

    @FXML
    private AnchorPane home;

    @FXML
    private JFXTextField txtusername;

    @FXML
    private JFXTextField txtpass;

    @FXML
    private JFXButton btnAdd;
    
    SettingService service=new SettingService(); 

    User user =null;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			user =service.getUser();
			txtusername.setText(user.getUsername());
			txtpass.setText(user.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	

    @FXML
    void updateUser(ActionEvent event) throws SQLException {
    	
    	user.setUsername(txtusername.getText());
    	user.setPassword(txtpass.getText());
    	
    	if(service.updateUser(user)) {
    		
    		Notifications notifications = Notifications.create();
			notifications.title("Succesfull");
			notifications.text("User detailes update succesfully");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showConfirm();
			
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
