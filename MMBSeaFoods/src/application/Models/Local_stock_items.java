package application.Models;

public class Local_stock_items {
	
	private int ID;
	private int Fish_Type;
	private double Total_Weight;
	private double buying_Price;
	private int Fish_stock_ID;
	private String fish_name;
	private double unitePrice;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getFish_Type() {
		return Fish_Type;
	}
	public void setFish_Type(int fish_Type) {
		Fish_Type = fish_Type;
	}
	public double getTotal_Weight() {
		return Total_Weight;
	}
	public void setTotal_Weight(double total_Weight) {
		Total_Weight = total_Weight;
	}
	public double getBuying_Price() {
		return buying_Price;
	}
	public void setBuying_Price(double buying_Price) {
		this.buying_Price = buying_Price;
	}
	public int getFish_stock_ID() {
		return Fish_stock_ID;
	}
	public void setFish_stock_ID(int fish_stock_ID) {
		Fish_stock_ID = fish_stock_ID;
	}
	public String getFish_name() {
		return fish_name;
	}
	public void setFish_name(String fish_name) {
		this.fish_name = fish_name;
	}
	
	
	//String view
	
	public double getUnitePrice() {
		return unitePrice;
	}
	public void setUnitePrice(double unitePrice) {
		this.unitePrice = unitePrice;
	}


	private String STotal_Weight;
	private String Sbuying_Price;
	public String getSTotal_Weight() {
		return STotal_Weight;
	}
	public void setSTotal_Weight(String sTotal_Weight) {
		STotal_Weight = sTotal_Weight;
	}
	public String getSbuying_Price() {
		return Sbuying_Price;
	}
	public void setSbuying_Price(String sbuying_Price) {
		Sbuying_Price = sbuying_Price;
	}
	
	

}
