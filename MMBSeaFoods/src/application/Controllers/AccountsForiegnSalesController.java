package application.Controllers;

import java.io.IOException; 

//import org.apache.log4j.net.SocketNode;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccountsForiegnSalesController {

    @FXML
    private AnchorPane Stocks;
    
    
    AnchorPane add;

    @FXML
    private Label BoatName;
    
    
    
    void setNode(Node node) {

		Stocks.getChildren().clear();
		Stocks.setTopAnchor(node, 0.0);
		Stocks.setRightAnchor(node, 0.0);
		Stocks.setLeftAnchor(node, 0.0);
		Stocks.setBottomAnchor(node, 0.0);
		Stocks.getChildren().addAll((Node) node);

		FadeTransition ft = new FadeTransition(Duration.millis(1500));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();

	}


    @FXML
    void back(ActionEvent event)throws IOException {
    	
    	add= FXMLLoader.load(getClass().getResource("/application/Views/Accounts/Accounts.fxml"));
    	setNode(add);

    }

}
