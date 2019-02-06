package application.Controllers;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddLocalFishTypeController {

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
    public void back(ActionEvent event)throws IOException {


	    add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LFishTypes.fxml"));
        setNode(add);

    	
    }
    
}
