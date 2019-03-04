package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Models.F_BoatEntryCatogries;
import application.Models.LFish_stock;
import application.Models.LocalBoat;
import application.Models.LocalBoatAccount;
import application.Models.LocalPurchase;
import application.Models.Local_Fish_types;
import application.Models.Local_stock_items;
import application.Models.Stock_Fish;
import application.Services.LFish_stockService;
import application.Services.LocalBoatAccountService;
import application.Services.LocalBoatService;
import application.Services.Local_Fish_typesServices;
import application.Services.Local_PurchasesService;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.fxml.Initializable;

public class AddLocalStockController implements Initializable {

	@FXML
	private AnchorPane NewStocks;

	@FXML
	private JFXComboBox<String> cmbLsBoat;

	@FXML
	private JFXTextField txthabour;

	@FXML
	private Label lbltotalprice;

	@FXML
	private Label lbltotalweight;

	@FXML
	private TableView<Local_stock_items> clmFishTable;

	@FXML
	private TableColumn<?, ?> clmfishtype;

	@FXML
	private TableColumn<?, ?> clmTotalWeight;

	@FXML
	private TableColumn<?, ?> clmTotalPrice;

	@FXML
	private JFXTextField Lfweight;

	@FXML
	private JFXComboBox<String> cmbLftype;

	@FXML
	private JFXButton btnremove;

	@FXML
	private JFXButton AddLFish;

	private double total_price;
	private double total_weight;

	AnchorPane add;

	ObservableList<String> LocalFishTypeList = FXCollections.observableArrayList();
	ObservableList<String> LocalBoatList = FXCollections.observableArrayList();

	Local_Fish_typesServices serviceC = new Local_Fish_typesServices();
	LFish_stockService serviceB = new LFish_stockService();
	LocalBoatAccountService serviceD = new LocalBoatAccountService();
	LocalBoatService serviceE = new LocalBoatService();
	Local_PurchasesService serviceF = new Local_PurchasesService();

	ArrayList<Local_Fish_types> local_fishtype = null;
	ArrayList<LocalBoat> local_boat = null;

