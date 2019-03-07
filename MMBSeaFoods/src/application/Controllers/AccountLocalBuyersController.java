package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXComboBox;

import application.Models.Boat_Account;
import application.Models.F_Fish_Buyers_Account;
import application.Models.F_Fish_Buyers_Account_Uncleard;
import application.Models.Fish_Lot;
import application.Models.LocalPurchase;
import application.Models.LocalSales;
import application.Models.Local_Buyers_Account;
import application.Models.Locl_Buyers_Account_Uncleared;
import application.Services.AccountServices;
import application.Services.LocalBuyerAccountService;
import application.Services.Local_PurchasesService;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccountLocalBuyersController implements Initializable {
	
	@FXML
	private AnchorPane Accounts;
	
	AnchorPane add;
	
	@FXML private JFXComboBox<String>  cmbBuyersNames;
	
	AccountServices accountServices=new AccountServices();
	Local_PurchasesService saleService =new Local_PurchasesService();
	LocalBuyerAccountService buyerService =new LocalBuyerAccountService();
	
	private ObservableList<String> buyersNameList = FXCollections.observableArrayList();
	private ObservableList<Local_Buyers_Account> buyersDetailsList = FXCollections.observableArrayList();

	
	@FXML private TableView<Local_Buyers_Account> tblvBuyersDetails;
	
	@FXML private TableColumn<?, ?> tblcDate;
	@FXML private TableColumn<?, ?> tblcReason;
	@FXML private TableColumn<?, ?> tblcTopay;
	@FXML private TableColumn<?, ?> tblcPaid;
	@Override
	public void initialize(URL location, ResourceBundle resources) {


		buyersNameList=accountServices.getAllBuyersNames();
		cmbBuyersNames.setItems(buyersNameList);
		
		
		cmbBuyersNames.valueProperty().addListener(new ChangeListener<String>() {
		        @Override public void changed(ObservableValue<? extends String> ov, String t, String t1) {
		          
		            System.out.println(t1);
		            
		            String name= t1;
					
					
					
					int id=accountServices.getBuyerIDByName(name);
					
					System.out.println(id);
					
					buyersDetailsList.clear();
					
					
					ArrayList<Local_Buyers_Account> boat_list = accountServices.getAllBuyersList(id);
					
					for( Local_Buyers_Account boat : boat_list ) {
						buyersDetailsList.add(boat);
					} 
					tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
					tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
					tblcTopay.setCellValueFactory(new PropertyValueFactory<>("To_Pay"));
					tblcPaid.setCellValueFactory(new PropertyValueFactory<>("Paid"));

					tblvBuyersDetails.setItems(buyersDetailsList);
					
					
					
		        }    
		    });
		
		
		
	}
	
public void notRecived(ActionEvent event) throws SQLException {
		
	Local_Buyers_Account entry = tblvBuyersDetails.getSelectionModel().getSelectedItem();
		
		if (entry != null && entry.getPaid() !=0) {
			Local_Buyers_Account Newentry = new Local_Buyers_Account();
			
			LocalSales lot =saleService.getLocalSales(entry.getPurchase_ID());
			
			Newentry.setDate(lot.getDate());
			Newentry.setBuyer_ID(entry.getBuyer_ID());
			Newentry.setPaid(0);
			Newentry.setPurchase_ID(entry.getPurchase_ID());
			Newentry.setTo_Pay(entry.getPaid());
			Newentry.setReason("Fish Purchase of " + lot.getTotalWeight()+"Kg");
			tblvBuyersDetails.getItems().remove(entry);
			tblvBuyersDetails.refresh();
			System.out.println("function working");
			if (buyerService.addEntriesUncleared(Newentry)){
				System.out.println("first working");
				if (buyerService.RemoveFromLocalBuyersAccount(entry.getID())){
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
	
	
    @FXML
    void back(ActionEvent event)throws IOException {
    	add=FXMLLoader.load(getClass().getResource("/application/Views/Accounts/Accounts.fxml"));
		setNode(add);
    	
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
	
	
	public void addRecieved(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/LAddBuyerReceived.fxml"));
		setNode(add);

	}
	
	

}
