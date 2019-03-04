package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXComboBox;

import application.Models.Boat_Account;
import application.Models.Boat_Account_UnCleared;
import application.Models.Fish_stock;
import application.Models.LocalBoatAccount;
import application.Models.LocalBoatAccountUnCleared;
import application.Models.LocalPurchase;
import application.Services.AccountServices;
import application.Services.LocalBoatAccountService;
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
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class AccountsLocalBoatController implements Initializable {

	@FXML
	private AnchorPane Accounts;

	AnchorPane add;

	@FXML
	private JFXComboBox<String> cmbBoatNames;

	AccountServices accountServices = new AccountServices();
	Local_PurchasesService purchaseService = new Local_PurchasesService();
	LocalBoatAccountService boatAccountService = new LocalBoatAccountService();

	private ObservableList<String> boatNameList = FXCollections.observableArrayList();
	private ObservableList<LocalBoatAccount> boatDetailsList = FXCollections.observableArrayList();

	@FXML
	private TableView<LocalBoatAccount> tblvBoatDetails;

	@FXML
	private TableColumn<?, ?> tblcDate;
	@FXML
	private TableColumn<?, ?> tblcReason;
	@FXML
	private TableColumn<?, ?> tblcTopay;
	@FXML
	private TableColumn<?, ?> tblcPaid;

	private String boatname;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		boatNameList = accountServices.getAllBoatNames();
		cmbBoatNames.setItems(boatNameList);

		cmbBoatNames.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {

				System.out.println(t1);

				String name = t1;

				boatname = name;

				int id = accountServices.getBoatIDByName(name);

				boatDetailsList.clear();

				ArrayList<LocalBoatAccount> boat_list = accountServices.getAllBOATList(id);

				for (LocalBoatAccount boat : boat_list) {
					boatDetailsList.add(boat);
				}
				tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
				tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
				tblcTopay.setCellValueFactory(new PropertyValueFactory<>("To_Pay"));
				tblcPaid.setCellValueFactory(new PropertyValueFactory<>("Paid"));

				tblvBoatDetails.setItems(boatDetailsList);

			}
		});

		tblvBoatDetails.setRowFactory(tv -> {
			TableRow<LocalBoatAccount> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					try {
						LocalBoatAccount rowData = row.getItem();
						FXMLLoader loader = new FXMLLoader(
								getClass().getResource("/application/Views/Ltrade/ViewPurchase.fxml"));
						Parent root;

						root = loader.load();

						ViewLocalPurchaseController controller = loader.<ViewLocalPurchaseController>getController();
						controller.setID(rowData.getPurchase_ID());
						controller.setBackCommond(1);
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

	public void getBoatDetails() {
		try {

			String name = cmbBoatNames.getSelectionModel().getSelectedItem().toString();

			boatname = name;

			int id = accountServices.getBoatIDByName(name);

			showBoatDetailsTableList(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showBoatDetailsTableList(int id) {

		boatDetailsList.clear();

		ArrayList<LocalBoatAccount> boat_list = accountServices.getAllBOATList(id);

		for (LocalBoatAccount boat : boat_list) {
			boatDetailsList.add(boat);
		}
		tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
		tblcTopay.setCellValueFactory(new PropertyValueFactory<>("To_Pay"));
		tblcPaid.setCellValueFactory(new PropertyValueFactory<>("Paid"));

		tblvBoatDetails.setItems(boatDetailsList);

		// tblvBoatDetails.setItems(boatDetailsList);

	}

	public void notPayied(ActionEvent event) throws SQLException {

		LocalBoatAccount entry = tblvBoatDetails.getSelectionModel().getSelectedItem();

		if (entry != null && entry.getPaid() != 0) {
			LocalBoatAccount Newentry = new LocalBoatAccount();

			Newentry.setBoat_ID(entry.getBoat_ID());
			Newentry.setPaid(0);
			Newentry.setPurchase_ID(entry.getPurchase_ID());
			Newentry.setTo_Pay(entry.getPaid());
			LocalPurchase stock = purchaseService.getLocalPurchase((int) entry.getPurchase_ID());
			Newentry.setReason("Fish Purchase of " + stock.getTotal_Weight());
			Newentry.setDate(stock.getDate());

			tblvBoatDetails.getItems().remove(entry);
			tblvBoatDetails.refresh();

			if (boatAccountService.addEntriesUncleard(Newentry)) {
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

	public void makePayment(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/LMakePayment.fxml"));
		setNode(add);

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
