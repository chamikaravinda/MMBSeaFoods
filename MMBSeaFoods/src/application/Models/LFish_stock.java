package application.Models;

public class LFish_stock {
	
    private int ID;
    private int Fish_Type;
	private Double Total_Weight;
	private String FishName;
	
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
	
	
	//String View
	private String STotal_Weight;

	public String getSTotal_Weight() {
		return STotal_Weight;
	}
	public void setSTotal_Weight(String sTotal_Weight) {
		STotal_Weight = sTotal_Weight;
	}
	
	
	
	
}
