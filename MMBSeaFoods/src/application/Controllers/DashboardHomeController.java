package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import application.Models.Foreign_Fish_types;
import application.Services.DashboardHomeService;
import application.Services.Foreign_Fish_typesServices;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DashboardHomeController implements Initializable {

    @FXML
    private Label lblLots;

    @FXML
    private Label lblcommition;

    @FXML
    private Label lblboatpay;

    @FXML
    private Label lblbuyerpay;

    @FXML
    private TableView<Foreign_Fish_types> tblFishType;

    @FXML
    private TableColumn<?, ?> clmName;

    @FXML
    private TableColumn<?, ?> clmB15;

    @FXML
    private TableColumn<?, ?> clm15B20;

    @FXML
    private TableColumn<?, ?> clmA20;
    
    @FXML
    private AnchorPane home;
    
    DashboardHomeService service = new DashboardHomeService();
    ObservableList<Foreign_Fish_types> list = FXCollections.observableArrayList();
	NumberFormat formatter = new DecimalFormat("#0.00"); 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		try {
			lblLots.setText(service.TotalLots());
			lblbuyerpay.setText(service.TotalBuyer());
			lblcommition.setText(service.TotalCommition());
			lblboatpay.setText(service.TotalBoat());
			
			list.clear();
	    	Foreign_Fish_typesServices service =new Foreign_Fish_typesServices();
	    	
	    	ArrayList<Foreign_Fish_types> ftypes =null;
	    	
	    	try {
				ftypes=service.getfishTypes();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    	for(Foreign_Fish_types sup : ftypes) {
	    		sup.setSprice_15B("Rs."+String.format ("%2.0f",sup.getPrice_15B())+".00");
	    		sup.setSprice20_15("Rs."+String.format ("%2.0f",sup.getPrice20_15())+".00");
	    		sup.setSprice_20P("Rs."+String.format ("%2.0f",sup.getPrice_20P())+".00");
				list.add(sup);
			}	
	    	
	    	clmName.setCellValueFactory(new PropertyValueFactory<>("Name"));
	    	clmB15.setCellValueFactory(new PropertyValueFactory<>("Sprice_15B"));
	    	clm15B20.setCellValueFactory(new PropertyValueFactory<>("Sprice20_15"));
	    	clmA20.setCellValueFactory(new PropertyValueFactory<>("Sprice_20P"));
	    	
	    	tblFishType.setItems(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @FXML
    void editType(ActionEvent event) throws IOException {
    	
    	Foreign_Fish_types type = tblFishType.getSelectionModel().getSelectedItem();
    	
    	if(type != null) {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Home/EditFishType.fxml"));
			Parent root = loader.load();
    		EditFishTypeController2 controller = loader.<EditFishTypeController2>getController();
    		String id=Integer.toString(type.getID());
			controller.setID(id); 
			
			setNode(root);
    	}else {
    		
    		Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Select a Fish type to edit");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
    		
    	}

    }

    void setNode(Node node) {
    	home.getChildren().clear();
    	home.setTopAnchor(node,0.0);
    	home.setRightAnchor(node, 0.0);
    	home.setLeftAnchor(node, 0.0);
        home.setBottomAnchor(node, 0.0);
        home.getChildren().addAll((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

}
