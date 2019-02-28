package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Models.Boat;
import application.Models.Fish_Lot;
import application.Models.Fish_stock;
import application.Services.BoatService;
import application.Services.Fish_LotServices;
import application.Services.Fish_stockService;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ViewLotController implements Initializable {

	@FXML
	private AnchorPane Accounts;

	@FXML
	private TableView<Fish_stock> Fish_Stock;

	@FXML
	private TableColumn<?, ?> clmBoat;

	@FXML
	private TableColumn<?, ?> clmWeight;

	@FXML
	private TableColumn<?, ?> clmBprice;

	@FXML
	private Label lblAddedDate;

	@FXML
	private Label lblLorryNumber;

	@FXML
	private Label lblLorryFee;

	@FXML
	private Label lblIceFee;

	@FXML
	private Label lblOtherFee;

	@FXML
	private Label lblFishWeight;

	@FXML
	private Label lblFishPrice;

	@FXML
	private Label lblTotalCommision;

	@FXML
	private Label lblBuyingPrice;

	@FXML
	private Label lblIceFee1;

	@FXML
	private Label lblLotID;
	AnchorPane lots;

	Fish_stockService service = new Fish_stockService();
	Fish_LotServices serviceB = new Fish_LotServices();
	BoatService serviceC = new BoatService();

	public ObservableList<Fish_stock> list = FXCollections.observableArrayList();

	public void setID(String id) {

		lblLotID.setText(id);

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		Platform.runLater(() -> {

			Fish_Lot thislot;

			list.clear();
			ArrayList<Fish_stock> lots = null;
			try {
				thislot = serviceB.getTheLot(Integer.parseInt(lblLotID.getText()));
				lots = service.getLotStocks(Integer.parseInt(lblLotID.getText()));

				for (Fish_stock lot : lots) {

					Fish_Lot Stocklot = null;
					Boat boat = null;

					Stocklot = serviceB.getTheLot(lot.getLot_ID());
					boat = serviceC.getBoat(lot.getBoat_ID());

					lot.setLorry_Number(Stocklot.getLorry_Number());
					lot.setBoatName(boat.getBoatNameorNumber());
					lot.setStotal_Weight("Kg " + lot.getTotal_Weight() + "0");
					lot.setStotalBuying_price("Rs." + lot.getTotalBuying_price() + "0");
					list.add(lot);
				}

				clmWeight.setCellValueFactory(new PropertyValueFactory<>("Stotal_Weight"));
				clmBprice.setCellValueFactory(new PropertyValueFactory<>("StotalBuying_price"));
				clmBoat.setCellValueFactory(new PropertyValueFactory<>("BoatName"));
				Fish_Stock.setItems(list);

				lblAddedDate.setText(thislot.getAdded_Date());
				lblLorryNumber.setText(thislot.getLorry_Number());
				lblFishWeight.setText(String.format("%2.0f", thislot.getBuying_Weight()) + ".00");
				lblIceFee.setText("Rs. " + String.format("%2.0f", thislot.getIce_fee()) + ".00");
				lblLorryFee.setText("Rs. " + String.format("%2.0f", thislot.getLorry_fee()) + ".00");
				lblOtherFee.setText("Rs. " + String.format("%2.0f", thislot.getOther_fees()) + ".00");
				lblTotalCommision.setText("Rs. " + String.format("%2.0f", thislot.getBrokerFee()) + ".00");
				lblBuyingPrice.setText("Rs. " + String.format("%2.0f", thislot.getBuying_price()) + ".00");

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
								controller.setbackCommond(1);

								setNode(root);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					return row;
				});

			} catch (SQLException e) {
				e.printStackTrace();
			}

		});

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

		lots = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Ftrade.fxml"));
		setNode(lots);
	}

}
