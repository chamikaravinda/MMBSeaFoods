package application.Models;

public class Local_sale_item {
	
	private int ID;
	private int Fish_Type;
	private double Total_Weight;
	private double buying_Price;
	private int Fish_sale_ID;
	private String fish_name;
	private double unit_price;
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
	
	public int getFish_sale_ID() {
		return Fish_sale_ID;
	}
	public void setFish_sale_ID(int fish_sale_ID) {
		Fish_sale_ID = fish_sale_ID;
	}
	public String getFish_name() {
		return fish_name;
	}
	public void setFish_name(String fish_name) {
		this.fish_name = fish_name;
	}
	
	
	
	//String view
	
	public double getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}



	private String STotal_Weight;
	private String Sbuying_Price;
	private String Sunite_price;
	
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
	public String getSunite_price() {
		return Sunite_price;
	}
	public void setSunite_price(String sunite_price) {
		Sunite_price = sunite_price;
	}
	
	

}
