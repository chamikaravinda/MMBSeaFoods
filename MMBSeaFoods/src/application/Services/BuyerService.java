package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.Models.Boat;
import application.Models.Buyers;

public class BuyerService {
	
	Connection connection;
	

	public boolean addBuyer(Buyers buyer) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Foreign_Fish_Buyers (Name, Mobile_No)" + 
							"VALUES (?,?)";
		try {
			
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, buyer.getName());
			preparedStatement.setString(2, buyer.getMobile_No());
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
}
