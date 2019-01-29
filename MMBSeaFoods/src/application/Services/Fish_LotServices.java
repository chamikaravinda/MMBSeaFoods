package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Models.Fish_Lot;
import application.Models.Vehicles;

public class Fish_LotServices {
	
	Connection connection;
	
	
	public boolean addLot(Fish_Lot lot) throws SQLException {
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Fish_Lot (Added_Date, Lorry_Number, Ice_fee,Lorry_fee,other_fees,buying_price)" + 
							"VALUES (?,?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, lot.getAdded_Date());
			preparedStatement.setString(2, lot.getLorry_Number());
			preparedStatement.setDouble(3, lot.getIce_fee());
			preparedStatement.setDouble(4, lot.getLorry_fee());
			preparedStatement.setDouble(5, lot.getOther_fees());
			preparedStatement.setDouble(6, 0);
			resultSet=preparedStatement.executeUpdate();
			
			if(resultSet != 0) {
				return true;
			}
			else 
				return false;
	
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
	}
	
	
	public ArrayList<Fish_Lot> getUnslodLots() throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Fish_Lot where Sold_To IS NULL ";
		ArrayList<Fish_Lot> list =new ArrayList<>();
		
		try {
			preparedStatement =connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Fish_Lot lot =new Fish_Lot();
				lot.setID(Integer.parseInt(resultSet.getString("ID")));
				lot.setAdded_Date(resultSet.getString("Added_Date"));
				lot.setBuying_Weight(Integer.parseInt(resultSet.getString("Buying_Weight")));
				lot.setLorry_Number(resultSet.getString("Lorry_Number"));
				lot.setBuying_price(Double.parseDouble(resultSet.getString("Buying_price")));
				list.add(lot);
				
			}
			return list;
			
		}catch(Exception e) {
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
		
		
	}

}
