package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;

import application.Models.Commition;
import application.Services.AccountServices;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccountCommitionUnclearedController implements Initializable{
	
	
	@FXML
	private AnchorPane Accounts;
	
	AnchorPane add;
	
	@FXML private TableView<Commition> tblvSales;
	
	@FXML private TableColumn<?, ?> tblcDate;
	@FXML private TableColumn<?, ?> tblcReason;
	@FXML private TableColumn<?, ?> tblcToPay;
	
	
	private ObservableList<Commition> commitionAccountEntries = FXCollections.observableArrayList();
	
	AccountServices accountServices=new AccountServices();
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {


		commitionAccountEntries.clear();
		
		
		ArrayList<Commition> entryList = accountServices.getAllCommitionListUncleared();
		
		for( Commition entry : entryList ) {
			if(entry.getTo_Pay()!=0) {
				entry.setSTo_Pay("Rs."+String.format ("%2.0f", entry.getTo_Pay())+".00");
			}else {
				entry.setSTo_Pay("Rs 0.00");
			}
	
			commitionAccountEntries.add(entry);
		} 
		
		
		tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
		tblcToPay.setCellValueFactory(new PropertyValueFactory<>("STo_Pay"));

		tblvSales.setItems(commitionAccountEntries);
		
		TableViewSelectionModel<Commition> tsm = tblvSales.getSelectionModel();
		tsm.setSelectionMode(SelectionMode.MULTIPLE);
		
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
	/* Pay method */
	public void pay(ActionEvent event) {
		
		ObservableList<Commition> selectedItems = tblvSales.getSelectionModel().getSelectedItems();
        // TEST
        ArrayList<Commition> selectedIDs = new ArrayList<Commition>();
        
        for (Commition row : selectedItems) {
           selectedIDs.add(row);
           System.out.println(row.getReason());
        }
		
	}
	
	 @FXML
	    void back(ActionEvent event) throws IOException {

	    	add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/Accounts.fxml"));
			setNode(add);
	    }

	
	
	

}

