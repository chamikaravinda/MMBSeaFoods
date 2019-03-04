package application.Controllers;

import java.awt.Dimension;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import org.controlsfx.control.Notifications;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import application.Models.Boat_Account;
import application.Models.Boat_Account_UnCleared;
import application.Models.Fish_stock;
import application.Models.LocalBoat;
import application.Models.LocalBoatAccount;
import application.Models.LocalBoatAccountUnCleared;
import application.Models.LocalPurchase;
import application.Services.AccountServices;
import application.Services.LocalBoatAccountService;
import application.Services.LocalBoatService;
import application.Services.Local_PurchasesService;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.TableRow;
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

public class AccountsLocalMakePaymentController implements Initializable {

	@FXML
	private AnchorPane Accounts;

	AnchorPane add;

	private StringProperty id;

	@FXML
	private TableView<LocalBoatAccountUnCleared> tblvBoatDetails;

	@FXML
	private TableColumn<?, ?> tblcDate;
	@FXML
	private TableColumn<?, ?> tblcReason;
	@FXML
	private TableColumn<?, ?> tblcTopay;
	@FXML
	private TableColumn<?, ?> tblcPaid;
	@FXML
	private TableColumn<?, ?> tblcBuyer;

	AccountServices accountServices = new AccountServices();
	LocalBoatService boatService = new LocalBoatService();
	LocalBoatAccountService boatAccountService =new LocalBoatAccountService();
	Local_PurchasesService purchaseService = new Local_PurchasesService();

