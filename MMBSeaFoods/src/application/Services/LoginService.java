package application.Services;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Models.User;



public class LoginService {

Connection connection;
	
	public User Login(User user) throws SQLException {
		connection=DBConnection.LoginConnector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "SELECT * FROM Users where USERNAME = ? and PASSWORD = ?"; 
		try { 
			
			preparedStatement =connection.prepareStatement(query);
			
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				user.setId(resultSet.getInt("ID"));
				return user;
			}
			else {
				return null;
			}
			
		}catch(Exception e) {
			return null;
		}finally {
			resultSet.close();
			preparedStatement.close();
			connection.close();
		}
		
	}
	
}
