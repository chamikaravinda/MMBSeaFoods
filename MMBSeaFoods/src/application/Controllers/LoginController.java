package application.Controllers;
  
import java.io.IOException; 
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.User;
import application.Services.LoginService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	@FXML
	private JFXTextField txtUsername;

	@FXML
	private JFXPasswordField txtPassword;
	
	@FXML
	private Label errorMessage;
	
	@FXML 
	private AnchorPane appLayout;	
	
	User user=new User();
	
	LoginService lgservice = new LoginService();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		RequiredFieldValidator Uvalidator =new RequiredFieldValidator();
		RequiredFieldValidator Pvalidator =new RequiredFieldValidator();
		
		txtUsername.getValidators().add(Uvalidator);
		Uvalidator.setMessage("Please input the Username");
		
		txtUsername.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtUsername.validate();
				}
				
			}
			
		});
		
		txtPassword.getValidators().add(Pvalidator);
		Pvalidator.setMessage("Please input the Password");
		
		txtPassword.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					txtPassword.validate();
				}
				
			}		
		});
		
		
		
		
	}
	
	public void Login(ActionEvent event) {
		
		user.setUsername(txtUsername.getText());
		user.setPassword(txtPassword.getText());
		
		try {
			User resultuser= lgservice.Login(user);
		
			if(resultuser != null ) {
			
				((Node)event.getSource()).getScene().getWindow().hide();
				
				Stage primaryStage =new Stage();
				FXMLLoader loader = new FXMLLoader();
				AnchorPane root = loader.load(getClass().getResource("/application/Views/Dashboard.fxml").openStream());
				
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
			//	scene.getStylesheets().add(getClass().getResource("/application/Views/dashboardStyle.css").toExternalForm());
				primaryStage.show();
				
			}else {
				
				errorMessage.setText("Invalid Credentials");
		
			}
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}
