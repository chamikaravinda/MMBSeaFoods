package application.Controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddLocalBoatController {


    @FXML
    private AnchorPane Boats;

    @FXML
    private JFXButton btnAdd;
    
    
    AnchorPane add;
    
    
    void setNode(Node node) {
    	Boats.getChildren().clear();
    	Boats.setTopAnchor(node,0.0);
    	Boats.setRightAnchor(node, 0.0);
    	Boats.setLeftAnchor(node, 0.0);
    	Boats.setBottomAnchor(node, 0.0);
    	Boats.getChildren().addAll((Node) node);

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

    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LBoats.fxml"));
        setNode(add);
    }

    
    
}