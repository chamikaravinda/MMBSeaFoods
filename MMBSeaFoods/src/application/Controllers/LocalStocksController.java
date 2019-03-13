
package application.Controllers;

import com.jfoenix.controls.JFXButton;

import application.Models.*;
import application.Services.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jws.soap.SOAPBinding.Use;

import org.controlsfx.control.Notifications;

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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class LocalStocksController implements Initializable {

	@FXML
	private AnchorPane LocalStocks;

	@FXML
	private TableView<LFish_stock> LstockTable;

	@FXML
	private TableColumn<?, ?> FishName;

	@FXML
	private TableColumn<?, ?> TotalWeight;

	@FXML
	private JFXButton switchNewStocks;

	@FXML
	private JFXButton switchStocks;

	@FXML
	private JFXButton switchBoats;

	@FXML
	private JFXButton switchBuyers;

	@FXML
	private JFXButton switchFishTypes;

	@FXML
	private TableView<LocalPurchase> tblPurchases;

	@FXML
	private TableColumn<?, ?> clmData;

	@FXML
	private TableColumn<?, ?> clmBoat;

	@FXML
	private TableColumn<?, ?> clmWeight;

	@FXML
	private TableColumn<?, ?> clmPrice;

	// if you want to load to date to table use that below method
	public ObservableList<LFish_stock> list = FXCollections.observableArrayList();
	public ObservableList<LocalPurchase> purhcaselist = FXCollections.observableArrayList();
	AnchorPane add, LocalNewStocks; // handle buttons that contains in Anchorpane

	LFish_stockService service = new LFish_stockService(); // Database handling
	Local_Fish_typesServices serviceB = new Local_Fish_typesServices();// Data base handling
	Local_PurchasesService serviceD = new Local_PurchasesService();
	LocalBoatService boatService = new LocalBoatService();
	LocalBoatAccountService boatAccountService = new LocalBoatAccountService();

	ArrayList<LFish_stock> Llots = null;
	ArrayList<LocalPurchase> plist = null;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		list.clear();// to remove temporary values
		try {
			Llots = service.getUnsoldLocalStocks();
			plist = serviceD.getLocalPurchase();

			for (LFish_stock Llot : Llots) {

				Local_Fish_types localfishtypes = serviceB.getLocalfishTypes(Llot.getFish_Type());
				Llot.setFishName(localfishtypes.getName());

				list.add(Llot);
			}

			for (LocalPurchase entry : plist) {
				LocalBoat boat = boatService.getLocalBoat(entry.getBoatID());
				entry.setBoatName(boat.getBoatNameorNumber());
				entry.setSTotal_Price("Rs. " + String.format("%2.2f", entry.getTotal_Price()));
				entry.setSTotal_Weight(String.format("%2.2f", entry.getTotal_Weight()));
				System.out.println(entry.getDate());
				purhcaselist.add(entry);
			}

			FishName.setCellValueFactory(new PropertyValueFactory<>("FishName"));
			TotalWeight.setCellValueFactory(new PropertyValueFactory<>("Total_Weight"));
			LstockTable.setItems(list);

			clmData.setCellValueFactory(new PropertyValueFactory<>("Date"));
			clmBoat.setCellValueFactory(new PropertyValueFactory<>("BoatName"));
			clmPrice.setCellValueFactory(new PropertyValueFactory<>("STotal_Price"));
			clmWeight.setCellValueFactory(new PropertyValueFactory<>("STotal_Weight"));
			tblPurchases.setItems(purhcaselist);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		tblPurchases.setRowFactory(tv -> {
			TableRow<LocalPurchase> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					try {
						LocalPurchase rowData = row.getItem();
						FXMLLoader loader = new FXMLLoader(
								getClass().getResource("/application/Views/Ltrade/ViewPurchase.fxml"));
						Parent root;

						root = loader.load();

						ViewLocalPurchaseController controller = loader.<ViewLocalPurchaseController>getController();
						controller.setID(rowData.getID());
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

	public void removePurchase(ActionEvent event) throws SQLException, IOException {
		LocalPurchase toDelete = tblPurchases.getSelectionModel().getSelectedItem();

		if (toDelete != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Are You Sure ? This will remove all the data belong to this"
							+ " Purchase including account details and this will update the current stock",
					ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				ArrayList<Local_stock_items> itemsTodelete = serviceD.getLPurchaseStockItems(toDelete.getID());
				if (service.UpdateStockAtRemovePurchases(itemsTodelete)) {
					if (serviceD.deleteLocalStockItems(toDelete.getID())) {
						if (boatAccountService.RemovePurchaseFromBoatAccount(toDelete.getID())) {
							if (boatAccountService.RemovePurchaseFromBoatAccount_Unclear(toDelete.getID())) {
								if (serviceD.deleteLocalPurchase(toDelete.getID())) {

									Notifications notifications = Notifications.create();
									notifications.title("Succesfull");
									notifications.text("Fish Purchase deleted succesfully");
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

				}
			}
		} else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Select a purchase to remove");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}

	}

	public void RemovestockItem(ActionEvent event) throws SQLException, IOException {
		LFish_stock toDelete = LstockTable.getSelectionModel().getSelectedItem();
		if (toDelete != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Are You Sure ? This will remove this fish type from stock",
					ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				if (service.deleteLocalStock(toDelete.getID())) {
					Notifications notifications = Notifications.create();
					notifications.title("Succesfull");
					notifications.text("Stock Item deleted succesfully");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showConfirm();
					
					add = FXMLLoader
							.load(getClass().getResource("/application/Views/Ltrade/LStocks.fxml"));
					setNode(add);

					
				}else {
					Notifications notifications = Notifications.create();
					notifications.title("Error");
					notifications.text("Error in removing item");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showError();
				}
			}
		} else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Select a stock item to remove");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}

	}

	void setNode(Node node) {
		LocalStocks.getChildren().clear();
		LocalStocks.setTopAnchor(node, 0.0);
		LocalStocks.setRightAnchor(node, 0.0);
		LocalStocks.setLeftAnchor(node, 0.0);
		LocalStocks.setBottomAnchor(node, 0.0);
		LocalStocks.getChildren().addAll((Node) node);

		FadeTransition ft = new FadeTransition(Duration.millis(1500));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();
	}

	@FXML
	private void switchNewStocks(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LNewStock.fxml"));
		setNode(add);
	}

	@FXML
	public void switchLocalBoats(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LBoats.fxml"));
		setNode(add);

	}

	@FXML
	public void switchLocalBuyers(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LBuyers.fxml"));
		setNode(add);

	}

	@FXML
	void switchFishTypes(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LFishTypes.fxml"));
		setNode(add);

	}

	@FXML
	void switchSell(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LocalSell.fxml"));
		setNode(add);
	}

	@FXML
	void switchLocalSell(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LocalSell.fxml"));
		setNode(add);

	}

}
