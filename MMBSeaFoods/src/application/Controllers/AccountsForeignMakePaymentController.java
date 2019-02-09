package application.Controllers;

import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import application.Models.Boat_Account;
import application.Services.AccountServices;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

public class AccountsForeignMakePaymentController implements Initializable{

	@FXML
	private AnchorPane Accounts;
	
	AnchorPane add;
	
	@FXML
	private Label lblBoatName;
	
	private StringProperty id;
	
	@FXML private TableView<Boat_Account> tblvBoatDetails;
	
	@FXML private TableColumn<?, ?> tblcDate;
	@FXML private TableColumn<?, ?> tblcReason;
	@FXML private TableColumn<?, ?> tblcTopay;
	@FXML private TableColumn<?, ?> tblcPaid;

	
	AccountServices accountServices=new AccountServices();
	
	private ObservableList<Boat_Account> boatDetailsList = FXCollections.observableArrayList();
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		

	Platform.runLater(() -> {
		System.out.println("hi"+lblBoatName.getText());
		
		int id=accountServices.getBoatIDByNameForeign(lblBoatName.getText());
		
		System.out.println("id "+id);
		
		boatDetailsList.clear();
		
		
		ArrayList<Boat_Account> boat_list = accountServices.getAllBOQListUnclearedForeign(id);
		
		for( Boat_Account boat : boat_list ) {
			boatDetailsList.add(boat);
		} 
		tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
		tblcTopay.setCellValueFactory(new PropertyValueFactory<>("To_Pay"));
		tblcPaid.setCellValueFactory(new PropertyValueFactory<>("Paid"));

		tblvBoatDetails.setItems(boatDetailsList);
	
	 });


		
		
	}
	
	
	public void pay() {
		
		generateAccountsLocalInvoice();
	}
	
	
	public void removeForeignBoatAccountUnclearedData(){
		
	}

	/*-------------------Generate Current Date -----------------*/
	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String newDate = dateFormat.format(date);
		
		return newDate;
	}
	
	/*-------------------Generate Current Time -----------------*/
	public static String getCurrentTime() {
		
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
	    
	     return (sdf.format(cal.getTime()));
	}


	

	/*---------------generate the jasper report--------------------*/
	public void generateAccountsLocalInvoice() {
		int id=accountServices.getBoatIDByNameForeign(lblBoatName.getText());


		String invoiceName = "FAI_"+getCurrentDate()+"_"+getCurrentTime()+".pdf";
		
		try {

			Connection con = application.Services.DBConnection.LoginConnector();

			JasperDesign jasperDesign = JRXmlLoader.load("D:\\SLIIT STUDIES\\extra\\JavaFX\\MMBSeaFoods\\MMBSeaFoods\\MMBSeaFoods\\src\\application\\Reports\\ForeignAccountInvoice.jrxml");

			// get the query
			String query = "SELECT * FROM Boat_Account_UnCleared WHERE Boat_ID = " + id; 
			JRDesignQuery jrQuery = new JRDesignQuery();
			jrQuery.setText(query);
			jasperDesign.setQuery(jrQuery);

			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
			JRViewer viewer = new JRViewer(jasperPrint);

			viewer.setOpaque(true);
			viewer.setVisible(true);

			JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\"+invoiceName);

		} catch (Exception e) {
			System.out.println("Exception  " + e);
			

		}
	}
	
	public void getBoatDetails(String name) {
		try {
			
			
			
			
			
			
			int id=accountServices.getBoatIDByNameForeign(name);
			
			
			showBoatDetailsTableList(id);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void showBoatDetailsTableList(int id) {
	
		
		boatDetailsList.clear();
		
		
		ArrayList<Boat_Account> boat_list = accountServices.getAllBOQListUnclearedForeign(id);
		
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

	public void getBoatName(String text) {

		
		lblBoatName.setText(text);
		
	}

}
