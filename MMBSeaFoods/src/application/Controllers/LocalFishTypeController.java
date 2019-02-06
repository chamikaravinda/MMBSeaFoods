package application.Controllers;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LocalFishTypeController {

    @FXML
    private AnchorPane FishType;

 AnchorPane add;
    
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


    @FXML
    void switchAddLFishType(ActionEvent event) throws IOException {
    	
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LNewFishType.fxml"));
        setNode(add);


    }
    
    @FXML
    void switchBoats(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LBoats.fxml"));
        setNode(add);
    }

    @FXML
    void switchBuyers(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LBuyers.fxml"));
        setNode(add);
    }

    @FXML
    void switchStocks(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LStocks.fxml"));
        setNode(add);
    }
  

}