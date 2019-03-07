package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.Models.Boat;
import application.Models.Fish_Lot;
import application.Models.Fish_stock;
import application.Models.Foreign_Fish_types;
import application.Models.Stock_Fish;
import application.Services.BoatService;
import application.Services.Fish_LotServices;
import application.Services.Fish_stockService;
import application.Services.Foreign_Fish_typesServices;
import application.Services.stock_FishService;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
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
import net.sf.jasperreports.engine.export.oasis.InternalImageProcessorResult;

public class ViewStockController implements Initializable {

	@FXML
	private AnchorPane NewStocks;

	@FXML
	private JFXButton btnAdd;

	@FXML
	private Label lblNoOfFish;

	@FXML
	private Label lbltotalPrice;

	@FXML
	private Label lbltotalWeight;

	@FXML
	private Label lbllot;

	@FXML
	private Label lblboat;

	@FXML
	private Label lblhabour;

	@FXML
	private Label lblStkID;

    @FXML
    private Label lblcommission;

    @FXML
    private Label lblfishprice;
    
	@FXML
	private TableView<Stock_Fish> tblFish;

	@FXML
	private TableColumn<?, ?> clmtype;

	@FXML
	private TableColumn<?, ?> clmWeight;

	@FXML
	private TableColumn<?, ?> clmPrice;
	
	public ObservableList<Stock_Fish> tbllist =FXCollections.observableArrayList();

	private AnchorPane types;

	private int backCommond=0;
	
	private int backCommondLot=0;
	
	Fish_stockService service = new Fish_stockService();
	Fish_LotServices serviceB = new Fish_LotServices();
	BoatService serviceC = new BoatService();
	stock_FishService serviceD = new stock_FishService();
	Foreign_Fish_typesServices serviceE =new Foreign_Fish_typesServices();
	
	Fish_stock stock;

	public void setID(String ID) {
		lblStkID.setText(ID);
	}
	
	public void setbackCommond(int cmd) {
		backCommond=cmd;
	}
	
	public void setbackCommondForLot(int cmd) {
		backCommondLot=cmd;
	}

	@FXML
	void back(ActionEvent event) throws IOException {
		if(backCommond ==0) {
		types = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/Stocks.fxml"));
		setNode(types);
		}else if(backCommond ==2) {
			types = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/FBoatAccount.fxml"));
			setNode(types);
		}else if(backCommond ==3) {
			types = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/MakePayment.fxml"));
			setNode(types);
		}else {
			
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/application/Views/Ftrade/ViewLot.fxml"));
			Parent root;

			root = loader.load();

			ViewLotController controller = loader.<ViewLotController>getController();
			String id = Integer.toString(stock.getID());
			controller.setID(id);
			controller.setBackCommond(backCommondLot);

			setNode(root);
			
		}
	}

	@FXML
	void printInvoice(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			try {
				
				
				stock = service.getStocks(Integer.parseInt(lblStkID.getText()));
				Fish_Lot stockLot = serviceB.getTheLot(stock.getLot_ID());
				lbllot.setText(stockLot.getDisplay_Name());

				Boat boat = serviceC.getBoat(stock.getBoat_ID());
				lblboat.setText(boat.getBoatNameorNumber());

				lblhabour.setText(stock.getHarbour());
				lblNoOfFish.setText(Integer.toString(stock.getNoofFishes()));
				System.out.println();
				lbltotalPrice.setText("Rs. "+String.format ("%2.0f", stock.getTotalBuying_price())+".00");
				lbltotalWeight.setText(Double.toString(stock.getTotal_Weight())+" Kg ");
				lblcommission.setText("Rs. "+String.format ("%2.0f", stock.getCommition_price())+".00");
				lblfishprice.setText("Rs. "+String.format ("%2.0f", stock.getFishprice())+".00");
				
				
				//load the data to the fish table 
				ArrayList<Stock_Fish> fishlist = serviceD.getStocksFish(Integer.parseInt(lblStkID.getText()));
				
				for(Stock_Fish fish: fishlist) {
					//set the fish type name
					Foreign_Fish_types type=serviceE.getfishTypes(fish.getType());
					fish.setFish_type_name(type.getName());
					fish.setSbuying_Price("Rs."+String.format ("%2.0f", fish.getPrice())+".00");
					fish.setSTotal_weight(fish.getWeight()+" Kg");
					tbllist.add(fish);
				}
				
				clmtype.setCellValueFactory(new PropertyValueFactory<>("fish_type_name"));
				clmWeight.setCellValueFactory(new PropertyValueFactory<>("STotal_weight"));
				clmPrice.setCellValueFactory(new PropertyValueFactory<>("Sbuying_Price"));

				tblFish.setItems(tbllist);
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	void setNode(Node node) {

		NewStocks.getChildren().clear();
		NewStocks.setTopAnchor(node, 0.0);
		NewStocks.setRightAnchor(node, 0.0);
		NewStocks.setLeftAnchor(node, 0.0);
		NewStocks.setBottomAnchor(node, 0.0);
		NewStocks.getChildren().addAll((Node) node);

		FadeTransition ft = new FadeTransition(Duration.millis(1500));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();

	}

}
