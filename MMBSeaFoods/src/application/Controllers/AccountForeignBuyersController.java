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

import application.Models.Buyers;
import application.Models.F_Fish_Buyers_Account;
import application.Models.F_Fish_Buyers_Account_Uncleard;
import application.Models.Fish_Lot;
import application.Models.Foreign_Fish_Buyers;
import application.Models.Local_Buyers_Account;
import application.Services.AccountServices;
import application.Services.BuyerService;
import application.Services.Fish_LotServices;
import application.Services.Foreign_Fish_Buyers_AccountServices;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class AccountForeignBuyersController implements Initializable {

	@FXML
	private AnchorPane Accounts;

	AnchorPane add;

	@FXML
	private JFXComboBox<String> cmbBuyersNames;

	@FXML
	private Label lblBuyer;

	AccountServices accountServices = new AccountServices();
	Foreign_Fish_Buyers_AccountServices service = new Foreign_Fish_Buyers_AccountServices();
	BuyerService serviceB = new BuyerService();
	Fish_LotServices lotService = new Fish_LotServices();

	private ObservableList<String> buyersNameList = FXCollections.observableArrayList();
	private ObservableList<F_Fish_Buyers_Account> buyersDetailsList = FXCollections.observableArrayList();

	@FXML
	private TableView<F_Fish_Buyers_Account> tblvBuyersDetails;

	@FXML
	private TableColumn<?, ?> tblcDate;
	@FXML
	private TableColumn<?, ?> tblcReason;
	@FXML
	private TableColumn<?, ?> tblcTopay;
	@FXML
	private TableColumn<?, ?> tblcPaid;

	String bname;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		buyersNameList = accountServices.getAllBuyersNamesForeign();
		cmbBuyersNames.setItems(buyersNameList);

		cmbBuyersNames.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {

				String name = t1;

				bname = name;
				try {

					Buyers id = serviceB.getBuyers(name);

					System.out.println(id.getID());
					buyersDetailsList.clear();

					ArrayList<F_Fish_Buyers_Account> buyersAccountDetials = service.getFBuyerAccountRecords(id.getID());

					System.out.println(buyersAccountDetials.isEmpty());

					for (F_Fish_Buyers_Account entry : buyersAccountDetials) {

						System.out.println(entry.getDate());
						buyersDetailsList.add(entry);
					}
					tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
					tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
					tblcTopay.setCellValueFactory(new PropertyValueFactory<>("To_Pay"));
					tblcPaid.setCellValueFactory(new PropertyValueFactory<>("Paid"));

					tblvBuyersDetails.setItems(buyersDetailsList);
					
					
					tblvBuyersDetails.setRowFactory(tv -> {
						TableRow<F_Fish_Buyers_Account> row = new TableRow<>();
						row.setOnMouseClicked(event -> {
							if (event.getClickCount() == 2 && (!row.isEmpty())) {
								try {
									F_Fish_Buyers_Account rowData = row.getItem();
									FXMLLoader loader = new FXMLLoader(
											getClass().getResource("/application/Views/Ftrade/ViewLot.fxml"));
									Parent root;

									root = loader.load();

									ViewLotController controller = loader.<ViewLotController>getController();
									String Lid = Integer.toString(rowData.getLot_ID());
									System.out.println("id of the lot is "+ Lid);
									controller.setBackCommond(1);
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

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	public void addRecieved(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/FAddBuyerReceived.fxml"));
		setNode(add);

	}

	@FXML
	void back(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/Accounts.fxml"));
		setNode(add);
	}
	
	public void notRecived(ActionEvent event) throws SQLException {
		
		F_Fish_Buyers_Account entry = tblvBuyersDetails.getSelectionModel().getSelectedItem();
		
		if (entry != null && entry.getPaid() !=0) {
			F_Fish_Buyers_Account_Uncleard Newentry = new F_Fish_Buyers_Account_Uncleard();
			
			Fish_Lot lot =lotService.getTheLot(entry.getLot_ID()); 
			
			Newentry.setDate(lot.getSold_Date());
			Newentry.setBuyer_ID(entry.getBuyer_ID());
			Newentry.setPaid(0);
			Newentry.setLot_ID(entry.getLot_ID());
			Newentry.setTo_Pay(entry.getPaid());
			Newentry.setReason("Selling Lot purchased on" + lot.getDisplay_Name());
			tblvBuyersDetails.getItems().remove(entry);
			tblvBuyersDetails.refresh();
			System.out.println("function working");
			if (accountServices.AddRecievedForeignBuyerPayment_Uncleard(Newentry)){
				System.out.println("first working");
				if (accountServices.removeForiegnFishBuyer(entry.getID())){
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

}
