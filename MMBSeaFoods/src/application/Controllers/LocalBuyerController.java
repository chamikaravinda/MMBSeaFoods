package application.Controllers;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LocalBuyerController {

    @FXML
    private AnchorPane Buyers;

   
    AnchorPane add;
    
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
    
    
    @FXML
    void switchAddLbuyer(ActionEvent event)throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LNewBuyer.fxml"));
        setNode(add);
    	
    }
    
  

    @FXML
    void switchBoats(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LBoats.fxml"));
        setNode(add);
    }

    @FXML
    void switchFishTypes(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LFishTypes.fxml"));
        setNode(add);
    }

    @FXML
    void switchStocks(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LStocks.fxml"));
        setNode(add);
    }

    
    
}