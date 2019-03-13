package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.Models.Third_Party_Account;

public class Third_Party_AccountServices {
	
	Connection connection;

	
	public boolean addEntry(Third_Party_Account entry) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Third_Party_Account (Date, Reason, To_Pay,Paid,stockID,lotID)" + 
							"VALUES (?,?,?,?,?,?)";
		try {
			
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, entry.getDate());
			preparedStatement.setString(2, entry.getReason());
			preparedStatement.setDouble(3,entry.getTo_Pay());
			preparedStatement.setDouble(4,entry.getPaid());
			preparedStatement.setDouble(5,entry.getStockID());
			preparedStatement.setDouble(6,entry.getLotID());
			resultSet=preparedStatement.executeUpdate();
			if(resultSet!=0)
				return true;
			else 
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
		
		
	}
	
	public boolean addEntry_Uncleared(Third_Party_Account entry) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Third_Party_Acc_Uncleared (Date, Reason, To_Pay,Paid,stockID,lotID)" + 
							"VALUES (?,?,?,?,?,?)";
		try {
			
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, entry.getDate());
			preparedStatement.setString(2, entry.getReason());
			preparedStatement.setDouble(3,entry.getTo_Pay());
			preparedStatement.setDouble(4,entry.getPaid());
			preparedStatement.setDouble(5,entry.getStockID());
			preparedStatement.setDouble(6,entry.getLotID());
			resultSet=preparedStatement.executeUpdate();
			if(resultSet!=0)
				return true;
			else 
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
		
		
	}
	
	//Clear all records
	
	
	public boolean clearAcconts() throws SQLException {

		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		
		
		String deleteQuery="delete * from Third_Party_Acc_Uncleared ";
		
		try {
			preparedStatement = connection.prepareStatement(deleteQuery);
			resultSet=preparedStatement.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			
			connection.close();
		}
	
		return true;
}
	
	public boolean clearAccontsStockDelete(int id) throws SQLException {

		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		
		
		String deleteQuery="delete from Third_Party_Account WHERE stockID=? ";
		
		try {
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setInt(1, id);
			resultSet=preparedStatement.executeUpdate();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			
			connection.close();
		}
	
		
}

	public boolean clearAccontsStockDeleteUncleard(int id) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;

		String deleteQuery = "delete from Third_Party_Acc_Uncleared WHERE stockID=? ";

		try {
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {

			connection.close();
		}

	
	}
	
	public boolean clearAccontsLotDeleteUncleard(int id) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;

		String deleteQuery = "delete from Third_Party_Acc_Uncleared WHERE lotID=? ";

		try {
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {

			connection.close();
		}

	
	}
	
	public boolean clearAccontsLotDelete(int id) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;

		String deleteQuery = "delete from Third_Party_Account WHERE lotID=? ";

		try {
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {

			connection.close();
		}

	
	}
}