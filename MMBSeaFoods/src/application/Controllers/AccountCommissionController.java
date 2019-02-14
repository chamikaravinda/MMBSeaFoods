package application.Controllers;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccountCommissionController {

	    @FXML
	    private AnchorPane CommissionAccounts;
	    
	    AnchorPane add;
	    
	    
	    
	    void setNode(Node node) {

	    	CommissionAccounts.getChildren().clear();
	    	CommissionAccounts.setTopAnchor(node, 0.0);
	    	CommissionAccounts.setRightAnchor(node, 0.0);
	    	CommissionAccounts.setLeftAnchor(node, 0.0);
	    	CommissionAccounts.setBottomAnchor(node, 0.0);
	    	CommissionAccounts.getChildren().addAll((Node) node);

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

	    	add = FXMLLoader.load(getClass().getResource("../Views/Accounts/Accounts.fxml"));
			setNode(add);
	    }

	}