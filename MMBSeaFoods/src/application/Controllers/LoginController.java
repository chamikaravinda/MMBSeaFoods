package application.Controllers;
  
import java.io.IOException; 
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.WinRegistry;

public class LoginController implements Initializable {
	
	
	@FXML ImageView imgView;
	
	@FXML private Label lblExpDate;
	
	@FXML
	private JFXTextField txtUsername;
	
	
	@FXML private JFXButton btnLogin;

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
		
		Image image=new Image("images/nova.png");
		
		imgView.setImage(image);
		
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
	
	public void Login(ActionEvent event) throws Exception{
		
		user.setUsername(txtUsername.getText());
		user.setPassword(txtPassword.getText());
		
		try {
			User resultuser= lgservice.Login(user);
		
			if(resultuser != null ) {
					
				

					((Node)event.getSource()).getScene().getWindow().hide();
					
					Stage primaryStage =new Stage();
					FXMLLoader loader = new FXMLLoader();
					AnchorPane root = loader.load(getClass().getResource("/application/Views/Dashboard.fxml").openStream());
					
					//loader.setLocation(getClass().getResource("/application/Views/Dashboard.fxml"));
					//Parent root = loader.load();
					
					Scene scene = new Scene(root);		
					primaryStage.setScene(scene);
					primaryStage.show();

				
		
				
			}else {
				
				errorMessage.setText("Invalid Credentials");
				
		
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void getChecker(Boolean value) {


		txtUsername.setEditable(true);
		txtPassword.setEditable(true);
		errorMessage.setText("");
		btnLogin.setDisable(false);
		
		
		
		if(!value) {
			
			txtUsername.setEditable(false);
			txtPassword.setEditable(false);
			errorMessage.setText("Set Correct Date");
			btnLogin.setDisable(true);
			
			
		}else {
			txtUsername.setEditable(true);
			txtPassword.setEditable(true);
			errorMessage.setText("");
			btnLogin.setDisable(false);
			
			
		}
		
		
		
	}

	public void getExpDate(String dateExp) {


		lblExpDate.setText("");
		lblExpDate.setText(dateExp);
		
	}
	
	
	
	
	public void activate(ActionEvent event) throws IOException {
		
		try {
			
			String year="";
				
			String key_Year_1_BUY = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"Year_1_BUY");
			System.out.println("Windows Distribution For NovaMMBSeaFood Year 1 Checking... = " + key_Year_1_BUY);
			
			String key_Year_2_BUY = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"Year_2_BUY");
			System.out.println("Windows Distribution For NovaMMBSeaFood Year 2 Checking... = " + key_Year_2_BUY);
			
			String key_Year_3_BUY = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"Year_3_BUY");
			System.out.println("Windows Distribution For NovaMMBSeaFood Year 3 Checking... = " + key_Year_3_BUY);
			
			String key_Year_4_BUY = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"Year_4_BUY");
			System.out.println("Windows Distribution For NovaMMBSeaFood Year 4 Checking... = " + key_Year_4_BUY);
						
			String key_Year_5_BUY = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"Year_5_BUY");
			System.out.println("Windows Distribution For NovaMMBSeaFood Year 5 Checking... = " + key_Year_5_BUY);
			
			
			
			
			if(key_Year_1_BUY.equals("89lko")) {
				
				if(key_Year_2_BUY.equals("21hjk")) {
					
					if(key_Year_3_BUY.equals("mnf56")) {
						
						if(key_Year_4_BUY.equals("op893")) {
							
							if(key_Year_5_BUY.equals("fvbg6")) {
								
							}else {			
								year= "FIVE";				
							}
							
						}else {			
							year= "FOUR";				
						}
						
					}else {			
						year= "THREE";				
					}
					
				}else {			
					year= "TWO";				
				}
					
			}else {
				
				year= "ONE";
				
			}
			
			
			
			
			try {

				/*FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/ViewLogin.fxml"));
				Parent root = loader.load();

				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());

				LoginController controller = loader.getController();
				System.out.println("Passing Values");
				controller.getChecker(chkVal);
				controller.getExpDate(dateExp);

				System.out.println("Starting");
				
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				primaryStage.setScene(scene);
				primaryStage.show();
				primaryStage.centerOnScreen();*/
				
				
				
				
				((Node)event.getSource()).getScene().getWindow().hide();
				
				Stage primaryStage =new Stage();
				FXMLLoader loader = new FXMLLoader();
				AnchorPane root = loader.load(getClass().getResource("/application/Views/ViewActivation.fxml").openStream());
				
				Scene scene = new Scene(root);
				
				ActivationController controller = loader.getController();
				System.out.println("Passing Values");
				controller.getYear(year);

				System.out.println("Starting");
				
				
				
				primaryStage.setScene(scene);
				primaryStage.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			
			
		} catch (Exception e) {
			
		}
		
	}

	

}
