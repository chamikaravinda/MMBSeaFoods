package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Models.F_Fish_Buyers_Account;



public class Foreign_Fish_Buyers_AccountServices {
	
	Connection connection;
	
	public boolean addEntry(F_Fish_Buyers_Account entry ) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO F_Fish_Buyers_Account (Date, Reason,To_Pay,Paid,Buyer_ID,Lot_ID)" + 
							"VALUES (?,?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1,entry.getDate());
			preparedStatement.setString(2, entry.getReason());
			preparedStatement.setDouble(3, entry.getTo_Pay());
			preparedStatement.setDouble(4, entry.getPaid());
			preparedStatement.setDouble(5, entry.getBuyer_ID());
			preparedStatement.setInt(6, entry.getLot_ID());
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
		String insertQuery=  "INSERT INTO F_Fish_Uncleared (Date, Reason,To_Pay,Paid,Buyer_ID,Lot_ID)" + 
				"VALUES (?,?,?,?,?,?)";
		
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1,unclearEntry.getDate());
			preparedStatement.setString(2, unclearEntry.getReason());
			preparedStatement.setDouble(3, unclearEntry.getTo_Pay());
			preparedStatement.setDouble(4, unclearEntry.getPaid());
			preparedStatement.setDouble(5, unclearEntry.getBuyer_ID());
			preparedStatement.setInt(6, unclearEntry.getLot_ID());
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
	
	
	public ArrayList<F_Fish_Buyers_Account> getFBuyerAccountRecords(int id) throws SQLException {
			
			
			connection=DBConnection.Connector();
			PreparedStatement preparedStatement=null;
			ResultSet resultSet=null;
			String query = "select * from F_Fish_Buyers_Account where Buyer_ID=? ORDER BY ID DESC";
			ArrayList<F_Fish_Buyers_Account> accountDetails=new ArrayList<>();
			
			try {
				preparedStatement =connection.prepareStatement(query);
				preparedStatement.setInt(1, id);
				
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					F_Fish_Buyers_Account entries = new F_Fish_Buyers_Account();
	
					entries.setID(Integer.parseInt(resultSet.getString("ID")));
					entries.setDate(resultSet.getString("Date"));
					entries.setReason(resultSet.getString("Reason"));
					entries.setTo_Pay(Double.parseDouble(resultSet.getString("To_Pay")));
					entries.setPaid(Double.parseDouble(resultSet.getString("Paid")));
					entries.setBuyer_ID(Integer.parseInt(resultSet.getString("Buyer_ID")));
					entries.setLot_ID(Integer.parseInt(resultSet.getString("Lot_ID")));
					accountDetails.add(entries);				
				}
				
				return accountDetails;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}finally {
				connection.close();
	
			}
		}
	

}
