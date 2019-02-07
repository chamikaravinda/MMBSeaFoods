package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccountsController implements Initializable {

	@FXML
	private AnchorPane Stocks;

	AnchorPane add;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

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
	    public void switchForiegnBoats(ActionEvent event) throws IOException {
		  
		  	add = FXMLLoader.load(getClass().getResource("../Views/Accounts/FBoatAccount.fxml"));
			setNode(add);

	    }

	    @FXML
	    public void switchForiegnBuyers(ActionEvent event) throws IOException {

	    	add = FXMLLoader.load(getClass().getResource("../Views/Accounts/FBuyerAccount.fxml"));
			setNode(add);

	    }

	    @FXML
	    public void switchLocalBuyers(ActionEvent event) throws IOException {
	    	
	    	add = FXMLLoader.load(getClass().getResource("../Views/Accounts/LBuyerAccount.fxml"));
			setNode(add);
	    }

	    @FXML
	    public void switchLocalBoats(ActionEvent event) throws IOException {
	    	add = FXMLLoader.load(getClass().getResource("../Views/Accounts/LBoatAccount.fxml"));
			setNode(add);


	    }

   

}