	private ObservableList<LocalBoatAccountUnCleared> boatDetailsList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Platform.runLater(() -> {

			boatDetailsList.clear();

			ArrayList<LocalBoatAccountUnCleared> boat_list = accountServices.getAllBoatAccountUncleared();

			for (LocalBoatAccountUnCleared boat : boat_list) {
				try {
					LocalBoat entryboat = boatService.getLocalBoat(boat.getBoat_ID());

					boat.setSPaid("Rs ." + String.format("%2.2f", boat.getPaid()));
					boat.setSTo_Pay("Rs ." + String.format("%2.2f", boat.getTo_Pay()));
					boat.setBoatName(entryboat.getBoatNameorNumber());
					boatDetailsList.add(boat);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
			tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
			tblcTopay.setCellValueFactory(new PropertyValueFactory<>("To_Pay"));
			tblcPaid.setCellValueFactory(new PropertyValueFactory<>("Paid"));
			tblcBuyer.setCellValueFactory(new PropertyValueFactory<>("BoatName"));
			tblvBoatDetails.setItems(boatDetailsList);

			tblvBoatDetails.setRowFactory(tv -> {
				TableRow<LocalBoatAccountUnCleared> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						try {
							LocalBoatAccountUnCleared rowData = row.getItem();
							FXMLLoader loader = new FXMLLoader(
									getClass().getResource("/application/Views/Ltrade/ViewPurchase.fxml"));
							Parent root;

							root = loader.load();

							ViewLocalPurchaseController controller = loader.<ViewLocalPurchaseController>getController();
							controller.setID(rowData.getPurchase_ID());
							controller.setBackCommond(2);
							setNode(root);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				return row;
			});
			
		});

	}

	public void pay() throws SQLException {


		LocalBoatAccountUnCleared entry = tblvBoatDetails.getSelectionModel().getSelectedItem();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		if (entry != null) {
			LocalBoatAccount Newentry = new LocalBoatAccount();
			Newentry.setDate(format1.format(new Date()));
			Newentry.setBoat_ID(entry.getBoat_ID());
			Newentry.setPaid(entry.getTo_Pay());
			Newentry.setPurchase_ID(entry.getPurchase_ID());
			Newentry.setTo_Pay(0);
			LocalPurchase stock =purchaseService.getLocalPurchase((int) entry.getPurchase_ID());
			Newentry.setReason("Payment for Fish purchase of " + stock.getTotal_Weight() + "Kg");

			tblvBoatDetails.getItems().remove(entry);
			tblvBoatDetails.refresh();

			if (boatAccountService.addEntries(Newentry)) {
				if (boatAccountService.RemoveFromBoatAccount_Unclear(entry.getID())) {
					Notifications notifications = Notifications.create();
					notifications.title("Succesfull");
					notifications.text("Payment paid succesfully");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showConfirm();
				}
			}
		}

	}

	// public void removeLocalBoatAccountUnclearedData(){
	//
	// }
	//
	// /*-------------------Generate Current Date -----------------*/
	// public static String getCurrentDate() {
	// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// Date date = new Date();
	// String newDate = dateFormat.format(date);
	//
	// return newDate;
	// }
	//
	// /*-------------------Generate Current Time -----------------*/
	// public static String getCurrentTime() {
	//
	// Calendar cal = Calendar.getInstance();
	// SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
	//
	// return (sdf.format(cal.getTime()));
	// }
	//

	//
	// /*---------------generate the jasper report--------------------*/
	// public void generateAccountsLocalInvoice() {
	// /*int id = accountServices.getBoatIDByName(lblBoatName.getText());
	//
	//
	// String invoiceName = "LAI_"+getCurrentDate()+"_"+getCurrentTime()+".pdf";
	//
	// try {
	//
	// Connection con = application.Services.DBConnection.LoginConnector();
	//
	// JasperDesign jasperDesign = JRXmlLoader.load("D:\\\\SLIIT
	// STUDIES\\\\extra\\\\JavaFX\\\\MMBSeaFoods\\\\MMBSeaFoods\\\\MMBSeaFoods\\\\src\\\\application\\\\Reports\\\\LocalAccountInvoice.jrxml");
	//
	// // get the query
	// String query = "SELECT * FROM Local_Boat_Account_UnCleared WHERE Boat_ID = "
	// + id;
	// JRDesignQuery jrQuery = new JRDesignQuery();
	// jrQuery.setText(query);
	// jasperDesign.setQuery(jrQuery);
	//
	// JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	// JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
	// con);
	// JRViewer viewer = new JRViewer(jasperPrint);
	//
	// viewer.setOpaque(true);
	// viewer.setVisible(true);
	//
	// JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\" +
	// System.getProperty("user.name") + "\\Documents\\"+invoiceName);
	//
	// } catch (Exception e) {
	// System.out.println("Exception " + e);
	//
	//
	// }*/
	//
	//
	// int id = accountServices.getBoatIDByName(lblBoatName.getText());
	//
	// ArrayList<Boat_Account> boat_list =
	// accountServices.getAllBOQListUncleared(id);
	//
	// String invoiceName = "LBAI_"+getCurrentDate()+"_"+getCurrentTime()+".pdf";
	//
	// double totalAmount = 0.0;
	//
	// Document document = new Document();
	// try
	// {
	// @SuppressWarnings("unused")
	// PdfWriter pdfWriter = PdfWriter.getInstance(document, new
	// FileOutputStream("C:\\Users\\" + System.getProperty("user.name") +
	// "\\Documents\\"+invoiceName));
	// document.open();
	//
	// Paragraph para = new Paragraph(
	// "MMB Sea Foods\n\n",new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD));
	// para.setAlignment(para.ALIGN_CENTER);
	//
	// Paragraph paraDesc = new Paragraph(
	// "Local Account Invoice " +"\n",new Font(Font.FontFamily.HELVETICA,
	// 15,Font.BOLD));
	// paraDesc.setAlignment(paraDesc.ALIGN_CENTER);
	//
	// Paragraph para2 = new Paragraph(
	// "Date : " + getCurrentDate(),new Font(Font.FontFamily.HELVETICA,
	// 13,Font.BOLD));
	// para2.setAlignment(para2.ALIGN_RIGHT);
	//
	// Paragraph para3 = new Paragraph(
	// "Time : " + getCurrentTime() +"\n\n",new Font(Font.FontFamily.HELVETICA,
	// 13,Font.BOLD));
	// para2.setAlignment(para3.ALIGN_LEFT);
	//
	//
	// document.add(para);
	// document.add(paraDesc);
	// document.add(para2);
	// document.add(para3);
	//
	//
	// PdfPTable pdfPTable =new PdfPTable(4);
	// PdfPCell pdfCell1 = new PdfPCell(new Phrase("Date"));
	// PdfPCell pdfCell2 = new PdfPCell(new Phrase("Reason"));
	// PdfPCell pdfCell3 = new PdfPCell(new Phrase("To Pay Amount"));
	// PdfPCell pdfCell4 = new PdfPCell(new Phrase("Boat ID"));
	//
	//
	// pdfCell1.setBackgroundColor(BaseColor.BLUE);
	// pdfCell2.setBackgroundColor(BaseColor.BLUE);
	// pdfCell3.setBackgroundColor(BaseColor.BLUE);
	// pdfCell4.setBackgroundColor(BaseColor.BLUE);
	//
	// pdfPTable.addCell(pdfCell1);
	// pdfPTable.addCell(pdfCell2);
	// pdfPTable.addCell(pdfCell3);
	// pdfPTable.addCell(pdfCell4);
	//
	//
	//
	// for( Boat_Account boat : boat_list ) {
	// pdfPTable.addCell(boat.getDate());
	// pdfPTable.addCell(boat.getReason());
	// pdfPTable.addCell(Double.toString(boat.getTo_Pay()));
	// pdfPTable.addCell(Integer.toString(boat.getBoat_ID()));
	//
	// totalAmount += boat.getTo_Pay();
	// }
	//
	// pdfPTable.addCell("Total");
	// pdfPTable.addCell("");
	// pdfPTable.addCell(Double.toString(totalAmount));
	// pdfPTable.addCell("");
	//
	//
	// pdfPTable.setWidthPercentage(90);
	//
	// document.add(pdfPTable);
	// document.close();
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// }

	void setNode(Node node) {

		Accounts.getChildren().clear();
		Accounts.setTopAnchor(node, 0.0);
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
	void back(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/LBoatAccount.fxml"));
		setNode(add);

	}

}
