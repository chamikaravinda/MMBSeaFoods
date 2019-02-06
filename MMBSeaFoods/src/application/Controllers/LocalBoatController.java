package application.Controllers;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LocalBoatController {

    @FXML
    private AnchorPane ftrade;
    
    AnchorPane add;
    
    void setNode(Node node) {
    	ftrade.getChildren().clear();
    	ftrade.setTopAnchor(node,0.0);
    	ftrade.setRightAnchor(node, 0.0);
    	ftrade.setLeftAnchor(node, 0.0);
    	ftrade.setBottomAnchor(node, 0.0);
    	ftrade.getChildren().addAll((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    

    @FXML
    void back(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LStocks.fxml"));
        setNode(add);

    }
     
    @FXML
    void switchAddLBoat(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LNewBoats.fxml"));
        setNode(add);
    }
    

    @FXML
    void switchBuyers(ActionEvent event)throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LBuyers.fxml"));
        setNode(add);
    }

    @FXML
    void switchFishTypes(ActionEvent event)throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LFishTypes.fxml"));
        setNode(add);
    }

    @FXML
    void switchStoks(ActionEvent event)throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LStocks.fxml"));
        setNode(add);
    }
    
    

}
