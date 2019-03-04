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

	// String views
	private String STotal_Price;
	private String STotal_Weight;

	public String getSTotal_Price() {
		return STotal_Price;
	}

	public void setSTotal_Price(String sTotal_Price) {
		STotal_Price = sTotal_Price;
	}
	public String getSTotal_Weight() {
		return STotal_Weight;
	}

	public void setSTotal_Weight(String sTotal_Weight) {
		STotal_Weight = sTotal_Weight;
	}

}
