package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Models.Buyers;
import application.Models.LocalBoat;
import application.Models.LocalBuyers;
import application.Services.LocalBuyerService;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LocalBuyerController implements Initializable {

    @FXML
    private AnchorPane Buyers;
    
    @FXML
    private TableView<LocalBuyers> tblBuyers;

    @FXML
    private TableColumn<?, ?> clmName;

    @FXML
    private TableColumn<?, ?> clmMobile;


    ObservableList<LocalBuyers> list = FXCollections.observableArrayList();
    AnchorPane add,lots,stoks,boats,buyers,fishtypes,newLots;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		
    	list.clear();
    	LocalBuyerService service= new LocalBuyerService();
    	ArrayList<LocalBuyers> LByrList=null;
    	
    	try {
    		LByrList=service.getLocalBuyer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	for (LocalBuyers  Lbuyer : LByrList) {
    		list.add(Lbuyer);
    	}
    	
    	clmName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    	clmMobile.setCellValueFactory(new PropertyValueFactory<>("Mobile_No"));
    	
    	tblBuyers.setItems(list);
		
	}
    
    void setNode(Node node) {
    	Buyers.getChildren().clear();
    	Buyers.setTopAnchor(node,0.0);
    	Buyers.setRightAnchor(node, 0.0);
    	Buyers.setLeftAnchor(node, 0.0);
    	Buyers.setBottomAnchor(node, 0.0);
    	Buyers.getChildren().addAll((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    
    
    @FXML
    void switchAddLbuyer(ActionEvent event)throws IOException {
    	add=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LNewBuyer.fxml"));
        setNode(add);
    	
    }
    
  

    @FXML
    void switchBoats(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LBoats.fxml"));
        setNode(add);
    }

    @FXML
    void switchFishTypes(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LFishTypes.fxml"));
        setNode(add);
    }

    @FXML
    void switchStocks(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LStocks.fxml"));
        setNode(add);
    }
    
	@FXML
	void switchSell(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LocalSell.fxml"));
		setNode(add);
	}
    @FXML
    void switchEditLocalBoats(ActionEvent event)throws IOException {
    	LocalBuyers LfBuyer = tblBuyers.getSelectionModel().getSelectedItem();

    	if (LfBuyer != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Views/Ltrade/LEditBuyer.fxml"));
			Parent root = loader.load();
			EditLocalBuyerController LByercontroller = loader.<EditLocalBuyerController>getController();
			String id = Integer.toString(LfBuyer.getID());
			LByercontroller.setID(id);

			setNode(root);
		}

    }
    


	

    
    
}