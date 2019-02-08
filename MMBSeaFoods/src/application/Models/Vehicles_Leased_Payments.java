package application.Models;

import java.util.Date;

public class Vehicles_Leased_Payments {

	private int ID;
	private int Vehicle_ID;
	private double Paid_Amount;
	private String Payment_Date;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getVehicle_ID() {
		return Vehicle_ID;
	}
	public void setVehicle_ID(int vehicle_ID) {
		Vehicle_ID = vehicle_ID;
	}
	public double getPaid_Amount() {
		return Paid_Amount;
	}
	public void setPaid_Amount(double paid_Amount) {
		Paid_Amount = paid_Amount;
	}
	public String getPayment_Date() {
		return Payment_Date;
	}
	public void setPayment_Date(String payment_Date) {
		Payment_Date = payment_Date;
	}
    
}
