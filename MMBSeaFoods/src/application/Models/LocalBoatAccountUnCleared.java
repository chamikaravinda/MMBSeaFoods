package application.Models;

public class LocalBoatAccountUnCleared {

	private int ID;
	private String Date;
	private String Reason;
	private double To_Pay;
	private double Paid;
    private int purchase_ID;
    private int Boat_ID ;
	public double getPaid() {
		return Paid;
	}
	public void setPaid(double paid) {
		Paid = paid;
	}
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
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}
	public double getTo_Pay() {
		return To_Pay;
	}
	public void setTo_Pay(double to_Pay) {
		To_Pay = to_Pay;
	}
	public int getBoat_ID() {
		return Boat_ID;
	}
	public void setBoat_ID(int boat_ID) {
		Boat_ID = boat_ID;
	}	
	public int getPurchase_ID() {
		return purchase_ID;
	}
	public void setPurchase_ID(int purchase_ID) {
		this.purchase_ID = purchase_ID;
	}


	//String views 
	private String STo_Pay;
	private String SPaid;
	private String BoatName;

	public String getSTo_Pay() {
		return STo_Pay;
	}
	public void setSTo_Pay(String string) {
		STo_Pay = string;
	}
	public String getSPaid() {
		return SPaid;
	}
	public void setSPaid(String sPaid) {
		SPaid = sPaid;
	}
	public String getBoatName() {
		return BoatName;
	}
	public void setBoatName(String boatName) {
		BoatName = boatName;
	}
	
	
}
