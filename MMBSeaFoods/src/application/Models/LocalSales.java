package application.Models;

public class LocalSales {
	
	private int ID;
	private String Date;
	private int BuyerID;
	private String BuyerName;
	private double Price;
	private double TotalWeight;

	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public int getBuyerID() {
		return BuyerID;
	}
	public void setBuyerID(int buyerID) {
		BuyerID = buyerID;
	}
	public String getBuyerName() {
		return BuyerName;
	}
	public void setBuyerName(String buyerName) {
		BuyerName = buyerName;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public double getTotalWeight() {
		return TotalWeight;
	}
	public void setTotalWeight(double totalWeight) {
		TotalWeight = totalWeight;
	}
	
	//String view
	
	private String SPrice;
	private String STotalWeight;


	public String getSPrice() {
		return SPrice;
	}
	public void setSPrice(String sPrice) {
		SPrice = sPrice;
	}
	public String getSTotalWeight() {
		return STotalWeight;
	}
	public void setSTotalWeight(String sTotalWeight) {
		STotalWeight = sTotalWeight;
	}
	
	

}