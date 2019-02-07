package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.print.DocFlavor.READER;

import application.Models.User;
import application.Models.Vehicles;

public class SettingService {

	Connection connection;

	
	//Add User Method
	public boolean addUser(User user) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;
		String insertQuery = "insert into User(Username,Password)" + "values(?,?)";

		try {

			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			resultSet = preparedStatement.executeUpdate();

			if (resultSet != 0) {

				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}

	}
	
	//Remove User Method
	
	public boolean removeUser(String username) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		
		User user = new User();
		
		String deleteQuery="delete from User"+"where username= '"+username+"' ";
		try {
		preparedStatement.setInt(1, user.getId());
		resultSet=preparedStatement.executeUpdate();
		
					
					if(resultSet!=0) {
						
						return true;
					}else {
						
						return false;
					}
		}
		
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			
			connection.close();
		}
		
	}
	
	//Update User Method
    public boolean updateUser(User user) throws SQLException {
    	connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		
		String upadateQuery="update Users set username=?,passworrd=?"+"where ID= '"+user.getId()+"' ";
		try {
			
			preparedStatement= connection.prepareStatement(upadateQuery);
			preparedStatement.setString(1,user.getUsername() );
			preparedStatement.setString(2,user.getPassword() );
			
			resultSet=preparedStatement.executeUpdate();
			
			if(resultSet!=0) {
				
				return true;
			}
			else {
				
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			
			connection.close();
		}
		
	}
    
    //Get User Name
    
    public User getUser(String username) throws SQLException {
    	
    	connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		String getUserQuery= "select * from User where username =?";
		User user= new User();
		
		try {
			preparedStatement= connection.prepareStatement(getUserQuery);
			preparedStatement.setString(1, username);
			resultSet=preparedStatement.executeQuery();
			
			
			user.setUsername(resultSet.getString("id"));
			user.setPassword(resultSet.getString("password"));
			user.setPassword(resultSet.getString("username"));
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			connection.close();
		}
		return user;

		
		
    }
    
    //get All users
   public ArrayList<User> getUsers() throws SQLException{
	    
	    connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String getUsersquery= "select * from Users";
		
		ArrayList<User> list =new ArrayList<>(); //new object created to use below
	   
		try {
			preparedStatement =connection.prepareStatement(getUsersquery);
			
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				User user= new User();
				user.setId(Integer.parseInt(resultSet.getString("id")));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				
				list.add(user);
				
			}
			
			return list;
			
		
		}catch (Exception e) {
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
	   
   }

   public User getUser() throws SQLException {
   	
   	connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		String getUserQuery= "select * from Users";
		User user= new User();
		
		try {
			preparedStatement= connection.prepareStatement(getUserQuery);
			resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next()) {
			user.setId(resultSet.getInt("id"));
			user.setPassword(resultSet.getString("password"));
			user.setUsername(resultSet.getString("username"));
			}
			return user;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			connection.close();
		}
		

		
		
   }

}
