package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import application.Models.Buyers;
import application.Models.Commition;
import application.Models.Fish_Lot;
import application.Models.LocalSales;
import application.Services.AccountServices;
import application.Services.BuyerService;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccountFSalesController implements Initializable{
	
	
	@FXML
	private AnchorPane Accounts;
	
	AnchorPane add;
	
	@FXML private TableView<Fish_Lot> tblcFSales;
	
	@FXML private TableColumn<?, ?> tblcSoldDate;
	@FXML private TableColumn<?, ?> tblcBuyPrice;
	@FXML private TableColumn<?, ?> tblcSellWeight;
	@FXML private TableColumn<?, ?> tblcSellPrice;
	@FXML private TableColumn<?, ?> tblcBuyer;
	@FXML private TableColumn<?, ?> tblcProfite;
	
	
	private ObservableList<Fish_Lot> boatDetailsList = FXCollections.observableArrayList();
	
	AccountServices accountServices=new AccountServices();
	BuyerService buyerService  = new BuyerService();
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {



		boatDetailsList.clear();
		
		
		ArrayList<Fish_Lot> boat_list = accountServices.getSoldLots();
		
		for( Fish_Lot boat : boat_list ) {
			
			try {
				Buyers lotbuyer = buyerService.getBuyers(boat.getSold_To());
				boat.setBuyer(lotbuyer.getName());
				
				double profite=boat.getSold_price()-boat.getBuying_price();
				boat.setSprofite("Rs. "+String.format ("%2.2f",profite ));
				boat.setSsold_price("Rs. "+String.format ("%2.2f",boat.getSold_price()));
				boatDetailsList.add(boat);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 

		tblcSoldDate.setCellValueFactory(new PropertyValueFactory<>("Sold_Date"));
		tblcBuyPrice.setCellValueFactory(new PropertyValueFactory<>("Buying_Weight"));
		tblcSellWeight.setCellValueFactory(new PropertyValueFactory<>("Sold_Weight"));
		tblcSellPrice.setCellValueFactory(new PropertyValueFactory<>("Ssold_price"));
		tblcBuyer.setCellValueFactory(new PropertyValueFactory<>("Buyer"));
		tblcProfite.setCellValueFactory(new PropertyValueFactory<>("Sprofite"));
		
		tblcFSales.setItems(boatDetailsList);
		
		/*View lot */
		
		tblcFSales.setRowFactory(tv -> {
			TableRow<Fish_Lot> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					try {
						Fish_Lot rowData = row.getItem();
						if (rowData.getBuying_Weight() != 0) {
							FXMLLoader loader = new FXMLLoader(
									getClass().getResource("/application/Views/Ftrade/ViewLot.fxml"));
							Parent root;

							root = loader.load();

							ViewLotController controller = loader.<ViewLotController>getController();
							String id = Integer.toString(rowData.getID());
							controller.setID(id);
							controller.setBackCommond(3);

							setNode(root);
						}else {
							Notifications notifications = Notifications.create();
							notifications.title("Error");
							notifications.text("No stocks in Lot to see");
							notifications.graphic(null);
							notifications.hideAfter(Duration.seconds(2));
							notifications.position(Pos.CENTER);
							notifications.showError();
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			return row;
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
	    void back(ActionEvent event)throws IOException {
	    	
	    	add= FXMLLoader.load(getClass().getResource("/application/Views/Accounts/Accounts.fxml"));
	    	setNode(add);

	    }

}
