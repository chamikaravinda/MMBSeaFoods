package application.Models;

public class Fish_stock {
	
    private int ID;
	private String Added_Date;
	private int Boat_ID;
	private String Harbour;
	private int NoofFishes;
	private Double total_Weight;
	private Double fishprice;
	private Double commition_price;
	private Double totalBuying_price;
	private int Lot_ID;
	private String Status;
	private String BoatName;
	private String Lorry_Number;
	
	
	public String getLorry_Number() {
		return Lorry_Number;
	}
	public void setLorry_Number(String lorry_Number) {
		Lorry_Number = lorry_Number;
	}
	public int getID() {
		return ID;
	}
	public String getBoatName() {
		return BoatName;
	}
	public void setBoatName(String boatName) {
		BoatName = boatName;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getAdded_Date() {
		return Added_Date;
	}
	public void setAdded_Date(String added_Date) {
		Added_Date = added_Date;
	}
	public int getBoat_ID() {
		return Boat_ID;
	}
	public void setBoat_ID(int boat_ID) {
		Boat_ID = boat_ID;
	}
	public String getHarbour() {
		return Harbour;
	}
	public void setHarbour(String harbour) {
		Harbour = harbour;
	}
	public int getNoofFishes() {
		return NoofFishes;
	}
	public void setNoofFishes(int noofFishes) {
		NoofFishes = noofFishes;
	}
	public Double getTotal_Weight() {
		return total_Weight;
	}
	public void setTotal_Weight(Double total_Weight) {
		this.total_Weight = total_Weight;
	}
	public Double getFishprice() {
		return fishprice;
	}
	public void setFishprice(Double fishprice) {
		this.fishprice = fishprice;
	}
	public Double getCommition_price() {
		return commition_price;
	}
	public void setCommition_price(Double commition_price) {
		this.commition_price = commition_price;
	}
	public Double getTotalBuying_price() {
		return totalBuying_price;
	}
	public void setTotalBuying_price(Double totalBuying_price) {
		this.totalBuying_price = totalBuying_price;
	}
	public int getLot_ID() {
		return Lot_ID;
	}
	public void setLot_ID(int lot_ID) {
		Lot_ID = lot_ID;
	}
	
	//Strings Views
	
		private String Stotal_Weight ;
		private String StotalBuying_price;


		public String getStotal_Weight() {
			return Stotal_Weight;
		}
		public void setStotal_Weight(String stotal_Weight) {
			Stotal_Weight = stotal_Weight;
		}
		public String getStotalBuying_price() {
			return StotalBuying_price;
		}
		public void setStotalBuying_price(String stotalBuying_price) {
			StotalBuying_price = stotalBuying_price;
		}

		
	
}
