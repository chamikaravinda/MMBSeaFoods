package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import application.Models.Foreign_Fish_types;
import application.Models.Local_Fish_types;
import application.Services.DashboardHomeService;
import application.Services.Foreign_Fish_typesServices;
import application.Services.Local_Fish_typesServices;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DashboardHomeController implements Initializable {

	@FXML
	private AnchorPane home;

	@FXML
	private Label lblLots;

	@FXML
	private Label lblcommition;

	@FXML
	private Label lblboatpay;

	@FXML
	private Label lblbuyerpay;

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

	@FXML
	private TableView<Local_Fish_types> tblvLocalFishType;

	@FXML
	private TableColumn<?, ?> tblcNameLocal;

	@FXML
	private TableColumn<?, ?> tblcPriceLocal;

	DashboardHomeService service = new DashboardHomeService();
	ObservableList<Foreign_Fish_types> list = FXCollections.observableArrayList();
	ObservableList<Local_Fish_types> listLocal = FXCollections.observableArrayList();
	NumberFormat formatter = new DecimalFormat("#0.00");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			lblLots.setText(service.TotalLots());
			lblbuyerpay.setText(service.TotalBuyer());
			lblcommition.setText(service.TotalCommition());
			lblboatpay.setText(service.TotalBoat());

			list.clear();
			listLocal.clear();
			Foreign_Fish_typesServices service = new Foreign_Fish_typesServices();
			Local_Fish_typesServices serviceLocal = new Local_Fish_typesServices();

			ArrayList<Foreign_Fish_types> ftypes = null;
			ArrayList<Local_Fish_types> ftypesLocal = null;

			try {
				ftypes = service.getfishTypes();
				ftypesLocal = serviceLocal.getLocalfishTypes();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (Foreign_Fish_types sup : ftypes) {
				sup.setSprice_U10("Rs." + String.format("%2.0f", sup.getPrice_U10()) + ".00");
				sup.setSprice_10T15("Rs." + String.format("%2.0f", sup.getPrice_10T15()) + ".00");
				sup.setSprice_15T20("Rs." + String.format("%2.0f", sup.getPrice_15T20()) + ".00");
				sup.setSprice_20T25("Rs." + String.format("%2.0f", sup.getPrice_20T25()) + ".00");
				sup.setSprice_25T30("Rs." + String.format("%2.0f", sup.getPrice_25T30()) + ".00");
				sup.setSprice_A30("Rs." + String.format("%2.0f", sup.getPrice_A30()) + ".00");
				list.add(sup);
				System.out.println("Bug check " +sup.getPrice_U10() );

			}

			for (Local_Fish_types sup1 : ftypesLocal) {

				listLocal.add(sup1);
			}
			
			clmName.setCellValueFactory(new PropertyValueFactory<>("Name"));
			clmB10.setCellValueFactory(new PropertyValueFactory<>("sprice_U10"));
			clm10T15.setCellValueFactory(new PropertyValueFactory<>("sprice_10T15"));
			clm15T20.setCellValueFactory(new PropertyValueFactory<>("sprice_15T20"));
			clm20T25.setCellValueFactory(new PropertyValueFactory<>("sprice_20T25"));
			clm25T30.setCellValueFactory(new PropertyValueFactory<>("sprice_25T30"));
			clmA30.setCellValueFactory(new PropertyValueFactory<>("sprice_A30"));

			tblFishType.setItems(list);

			tblcNameLocal.setCellValueFactory(new PropertyValueFactory<>("Name"));
			tblcPriceLocal.setCellValueFactory(new PropertyValueFactory<>("Price"));

			tblvLocalFishType.setItems(listLocal);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void editType(ActionEvent event) throws IOException {

		Foreign_Fish_types type = tblFishType.getSelectionModel().getSelectedItem();

		if (type != null) {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Views/Home/EditFishType.fxml"));
			Parent root = loader.load();
			EditFishTypeController2 controller = loader.<EditFishTypeController2>getController();
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

	@FXML
	void editTypeLocal(ActionEvent event) throws IOException {

		Local_Fish_types type = tblvLocalFishType.getSelectionModel().getSelectedItem();

		if (type != null) {

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/application/Views/Home/EditFishTypeLocal.fxml"));
			Parent root = loader.load();
			EditFishTypeController2Local controller = loader.<EditFishTypeController2Local>getController();
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

	void setNode(Node node) {
		home.getChildren().clear();
		home.setTopAnchor(node, 0.0);
		home.setRightAnchor(node, 0.0);
		home.setLeftAnchor(node, 0.0);
		home.setBottomAnchor(node, 0.0);
		home.getChildren().addAll((Node) node);

		FadeTransition ft = new FadeTransition(Duration.millis(1500));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();
	}

}
