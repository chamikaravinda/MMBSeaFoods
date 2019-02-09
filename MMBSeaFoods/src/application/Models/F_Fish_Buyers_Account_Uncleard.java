package application.Models;

public class F_Fish_Buyers_Account_Uncleard {

	private int ID;
	private String Date;
	private String Reason;
	private double To_Pay;
	private double Paid;
	private int Buyer_ID;
	
	public int getBuyer_ID() {
		return Buyer_ID;
	}
	public void setBuyer_ID(int buyer_ID) {
		Buyer_ID = buyer_ID;
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
	public double getPaid() {
		return Paid;
	}
	public void setPaid(double paid) {
		Paid = paid;
	}

	
	
	

}
