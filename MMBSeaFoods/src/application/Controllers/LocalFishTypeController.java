package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Models.Foreign_Fish_types;
import application.Models.LocalBoat;
import application.Models.Local_Fish_types;
import application.Services.Local_Fish_typesServices;
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

public class LocalFishTypeController implements Initializable {

	@FXML
	private AnchorPane FishType;

	@FXML
	private TableView<Local_Fish_types> tblFishType;

	@FXML
	private TableColumn<?, ?> clmName;

	@FXML
	private TableColumn<?, ?> clmB10;

	@FXML
	private TableColumn<?, ?> clm10T15;

	@FXML
	private TableColumn<?, ?> clm15T20;

	@FXML
	private TableColumn<?, ?> clm20T25;

	@FXML
	private TableColumn<?, ?> clm25T30;

	@FXML
	private TableColumn<?, ?> clmA30;

	AnchorPane add;

	ObservableList<Local_Fish_types> list = FXCollections.observableArrayList();

	Local_Fish_typesServices service = new Local_Fish_typesServices();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list.clear();
		ArrayList<Local_Fish_types> LFtypes = new ArrayList<>();

		try {
			LFtypes = service.getLocalfishTypes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Local_Fish_types sup : LFtypes) {
			sup.setSprice_U10("Rs." + String.format("%2.2f", sup.getPrice_U10()) );
			sup.setSprice_10T15("Rs." + String.format("%2.2f", sup.getPrice_10T15()) );
			sup.setSprice_15T20("Rs." + String.format("%2.2f", sup.getPrice_15T20()) );
			sup.setSprice_20T25("Rs." + String.format("%2.2f", sup.getPrice_20T25()));
			sup.setSprice_25T30("Rs." + String.format("%2.2f", sup.getPrice_25T30()));
			sup.setSprice_A30("Rs." + String.format("%2.2f", sup.getPrice_A30()) );
			
			list.add(sup);
		}

		clmName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		clmB10.setCellValueFactory(new PropertyValueFactory<>("sprice_U10"));
		clm10T15.setCellValueFactory(new PropertyValueFactory<>("sprice_10T15"));
		clm15T20.setCellValueFactory(new PropertyValueFactory<>("sprice_15T20"));
		clm20T25.setCellValueFactory(new PropertyValueFactory<>("sprice_20T25"));
		clm25T30.setCellValueFactory(new PropertyValueFactory<>("sprice_25T30"));
		clmA30.setCellValueFactory(new PropertyValueFactory<>("sprice_A30"));
		

		tblFishType.setItems(list);

	}

	void setNode(Node node) {
		FishType.getChildren().clear();
		FishType.setTopAnchor(node, 0.0);
		FishType.setRightAnchor(node, 0.0);
		FishType.setLeftAnchor(node, 0.0);
		FishType.setBottomAnchor(node, 0.0);
		FishType.getChildren().addAll((Node) node);

		FadeTransition ft = new FadeTransition(Duration.millis(1500));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();
	}

	@FXML
	void switchAddLFishType(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LNewFishType.fxml"));
		setNode(add);

	}

	@FXML
	void switchBoats(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LBoats.fxml"));
		setNode(add);
	}

	@FXML
	void switchBuyers(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LBuyers.fxml"));
		setNode(add);
	}

	@FXML
	void switchStocks(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LStocks.fxml"));
		setNode(add);
	}

	@FXML
	void switchSell(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LocalSell.fxml"));
		setNode(add);
	}

	@FXML
	void switchEditLFishType(ActionEvent event) throws IOException {

		Local_Fish_types lFtype = tblFishType.getSelectionModel().getSelectedItem();


		if (lFtype != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Views/Ltrade/LEditFishType.fxml"));
			Parent root = loader.load();
			EditLocalFishTypeController LFishcontroller = loader.<EditLocalFishTypeController>getController();
			String id = Integer.toString(lFtype.getID());
			LFishcontroller.setID(id);

			setNode(root);
		}

	}

}