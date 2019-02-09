/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane holderPane;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnFtrade;
    @FXML
    private JFXButton btnLTrade;
    @FXML
    private JFXButton btnVehicles;
    @FXML
    private JFXButton btnSettings;
    
    @FXML
    private JFXButton btnLogout;
    
    
    AnchorPane Home,Ftrade,Ltrade,Vehicles,Settings,Accounts;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Load all fxmls in a cache
        try {
             Home = FXMLLoader.load(getClass().getResource("../Views/Home/Home.fxml"));            
             Ftrade = FXMLLoader.load(getClass().getResource("../Views/Ftrade/Ftrade.fxml"));
             Ltrade = FXMLLoader.load(getClass().getResource("../Views/Ltrade/LStocks.fxml"));
             Vehicles = FXMLLoader.load(getClass().getResource("../Views/Vehicles/Vehicles.fxml"));
             Settings = FXMLLoader.load(getClass().getResource("../Views/Settings/settings.fxml"));
             Accounts = FXMLLoader.load(getClass().getResource("../Views/Accounts/Accounts.fxml"));
            setNode(Home);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
	//Set selected node to a content holder
    void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.setTopAnchor(node,0.0);
        holderPane.setRightAnchor(node, 0.0);
        holderPane.setLeftAnchor(node, 0.0);
        holderPane.setBottomAnchor(node, 0.0);
        holderPane.getChildren().addAll((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void switchHome(ActionEvent event) throws IOException {
    	Home=FXMLLoader.load(getClass().getResource("../Views/Home/Home.fxml"));
        setNode(Home);
    }

    @FXML
    private void switchFtrade(ActionEvent event) {
        setNode(Ftrade);
    }

    @FXML
    private void switchLtrade(ActionEvent event) {
        setNode(Ltrade);
    }

    @FXML
    private void switchVehicles(ActionEvent event) {
        setNode(Vehicles);
    }

    @FXML
    private void switchSettings(ActionEvent event) {
        setNode(Settings);
    }
    
    @FXML
    private void switchAccounts(ActionEvent event) {
        setNode(Accounts);
    }
    
    @FXML
    private void LogOut(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../Views/Login.fxml"));
		Parent parent = loader.load();
		
		Scene scene =  new Scene(parent);
		scene.getStylesheets().add(getClass().getResource("../Views/custom.css").toExternalForm());

		
		Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
		window.centerOnScreen();
    }
    @FXML
    private JFXButton btnVehicles1;

   


   

    

}
