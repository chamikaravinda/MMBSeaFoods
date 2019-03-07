package application.Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
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
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Models.Local_Fish_types;
import application.Models.Local_sale_item;
import application.Models.Local_stock_items;
import application.Models.ForeignSallingFish;
import application.Models.LFish_stock;
import application.Models.LocalBoat;
import application.Models.LocalBuyers;
import application.Models.LocalSales;
import application.Models.Local_Buyers_Account;
import application.Services.LFish_stockService;
import application.Services.LocalBuyerAccountService;
import application.Services.LocalBuyerService;
import application.Services.Local_Fish_typesServices;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LocalSellController implements Initializable {

	@FXML
	private AnchorPane Stocks;

	@FXML
	private TableView<Local_sale_item> clmFishTable;

	@FXML
	private TableColumn<?, ?> clmfishtype;

	@FXML
	private TableColumn<?, ?> clmTotalWeight;

	@FXML
	private JFXComboBox<String> cmbLftype;

	@FXML
	private JFXTextField Lfweight;

	@FXML
	private JFXComboBox<String> cmbLBuyers;

	@FXML
	private JFXButton btnremove;
	@FXML
	private Label lbltotalprice;
	@FXML
	private Label lbltotalweight;

	AnchorPane back;

	private double totalWeight = 0;
	private double totalPrice = 0;

	ObservableList<String> LocalFishTypeList = FXCollections.observableArrayList();
	ObservableList<String> LocalBuyersList = FXCollections.observableArrayList();

	ArrayList<Local_Fish_types> local_fishtype = null;
	ArrayList<LocalBuyers> LocalBuyers = null;
	ArrayList<LFish_stock> currentStock = null;

	Local_Fish_typesServices serviceC = new Local_Fish_typesServices();
	LocalBuyerService serviceD = new LocalBuyerService();
	LFish_stockService serviceE = new LFish_stockService();
	LocalBuyerAccountService serviceF = new LocalBuyerAccountService();

	ObservableList<Local_sale_item> local_fishStock = FXCollections.observableArrayList();

	String invoiceName;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			local_fishtype = serviceC.getLocalfishTypes();
			LocalBuyers = serviceD.getLocalBuyer();
			currentStock = serviceE.getUnsoldLocalStocks();

			for (Local_Fish_types Ltyp : local_fishtype) {
				LocalFishTypeList.add(Ltyp.getName());
			}

			for (LocalBuyers buyer : LocalBuyers) {
				LocalBuyersList.add(buyer.getName());
			}

			cmbLftype.setItems(LocalFishTypeList);
			cmbLBuyers.setItems(LocalBuyersList);

			clmfishtype.setCellValueFactory(new PropertyValueFactory<>("fish_name"));
			clmTotalWeight.setCellValueFactory(new PropertyValueFactory<>("Total_Weight"));

			clmFishTable.setEditable(true);

			TableColumn<Local_sale_item, String> clmSellprice = new TableColumn<>("Unit Price (1Kg)");
			clmSellprice.setCellValueFactory(new PropertyValueFactory<>("Sunite_price"));

			clmSellprice.setCellFactory(TextFieldTableCell.<Local_sale_item>forTableColumn());
			clmSellprice.setOnEditCommit(event -> {
				Local_sale_item fish = event.getRowValue();
				fish.setSunite_price(event.getNewValue());
				fish.setUnit_price(Double.parseDouble(fish.getSunite_price()));
				fish.setBuying_Price(fish.getTotal_Weight() * fish.getUnit_price());
				fish.setSbuying_Price("Rs." + String.format("%2.2f", fish.getBuying_Price()));
				fish.setSunite_price(Double.toString(fish.getUnit_price()));

				clmFishTable.refresh();
				totalPrice = 0;
				totalWeight = 0;

				for (Local_sale_item fishprice : local_fishStock) {
					totalPrice = totalPrice + fishprice.getBuying_Price();
					totalWeight = totalWeight + fishprice.getTotal_Weight();
				}
				lbltotalprice.setText("Rs . " + String.format("%2.2f", totalPrice));
				lbltotalweight.setText(String.format("%2.2f", totalWeight) + "Kg");

			});

			TableColumn<Local_sale_item, String> clmTotalPrice = new TableColumn<>("Total Price");
			clmTotalPrice.setCellValueFactory(new PropertyValueFactory<>("Sbuying_Price"));

			clmFishTable.getColumns().addAll(clmSellprice, clmTotalPrice);
			clmFishTable.setItems(local_fishStock);

			btnremove.setOnAction(e -> {
				Local_sale_item local_Fish = clmFishTable.getSelectionModel().getSelectedItem();
				for (LFish_stock fish : currentStock) {
					if (fish.getFish_Type() == local_Fish.getFish_Type()) {
						fish.setTotal_Weight(fish.getTotal_Weight() + local_Fish.getTotal_Weight());
					}
				}
				clmFishTable.getItems().remove(local_Fish);
				clmFishTable.refresh();
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void AddLocalFishActions(ActionEvent event) throws SQLException {

		Local_sale_item local_Fish = new Local_sale_item();
		Local_Fish_types types = serviceC.getLocalfishTypes(cmbLftype.getValue());

		if (cmbLftype.getSelectionModel().getSelectedItem() != null && Lfweight.getText() != null) {
			local_Fish.setFish_name(cmbLftype.getValue());
			local_Fish.setTotal_Weight(Double.parseDouble(Lfweight.getText()));

			local_Fish.setFish_Type(types.getID());

			for (LFish_stock fish : currentStock) {
				if (fish.getFish_Type() == local_Fish.getFish_Type()) {
					if (fish.getTotal_Weight() >= local_Fish.getTotal_Weight()) {
						local_fishStock.add(local_Fish);
						fish.setTotal_Weight(fish.getTotal_Weight() - local_Fish.getTotal_Weight());

					} else {
						Notifications notifications = Notifications.create();
						notifications.title("Error");
						notifications.text("Not enough stocks");
						notifications.graphic(null);
						notifications.hideAfter(Duration.seconds(2));
						notifications.position(Pos.CENTER);
						notifications.showError();
					}
				}
			}
			Lfweight.clear();

		}

	}

	@FXML
	void AddFinalizeStock(ActionEvent event) throws IOException {
		try {
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String Buyername = cmbLBuyers.getValue();
			LocalBuyers buyer = serviceD.getLocalBoat(Buyername);
			if (Buyername != null && !local_fishStock.isEmpty() && isPricesSet()) {
				if (serviceE.sellStock(local_fishStock)) {

					LocalSales sale = new LocalSales();
					sale.setDate(format1.format(new Date()));
					sale.setBuyerID(buyer.getID());
					sale.setPrice(totalPrice);
					sale.setTotalWeight(totalWeight);
					long purchaseId = serviceF.addLocalSale(sale);
					if (purchaseId != 0) {
						Local_Buyers_Account newEntry = new Local_Buyers_Account();
						newEntry.setDate(format1.format(new Date()));
						newEntry.setReason("Fish Purchase of " + String.format("%2.1f", totalWeight) + "Kg");
						newEntry.setTo_Pay(totalPrice);
						newEntry.setPaid(0);
						newEntry.setBuyer_ID(buyer.getID());
						newEntry.setPurchase_ID((int) purchaseId);
						if (serviceF.addEntries(newEntry)) {
							if (serviceF.addEntriesUncleared(newEntry)) {

								for (Local_sale_item item : local_fishStock) {
									item.setFish_sale_ID((int)purchaseId);
								}
								if (serviceF.addLocalSaleItems(local_fishStock)) {
									Notifications notifications = Notifications.create();
									notifications.title("Succesfull");
									notifications.text("Sold succesfully");
									notifications.graphic(null);
									notifications.hideAfter(Duration.seconds(2));
									notifications.position(Pos.CENTER);
									notifications.showConfirm();

									back = FXMLLoader
											.load(getClass().getResource("/application/Views/Ltrade/LStocks.fxml"));
									setNode(back);
									
									generateAccountsLocalInvoice(local_fishStock,buyer.getName());

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
					}
				} else {
					Notifications notifications = Notifications.create();
					notifications.title("Error");
					notifications.text("Selling  unsuccesfull");
					notifications.graphic(null);
					notifications.hideAfter(Duration.seconds(2));
					notifications.position(Pos.CENTER);
					notifications.showError();
				}
			} else {
				Notifications notifications = Notifications.create();
				notifications.title("Error");
				notifications.text("Buyer Not selected or Items are empty");
				notifications.graphic(null);
				notifications.hideAfter(Duration.seconds(2));
				notifications.position(Pos.CENTER);
				notifications.showError();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	/*---------------generate the jasper report--------------------*/
	public void generateAccountsLocalInvoice(ObservableList<Local_sale_item> list , String buyer) {

		invoiceName = "SellLocalStock_Invoice_" + getCurrentDate() + "_" + getCurrentTime() + ".pdf";

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

			PdfPTable table = new PdfPTable(2);
			PdfPCell cellOne = new PdfPCell(new Phrase("Date : " + getCurrentDate(), topDetails));
			PdfPCell cellTwo = new PdfPCell(new Phrase("Buyer : " + buyer, topDetails));
	

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
			PdfPCell pdfCell1 = new PdfPCell(new Phrase("Item", cellFont));
			PdfPCell pdfCell2 = new PdfPCell(new Phrase("Weight", cellFont));
			PdfPCell pdfCell3 = new PdfPCell(new Phrase("Price", cellFont));

			BaseColor cellColor = WebColors.getRGBColor("#78909c");

			pdfCell1.setBackgroundColor(cellColor);
			pdfCell2.setBackgroundColor(cellColor);
			pdfCell3.setBackgroundColor(cellColor);

			pdfPTable.addCell(pdfCell1);
			pdfPTable.addCell(pdfCell2);
			pdfPTable.addCell(pdfCell3);

			for (Local_sale_item entry : list) {
				Font priceCell = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

				PdfPCell itemCell = new PdfPCell(new Phrase(entry.getFish_name(), priceCell));
				pdfPTable.addCell(itemCell);

				PdfPCell weight = new PdfPCell(new Phrase(String.format("%2.2f", entry.getTotal_Weight()), priceCell));
				weight.setHorizontalAlignment(weight.ALIGN_RIGHT);
				pdfPTable.addCell(weight);

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

			Font footerCell = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
			PdfPCell total = new PdfPCell(new Phrase("Total", footerCell));
			pdfPTable.addCell(total);

			PdfPCell weight = new PdfPCell(new Phrase(String.format("%2.2f", totalWeight), footerCell));
			weight.setHorizontalAlignment(weight.ALIGN_RIGHT);
			pdfPTable.addCell(weight);

			
			PdfPCell amount = new PdfPCell(new Phrase(String.format("%2.2f",totalAmount ), footerCell));
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
	public void back(ActionEvent event) throws IOException {

		back = FXMLLoader.load(getClass().getResource("/application/Views/Ltrade/LStocks.fxml"));
		setNode(back);

	}

	private boolean isPricesSet() {

		for (Local_sale_item fishprice : local_fishStock) {
			if (fishprice.getBuying_Price() == 0 || fishprice.getUnit_price() == 0) {
				return false;
			}
		}

		return true;
	}

}
