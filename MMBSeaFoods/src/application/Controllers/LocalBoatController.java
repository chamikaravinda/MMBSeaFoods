package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Models.Boat;
import application.Models.LocalBoat;
import application.Services.LocalBoatService;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LocalBoatController implements Initializable {

	@FXML
	private AnchorPane ftrade;

	@FXML
	private TableView<LocalBoat> tblboats;

	@FXML
	private TableColumn<?, ?> clmName;

	@FXML
	private TableColumn<?, ?> clmMobile;

	@FXML
	private TableColumn<?, ?> clmOwner;

	AnchorPane add;

	ObservableList<LocalBoat> list = FXCollections.observableArrayList();

	AnchorPane lots, stoks, boats, buyers, fishtypes, newLots;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		list.clear();

		LocalBoatService service = new LocalBoatService();
		ArrayList<LocalBoat> LBlist = null;

		try {
			LBlist = service.getLocalBoat();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (LocalBoat sup : LBlist) {
			list.add(sup);
		}

		clmName.setCellValueFactory(new PropertyValueFactory<>("BoatNameorNumber"));
		clmMobile.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
		clmOwner.setCellValueFactory(new PropertyValueFactory<>("Owner"));

		tblboats.setItems(list);

	}

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

	@FXML
	void back(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LStocks.fxml"));
		setNode(add);

	}

	@FXML
	void switchAddLBoat(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LNewBoats.fxml"));
		setNode(add);
	}

	@FXML
	void switchBuyers(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LBuyers.fxml"));
		setNode(add);
	}

	@FXML
	void switchFishTypes(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LFishTypes.fxml"));
		setNode(add);
	}

	@FXML
	void switchStoks(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LStocks.fxml"));
		setNode(add);
	}
	
	@FXML
	void switchSell(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LocalSell.fxml"));
		setNode(add);
	}

	public void switchEditLocalBoats(ActionEvent event) throws IOException {
		LocalBoat Lboat = tblboats.getSelectionModel().getSelectedItem();

		
		if (Lboat != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Views/Ltrade/EditLocalBoat.fxml"));
			Parent root = loader.load();
			EditLocalBoatController LBcontroller = loader.<EditLocalBoatController>getController();
			String id = Integer.toString(Lboat.getID());
			LBcontroller.setID(id);

			setNode(root);
		}
	}

}
