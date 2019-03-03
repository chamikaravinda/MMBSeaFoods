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

	/**
	 * Local fish table
	 */
	@FXML
	private TableView<Local_Fish_types> tblFishType1;

	@FXML
	private TableColumn<?, ?> clmName1;

	@FXML
	private TableColumn<?, ?> clmB101;

	@FXML
	private TableColumn<?, ?> clm10T151;

	@FXML
	private TableColumn<?, ?> clm15T201;

	@FXML
	private TableColumn<?, ?> clm20T251;

	@FXML
	private TableColumn<?, ?> clm25T301;

	@FXML
	private TableColumn<?, ?> clmA301;

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
				sup.setSprice_U10("Rs." + String.format("%2.2f", sup.getPrice_U10()) );
				sup.setSprice_10T15("Rs." + String.format("%2.2f", sup.getPrice_10T15()) );
				sup.setSprice_15T20("Rs." + String.format("%2.2f", sup.getPrice_15T20()) );
				sup.setSprice_20T25("Rs." + String.format("%2.2f", sup.getPrice_20T25()));
				sup.setSprice_25T30("Rs." + String.format("%2.2f", sup.getPrice_25T30()));
				sup.setSprice_A30("Rs." + String.format("%2.2f", sup.getPrice_A30()) );
				list.add(sup);

			}

			for (Local_Fish_types sup : ftypesLocal) {
				sup.setSprice_U10("Rs." + String.format("%2.2f", sup.getPrice_U10()) );
				sup.setSprice_10T15("Rs." + String.format("%2.2f", sup.getPrice_10T15()) );
				sup.setSprice_15T20("Rs." + String.format("%2.2f", sup.getPrice_15T20()) );
				sup.setSprice_20T25("Rs." + String.format("%2.2f", sup.getPrice_20T25()));
				sup.setSprice_25T30("Rs." + String.format("%2.2f", sup.getPrice_25T30()));
				sup.setSprice_A30("Rs." + String.format("%2.2f", sup.getPrice_A30()) );
				listLocal.add(sup);

			}
			
			clmName.setCellValueFactory(new PropertyValueFactory<>("Name"));
			clmB10.setCellValueFactory(new PropertyValueFactory<>("sprice_U10"));
			clm10T15.setCellValueFactory(new PropertyValueFactory<>("sprice_10T15"));
			clm15T20.setCellValueFactory(new PropertyValueFactory<>("sprice_15T20"));
			clm20T25.setCellValueFactory(new PropertyValueFactory<>("sprice_20T25"));
			clm25T30.setCellValueFactory(new PropertyValueFactory<>("sprice_25T30"));
			clmA30.setCellValueFactory(new PropertyValueFactory<>("sprice_A30"));

			tblFishType.setItems(list);

			clmName1.setCellValueFactory(new PropertyValueFactory<>("Name"));
			clmB101.setCellValueFactory(new PropertyValueFactory<>("sprice_U10"));
			clm10T151.setCellValueFactory(new PropertyValueFactory<>("sprice_10T15"));
			clm15T201.setCellValueFactory(new PropertyValueFactory<>("sprice_15T20"));
			clm20T251.setCellValueFactory(new PropertyValueFactory<>("sprice_20T25"));
			clm25T301.setCellValueFactory(new PropertyValueFactory<>("sprice_25T30"));
			clmA301.setCellValueFactory(new PropertyValueFactory<>("sprice_A30"));

			tblFishType1.setItems(listLocal);

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

		Local_Fish_types type = tblFishType1.getSelectionModel().getSelectedItem();

		if (type != null) {

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/application/Views/Ltrade/LEditFishType.fxml"));
			Parent root = loader.load();
			EditLocalFishTypeController controller = loader.<EditLocalFishTypeController>getController();
			String id = Integer.toString(type.getID());
			controller.setID(id);
			controller.setBackCommond(1);

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
