/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controllers;

import com.jfoenix.controls.JFXButton;

import application.Models.Boat_Account_UnCleared;
import application.Models.Fish_Lot;
import application.Models.Fish_stock;
import application.Models.LocalBoatAccountUnCleared;
import application.Models.Vehicles;
import application.Services.BoatService;
import application.Services.Boat_AccountServices;
import application.Services.Fish_LotServices;
import application.Services.Fish_stockService;
import application.Services.Third_Party_AccountServices;
import application.Services.stock_FishService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import sun.print.resources.serviceui_pt_BR;

public class FtradeController implements Initializable {

	@FXML
	private AnchorPane ftrade;

	@FXML
	private TableView<Fish_Lot> tableLots;

	@FXML
	private TableColumn<?, ?> clmDate;

	@FXML
	private TableColumn<?, ?> clmWeight;

	@FXML
	private TableColumn<?, ?> clmLorry;

	@FXML
	private TableColumn<?, ?> clmBprice;

	@FXML
	private TableColumn<?, ?> clmsell;

	public ObservableList<Fish_Lot> list = FXCollections.observableArrayList();

	AnchorPane lots, stoks, boats, buyers, fishtypes, newLots;

	Fish_stockService service = new Fish_stockService();
	Fish_LotServices serviceB = new Fish_LotServices();
	BoatService serviceC = new BoatService();
	Boat_AccountServices serviceD = new Boat_AccountServices();
	Third_Party_AccountServices serviceF = new Third_Party_AccountServices();
	stock_FishService serviceG = new stock_FishService();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		list.clear();

