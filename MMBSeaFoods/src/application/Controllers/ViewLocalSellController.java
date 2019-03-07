package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.imageio.spi.InputStreamImageInputStreamSpi;

import application.Models.LocalBuyers;
import application.Models.LocalPurchase;
import application.Models.LocalSales;
import application.Models.Local_Fish_types;
import application.Models.Local_sale_item;
import application.Models.Stock_Fish;
import application.Services.LocalBuyerService;
import application.Services.Local_Fish_typesServices;
import application.Services.Local_PurchasesService;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ViewLocalSellController implements Initializable {

	@FXML
	private AnchorPane Stocks;

	@FXML
	private TableView<Local_sale_item> clmFishTable;

	@FXML
	private TableColumn<?, ?> clmfishtype;

	@FXML
	private TableColumn<?, ?> clmTotalWeight;
	
	@FXML
	private TableColumn<?, ?> clmPrice;

	@FXML
	private Label lbltotalweight;

	@FXML
	private Label lbltotalprice;

	@FXML
	private Label lblbuyer;
	
	@FXML
	private Label lblID;

	@FXML
	private Label lblDate;

	public ObservableList<Local_sale_item> tbllist = FXCollections.observableArrayList();

	private AnchorPane types;

	private int id;
	
	private int backCommond=0;

	public void setID(int ID) {
		this.id=ID;
	}


	public void setbackCommond(int cmd) {
		backCommond=cmd;
	}

	public ObservableList<Local_sale_item> itemList=FXCollections.observableArrayList();
	
	AnchorPane add;
	
	Local_PurchasesService purchaseService =new Local_PurchasesService();
	LocalBuyerService buyerService = new LocalBuyerService();
	Local_Fish_typesServices fishService =new Local_Fish_typesServices();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Platform.runLater(() -> {
			
			try {
				LocalSales sale=purchaseService.getLocalSales(id);
				LocalBuyers buyer =  buyerService.getLocalBuyer(sale.getBuyerID());
				lblDate.setText(sale.getDate());
				lbltotalprice.setText("Rs."+String.format("%2.2f", sale.getPrice()));
				lbltotalweight.setText(String.format("%2.1f",sale.getTotalWeight())+"Kg");
				lblbuyer.setText(buyer.getName());
				
				
				ArrayList<Local_sale_item> items =purchaseService.getLocalSaleItems(id);
				Local_Fish_types type =new Local_Fish_types();
				
				for(Local_sale_item item :items) {
					type=fishService.getLocalfishTypes(item.getFish_Type());
					item.setSbuying_Price("Rs."+String.format("%2.2f", item.getBuying_Price()));
					item.setSTotal_Weight(String.format("%2.2f", item.getTotal_Weight()));
					item.setFish_name(type.getName());
					itemList.add(item);
				}
				
				clmfishtype.setCellValueFactory(new PropertyValueFactory<>("fish_name"));
				clmTotalWeight.setCellValueFactory(new PropertyValueFactory<>("Total_Weight"));
				clmPrice.setCellValueFactory(new PropertyValueFactory<>("buying_Price"));

				clmFishTable.setItems(itemList);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
	}

	void setNode(Node node) {

		Stocks.getChildren().clear();
		Stocks.setTopAnchor(node, 0.0);
		Stocks.setRightAnchor(node, 0.0);
		Stocks.setLeftAnchor(node, 0.0);
		Stocks.setBottomAnchor(node, 0.0);
		Stocks.getChildren().addAll((Node) node);

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

		types = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/LocalSales.fxml"));
		setNode(types);
		
	}

}
