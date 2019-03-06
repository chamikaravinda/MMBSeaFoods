package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.Models.Boat_Account;
import application.Models.LocalBuyers;
import application.Models.LocalSales;
import application.Models.Local_Fish_types;
import application.Services.AccountServices;
import application.Services.LocalBuyerService;
import application.Services.Local_Fish_typesServices;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccountLocalSalesController implements Initializable {

	@FXML
	private AnchorPane Accounts;

	@FXML
	private TableView<LocalSales> tblLsales;

	@FXML
	private TableColumn<?, ?> clmDate;

	@FXML
	private TableColumn<?, ?> clmBname;

	@FXML
	private TableColumn<?, ?> clmPrice;

	@FXML
	private TableColumn<?, ?> clmWeight;

	AnchorPane add;

	private ObservableList<LocalSales> localSalesEntries = FXCollections.observableArrayList();

	AccountServices accountServices = new AccountServices();
	LocalBuyerService buyerService = new LocalBuyerService();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		localSalesEntries.clear();
		LocalBuyers buyer = new LocalBuyers();

		ArrayList<LocalSales> saleList;
		try {
			saleList = accountServices.getAllLocalSales();
			for (LocalSales sales : saleList) {
				buyer = buyerService.getLocalBuyer(sales.getBuyerID());
				sales.setBuyerName(buyer.getName());
				sales.setSPrice("Rs." +String.format("%2.2f", sales.getPrice()));
				sales.setSTotalWeight(String.format("%2.1f",sales.getTotalWeight()+"Kg"));
				localSalesEntries.add(sales);
			}
			clmDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
			clmBname.setCellValueFactory(new PropertyValueFactory<>("BuyerName"));
			clmPrice.setCellValueFactory(new PropertyValueFactory<>("SPrice"));
			clmWeight.setCellValueFactory(new PropertyValueFactory<>("STotalWeight"));

			tblLsales.setItems(localSalesEntries);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@FXML
	void back(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/Accounts.fxml"));
		setNode(add);

	}

}