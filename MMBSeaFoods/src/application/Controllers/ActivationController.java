package application.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.KeyFive;
import util.WinRegistry;

public class ActivationController implements Initializable{

	@FXML private TextField txtbLicenseKey;
	@FXML private Label lblActivationMsg;
	@FXML private Label lblProductID;
	
	@FXML ImageView imgView;
	
	String key_year="";
	
	
	KeyFive keyFive=new KeyFive();
	
	public void getYear(String year) {
		key_year=year;
		lblActivationMsg.setText("You Have To Buy Licenese Key For Year "+year);
		lblProductID.setText("Your Product ID  : "+keyFive.getProductID());
	}
	
	
	@FXML
	public void activate(ActionEvent event) {
		
		
		try {
			
			String user_key=txtbLicenseKey.getText();
			
			
			/*String year_1_key = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER,
					"SOFTWARE\\NovaBillMaker", "Year_1");
			String year_2_key = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER,
					"SOFTWARE\\NovaBillMaker", "Year_2");
			String year_3_key = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER,
					"SOFTWARE\\NovaBillMaker", "Year_3");
			String year_4_key = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER,
					"SOFTWARE\\NovaBillMaker", "Year_4");
			String year_5_key = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER,
					"SOFTWARE\\NovaBillMaker", "Year_5");*/
			
			
			String year_1_key =keyFive.getYearOneKey();
			String year_2_key =keyFive.getYearTwoKey();
			String year_3_key =keyFive.getYearThreeKey();
			String year_4_key =keyFive.getYearFourKey();
			String year_5_key =keyFive.getYearFiveKey();
			
			
			
			
			
			
			if(key_year.equals("ONE")) {
				
				if(user_key.equals(year_1_key)) {
					
					
					WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_1_BUY", "89lko");
					
					Alert alert=new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setContentText("Product Activation Is Success");
					alert.showAndWait();
					Platform.exit();
				}else {
					Alert alert=new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Product Activation Failed");
					alert.showAndWait();
					Platform.exit();
				}
				
			}
			
			
			if(key_year.equals("TWO")) {
				
				if(user_key.equals(year_2_key)) {
					
					
					WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_2_BUY", "21hjk");
					
					Alert alert=new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setContentText("Product Activation Is Success");
					alert.showAndWait();
					Platform.exit();
				}else {
					Alert alert=new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Product Activation Failed");
					alert.showAndWait();
					Platform.exit();
				}
				
			}


			if(key_year.equals("THREE")) {
				
				if(user_key.equals(year_3_key)) {
					
					
					WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_3_BUY", "mnf56");
					
					Alert alert=new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setContentText("Product Activation Is Success");
					alert.showAndWait();
					Platform.exit();
				}else {
					Alert alert=new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Product Activation Failed");
					alert.showAndWait();
					Platform.exit();
				}
				
			}
			
			
			if(key_year.equals("FOUR")) {
				
				if(user_key.equals(year_4_key)) {
					
					
					WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_4_BUY", "op893");
					
					Alert alert=new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setContentText("Product Activation Is Success");
					alert.showAndWait();
					Platform.exit();
				}else {
					Alert alert=new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Product Activation Failed");
					alert.showAndWait();
					Platform.exit();
				}
				
			}
			
			
			if(key_year.equals("FIVE")) {
				
				if(user_key.equals(year_5_key)) {
					
					
					WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_5_BUY", "fvbg6");
					
					Alert alert=new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setContentText("Product Activation Is Success");
					alert.showAndWait();
					Platform.exit();
				}else {
					Alert alert=new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Product Activation Failed");
					alert.showAndWait();
					Platform.exit();
				}
				
			}

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void ExitWindow(ActionEvent event) throws Exception{
		Platform.exit();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {


		Image image=new Image("images/novadark.png");
		
		imgView.setImage(image);
		
	}

}