		ArrayList<Fish_Lot> lots = null;
		try {
			lots = serviceB.getUnslodLots();
			for (Fish_Lot lot : lots) {
				lot.setSbuying_price("Rs." + lot.getBuying_price() + "0");
				lot.setSBuying_Weight("Kg " + lot.getBuying_Weight() + "0");
				list.add(lot);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		clmDate.setCellValueFactory(new PropertyValueFactory<>("Added_Date"));
		clmWeight.setCellValueFactory(new PropertyValueFactory<>("SBuying_Weight"));
		clmLorry.setCellValueFactory(new PropertyValueFactory<>("Lorry_Number"));
		clmBprice.setCellValueFactory(new PropertyValueFactory<>("Sbuying_price"));

		tableLots.setItems(list);

		tableLots.setRowFactory(tv -> {
			TableRow<Fish_Lot> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					try {
						Fish_Lot rowData = row.getItem();
						if (rowData.getBuying_Weight() != 0) {
							FXMLLoader loader = new FXMLLoader(
									getClass().getResource("/application/Views/Ftrade/ViewLot.fxml"));
							Parent root;

							root = loader.load();

							ViewLotController controller = loader.<ViewLotController>getController();
							String id = Integer.toString(rowData.getID());
							controller.setID(id);

							setNode(root);
						} else {
							Notifications notifications = Notifications.create();
							notifications.title("Error");
							notifications.text("No stocks in Lot to see");
							notifications.graphic(null);
							notifications.hideAfter(Duration.seconds(2));
							notifications.position(Pos.CENTER);
							notifications.showError();
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			return row;
		});

	}

	// Set selected node to a content holder
	void setNode(Node node) {
		ftrade.getChildren().clear();
		ftrade.setTopAnchor(node, 0.0);
		ftrade.setRightAnchor(node, 0.0);
		ftrade.setLeftAnchor(node, 0.0);
		ftrade.setBottomAnchor(node, 0.0);
		ftrade.getChildren().addAll((Node) node);

		FadeTransition ft = new FadeTransition(Duration.millis(1500));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();
	}

	public void deleteLot(ActionEvent event) throws SQLException {

		Fish_Lot toDeleteLot = tableLots.getSelectionModel().getSelectedItem();
		ArrayList<Fish_stock> toDeleteStocks = service.getLotStocks(toDeleteLot.getID());
		if (toDeleteLot != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Are You Sure ? This will remove all the data belong to this Lot including account details and stocks",
					ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				if (deleteStocks(toDeleteStocks)) {
					if (serviceF.clearAccontsLotDeleteUncleard(toDeleteLot.getID())) {
						if (serviceF.clearAccontsLotDelete(toDeleteLot.getID())) {
							if (serviceB.deleteLot(toDeleteLot.getID())) {

								Notifications notifications = Notifications.create();
								notifications.title("Succesfull");
								notifications.text("Lot deleted succesfully");
								notifications.graphic(null);
								notifications.hideAfter(Duration.seconds(2));
								notifications.position(Pos.CENTER);
								notifications.showConfirm();

								tableLots.getItems().remove(toDeleteLot);
								tableLots.refresh();

							}
						}
					}
				}
			}
		} else {

			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Select A Lot to delete");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}
	}

	public void SellALot() throws IOException {

		Fish_Lot lot = tableLots.getSelectionModel().getSelectedItem();
		if (lot != null) {
			if (lot.getBuying_Weight() != 0) {

				/*
				 * lots=FXMLLoader.load(getClass().getResource(
				 * "/application/Views/Ftrade/NewSales.fxml")); setNode(lots);
				 */

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Views/Ftrade/NewSales.fxml"));
				Parent root = loader.load();
				FLotSalesController controller = loader.<FLotSalesController>getController();
				String id = Integer.toString(lot.getID());
				controller.setID(id);

				setNode(root);

			} else {
				Notifications notifications = Notifications.create();
				notifications.title("Error");
				notifications.text("No stocks in Lot to sell");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showError();
			}

		} else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Select a Lot to sell");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}
	}

	public void switchStock(ActionEvent event) throws IOException {
		lots = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Stocks.fxml"));
		setNode(lots);

	}

	public void AddNewLot(ActionEvent event) throws IOException {
		lots = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/NewLot.fxml"));
		setNode(lots);

	}

	public void switchLot(ActionEvent event) throws IOException {
		lots = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Ftrade.fxml"));
		setNode(lots);

	}

	public void switchBoat(ActionEvent event) throws IOException {
		lots = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Boats.fxml"));
		setNode(lots);

	}

	public void switchBuyers(ActionEvent event) throws IOException {
		lots = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Buyers.fxml"));
		setNode(lots);

	}

	public void switchFishTypes(ActionEvent event) throws IOException {
		lots = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/FishTypes.fxml"));
		setNode(lots);

	}

	private boolean deleteStocks(ArrayList<Fish_stock> toDeleteStocks) throws SQLException {
		for (Fish_stock stock : toDeleteStocks) {

			Fish_stock toDelete = stock;
			if (serviceD.RemoveFromBoatAccountStockEntry(toDelete.getID())) {
				Boat_Account_UnCleared entry =new Boat_Account_UnCleared();
				entry.setBoat_ID(toDelete.getBoat_ID());
				entry.setTo_Pay(toDelete.getFishprice());
				if (serviceD.RemoveFromBoatAccountStockEntryUncleard(entry)) {
					if (serviceF.clearAccontsStockDelete(toDelete.getID())) {
						if (serviceF.clearAccontsStockDeleteUncleard(toDelete.getID())) {
							if (serviceG.deleteStockFishes(toDelete.getID())) {
								if (service.deleteStock(toDelete.getID())) {
									tableLots.refresh();
								} else {

									Notifications notifications = Notifications.create();
									notifications.title("Error");
									notifications.text("Deleting stock unsuccesfull");
									notifications.graphic(null);
									notifications.hideAfter(Duration.seconds(2));
									notifications.position(Pos.CENTER);
									notifications.showError();
									System.out.println("Delete stock fail");
									return false;

								}
							} else {
								System.out.println("Delete stock fish fail");
							}

						} else {
							System.out.println("Delete commision entry unclear fail");
						}
					} else {
						System.out.println("Delete commision entry fail");
					}

				} else {
					System.out.println("Delete boat account entry unclear fail");
				}
			} else {
				System.out.println("Delete boat account entry fail");
			}

		}

		return true;
	}

}
