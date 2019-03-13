package application.Models;

public class LocalBoatAccountUnCleared {

	private int ID;
	private double To_Pay;
    private int Boat_ID ;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	public double getTo_Pay() {
		return To_Pay;
	}
	public void setTo_Pay(double to_Pay) {
		To_Pay = to_Pay;
	}
	public int getBoat_ID() {
		return Boat_ID;
	}
	public void setBoat_ID(int boat_ID) {
		Boat_ID = boat_ID;
	}
	
	//String views 
	private String STo_Pay;
	private String SPaid;
	private String BoatName;

	public String getSTo_Pay() {
		return STo_Pay;
	}
	public void setSTo_Pay(String string) {
		STo_Pay = string;
	}
	public String getSPaid() {
		return SPaid;
	}
	public void setSPaid(String sPaid) {
		SPaid = sPaid;
	}
	public String getBoatName() {
		return BoatName;
	}
	public void setBoatName(String boatName) {
		BoatName = boatName;
	}
	
	
}
