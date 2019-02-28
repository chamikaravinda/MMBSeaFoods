/*
/MMBSeaFoods/src/application/Views/Ltrade/LStocks.fxml * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controllers;

import com.jfoenix.controls.JFXButton;

import application.Models.Boat;
import application.Models.Fish_Lot;
import application.Models.Fish_stock;
import application.Models.Vehicles;
import application.Services.BoatService;
import application.Services.Fish_LotServices;
import application.Services.Fish_stockService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class StocksController implements Initializable {

	@FXML
	private AnchorPane Stocks;

	@FXML
	private TableView<Fish_stock> Fish_Stock;

	@FXML
	private TableColumn<?, ?> clmDate;

	@FXML
	private TableColumn<?, ?> clmLorry;

	@FXML
	private TableColumn<?, ?> clmWeight;

	@FXML
	private TableColumn<?, ?> clmBprice;

	@FXML
	private TableColumn<?, ?> clmBoat;

	public ObservableList<Fish_stock> list = FXCollections.observableArrayList();// if you want to load to date to table
																					// use that method

	AnchorPane lots, stoks, boats, buyers, fishtypes, newLots;

	Fish_stockService service = new Fish_stockService();
	Fish_LotServices serviceB = new Fish_LotServices();
	BoatService serviceC = new BoatService();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		list.clear();
		ArrayList<Fish_stock> lots = null;
		try {
			lots = service.getUnsoldStocks();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Fish_stock lot : lots) {

			Fish_Lot Stocklot = null;
			Boat boat = null;

			try {
				Stocklot = serviceB.getTheLot(lot.getLot_ID());
				boat = serviceC.getBoat(lot.getBoat_ID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			lot.setLorry_Number(Stocklot.getLorry_Number());
			lot.setBoatName(boat.getBoatNameorNumber());
			lot.setStotal_Weight("Kg " + lot.getTotal_Weight() + "0");
			lot.setStotalBuying_price("Rs." + lot.getTotalBuying_price() + "0");
			list.add(lot);
		}

		clmDate.setCellValueFactory(new PropertyValueFactory<>("Added_Date"));
		clmWeight.setCellValueFactory(new PropertyValueFactory<>("Stotal_Weight"));
		clmLorry.setCellValueFactory(new PropertyValueFactory<>("Lorry_Number"));
		clmBprice.setCellValueFactory(new PropertyValueFactory<>("StotalBuying_price"));
		clmBoat.setCellValueFactory(new PropertyValueFactory<>("BoatName"));
		Fish_Stock.setItems(list);

		Fish_Stock.setRowFactory(tv -> {
			TableRow<Fish_stock> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					try {
						Fish_stock rowData = row.getItem();
						FXMLLoader loader = new FXMLLoader(
								getClass().getResource("/application/Views/Ftrade/ViewStock.fxml"));
						Parent root;

						root = loader.load();

						ViewStockController controller = loader.<ViewStockController>getController();
						String id = Integer.toString(rowData.getID());
						controller.setID(id);

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

	// Set selected node to a content holder
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

	public void switchStock(ActionEvent event) throws IOException {
		lots = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Stocks.fxml"));
		setNode(lots);

	}

	public void AddStoks(ActionEvent event) throws IOException {
		lots = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/NewStock.fxml"));
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



}
