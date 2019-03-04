package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXComboBox;

import application.Models.Boat_Account;
import application.Models.Boat_Account_UnCleared;
import application.Models.Fish_stock;
import application.Services.AccountServices;
import application.Services.Boat_AccountServices;
import application.Services.Fish_stockService;
import application.Services.Foreign_Fish_Buyers_AccountServices;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccountsForeignBoatController implements Initializable {

	@FXML
	private AnchorPane Accounts;

	AnchorPane add;

	@FXML
	private JFXComboBox<String> cmbBoatNames;

	AccountServices accountServices = new AccountServices();
	Boat_AccountServices boatAccountService = new Boat_AccountServices();
	Fish_stockService stockService = new Fish_stockService();

	private ObservableList<String> boatNameList = FXCollections.observableArrayList();
	private ObservableList<Boat_Account> boatDetailsList = FXCollections.observableArrayList();

	@FXML
	private TableView<Boat_Account> tblvBoatDetails;

	@FXML
	private TableColumn<?, ?> tblcDate;
	@FXML
	private TableColumn<?, ?> tblcReason;
	@FXML
	private TableColumn<?, ?> tblcTopay;
	@FXML
	private TableColumn<?, ?> tblcPaid;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		boatNameList = accountServices.getAllBoatNamesForeign();
		cmbBoatNames.setItems(boatNameList);

		cmbBoatNames.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {

				System.out.println(t1);

				String name = t1;

				int id = accountServices.getBoatIDByNameForeign(name);

				boatDetailsList.clear();

				ArrayList<Boat_Account> boat_list = accountServices.getAllBOATListForeign(id);

				for (Boat_Account boat : boat_list) {
					if (boat.getTo_Pay() != 0) {
						boat.setSTo_Pay("Rs." + String.format("%2.0f", boat.getTo_Pay()) + ".00");
					} else {
						boat.setSTo_Pay("Rs 0.00");
					}
					if (boat.getPaid() != 0) {
						boat.setSPaid("Rs. " + String.format("%2.0f", boat.getPaid()) + ".00");
					} else {
						boat.setSPaid("Rs 0.00");
					}

					boatDetailsList.add(boat);
				}
				tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
				tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
				tblcTopay.setCellValueFactory(new PropertyValueFactory<>("STo_Pay"));
				tblcPaid.setCellValueFactory(new PropertyValueFactory<>("SPaid"));

				tblvBoatDetails.setItems(boatDetailsList);

				tblvBoatDetails.setRowFactory(tv -> {
					TableRow<Boat_Account> row = new TableRow<>();
					row.setOnMouseClicked(event -> {
						if (event.getClickCount() == 2 && (!row.isEmpty())) {
							try {
								Boat_Account rowData = row.getItem();
								FXMLLoader loader = new FXMLLoader(
										getClass().getResource("/application/Views/Ftrade/ViewStock.fxml"));
								Parent root;

								root = loader.load();

								ViewStockController controller = loader.<ViewStockController>getController();
								String Stockid = Long.toString(rowData.getStock_ID());
								controller.setID(Stockid);
								controller.setbackCommond(2);

								setNode(root);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					return row;
				});

			}
		});

	}

	public void getBoatDetails() {
		try {

			String name = cmbBoatNames.getSelectionModel().getSelectedItem().toString();
			int id = accountServices.getBoatIDByNameForeign(name);

			showBoatDetailsTableList(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showBoatDetailsTableList(int id) throws SQLException {

		boatDetailsList.clear();

		ArrayList<Boat_Account> boat_list = boatAccountService.getAllentries(id);
		for (Boat_Account boat : boat_list) {
			boat.setSTo_Pay("Rs. " + String.format("%2.0f", boat.getTo_Pay()) + ".00");
			boat.setSPaid("Rs. " + String.format("%2.0f", boat.getSPaid()) + ".00");
			boatDetailsList.add(boat);
		}
		tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
		tblcTopay.setCellValueFactory(new PropertyValueFactory<>("STo_Pay"));
		tblcPaid.setCellValueFactory(new PropertyValueFactory<>("SPaid"));

		tblvBoatDetails.setItems(boatDetailsList);

	}

	public void makePayment(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/MakePayment.fxml"));
		setNode(add);

	}

	public void notPayied(ActionEvent event) throws SQLException {

		Boat_Account entry = tblvBoatDetails.getSelectionModel().getSelectedItem();

		if (entry != null && entry.getPaid() != 0) {
			Boat_Account_UnCleared Newentry = new Boat_Account_UnCleared();

			Newentry.setBoat_ID(entry.getBoat_ID());
			Newentry.setPaid(0);
			Newentry.setStock_ID(entry.getStock_ID());
			Newentry.setTo_Pay(entry.getPaid());
			Fish_stock stock = stockService.getStocks((int) entry.getStock_ID());
			Newentry.setReason("Stock Purchase of " + stock.getTotal_Weight());
			Newentry.setDate(stock.getAdded_Date());

			tblvBoatDetails.getItems().remove(entry);
			tblvBoatDetails.refresh();
			
			if (boatAccountService.addEntries_Uncleard(Newentry)) {
				if (boatAccountService.RemoveFromBoatAccount(entry.getID())) {
					Notifications notifications = Notifications.create();
					notifications.title("Succesfull");
					notifications.text("Payment removed succesfully");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showConfirm();
				}
			}
		} else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Select a paid entry");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}

	}

	@FXML
	void back(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/Accounts.fxml"));
		setNode(add);

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

}
