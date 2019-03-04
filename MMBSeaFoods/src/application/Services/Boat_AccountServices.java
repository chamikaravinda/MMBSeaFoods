package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import application.Models.Boat_Account;
import application.Models.Boat_Account_UnCleared;
import application.Models.F_Fish_Buyers_Account;
import application.Models.F_Fish_Buyers_Account_Uncleard;
import application.Models.User;

public class Boat_AccountServices {
	
	
	Connection connection;
	
	
	//search and get particular Boat account
	public ArrayList<Boat_Account> getAllentries(int ID) throws SQLException {
    	
    	connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		Boat_Account boat_Account= new Boat_Account();
		
		String getAllentriesQuery= "select * from Boat_Account where ID =? ";
	
		ArrayList<Boat_Account> list= new ArrayList<Boat_Account>();
		try {
			
			preparedStatement= connection.prepareStatement(getAllentriesQuery);
			preparedStatement.setInt(1,ID);
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				Boat_Account boat= new Boat_Account();
				boat.setBoat_ID(resultSet.getInt("Boat_ID"));
				boat.setDate(resultSet.getString("Date"));
				boat.setPaid(resultSet.getDouble("Paid"));
				boat.setReason(resultSet.getString("Reason"));
				boat.setTo_Pay(resultSet.getDouble("To_Pay"));
				
				list.add(boat);
				
			}
			
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			connection.close();
		}
	
    }
	
	// add entries
	public boolean addEntries(Boat_Account entry){
		

    	connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		
		
		String addEntryQuery="INSERT INTO Boat_Account(Date,Reason,To_Pay,Paid,Boat_ID,Stock_ID)"+" VALUES(?,?,?,?,?,?)";
		
		
		try {
			  
			preparedStatement=connection.prepareStatement(addEntryQuery);
			preparedStatement.setString(1, entry.getDate());
			preparedStatement.setString(2,entry.getReason());
			preparedStatement.setDouble(3, entry.getTo_Pay());
			preparedStatement.setDouble(4, entry.getPaid());
			preparedStatement.setDouble(5, entry.getBoat_ID());
			preparedStatement.setDouble(6, entry.getStock_ID());
			resultSet= preparedStatement.executeUpdate();
			
			if (resultSet != 0) {

				return true;
			} else {
				return false;
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		
	}
	
	//add uncleard entries
	
	public boolean addEntries_Uncleard(Boat_Account_UnCleared entry)throws SQLException {
		

    	connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		
		String addEntryQuery="INSERT INTO Boat_Account_UnCleared(Date,Reason,To_Pay,Paid,Boat_ID,Stock_ID)"+" VALUES(?,?,?,?,?,?)";
		
		try {
			  
			preparedStatement=connection.prepareStatement(addEntryQuery);
			preparedStatement.setString(1, entry.getDate());
			preparedStatement.setString(2,entry.getReason());
			preparedStatement.setDouble(3, entry.getTo_Pay());
			preparedStatement.setDouble(4, entry.getPaid());
			preparedStatement.setDouble(5, entry.getBoat_ID());
			preparedStatement.setDouble(6, entry.getStock_ID());
			resultSet= preparedStatement.executeUpdate();
			
			if (resultSet != 0) {

				return true;
			} else {
				return false;
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}

		
	}
	
	public boolean RemoveFromBoatAccount(int id) {

		try {		
			
			connection=DBConnection.Connector();
			PreparedStatement preparedStatement=null;	
			
			String query = "DELETE FROM Boat_Account WHERE ID = ?";
			
			preparedStatement = connection.prepareStatement(query);		
			
			
			preparedStatement.setInt(1, id);
			
			if(preparedStatement.execute()) {
				return true;
			}else {
				return false;
			}
		
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			return false;
			
		} catch (Exception e) {
			return false;
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In setUnclearedBuyerRecievedForeign : " + e);
			}		
			
		}
		
	}

}
