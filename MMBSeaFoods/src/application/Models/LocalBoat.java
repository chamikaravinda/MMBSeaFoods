package application.Models;

public class LocalBoat {

	private int ID;
	private String BoatNameorNumber;
	private String Mobile;
	private String Owner;
	
	
	public String getOwner() {
		return Owner;
	}
	public void setOwner(String owner) {
		Owner = owner;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getBoatNameorNumber() {
		return BoatNameorNumber;
	}
	public void setBoatNameorNumber(String boatNameorNumber) {
		BoatNameorNumber = boatNameorNumber;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	
	
}
