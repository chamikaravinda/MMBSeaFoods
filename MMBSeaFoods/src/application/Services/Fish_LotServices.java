package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.Models.Fish_Lot;

public class Fish_LotServices {
	
	Connection connection;
	
	
	public boolean addLot(Fish_Lot lot) throws SQLException {
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Fish_Lot (Added_Date, Lorry_Number, Ice_fee,Lorry_fee,other_fees)" + 
							"VALUES (?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, lot.getAdded_Date());
			preparedStatement.setString(2, lot.getLorry_Number());
			preparedStatement.setDouble(3, lot.getIce_fee());
			preparedStatement.setDouble(4, lot.getLorry_fee());
			preparedStatement.setDouble(5, lot.getOther_fees());
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

}
