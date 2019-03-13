package application.Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.Models.F_BoatEntryCatogries;
import application.Models.LFish_stock;
import application.Models.LocalBoat;
import application.Models.LocalBoatAccount;
import application.Models.LocalBoatAccountUnCleared;
import application.Models.LocalPurchase;
import application.Models.Local_Fish_types;
import application.Models.Local_stock_items;
import application.Models.Stock_Fish;
import application.Services.LFish_stockService;
import application.Services.LocalBoatAccountService;
import application.Services.LocalBoatService;
import application.Services.Local_Fish_typesServices;
import application.Services.Local_PurchasesService;
import javafx.animation.FadeTransition;
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
import javafx.fxml.Initializable;

public class AddLocalStockController implements Initializable {

	@FXML
	private AnchorPane NewStocks;

	@FXML
	private JFXComboBox<String> cmbLsBoat;

	@FXML
	private JFXTextField txthabour;

	@FXML
	private Label lbltotalprice;

	@FXML
	private Label lbltotalweight;

	@FXML
	private TableView<Local_stock_items> clmFishTable;

	@FXML
	private TableColumn<?, ?> clmfishtype;

	@FXML
	private TableColumn<?, ?> clmTotalWeight;

	@FXML
	private TableColumn<?, ?> clmTotalPrice;

	@FXML
	private JFXTextField Lfweight;

	@FXML
	private JFXComboBox<String> cmbLftype;

	@FXML
	private JFXButton btnremove;

	@FXML
	private JFXDatePicker date;

	@FXML
	private JFXCheckBox billDate;
	@FXML
	private JFXButton AddLFish;

	private double total_price;
	private double total_weight;

	String invoiceName;

	AnchorPane add;

	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

	ObservableList<String> LocalFishTypeList = FXCollections.observableArrayList();
	ObservableList<String> LocalBoatList = FXCollections.observableArrayList();

	Local_Fish_typesServices serviceC = new Local_Fish_typesServices();
	LFish_stockService serviceB = new LFish_stockService();
	LocalBoatAccountService serviceD = new LocalBoatAccountService();
	LocalBoatService serviceE = new LocalBoatService();
	Local_PurchasesService serviceF = new Local_PurchasesService();

	ArrayList<Local_Fish_types> local_fishtype = null;
	ArrayList<LocalBoat> local_boat = null;

