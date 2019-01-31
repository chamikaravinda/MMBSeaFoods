package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.Models.F_Fish_Buyers_Account;



public class Foreign_Fish_Buyers_AccountServices {
	
	Connection connection;
	
	public boolean addEntry(F_Fish_Buyers_Account entry ) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO F_Fish_Buyers_Account (BoatNameorNumber, Mobile)" + 
							"VALUES (?,?)";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1,entry.getBoatNameorNumber());
			preparedStatement.setString(2, entry.getMobile());
		
			resultSet=preparedStatement.executeUpdate();
			
			if(resultSet != 0) {
				return true;
			}
			else 
				return false;
	
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
	}
	
	public boolean addEntryUncleared(F_Fish_Buyers_Account unclearEntry ) throws SQLException {
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO F_Fish_Buyers_Account_Uncleared(BoatNameorNumber, Mobile)" + 
							"VALUES (?,?)";
		
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1,unclearEntry.getBoatNameorNumber());
			preparedStatement.setString(2, unclearEntry.getMobile());
		
			resultSet=preparedStatement.executeUpdate();
			
			if(resultSet != 0) {
				return true;
			}
			else 
				return false;
	
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
		
	}

}
