package application.Models;

import java.util.Date;

public class Vehicles {

	private int ID;
	private String Vehicle_No;     
    private double Total_Lease;     
    private double Paid_Amount;   
    private double To_Pay;
    private double Premium_Amount;
    private String Last_Payment;
    
    
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getVehicle_No() {
		return Vehicle_No;
	}
	public void setVehicle_No(String vehicle_No) {
		Vehicle_No = vehicle_No;
	}
	public double getTotal_Lease() {
		return Total_Lease;
	}
	public void setTotal_Lease(double total_Lease) {
		Total_Lease = total_Lease;
	}
	public double getPaid_Amount() {
		return Paid_Amount;
	}
	public void setPaid_Amount(double paid_Amount) {
		Paid_Amount = paid_Amount;
	}
	public double getTo_Pay() {
		return To_Pay;
	}
	public void setTo_Pay(double to_Pay) {
		To_Pay = to_Pay;
	}
	public double getPremium_Amount() {
		return Premium_Amount;
	}
	public void setPremium_Amount(double premium_Amount) {
		Premium_Amount = premium_Amount;
	}
	public String getLast_Payment() {
		return Last_Payment;
	}
	public void setLast_Payment(String last_Payment) {
		Last_Payment = last_Payment;
	}
	
    
    
}