	ObservableList<Local_stock_items> local_fishStock = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		local_fishStock.clear();
		// Load DataS to the Combo Boxs
		try {
			local_fishtype = serviceC.getLocalfishTypes();

			for (Local_Fish_types Ltyp : local_fishtype) {

				LocalFishTypeList.add(Ltyp.getName());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			local_boat = serviceE.getLocalBoat();

			for (LocalBoat lboat : local_boat) {

				LocalBoatList.add(lboat.getBoatNameorNumber());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		cmbLsBoat.setItems(LocalBoatList);
		cmbLftype.setItems(LocalFishTypeList);
		// end the Data to Combo Box

		clmfishtype.setCellValueFactory(new PropertyValueFactory<>("fish_name"));
		clmTotalWeight.setCellValueFactory(new PropertyValueFactory<>("Total_Weight"));
		clmTotalPrice.setCellValueFactory(new PropertyValueFactory<>("buying_Price"));

		clmFishTable.setItems(local_fishStock);

		btnremove.setOnAction(e -> {
			Local_stock_items local_Fish = clmFishTable.getSelectionModel().getSelectedItem();
			clmFishTable.getItems().remove(local_Fish);
			clmFishTable.refresh();

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

	public void AddLocalFish(ActionEvent event) throws SQLException {

		if (cmbLftype.getSelectionModel().getSelectedItem() != null && Lfweight.getText() != null) {

			Local_stock_items local_Fish = new Local_stock_items();
			Local_Fish_types types = serviceC.getLocalfishTypes(cmbLftype.getValue());

			local_Fish.setFish_name(cmbLftype.getValue());

			local_Fish.setTotal_Weight(Double.parseDouble(Lfweight.getText()));
			if (local_Fish.getTotal_Weight() > 30.0) {
				local_Fish.setBuying_Price(local_Fish.getTotal_Weight() * types.getPrice_A30());
				local_Fish.setUnitePrice(types.getPrice_A30());
			} else if (local_Fish.getTotal_Weight() > 25.0 && local_Fish.getTotal_Weight() <= 30.0) {
				local_Fish.setBuying_Price(local_Fish.getTotal_Weight() * types.getPrice_25T30());
				local_Fish.setUnitePrice(types.getPrice_25T30());
			} else if (local_Fish.getTotal_Weight() > 20.0 && local_Fish.getTotal_Weight() <= 25.0) {
				local_Fish.setBuying_Price(local_Fish.getTotal_Weight() * types.getPrice_20T25());
				local_Fish.setUnitePrice(types.getPrice_20T25());
			} else if (local_Fish.getTotal_Weight() > 15.0 && local_Fish.getTotal_Weight() <= 20.0) {
				local_Fish.setBuying_Price(local_Fish.getTotal_Weight() * types.getPrice_15T20());
				local_Fish.setUnitePrice(types.getPrice_15T20());
			} else if (local_Fish.getTotal_Weight() > 10.0 && local_Fish.getTotal_Weight() <= 15.0) {
				local_Fish.setBuying_Price(local_Fish.getTotal_Weight() * types.getPrice_10T15());
				local_Fish.setUnitePrice(types.getPrice_10T15());
			} else {
				local_Fish.setBuying_Price(local_Fish.getTotal_Weight() * types.getPrice_U10());
				local_Fish.setUnitePrice(types.getPrice_U10());
			}

			local_Fish.setFish_Type(types.getID());
			local_fishStock.add(local_Fish);
			clmFishTable.refresh();
			Lfweight.clear();

			total_price = total_price + local_Fish.getBuying_Price();
			lbltotalprice.setText("Total Price    : Rs." + String.format("%2.2f", total_price));

			total_weight = total_weight + local_Fish.getTotal_Weight();
			lbltotalweight.setText("Total Weight : " + String.format("%2.2f", total_weight));
		}

	}

	public void AddFinalizeStock(ActionEvent event) throws SQLException, IOException {
		LocalDate localDate = date.getValue();
		
		if (cmbLsBoat.getValue() != null && txthabour.getText() != null) {
			String boatname = cmbLsBoat.getValue();
			LocalBoat boat = serviceE.getLocalBoat(boatname);

			if (!local_fishStock.isEmpty()) {
				LocalPurchase newPurchase = new LocalPurchase();

				newPurchase.setBoatID(boat.getID());
				newPurchase.setDate(getDate(localDate));
				newPurchase.setHabour(txthabour.getText());
				newPurchase.setTotal_Price(total_price);
				newPurchase.setTotal_Weight(total_weight);
				long StockId = serviceF.addLocalPurchase(newPurchase);
				if (StockId != 0) {
					if (serviceB.newStock(local_fishStock)) {
						LocalBoatAccount entry = new LocalBoatAccount();

						entry.setBoat_ID(boat.getID());
						entry.setDate(getDate(localDate));
						entry.setPaid(0);
						entry.setPurchase_ID((int) StockId);
						entry.setReason("Fish Purchase of " + total_weight + "Kg");
						entry.setTo_Pay(total_price);
						if (serviceD.addEntries(entry)) {
							
							LocalBoatAccountUnCleared unEntry =new LocalBoatAccountUnCleared();
							unEntry.setBoat_ID(boat.getID());
							unEntry.setTo_Pay(total_price);
							
							if (serviceD.addEntriesUncleard(unEntry)) {
								if (serviceF.addStockItems(local_fishStock, (int) StockId)) {
									Notifications notifications = Notifications.create();
									notifications.title("Succesfull");
									notifications.text("Fish stock added succesfully");
									notifications.graphic(null);
									notifications.hideAfter(Duration.seconds(2));
									notifications.position(Pos.CENTER);
									notifications.showConfirm();

									add = FXMLLoader
											.load(getClass().getResource("/application/Views/Ltrade/LStocks.fxml"));
									setNode(add);

									generateAccountsLocalInvoice(local_fishStock, boat.getBoatNameorNumber(),
											txthabour.getText());

									// open pdf
									File pdfFile = new File("C:\\Users\\" + System.getProperty("user.name")
											+ "\\Documents\\" + invoiceName);
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
					}

				} else {
					Notifications notifications = Notifications.create();
					notifications.title("Error");
					notifications.text("Fish stock adding unsuccesfull");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showError();

				}
			} else {
				Notifications notifications = Notifications.create();
				notifications.title("Error");
				notifications.text("Add Fishes to the List");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showError();
			}
		} else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Fill the missing fieleds");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}
	}

	/*---------------generate the jasper report--------------------*/
	public void generateAccountsLocalInvoice(ObservableList<Local_stock_items> list, String boatName, String harbour) {

		invoiceName = "LocalAddStock_Invoice_" + getCurrentDate() + "_" + getCurrentTime() + ".pdf";

		double totalAmount = 0.0;
		double totalWeight = 0.0;

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

			PdfPTable table = new PdfPTable(3);
			PdfPCell cellOne = new PdfPCell(new Phrase("Date : " + getCurrentDate(), topDetails));
			PdfPCell cellTwo = new PdfPCell(new Phrase("Boat : " + boatName, topDetails));
			PdfPCell cellThree = new PdfPCell(new Phrase("Harbour : " + harbour, topDetails));

			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER);
			cellThree.setBorder(Rectangle.NO_BORDER);

			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);

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

			PdfPTable pdfPTable = new PdfPTable(4);
			PdfPCell pdfCell1 = new PdfPCell(new Phrase("Item", cellFont));
			PdfPCell pdfCell2 = new PdfPCell(new Phrase("Weight", cellFont));
			PdfPCell pdfCell4 = new PdfPCell(new Phrase("Unite Price", cellFont));
			PdfPCell pdfCell3 = new PdfPCell(new Phrase("Price", cellFont));

			BaseColor cellColor = WebColors.getRGBColor("#78909c");

			pdfCell1.setBackgroundColor(cellColor);
			pdfCell2.setBackgroundColor(cellColor);
			pdfCell3.setBackgroundColor(cellColor);
			pdfCell4.setBackgroundColor(cellColor);

			pdfPTable.addCell(pdfCell1);
			pdfPTable.addCell(pdfCell2);
			pdfPTable.addCell(pdfCell4);
			pdfPTable.addCell(pdfCell3);

			for (Local_stock_items entry : list) {
				Font priceCell = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

				PdfPCell itemCell = new PdfPCell(new Phrase(entry.getFish_name(), priceCell));
				pdfPTable.addCell(itemCell);

				PdfPCell weight = new PdfPCell(new Phrase(String.format("%2.2f", entry.getTotal_Weight()), priceCell));
				weight.setHorizontalAlignment(weight.ALIGN_RIGHT);
				pdfPTable.addCell(weight);

				PdfPCell unitePrice = new PdfPCell(
						new Phrase(String.format("%2.2f", entry.getUnitePrice()), priceCell));
				unitePrice.setHorizontalAlignment(unitePrice.ALIGN_RIGHT);
				pdfPTable.addCell(unitePrice);

				PdfPCell total = new PdfPCell(new Phrase(String.format("%2.2f", entry.getBuying_Price()), priceCell));
				total.setHorizontalAlignment(total.ALIGN_RIGHT);
				pdfPTable.addCell(total);

				totalAmount += entry.getBuying_Price();
				totalWeight += entry.getTotal_Weight();
			}

			// Add spacing cell
			pdfPTable.addCell(" ");
			pdfPTable.addCell(" ");
			pdfPTable.addCell(" ");
			pdfPTable.addCell(" ");

			Font footerCell = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
			PdfPCell total = new PdfPCell(new Phrase("Total", footerCell));
			pdfPTable.addCell(total);

			PdfPCell weight = new PdfPCell(new Phrase(String.format("%2.2f", totalWeight), footerCell));
			weight.setHorizontalAlignment(weight.ALIGN_RIGHT);
			pdfPTable.addCell(weight);

			PdfPCell blankcell = new PdfPCell(new Phrase(" "));
			blankcell.setHorizontalAlignment(blankcell.ALIGN_RIGHT);
			pdfPTable.addCell(blankcell);
			
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

	/*-------------------Generate Bill Date -----------------*/

	private String getDate(LocalDate date) {
		if (date != null) {
			return date.toString();
		} else {
			return format1.format(new Date());

		}
	}

	private String getCurrentDate() {

		if (billDate.isSelected()) {

			return getDate(date.getValue());

		} else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String newDate = dateFormat.format(date);

			return newDate;
		}
	}

	/*-------------------Generate Current Time -----------------*/
	public static String getCurrentTime() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");

		return (sdf.format(cal.getTime()));
	}
	
	public void back(ActionEvent event) throws IOException {
		add = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LStocks.fxml"));
		setNode(add);

	}
}
