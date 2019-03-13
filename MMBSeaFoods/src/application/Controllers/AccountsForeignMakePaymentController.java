package application.Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.Boat;
import application.Models.Boat_Account;
import application.Models.Boat_Account_UnCleared;
import application.Models.Fish_stock;
import application.Models.Stock_Fish;
import application.Services.AccountServices;
import application.Services.BoatService;
import application.Services.Boat_AccountServices;
import application.Services.Fish_stockService;
import application.Services.stock_FishService;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class AccountsForeignMakePaymentController implements Initializable {

	AnchorPane add;

	@FXML
	private AnchorPane Accounts;

	@FXML
	private Label lblBoatName;

	@FXML
	private TableView<Boat_Account_UnCleared> tblvBoatDetails;

	@FXML
	private TableColumn<?, ?> tblBoat;

	@FXML
	private TableColumn<?, ?> tblcTopay;

	@FXML
	private JFXComboBox<String> cmbBoatNames;

	@FXML
	private JFXTextField txtAmount;

	@FXML
	private JFXTextField txtDescription;

	@FXML
	private JFXDatePicker date;

	String invoiceName;
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

	AccountServices accountServices = new AccountServices();
	Boat_AccountServices boatAccountService = new Boat_AccountServices();
	Fish_stockService stockService = new Fish_stockService();
	BoatService boatService = new BoatService();
	private ObservableList<Boat_Account_UnCleared> boatDetailsList = FXCollections.observableArrayList();
	private ObservableList<String> boatNameList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Platform.runLater(() -> {

			boatNameList = accountServices.getAllBoatNamesForeign();
			cmbBoatNames.setItems(boatNameList);

			NumberValidator numValidator = new NumberValidator();
			RequiredFieldValidator textValidator = new RequiredFieldValidator();

			txtAmount.getValidators().add(numValidator);
			numValidator.setMessage("Please input Paying Amount Correctly");

			txtAmount.focusedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (!newValue) {
						if (!txtAmount.getText().isEmpty()) {
							txtAmount.validate();
						}
					}

				}

			});

			txtDescription.getValidators().add(textValidator);
			textValidator.setMessage("Please input Description");

			txtDescription.focusedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (!newValue) {
						if (!txtDescription.getText().isEmpty()) {
							txtDescription.validate();
						}
					}

				}

			});

			boatDetailsList.clear();

			ArrayList<Boat_Account_UnCleared> boat_list = accountServices.getAllUnclearedpayments();

			for (Boat_Account_UnCleared boat : boat_list) {
				try {
					if (boat.getTo_Pay() != 0) {
						boat.setSTo_Pay("Rs." + String.format("%2.0f", boat.getTo_Pay()) + ".00");
					} else {
						boat.setSTo_Pay("Rs 0.00");
					}

					Boat entryboat = boatService.getBoat(boat.getBoat_ID());
					boat.setBoatName(entryboat.getBoatNameorNumber());
					boatDetailsList.add(boat);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			tblBoat.setCellValueFactory(new PropertyValueFactory<>("BoatName"));
			tblcTopay.setCellValueFactory(new PropertyValueFactory<>("STo_Pay"));

			tblvBoatDetails.setItems(boatDetailsList);

		});

	}

	public void paid(ActionEvent event) throws SQLException, IOException {
		LocalDate localDate = date.getValue();

		String Payingboat = cmbBoatNames.getValue();
		Boat entryboat = boatService.getBoat(Payingboat);

		Boat_Account entry = new Boat_Account();

		entry.setDate(getDate(localDate));
		entry.setPaid(Double.parseDouble(txtAmount.getText()));
		entry.setReason(txtDescription.getText());
		entry.setBoat_ID(entryboat.getID());
		entry.setStock_ID(0);
		entry.setTo_Pay(0);

		Boat_Account_UnCleared unEntry = new Boat_Account_UnCleared();
		unEntry.setBoat_ID(entryboat.getID());
		unEntry.setTo_Pay(entry.getPaid());

		if (boatAccountService.addEntries(entry)) {
			if (boatAccountService.RemoveFromBoatAccountStockEntryUncleard(unEntry)) {

				Notifications notifications = Notifications.create();
				notifications.title("Succesfull");
				notifications.text("Payment added succesfully");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showConfirm();
				
				add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/MakePayment.fxml"));
				setNode(add);

				generateAccountsLocalInvoice(entryboat.getBoatNameorNumber(),entry);

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
		} else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Error in completing the payment");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}
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

	public void back(ActionEvent event) throws IOException {

		add = FXMLLoader.load(getClass().getResource("/application/Views/Accounts/FBoatAccount.fxml"));
		setNode(add);

	}

	/*---------------generate the jasper report--------------------*/
	public void generateAccountsLocalInvoice(String boatName, Boat_Account entry) {

		invoiceName = "ForeingPaymentInvoice" + getCurrentDate() + "_" + getCurrentTime() + ".pdf";

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

			Font topDetails = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
			topDetails.setColor(BaseColor.BLACK);

			PdfPTable table = new PdfPTable(2);
			PdfPCell cellOne = new PdfPCell(new Phrase("Date : " + entry.getDate(), topDetails));
			PdfPCell cellTwo = new PdfPCell(new Phrase("Boat : " + boatName, topDetails));

			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER);

			table.addCell(cellOne);
			table.addCell(cellTwo);

			table.setWidthPercentage(100);

			Paragraph line_brake = new Paragraph("\n", new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL));
			line_brake.setAlignment(line_brake.ALIGN_LEFT);

			document.add(title);
			document.add(address);
			document.add(telephone);
			document.add(table);
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

			Font priceCell = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

			PdfPCell itemCell = new PdfPCell(new Phrase(entry.getDate(), priceCell));
			pdfPTable.addCell(itemCell);

			PdfPCell weight = new PdfPCell(new Phrase(entry.getReason(), priceCell));
			weight.setHorizontalAlignment(weight.ALIGN_RIGHT);
			pdfPTable.addCell(weight);

			PdfPCell uniteprice = new PdfPCell(new Phrase(String.format("%2.2f", entry.getPaid()), priceCell));
			uniteprice.setHorizontalAlignment(uniteprice.ALIGN_RIGHT);
			pdfPTable.addCell(uniteprice);

			pdfPTable.setWidthPercentage(100);

			document.add(pdfPTable);
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*-------------------Generate Bill Date -----------------*/

	private String getDate(LocalDate date) {
		if (date != null) {
			return date.toString();
		} else {
			return format1.format(new Date());

		}
	}

	private String getCurrentDate() {

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
