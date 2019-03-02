package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXComboBox;

import application.Models.Boat_Account;
import application.Models.Boat_Account_UnCleared;
import application.Models.Buyers;
import application.Models.F_Fish_Buyers_Account;
import application.Models.F_Fish_Buyers_Account_Uncleard;
import application.Models.Fish_Lot;
import application.Models.Fish_stock;
import application.Models.Local_Buyers_Account;
import application.Services.AccountServices;
import application.Services.BuyerService;
import application.Services.Fish_LotServices;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
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

public class ForeignAddBuyerRecievedController implements Initializable {

	@FXML
	private AnchorPane Accounts;

	AnchorPane add;

	private StringProperty id;

	@FXML
	private TableView<F_Fish_Buyers_Account_Uncleard> tblvBuyersDetails;

	@FXML
	private TableColumn<?, ?> tblcDate;
	@FXML
	private TableColumn<?, ?> tblcReason;
	@FXML
	private TableColumn<?, ?> tblcTopay;
	@FXML
	private TableColumn<?, ?> tblcBuyer;

	AccountServices accountServices = new AccountServices();
	BuyerService buyerService = new BuyerService();
	Fish_LotServices LotService =new Fish_LotServices();

	private ObservableList<F_Fish_Buyers_Account_Uncleard> buyerDetailsList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Platform.runLater(() -> {

			buyerDetailsList.clear();

			ArrayList<F_Fish_Buyers_Account_Uncleard> buyerEntry = accountServices.getAllForiegnBuyerAccountUnclear();

			for (F_Fish_Buyers_Account_Uncleard entry : buyerEntry) {
				try {
					Buyers entrybuyer = buyerService.getBuyers(entry.getBuyer_ID());
					entry.setBuyer_name(entrybuyer.getName());
					buyerDetailsList.add(entry);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
			tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
			tblcTopay.setCellValueFactory(new PropertyValueFactory<>("To_Pay"));
			tblcBuyer.setCellValueFactory(new PropertyValueFactory<>("buyer_name"));

			tblvBuyersDetails.setItems(buyerDetailsList);
			
			
			tblvBuyersDetails.setRowFactory(tv -> {
				TableRow<F_Fish_Buyers_Account_Uncleard> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						try {
							F_Fish_Buyers_Account_Uncleard rowData = row.getItem();
							FXMLLoader loader = new FXMLLoader(
									getClass().getResource("/application/Views/Ftrade/ViewLot.fxml"));
							Parent root;

							root = loader.load();

							ViewLotController controller = loader.<ViewLotController>getController();
							String Lid = Integer.toString(rowData.getLot_ID());
							controller.setBackCommond(2);
							controller.setID(Lid);
					
							
							setNode(root);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				return row;
			});


		});

	}
	
	public void recevied(ActionEvent event){
		
		F_Fish_Buyers_Account_Uncleard entry = tblvBuyersDetails.getSelectionModel().getSelectedItem();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		if (entry != null) {
			F_Fish_Buyers_Account Newentry = new F_Fish_Buyers_Account();
			Newentry.setDate(format1.format(new Date()));
			Newentry.setBuyer_ID(entry.getBuyer_ID());
			Newentry.setPaid(entry.getTo_Pay());
			Newentry.setLot_ID(entry.getLot_ID());
			Newentry.setTo_Pay(0);
			Newentry.setReason("Payment reciving for stock sale on  " + entry.getDate());
			tblvBuyersDetails.getItems().remove(entry);
			tblvBuyersDetails.refresh();

			if (accountServices.AddRecievedForeignBuyerPayment(Newentry)) {
				if (accountServices.removeEntryForiegnFishBuyerAccount(entry.getID())) {
					Notifications notifications = Notifications.create();
					notifications.title("Succesfull");
					notifications.text("Payment paid succesfully");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showConfirm();
				}
			}
		}else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Boat adding unsuccesfull");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}
		
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

	// @FXML
	// void done(ActionEvent event) throws IOException {
	//
	// int id = accountServices.getBuyerIDByNameForeign(buyerName);
	//
	// for (F_Fish_Buyers_Account_Uncleard item : tblvBuyersDetails.getItems()) {
	//
	// F_Fish_Buyers_Account_Uncleard boat= new F_Fish_Buyers_Account_Uncleard();
	//
	// boat.setID(item.getID());
	// boat.setReason(item.getReason());
	// boat.setDate(item.getDate());
	// boat.setTo_Pay(0);
	// boat.setPaid(item.getTo_Pay());
	// boat.setBuyer_ID(id);
	//
	//
	// accountServices.AddNewRecievedForeign(boat);
	// System.out.println(boat.getBuyer_ID());
	// accountServices.setUnclearedBuyerRecievedForeign(boat.getBuyer_ID());
	//
	//
	// }
	//
	//
	// }

	@FXML
	void back(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/FBuyerAccount.fxml"));
		setNode(add);
	}

}
