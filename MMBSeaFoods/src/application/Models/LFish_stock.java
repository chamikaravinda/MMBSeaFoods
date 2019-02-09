package application.Models;

public class LFish_stock {
	
    private int ID;
    private int Fish_Type;
	private Double Total_Weight;
	private String FishName;
	private double Price;
	
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public String getFishName() {
		return FishName;
	}
	public void setFishName(String fishName) {
		FishName = fishName;
	}
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
	public Double getTotal_Weight() {
		return Total_Weight;
	}
	public void setTotal_Weight(Double total_Weight) {
		Total_Weight = total_Weight;
	}
	
	
	
	
	
}
