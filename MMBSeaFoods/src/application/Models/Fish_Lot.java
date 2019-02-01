package application.Models;

public class Fish_Lot {
	
	private int  ID;
    private String Added_Date;     
    private String Lorry_Number;   
    private int Buying_Weight;  
    private double Ice_fee;
    private double Lorry_fee;  
    private double other_fees; 
    private double brokerFee;  
    private double buying_price;
    private int Sold_Weight;
    private double Sold_price;
    private int Sold_To;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getAdded_Date() {
		return Added_Date;
	}
	public void setAdded_Date(String added_Date) {
		Added_Date = added_Date;
	}
	public String getLorry_Number() {
		return Lorry_Number;
	}
	public void setLorry_Number(String lorry_Number) {
		Lorry_Number = lorry_Number;
	}
	public int getBuying_Weight() {
		return Buying_Weight;
	}
	public void setBuying_Weight(int buying_Weight) {
		Buying_Weight = buying_Weight;
	}
	public double getIce_fee() {
		return Ice_fee;
	}
	public void setIce_fee(double ice_fee) {
		Ice_fee = ice_fee;
	}
	public double getLorry_fee() {
		return Lorry_fee;
	}
	public void setLorry_fee(double lorry_fee) {
		Lorry_fee = lorry_fee;
	}
	public double getOther_fees() {
		return other_fees;
	}
	public void setOther_fees(double other_fees) {
		this.other_fees = other_fees;
	}
	public double getBrokerFee() {
		return brokerFee;
	}
	public void setBrokerFee(double brokerFee) {
		this.brokerFee = brokerFee;
	}
	public double getBuying_price() {
		return buying_price;
	}
	public void setBuying_price(double buying_price) {
		this.buying_price = buying_price;
	}
	public int getSold_Weight() {
		return Sold_Weight;
	}
	public void setSold_Weight(int sold_Weight) {
		Sold_Weight = sold_Weight;
	}
	public double getSold_price() {
		return Sold_price;
	}
	public void setSold_price(double sold_price) {
		Sold_price = sold_price;
	}
	public int getSold_To() {
		return Sold_To;
	}
	public void setSold_To(int sold_To) {
		Sold_To = sold_To;
	}  
    
    

}
