package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Models.LocalBoat;
import application.Models.Local_Fish_types;
import application.Services.Local_Fish_typesServices;
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

public class LocalFishTypeController implements Initializable {

    @FXML
    private AnchorPane FishType;
    
    
    @FXML
    private TableView<Local_Fish_types> tblFishType;

    @FXML
    private TableColumn<?, ?> clmName;

    @FXML
    private TableColumn<?, ?> clmUprice;


 AnchorPane add;
 
 	ObservableList<Local_Fish_types> list = FXCollections.observableArrayList();
    
    Local_Fish_typesServices service= new Local_Fish_typesServices();
 
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		list.clear();
		ArrayList<Local_Fish_types> LFtypes= new ArrayList<>();
		
		
		try {
			LFtypes  = service.getLocalfishTypes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Local_Fish_types sup : LFtypes) {
			list.add(sup);
		}	
		
		clmName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		clmUprice.setCellValueFactory(new PropertyValueFactory<>("Price"));
		tblFishType.setItems(list);
    	
		
	}

 
 
 
 
    
    void setNode(Node node) {
    	FishType.getChildren().clear();
    	FishType.setTopAnchor(node,0.0);
    	FishType.setRightAnchor(node, 0.0);
    	FishType.setLeftAnchor(node, 0.0);
    	FishType.setBottomAnchor(node, 0.0);
    	FishType.getChildren().addAll((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }


    @FXML
    void switchAddLFishType(ActionEvent event) throws IOException {
    	
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LNewFishType.fxml"));
        setNode(add);


    }
    
    @FXML
    void switchBoats(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LBoats.fxml"));
        setNode(add);
    }

    @FXML
    void switchBuyers(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LBuyers.fxml"));
        setNode(add);
    }

    @FXML
    void switchStocks(ActionEvent event) throws IOException {
    	add=FXMLLoader.load(getClass().getResource("../Views/Ltrade/LStocks.fxml"));
        setNode(add);
    }

    @FXML
    void switchEditLFishType(ActionEvent event) throws IOException {
    	
    	Local_Fish_types lFtype = tblFishType.getSelectionModel().getSelectedItem();
    	
    	System.out.println(lFtype.getName());
    	System.out.println(lFtype.getPrice());

    	if (lFtype != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Ltrade/LEditFishType.fxml"));
			Parent root = loader.load();
			EditLocalFishTypeController LFishcontroller = loader.<EditLocalFishTypeController>getController();
			String id = Integer.toString(lFtype.getID());
			LFishcontroller.setID(id);

			setNode(root);
		}

    }

	

}