	ObservableList<Local_stock_items> local_fishStock = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		local_fishStock.clear();
		// Load DataS to the Combo Boxs
		try {
			local_fishtype = serviceC.getLocalfishTypes();

			for (Local_Fish_types Ltyp : local_fishtype) {

				LocalFishTypeList.add(Ltyp.getName());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			local_boat = serviceE.getLocalBoat();

			for (LocalBoat lboat : local_boat) {

				LocalBoatList.add(lboat.getBoatNameorNumber());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		cmbLsBoat.setItems(LocalBoatList);
		cmbLftype.setItems(LocalFishTypeList);
		// end the Data to Combo Box

		clmfishtype.setCellValueFactory(new PropertyValueFactory<>("fish_name"));
		clmTotalWeight.setCellValueFactory(new PropertyValueFactory<>("Total_Weight"));
		clmTotalPrice.setCellValueFactory(new PropertyValueFactory<>("buying_Price"));

		clmFishTable.setItems(local_fishStock);

		btnremove.setOnAction(e -> {
			Local_stock_items local_Fish = clmFishTable.getSelectionModel().getSelectedItem();
			clmFishTable.getItems().remove(local_Fish);
			clmFishTable.refresh();

		});

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

	public void AddLocalFish(ActionEvent event) throws SQLException {

		if (cmbLftype.getSelectionModel().getSelectedItem() != null && Lfweight.getText() != null) {

			Local_stock_items local_Fish = new Local_stock_items();
			Local_Fish_types types = serviceC.getLocalfishTypes(cmbLftype.getValue());

			local_Fish.setFish_name(cmbLftype.getValue());

			local_Fish.setTotal_Weight(Double.parseDouble(Lfweight.getText()));
			if (local_Fish.getTotal_Weight() > 30.0) {
				local_Fish.setBuying_Price(local_Fish.getTotal_Weight() * types.getPrice_A30());
			} else if (local_Fish.getTotal_Weight() > 25.0 && local_Fish.getTotal_Weight() <= 30.0) {
				local_Fish.setBuying_Price(local_Fish.getTotal_Weight() * types.getPrice_25T30());
			} else if (local_Fish.getTotal_Weight() > 20.0 && local_Fish.getTotal_Weight() <= 25.0) {
				local_Fish.setBuying_Price(local_Fish.getTotal_Weight() * types.getPrice_20T25());
			} else if (local_Fish.getTotal_Weight() > 15.0 && local_Fish.getTotal_Weight() <= 20.0) {
				local_Fish.setBuying_Price(local_Fish.getTotal_Weight() * types.getPrice_15T20());
			} else if (local_Fish.getTotal_Weight() > 10.0 && local_Fish.getTotal_Weight() <= 15.0) {
				local_Fish.setBuying_Price(local_Fish.getTotal_Weight() * types.getPrice_10T15());
			} else {
				local_Fish.setBuying_Price(local_Fish.getTotal_Weight() * types.getPrice_U10());
			}
			local_Fish.setFish_Type(types.getID());
			local_fishStock.add(local_Fish);
			clmFishTable.refresh();
			Lfweight.clear();

			total_price = total_price + local_Fish.getBuying_Price();
			lbltotalprice.setText("Total Price    : Rs." + String.format("%2.2f", total_price));

			total_weight = total_weight + local_Fish.getTotal_Weight();
			lbltotalweight.setText("Total Weight : " + String.format("%2.2f", total_weight));
		}

	}

	public void AddFinalizeStock(ActionEvent event) throws SQLException, IOException {
		if (cmbLsBoat.getValue() != null && txthabour.getText() != null) {
			String boatname = cmbLsBoat.getValue();
			LocalBoat boat = serviceE.getLocalBoat(boatname);

			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

			if (!local_fishStock.isEmpty()) {
				LocalPurchase newPurchase = new LocalPurchase();

				newPurchase.setBoatID(boat.getID());
				newPurchase.setDate(format1.format(new Date()));
				newPurchase.setHabour(txthabour.getText());
				newPurchase.setTotal_Price(total_price);
				newPurchase.setTotal_Weight(total_weight);
				long StockId = serviceF.addLocalPurchase(newPurchase);
				if (StockId != 0) {
					if (serviceB.newStock(local_fishStock)) {
						LocalBoatAccount entry = new LocalBoatAccount();

						entry.setBoat_ID(boat.getID());
						entry.setDate(format1.format(new Date()));
						entry.setPaid(0);
						entry.setPurchase_ID((int) StockId);
						entry.setReason("Fish Purchase of " + total_weight + "Kg");
						entry.setTo_Pay(total_price);
						if (serviceD.addEntries(entry)) {
							if (serviceD.addEntriesUncleard(entry)) {
								if (serviceF.addStockItems(local_fishStock, (int) StockId)) {
									Notifications notifications = Notifications.create();
									notifications.title("Succesfull");
									notifications.text("Fish stock added succesfully");
									notifications.graphic(null);
									notifications.hideAfter(Duration.seconds(2));
									notifications.position(Pos.CENTER);
									notifications.showConfirm();

									add = FXMLLoader
											.load(getClass().getResource("/application/Views/Ltrade/LStocks.fxml"));
									setNode(add);
								}
							}
						}
					}

				} else {
					Notifications notifications = Notifications.create();
					notifications.title("Error");
					notifications.text("Fish stock adding unsuccesfull");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showError();

				}
			} else {
				Notifications notifications = Notifications.create();
				notifications.title("Error");
				notifications.text("Add Fishes to the List");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showError();
			}
		} else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Fill the missing fieleds");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}
	}

	@FXML
	public void back(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LStocks.fxml"));
		setNode(add);

	}

}
