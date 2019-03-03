package application.Models;

public class Local_stock_items {
	
	private int ID;
	private int Fish_Type;
	private double Total_Weight;
	private double buying_Price;
	private int Fish_stock_ID;
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
	
	

}
