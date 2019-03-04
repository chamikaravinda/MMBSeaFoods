package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.springframework.cglib.core.Local;

import com.jfoenix.controls.JFXButton;

import application.Models.LocalBoat;
import application.Models.LocalPurchase;
import application.Models.Local_Fish_types;
import application.Models.Local_stock_items;
import application.Models.Stock_Fish;
import application.Services.LocalBoatService;
import application.Services.Local_Fish_typesServices;
import application.Services.Local_PurchasesService;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
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

public class ViewLocalPurchaseController implements Initializable {

	@FXML
	private AnchorPane NewStocks;

	@FXML
	private Label lblID;

	@FXML
	private Label lbltotalprice;

	@FXML
	private Label lbltotalweight;

	@FXML
	private Label lblHabour;

	@FXML
	private Label lblBoat;

	@FXML
	private TableView<Local_stock_items> clmFishTable;

	@FXML
	private TableColumn<?, ?> clmfishtype;

	@FXML
	private TableColumn<?, ?> clmTotalWeight;

	@FXML
	private TableColumn<?, ?> clmTotalPrice;

	@FXML
	private JFXButton btnremove;

	AnchorPane add;

	public ObservableList<Local_stock_items> tbllist = FXCollections.observableArrayList();

	int purchaseID = 0;

	int backCommond = 0;
	Local_PurchasesService purchaseservice = new Local_PurchasesService();
	LocalBoatService boatservice = new LocalBoatService();
	Local_Fish_typesServices fishServices = new Local_Fish_typesServices();

	public void setID(int id) {
		this.purchaseID = id;
	};

	public void setBackCommond(int cmd) {
		this.backCommond = cmd;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Platform.runLater(() -> {

			try {
				LocalPurchase purchase = purchaseservice.getLocalPurchase(purchaseID);
				LocalBoat boat = boatservice.getLocalBoat(purchase.getBoatID());
				purchase.setSTotal_Price("Rs." + String.format("%2.2f", purchase.getTotal_Price()));
				purchase.setSTotal_Weight(String.format("%2.2f", purchase.getTotal_Weight()));
				lblHabour.setText(purchase.getHabour());
				lblBoat.setText(boat.getBoatNameorNumber());
				lbltotalprice.setText(purchase.getSTotal_Price());
				lbltotalweight.setText(purchase.getSTotal_Weight());

				ArrayList<Local_stock_items> list = purchaseservice.getLPurchaseStockItems(purchaseID);

				for (Local_stock_items item : list) {
					Local_Fish_types type = fishServices.getLocalfishTypes(item.getFish_Type());

					item.setSTotal_Weight(String.format("%2.2f", item.getTotal_Weight()));
					item.setSbuying_Price("Rs." + String.format("%2.2f", item.getBuying_Price()));
					item.setFish_name(type.getName());
					tbllist.add(item);
				}

				clmTotalPrice.setCellValueFactory(new PropertyValueFactory<>("Sbuying_Price"));
				clmfishtype.setCellValueFactory(new PropertyValueFactory<>("fish_name"));
				clmTotalWeight.setCellValueFactory(new PropertyValueFactory<>("STotal_Weight"));

				clmFishTable.setItems(tbllist);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
	}

	@FXML
	void back(ActionEvent event) throws IOException {

		if (backCommond == 1) {
			add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/LBoatAccount.fxml"));
			setNode(add);
		} else if (backCommond == 2) {
			add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/LMakePayment.fxml"));
			setNode(add);
		} else {
			add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LStocks.fxml"));
			setNode(add);
		}
	}

	void setNode(Node node) {

		NewStocks.getChildren().clear();
		NewStocks.setTopAnchor(node, 0.0);
		NewStocks.setRightAnchor(node, 0.0);
		NewStocks.setLeftAnchor(node, 0.0);
		NewStocks.setBottomAnchor(node, 0.0);
		NewStocks.getChildren().addAll((Node) node);

		FadeTransition ft = new FadeTransition(Duration.millis(1500));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();

	}

}
