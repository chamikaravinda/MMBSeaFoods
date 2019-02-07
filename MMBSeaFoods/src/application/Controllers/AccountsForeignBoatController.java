package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import application.Models.Boat_Account;
import application.Services.AccountServices;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccountsForeignBoatController implements Initializable{
	
	@FXML
	private AnchorPane Accounts;
	
	AnchorPane add;
	
	@FXML private JFXComboBox<String>  cmbBoatNames;

	@FXML private Label  lblBoat;
	
	AccountServices accountServices=new AccountServices();
	
	private ObservableList<String> boatNameList = FXCollections.observableArrayList();
	private ObservableList<Boat_Account> boatDetailsList = FXCollections.observableArrayList();

	
	@FXML private TableView<Boat_Account> tblvBoatDetails;
	
	@FXML private TableColumn<?, ?> tblcDate;
	@FXML private TableColumn<?, ?> tblcReason;
	@FXML private TableColumn<?, ?> tblcTopay;
	@FXML private TableColumn<?, ?> tblcPaid;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		boatNameList=accountServices.getAllBoatNamesForeign();
		cmbBoatNames.setItems(boatNameList);
		
		
		cmbBoatNames.valueProperty().addListener(new ChangeListener<String>() {
		        @Override public void changed(ObservableValue<? extends String> ov, String t, String t1) {
		          
		            System.out.println(t1);
		            
		            String name= t1;
					
					lblBoat.setText(name);
					
					int id=accountServices.getBoatIDByNameForeign(name);
					
					System.out.println(id);
					
					boatDetailsList.clear();
					
					
					ArrayList<Boat_Account> boat_list = accountServices.getAllBOATListForeign(id);
					
					for( Boat_Account boat : boat_list ) {
						boatDetailsList.add(boat);
					} 
					tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
					tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
					tblcTopay.setCellValueFactory(new PropertyValueFactory<>("To_Pay"));
					tblcPaid.setCellValueFactory(new PropertyValueFactory<>("Paid"));

					tblvBoatDetails.setItems(boatDetailsList);
					
					
					
		        }    
		    });
		
		
		
		
		
	}
	
	public void getBoatDetails() {
		try {
			
			
			
			String name= cmbBoatNames.getSelectionModel().getSelectedItem().toString();
			
			lblBoat.setText(name);
			
			int id=accountServices.getBoatIDByNameForeign(name);
			
			showBoatDetailsTableList(id);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void showBoatDetailsTableList(int id) {
	
		
		boatDetailsList.clear();
		
		
		ArrayList<Boat_Account> boat_list = accountServices.getAllBOATList(id);
		
		for( Boat_Account boat : boat_list ) {
			boatDetailsList.add(boat);
		} 
		tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
		tblcTopay.setCellValueFactory(new PropertyValueFactory<>("To_Pay"));
		tblcPaid.setCellValueFactory(new PropertyValueFactory<>("Paid"));

		tblvBoatDetails.setItems(boatDetailsList);
		
		//tblvBoatDetails.setItems(boatDetailsList);
		
		
	}
	
	
	public void makePayment(ActionEvent event) throws IOException {
		
		String name=lblBoat.getText();
		System.out.println(name);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Accounts/MakePayment.fxml"));
		Parent root = loader.load();
		AccountsForeignMakePaymentController controller = loader.<AccountsForeignMakePaymentController>getController();
		controller.getBoatName(name);
		System.out.println("makd"+name);
		//controller.getBoatDetails(name);
		setNode(root);
		
		/*add=FXMLLoader.load(getClass().getResource("../Views/Accounts/LMakePayment.fxml"));
		setNode(add);*/
		
		
		
	}
	

    @FXML
    void back(ActionEvent event) throws IOException {
        add=FXMLLoader.load(getClass().getResource("../Views/Accounts/Accounts.fxml"));
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

}
