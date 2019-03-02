package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccountCommissionController implements Initializable {

    @FXML
    private AnchorPane CommissionAccounts;

    @FXML
    private TableView<Commition> tblCommision;

    @FXML
    private TableColumn<?, ?> clmDate;

    @FXML
    private TableColumn<?, ?> clmReason;

    @FXML
    private TableColumn<?, ?> clmTopay;

    @FXML
    private TableColumn<?, ?> clmPaid;

    AnchorPane add;

	private ObservableList<Commition> commitionAccountEntries = FXCollections.observableArrayList();
	  
	AccountServices accountServices=new AccountServices();
	
	    void setNode(Node node) {

	    	CommissionAccounts.getChildren().clear();
	    	CommissionAccounts.setTopAnchor(node, 0.0);
	    	CommissionAccounts.setRightAnchor(node, 0.0);
	    	CommissionAccounts.setLeftAnchor(node, 0.0);
	    	CommissionAccounts.setBottomAnchor(node, 0.0);
	    	CommissionAccounts.getChildren().addAll((Node) node);

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

	    	add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/Accounts.fxml"));
			setNode(add);
	    }
	    
	    @FXML
	    void makePayment(ActionEvent event) throws IOException {
	    	add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/FCommitionPayment.fxml"));
			setNode(add);
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {

			commitionAccountEntries.clear();
			
			ArrayList<Commition> entryList = accountServices.getAllCommitionList();
			
			for( Commition entry : entryList ) {
				if(entry.getTo_Pay()!=0) {
					entry.setSTo_Pay("Rs."+String.format ("%2.2f", entry.getTo_Pay()));
				}else {
					entry.setSTo_Pay("Rs 0.00");
				}
		
				commitionAccountEntries.add(entry);
			}
			
			
			clmDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
			clmReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
			clmTopay.setCellValueFactory(new PropertyValueFactory<>("STo_Pay"));
			clmPaid.setCellValueFactory(new PropertyValueFactory<>("STo_Pay"));
			
			tblCommision.setItems(commitionAccountEntries);
				
		}

	}