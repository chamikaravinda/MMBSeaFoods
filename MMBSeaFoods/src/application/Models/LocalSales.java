package application.Models;

public class LocalSales {
	
	private int ID;
	private String Date;
	private int BuyerID;
	private String BuyerName;
	private int FishType;
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
	public int getFishType() {
		return FishType;
	}
	public void setFishType(int fishType) {
		FishType = fishType;
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
	
	
	

}