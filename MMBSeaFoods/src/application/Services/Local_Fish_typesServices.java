package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Models.Local_Fish_types;

public class Local_Fish_typesServices {

	Connection connection;

	public boolean addLocal_Fish_Type(Local_Fish_types Lftype) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;
		String insertQuery = "INSERT INTO Local_Fish_types (Name,price )" + "VALUES (?,?)";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, Lftype.getName());
			preparedStatement.setDouble(2, Lftype.getPrice());

			resultSet = preparedStatement.executeUpdate();
			
		    

			if (resultSet != 0) {
				return true;
			} else
				return false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}
	}

	// If want to get all data
	public ArrayList<Local_Fish_types> getLocalfishTypes() throws SQLException {
                                                
		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Local_Fish_types";
		ArrayList<Local_Fish_types> list = new ArrayList<>();

		try {
			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Local_Fish_types local_fish = new Local_Fish_types();
				local_fish.setID(Integer.parseInt(resultSet.getString("ID")));
				local_fish.setName(resultSet.getString("Name"));
				local_fish.setPrice(Double.parseDouble(resultSet.getString("price")));

				list.add(local_fish);

			}
			return list;

		} catch (Exception e) {
			return null;
		} finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}

	}

	// Just only one data according to type
	public Local_Fish_types getLocalfishTypes(int type) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Local_Fish_types where ID= ?";
		Local_Fish_types local_fish = new Local_Fish_types();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, type);//type
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				local_fish.setID(resultSet.getInt("ID"));
				local_fish.setName(resultSet.getString("Name"));
				local_fish.setPrice(resultSet.getDouble("price"));
				
			}
			
			
			return local_fish;

		} catch (Exception e) {
			return null;
		} finally {
			connection.close();
		}

	}
	
	
	public Local_Fish_types getLocalfishTypes(String typeName) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Local_Fish_types where Name= ?";
		Local_Fish_types local_fish = new Local_Fish_types();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, typeName);//type
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				local_fish.setID(resultSet.getInt(1));
				local_fish.setName(resultSet.getString(2));
				local_fish.setPrice(Double.parseDouble(resultSet.getString(3)));
				
			}
			
			
			return local_fish;

		} catch (Exception e) {
			return null;
		} finally {
			connection.close();
		}

	}
	
	public boolean UpdateLocal_Fish_Type(Local_Fish_types type) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "UPDATE Local_Fish_types set Name =?, price =? where ID=?";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1,type.getName());
			preparedStatement.setDouble(2,type.getPrice());
			preparedStatement.setDouble(3,type.getID());
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
