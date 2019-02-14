package application.Models;

public class LocalPurchase {
	
	private int ID;
	private String Date;
	private int BoatID;
	private String BoatName;
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
	public int getBoatID() {
		return BoatID;
	}
	public void setBoatID(int boatID) {
		BoatID = boatID;
	}
	public String getBoatName() {
		return BoatName;
	}
	public void setBoatName(String boatName) {
		BoatName = boatName;
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
