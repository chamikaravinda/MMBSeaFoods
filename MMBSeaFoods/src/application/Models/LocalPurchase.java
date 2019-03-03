package application.Models;

public class LocalPurchase {
	
	private int ID;
	private String Date;
	private int BoatID;
	private String BoatName;
	private String Habour;
	private double Total_Price;
	private double Total_Weight;
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
	public String getHabour() {
		return Habour;
	}
	public void setHabour(String habour) {
		Habour = habour;
	}
	public double getTotal_Price() {
		return Total_Price;
	}
	public void setTotal_Price(double total_Price) {
		Total_Price = total_Price;
	}
	public double getTotal_Weight() {
		return Total_Weight;
	}
	public void setTotal_Weight(double total_Weight) {
		Total_Weight = total_Weight;
	}
	
	
	

}
