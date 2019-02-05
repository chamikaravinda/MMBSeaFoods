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
	private AnchorPane Accounts;

	AnchorPane add;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	void setNode(Node node) {

		Accounts.getChildren().clear();
		Accounts.setTopAnchor(node, 0.0);
		Accounts.setRightAnchor(node, 0.0);
		Accounts.setLeftAnchor(node, 0.0);
		Accounts.setBottomAnchor(node, 0.0);
		Accounts.getChildren().addAll((Node) node);

		FadeTransition ft = new FadeTransition(Duration.millis(1500));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();

	}

	public void LocalBoats(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("../Views/Accounts/LBoatAccount.fxml"));
		setNode(add);

	}

	public void ForeignBoats(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("../Views/Accounts/FBoatAccount.fxml"));
		setNode(add);

	}
	
	public void LocalBuyers(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("../Views/Accounts/LBuyerAccount.fxml"));
		setNode(add);

	}
	
	public void ForeignBuyers(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("../Views/Accounts/FBuyerAccount.fxml"));
		setNode(add);

	}
	
	public void LocalSales(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("../Views/Accounts/Sales.fxml"));
		setNode(add);

	}

}
