package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import application.Models.Boat_Account;
import application.Models.Boat_Account_UnCleared;
import application.Models.F_Fish_Buyers_Account_Uncleard;
import application.Models.Fish_stock;
import application.Models.LocalSales;
import application.Models.Local_Buyers_Account;
import application.Models.Locl_Buyers_Account_Uncleared;
import application.Services.AccountServices;
import application.Services.LocalBuyerAccountService;
import application.Services.LocalBuyerService;
import application.Services.Local_PurchasesService;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LocalAddBuyerRecievedController implements Initializable {
	
	@FXML
	private AnchorPane Accounts;
	
	AnchorPane add;

	private StringProperty id;
	
	@FXML private TableView<Locl_Buyers_Account_Uncleared> tblvBuyersDetails;
	
	@FXML private TableColumn<?, ?> tblcDate;
	@FXML private TableColumn<?, ?> tblcReason;
	@FXML private TableColumn<?, ?> tblcTopay;

	
	AccountServices accountServices=new AccountServices();
	Local_PurchasesService purchaseService = new Local_PurchasesService();
	LocalBuyerAccountService buyerService =new LocalBuyerAccountService();
	
	private ObservableList<Locl_Buyers_Account_Uncleared> buyerDetailsList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		Platform.runLater(() -> {			
			buyerDetailsList.clear();

			ArrayList<Locl_Buyers_Account_Uncleared> boat_list = accountServices.getAllBuyerListUncleared();
			
			for( Locl_Buyers_Account_Uncleared boat : boat_list ) {
				buyerDetailsList.add(boat);
			} 
			tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
			tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
			tblcTopay.setCellValueFactory(new PropertyValueFactory<>("To_Pay"));

			tblvBuyersDetails.setItems(buyerDetailsList);
		
		 });
		
		
	}
	
	
	void setNode(Node node) {
		
		Accounts.getChildren().clear();
		Accounts.setTopAnchor(node,0.0);
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
    void done(ActionEvent event) throws IOException, SQLException {
		
		Locl_Buyers_Account_Uncleared entry = tblvBuyersDetails.getSelectionModel().getSelectedItem();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		if (entry != null) {
			Local_Buyers_Account Newentry = new Local_Buyers_Account();
			Newentry.setDate(format1.format(new Date()));
			Newentry.setPaid(entry.getTo_Pay());
			Newentry.setPurchase_ID(entry.getPurchase_ID());
			Newentry.setTo_Pay(0);
			Newentry.setBuyer_ID(entry.getBuyer_ID());
			LocalSales stock = purchaseService.getLocalSales(entry.getPurchase_ID());
			Newentry.setReason("Payment for stock Sale  of " + stock.getTotalWeight()+ "Kg");
			System.out.println("Done");

			tblvBuyersDetails.getItems().remove(entry);
			tblvBuyersDetails.refresh();

			if (buyerService.addEntries(Newentry)) {
				if (buyerService.RemoveFromLocalBuyersAccount_Uncleared(entry.getID())) {
					Notifications notifications = Notifications.create();
					notifications.title("Succesfull");
					notifications.text("Payment paid succesfully");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showConfirm();
				}
			}
		}
		
		
    }

	
	@FXML
    void back(ActionEvent event)throws IOException {
    	add=FXMLLoader.load(getClass().getResource("/application/Views/Accounts/Accounts.fxml"));
		setNode(add);
    	
    }
}
