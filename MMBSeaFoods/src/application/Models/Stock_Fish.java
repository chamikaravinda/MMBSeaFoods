package application.Models;

public class Stock_Fish {
	private int ID;
	private int Type;
	private double Total_weight ;
	private double buying_Price;
	private int Fish_stock_ID;
	
	public double getWeight() {
		return Total_weight;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	public int getFish_stock_ID() {
		return Fish_stock_ID;
	}
	public void setFish_stock_ID(int fish_stock_ID) {
		Fish_stock_ID = fish_stock_ID;
	}
	public void setWeight(double weight) {
		this.Total_weight = weight;
	}
	public double getPrice() {
		return buying_Price;
	}
	public void setPrice(double price) {
		this.buying_Price = price;
	}

	
}
