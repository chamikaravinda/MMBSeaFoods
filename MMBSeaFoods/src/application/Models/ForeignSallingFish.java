package application.Models;

public class ForeignSallingFish {
	
	private int id;
	private String name;
	private double totalWeigth;
	private double price;
	private int weightclass;
	private double unitePrice;
	private double totalSellingPrice;
	
	/* weight class
	 * 1= below 15kg
	 * 2= between 15-20kg
	 * 3=above 20
	 */
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getTotalWeigth() {
		return totalWeigth;
	}
	public void setTotalWeigth(double totalWeigth) {
		this.totalWeigth = totalWeigth;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getWeightclass() {
		return weightclass;
	}
	public void setWeightclass(int weightclass) {
		this.weightclass = weightclass;
	}
	public double getUnitePrice() {
		return unitePrice;
	}
	public void setUnitePrice(double unitePrice) {
		this.unitePrice = unitePrice;
	}
	public double getTotalSellingPrice() {
		return totalSellingPrice;
	}
	public void setTotalSellingPrice(double totalSellingPrice) {
		this.totalSellingPrice = totalSellingPrice;
	} 

	

}
