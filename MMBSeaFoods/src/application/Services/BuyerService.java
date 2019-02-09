package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	
	public ArrayList<Buyers> getBuyers() throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Foreign_Fish_Buyers";
		ArrayList<Buyers> list =new ArrayList<>();
		
		try {
			preparedStatement =connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Buyers buyers=new Buyers();
				buyers.setID(Integer.parseInt(resultSet.getString("ID")));
				buyers.setMobile_No(resultSet.getString("Mobile_No"));
				buyers.setName(resultSet.getString("Name"));
				list.add(buyers);
				
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
	
	
	public Buyers getBuyers(String Name) throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Foreign_Fish_Buyers where Name =?";
		ArrayList<Buyers> list =new ArrayList<>();
		Buyers buyers=new Buyers();
		try {
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setString(1, Name);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				buyers.setID(Integer.parseInt(resultSet.getString("ID")));
				buyers.setMobile_No(resultSet.getString("Mobile_No"));
				buyers.setName(resultSet.getString("Name"));
				
			}
			return buyers;
				
			
		}catch(Exception e) {
			return null;
		}finally {

			connection.close();
		}
		
		
	}
	
	public Buyers getBuyers(int ID) throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Foreign_Fish_Buyers where ID =?";
		ArrayList<Buyers> list =new ArrayList<>();
		Buyers buyers=new Buyers();
		try {
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setInt(1, ID);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				buyers.setID(Integer.parseInt(resultSet.getString("ID")));
				buyers.setMobile_No(resultSet.getString("Mobile_No"));
				buyers.setName(resultSet.getString("Name"));
				
			}
			return buyers;
				
			
		}catch(Exception e) {
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
		
		
	}
	

	public boolean UpdateBuyer(Buyers buyer) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "UPDATE Foreign_Fish_Buyers set Name=? , Mobile_No=?" + 
							"where ID=?";
		try {
			
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, buyer.getName());
			preparedStatement.setString(2, buyer.getMobile_No());
			preparedStatement.setInt(3, buyer.getID());
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
