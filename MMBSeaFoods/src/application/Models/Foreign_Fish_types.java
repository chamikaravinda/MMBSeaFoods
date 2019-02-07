package application.Models;

public class Foreign_Fish_types {
	
	private int ID;
	private String Name;
	private double price_15B;
	private double price20_15;
	private double price_20P;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public double getPrice_15B() {
		return price_15B;
	}
	public void setPrice_15B(double price_15b) {
		price_15B = price_15b;
	}
	public double getPrice20_15() {
		return price20_15;
	}
	public void setPrice20_15(double price20_15) {
		this.price20_15 = price20_15;
	}
	public double getPrice_20P() {
		return price_20P;
	}
	public void setPrice_20P(double price_20p) {
		price_20P = price_20p;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	//string values to show the views
	
	private String Sprice_15B;
	private String Sprice20_15;
	private String Sprice_20P;
	
	public String getSprice_15B() {
		return Sprice_15B;
	}
	public void setSprice_15B(String sprice_15b) {
		Sprice_15B = sprice_15b;
	}
	public String getSprice20_15() {
		return Sprice20_15;
	}
	public void setSprice20_15(String sprice20_15) {
		Sprice20_15 = sprice20_15;
	}
	public String getSprice_20P() {
		return Sprice_20P;
	}
	public void setSprice_20P(String sprice_20p) {
		Sprice_20P = sprice_20p;
	}
	
	
	
	
	

}
