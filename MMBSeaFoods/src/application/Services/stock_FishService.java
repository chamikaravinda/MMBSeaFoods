package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.Models.Stock_Fish;

public class stock_FishService {
	
	Connection connection;
	
	public boolean addFish(Stock_Fish fish) throws SQLException {

		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO stock_fishes (Type, Total_Weight, buying_Price,Fish_stock_ID)" + 
							"VALUES (?,?,?,?)";
		try {
			
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, fish.getType());
			preparedStatement.setDouble(2, fish.getWeight());
			preparedStatement.setDouble(3,fish.getPrice());
			preparedStatement.setDouble(4,fish.getFish_stock_ID());
			resultSet=preparedStatement.executeUpdate();
			if(resultSet != 0)
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
