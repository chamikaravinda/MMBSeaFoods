package application.Models;

public class Third_Party_Acc_Uncleared {
	
	
			private int ID;
    		private String date;
            private String Reason;
            private double To_Pay;             
            private double Paid;
            
			public int getID() {
				return ID;
			}
			public void setID(int iD) {
				ID = iD;
			}
			public String getDate() {
				return date;
			}
			public void setDate(String date) {
				this.date = date;
			}
			public String getReason() {
				return Reason;
			}
			public void setReason(String reason) {
				Reason = reason;
			}
			public double getTo_Pay() {
				return To_Pay;
			}
			public void setTo_Pay(double to_Pay) {
				To_Pay = to_Pay;
			}
			public double getPaid() {
				return Paid;
			}
			public void setPaid(double paid) {
				Paid = paid;
			}
            
            

}
