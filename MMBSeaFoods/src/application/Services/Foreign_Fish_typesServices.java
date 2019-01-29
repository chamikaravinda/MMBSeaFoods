package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.Models.Foreign_Fish_types;

public class Foreign_Fish_typesServices {
	
	Connection connection;
	
	public boolean addForeign_Fish_Type(Foreign_Fish_types type) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Foreign_Fish_types (Name, price_20P, price_15B,price20_15)" + 
							"VALUES (?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1,type.getName());
			preparedStatement.setDouble(2,type.getPrice_20P());
			preparedStatement.setDouble(3,type.getPrice_15B());
			preparedStatement.setDouble(4,type.getPrice20_15());
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
