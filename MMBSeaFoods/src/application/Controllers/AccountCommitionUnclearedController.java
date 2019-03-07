package application.Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ListIterator;
import java.util.ResourceBundle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import application.Models.Boat_Account;
import application.Models.Commition;
import application.Models.Third_Party_Account;
import application.Services.AccountServices;
import application.Services.Third_Party_AccountServices;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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
import application.Services.AccountServices;
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

public class AccountCommitionUnclearedController implements Initializable {

	@FXML
	private AnchorPane Accounts;

	AnchorPane add;

	@FXML
	private TableView<Commition> tblvSales;

	@FXML
	private TableColumn<?, ?> tblcDate;
	@FXML
	private TableColumn<?, ?> tblcReason;
	@FXML
	private TableColumn<?, ?> tblcToPay;

	private ObservableList<Commition> commitionAccountEntries = FXCollections.observableArrayList();

	AccountServices accountServices = new AccountServices();
	Third_Party_AccountServices serviceF = new Third_Party_AccountServices();

	String invoiceName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		commitionAccountEntries.clear();

		ArrayList<Commition> entryList = accountServices.getAllCommitionListUncleared();

		for (Commition entry : entryList) {
			if (entry.getTo_Pay() != 0) {
				entry.setSTo_Pay("Rs." + String.format("%2.0f", entry.getTo_Pay()) + ".00");
			} else {
				entry.setSTo_Pay("Rs 0.00");
			}

			commitionAccountEntries.add(entry);
		}

		tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
		tblcToPay.setCellValueFactory(new PropertyValueFactory<>("STo_Pay"));

		tblvSales.setItems(commitionAccountEntries);

		TableViewSelectionModel<Commition> tsm = tblvSales.getSelectionModel();
		tsm.setSelectionMode(SelectionMode.MULTIPLE);

	}

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

	/* Pay method */
	public void pay(ActionEvent event) throws IOException, SQLException {
		double totalPayment = 0;
		ObservableList<Commition> selectedItems = tblvSales.getSelectionModel().getSelectedItems();
		// TEST
		ArrayList<Commition> selectedIDs = new ArrayList<Commition>();

		for (Commition row : selectedItems) {

			selectedIDs.add(row);
			totalPayment = totalPayment + row.getTo_Pay();
		}

		if (accountServices.removeCommisionAccountSelected(selectedIDs)) {

			Third_Party_Account addEntry = new Third_Party_Account();
			addEntry.setDate(getCurrentDate());
			addEntry.setReason("Commition Payment");
			addEntry.setTo_Pay(0);
			addEntry.setPaid(totalPayment);
			System.out.println(addEntry.getPaid());
			if (serviceF.addEntry(addEntry)) {

				// generate pdf
				generateAccountsLocalInvoice(selectedIDs);

				// open pdf
				File pdfFile = new File(
						"C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\" + invoiceName);
				if (pdfFile.exists()) {

					if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().open(pdfFile);
					} else {
						System.out.println("Awt Desktop is not supported!");
					}

				} else {
					System.out.println("File is not exists!");
				}

			}
		}

	}

	@FXML
	void back(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/Accounts.fxml"));
		setNode(add);
	}

	/*---------------generate the jasper report--------------------*/
	public void generateAccountsLocalInvoice(ArrayList<Commition> selectedIDs) {

		invoiceName = "Commision_Invoice_" + getCurrentDate() + "_" + getCurrentTime() + ".pdf";

		double totalAmount = 0.0;

		Document document = new Document();
		try {
			@SuppressWarnings("unused")
			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(
					"C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\" + invoiceName));
			document.open();

			Font f1 = new Font(Font.FontFamily.HELVETICA, 21, Font.BOLD);
			BaseColor color = WebColors.getRGBColor("#283593");
			f1.setColor(color);

			Paragraph title = new Paragraph("M.M.B. SEA FOOD SUPPLIERS", f1);
			title.setAlignment(title.ALIGN_CENTER);

			Font f2 = new Font(Font.FontFamily.HELVETICA, 12);
			BaseColor color2 = WebColors.getRGBColor("#303f9f");
			f2.setColor(color2);
			Paragraph address = new Paragraph("Kanthoru Watta,Kiriparuwa Road,Devinuwara", f2);
			address.setAlignment(address.ALIGN_CENTER);

			Font f3 = new Font(Font.FontFamily.HELVETICA, 12);
			BaseColor color3 = WebColors.getRGBColor("#303f9f");
			f3.setColor(color3);
			Paragraph telephone = new Paragraph("Tel : 071 71 79 382 / 071 30 13 939(Predeep Samaru) " + "\n \n", f3);
			telephone.setAlignment(telephone.ALIGN_CENTER);

			Paragraph date = new Paragraph("Date : " + getCurrentDate(),
					new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL));
			date.setAlignment(date.ALIGN_RIGHT);

			Paragraph line_brake = new Paragraph("\n", new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL));
			date.setAlignment(date.ALIGN_LEFT);

			document.add(title);
			document.add(address);
			document.add(telephone);
			document.add(date);
			document.add(line_brake);

			Font cellFont = new Font(Font.FontFamily.HELVETICA);
			cellFont.setColor(BaseColor.WHITE);

			PdfPTable pdfPTable = new PdfPTable(3);
			PdfPCell pdfCell1 = new PdfPCell(new Phrase("Date", cellFont));
			PdfPCell pdfCell2 = new PdfPCell(new Phrase("Reason", cellFont));
			PdfPCell pdfCell3 = new PdfPCell(new Phrase("Amount", cellFont));

			BaseColor cellColor = WebColors.getRGBColor("#78909c");

			pdfCell1.setBackgroundColor(cellColor);
			pdfCell2.setBackgroundColor(cellColor);
			pdfCell3.setBackgroundColor(cellColor);

			pdfPTable.addCell(pdfCell1);
			pdfPTable.addCell(pdfCell2);
			pdfPTable.addCell(pdfCell3);

			for (Commition entry : selectedIDs) {
				Font priceCell = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

				PdfPCell dateCell = new PdfPCell(new Phrase(entry.getDate(), priceCell));
				pdfPTable.addCell(dateCell);

				PdfPCell ReasonCell = new PdfPCell(new Phrase(entry.getReason(), priceCell));
				pdfPTable.addCell(ReasonCell);

				PdfPCell total = new PdfPCell(new Phrase(String.format("%2.2f", entry.getTo_Pay()), priceCell));
				total.setHorizontalAlignment(total.ALIGN_RIGHT);
				pdfPTable.addCell(total);

				totalAmount += entry.getTo_Pay();
			}

			// Add spacing cell
			pdfPTable.addCell(" ");
			pdfPTable.addCell(" ");
			pdfPTable.addCell(" ");

			pdfPTable.addCell("");
			Font footerCell = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
			PdfPCell total = new PdfPCell(new Phrase("Total", footerCell));
			pdfPTable.addCell(total);

			PdfPCell amount = new PdfPCell(new Phrase(String.format("%2.2f", totalAmount), footerCell));
			amount.setHorizontalAlignment(amount.ALIGN_RIGHT);
			pdfPTable.addCell(amount);

			pdfPTable.setWidthPercentage(100);

			document.add(pdfPTable);
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

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

}
