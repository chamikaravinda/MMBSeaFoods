/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controllers;

import com.jfoenix.controls.JFXButton;

import application.Models.Boat;
import application.Models.Fish_Lot;
import application.Models.Foreign_Fish_types;
import application.Services.Foreign_Fish_typesServices;
import application.Services.ProfiteAndLossService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.TabableView;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class FishTypeController implements Initializable {

	@FXML
	private AnchorPane FishType;

	@FXML
	private TableView<Foreign_Fish_types> tblFishType;

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

	ObservableList<Foreign_Fish_types> list = FXCollections.observableArrayList();

	AnchorPane lots, stoks, boats, buyers, fishtypes, newLots;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		list.clear();
		Foreign_Fish_typesServices service = new Foreign_Fish_typesServices();

		ArrayList<Foreign_Fish_types> ftypes = null;

		try {
			ftypes = service.getfishTypes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Foreign_Fish_types sup : ftypes) {
			list.add(sup);
		}

		clmName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		clmB10.setCellValueFactory(new PropertyValueFactory<>("price_U10"));
		clm10T15.setCellValueFactory(new PropertyValueFactory<>("price_10T15"));
		clm15T20.setCellValueFactory(new PropertyValueFactory<>("price_15T20"));
		clm20T25.setCellValueFactory(new PropertyValueFactory<>("price_20T25"));
		clm25T30.setCellValueFactory(new PropertyValueFactory<>("price_25T30"));
		clmA30.setCellValueFactory(new PropertyValueFactory<>("price_A30"));
		

		tblFishType.setItems(list);
	}

	// Set selected node to a content holder
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

	public void switchStock(ActionEvent event) throws IOException {
		lots = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Stocks.fxml"));
		setNode(lots);

	}

	public void AddNewFishType(ActionEvent event) throws IOException {
		lots = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/NewFishType.fxml"));
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

	public void editType(ActionEvent event) throws IOException {

		Foreign_Fish_types type = tblFishType.getSelectionModel().getSelectedItem();

		if (type != null) {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Views/Ftrade/EditFishType.fxml"));
			Parent root = loader.load();
			EditFishTypeController controller = loader.<EditFishTypeController>getController();
			String id = Integer.toString(type.getID());
			controller.setID(id);

			setNode(root);
		} else {

			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Select a Fish type to edit");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();

		}

	}

}
