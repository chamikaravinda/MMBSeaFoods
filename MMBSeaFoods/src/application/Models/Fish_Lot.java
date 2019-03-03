package application.Models;

public class Fish_Lot {
	
	private int  ID;
    private String Added_Date;     
    private String Lorry_Number;   
    private double Buying_Weight;  
    private double Ice_fee;
    private double Lorry_fee;  
    private double other_fees; 
    private double brokerFee;  
    private double buying_price;
    private double Sold_Weight;
    private double Sold_price;
    private int Sold_To;
    private String display_Name;
    private String Sold_Date;
    
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
	public double getBuying_Weight() {
		return Buying_Weight;
	}
	public void setBuying_Weight(double buying_Weight) {
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
	public double getSold_Weight() {
		return Sold_Weight;
	}
	public void setSold_Weight(double sold_Weight) {
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
	public String getDisplay_Name() {
		return display_Name;
	}
	public void setDisplay_Name(String display_Name) {
		this.display_Name = display_Name;
	}
	public String getSold_Date() {
		return Sold_Date;
	}
	public void setSold_Date(String date) {
		Sold_Date = date;
	}





	//display Strings
	private String Sbuying_price;
	private String SBuying_Weight;
	private String Buyer;
	private String Sprofite;
	private String Ssold_price;
	
	public String getSbuying_price() {
		return Sbuying_price;
	}
	public void setSbuying_price(String sbuying_price) {
		Sbuying_price = sbuying_price;
	}
	public String getSBuying_Weight() {
		return SBuying_Weight;
	}
	public void setSBuying_Weight(String sBuying_Weight) {
		SBuying_Weight = sBuying_Weight;
	}
	public String getBuyer() {
		return Buyer;
	}
	public void setBuyer(String buyer) {
		Buyer = buyer;
	}
	public String getSprofite() {
		return Sprofite;
	}
	public void setSprofite(String sprofite) {
		Sprofite = sprofite;
	}
	public String getSsold_price() {
		return Ssold_price;
	}
	public void setSsold_price(String ssold_price) {
		Ssold_price = ssold_price;
	}
	
	
	

}
