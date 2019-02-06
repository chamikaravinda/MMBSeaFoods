package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Models.Boat;
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
	
	public ArrayList<Foreign_Fish_types> getfishTypes() throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Foreign_Fish_types";
		ArrayList<Foreign_Fish_types> list =new ArrayList<>();
		
		try {
			preparedStatement =connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Foreign_Fish_types fish=new Foreign_Fish_types();
				fish.setID(Integer.parseInt(resultSet.getString("ID")));
				fish.setName(resultSet.getString("Name"));
				fish.setPrice20_15(Double.parseDouble(resultSet.getString("price20_15")));
				fish.setPrice_15B(Double.parseDouble(resultSet.getString("price_15B")));
				fish.setPrice_20P(Double.parseDouble(resultSet.getString("price_20P")));
				list.add(fish);
				
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


	public Foreign_Fish_types getfishTypes(String type) throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Foreign_Fish_types where Name= ?";
		Foreign_Fish_types fish=new Foreign_Fish_types();
		try {
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setString(1, type);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				fish.setID(Integer.parseInt(resultSet.getString("ID")));
				fish.setName(resultSet.getString("Name"));
				fish.setPrice20_15(Double.parseDouble(resultSet.getString("price20_15")));
				fish.setPrice_15B(Double.parseDouble(resultSet.getString("price_15B")));
				fish.setPrice_20P(Double.parseDouble(resultSet.getString("price_20P")));
				
			}
			return fish;
			
		}catch(Exception e) {
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
		
		
	}
	
	public Foreign_Fish_types getfishTypes(int id) throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Foreign_Fish_types where ID= ?";
		Foreign_Fish_types fish=new Foreign_Fish_types();
		try {
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				fish.setID(Integer.parseInt(resultSet.getString("ID")));
				fish.setName(resultSet.getString("Name"));
				fish.setPrice20_15(Double.parseDouble(resultSet.getString("price20_15")));
				fish.setPrice_15B(Double.parseDouble(resultSet.getString("price_15B")));
				fish.setPrice_20P(Double.parseDouble(resultSet.getString("price_20P")));
				
			}
			return fish;
			
		}catch(Exception e) {
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
		
		
	}
	
	
public boolean UpdateForeign_Fish_Type(Foreign_Fish_types type) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "UPDATE Foreign_Fish_types set Name =?, price_20P =?, price_15B=?, price20_15=? where ID=?";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1,type.getName());
			preparedStatement.setDouble(2,type.getPrice_20P());
			preparedStatement.setDouble(3,type.getPrice_15B());
			preparedStatement.setDouble(4,type.getPrice20_15());
			preparedStatement.setDouble(5,type.getID());
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
