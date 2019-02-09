package application.Models;

public class F_BoatEntryCatogries {
	
	private int ID;
	private int typeID;
	private String typeName;
	private int NoOfFishes;
	private double totalPrice;
	private int WeightGroup;
	private double weight;
	
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getNoOfFishes() {
		return NoOfFishes;
	}
	public void setNoOfFishes(int noOfFishes) {
		NoOfFishes = noOfFishes;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getWeightGroup() {
		return WeightGroup;
	}
	public void setWeightGroup(int weightGroup) {
		WeightGroup = weightGroup;
	}

	
	
}
