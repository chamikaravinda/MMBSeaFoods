package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Models.Local_Fish_types;

public class Local_Fish_typesServices {

	Connection connection;

	public boolean addLocal_Fish_Type(Local_Fish_types type) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;
		String insertQuery = "INSERT INTO Local_Fish_types (Name,price_U10, price_10T15,"
				+ "price_15T20,price_20T25,price_25T30,price_A30 ) VALUES (?,?,?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1,type.getName());
			preparedStatement.setDouble(2,type.getPrice_U10());
			preparedStatement.setDouble(3,type.getPrice_10T15());
			preparedStatement.setDouble(4,type.getPrice_15T20());
			preparedStatement.setDouble(5,type.getPrice_20T25());
			preparedStatement.setDouble(6,type.getPrice_25T30());
			preparedStatement.setDouble(7,type.getPrice_A30());
			resultSet=preparedStatement.executeUpdate();
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
				Local_Fish_types fish = new Local_Fish_types();
				fish.setID(Integer.parseInt(resultSet.getString("ID")));
				fish.setName(resultSet.getString("Name"));
				fish.setPrice_U10(Double.parseDouble(resultSet.getString("price_U10")));
				fish.setPrice_10T15(Double.parseDouble(resultSet.getString("price_10T15")));
				fish.setPrice_15T20(Double.parseDouble(resultSet.getString("price_15T20")));
				fish.setPrice_20T25(Double.parseDouble(resultSet.getString("price_20T25")));
				fish.setPrice_25T30(Double.parseDouble(resultSet.getString("price_25T30")));
				fish.setPrice_A30(Double.parseDouble(resultSet.getString("price_A30")));
				list.add(fish);

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
		Local_Fish_types fish = new Local_Fish_types();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, type);//type
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				fish.setID(Integer.parseInt(resultSet.getString("ID")));
				fish.setName(resultSet.getString("Name"));
				fish.setPrice_U10(Double.parseDouble(resultSet.getString("price_U10")));
				fish.setPrice_10T15(Double.parseDouble(resultSet.getString("price_10T15")));
				fish.setPrice_15T20(Double.parseDouble(resultSet.getString("price_15T20")));
				fish.setPrice_20T25(Double.parseDouble(resultSet.getString("price_20T25")));
				fish.setPrice_25T30(Double.parseDouble(resultSet.getString("price_25T30")));
				fish.setPrice_A30(Double.parseDouble(resultSet.getString("price_A30")));				
			}
			
			
			return fish;

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
		Local_Fish_types fish = new Local_Fish_types();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, typeName);//type
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				fish.setID(Integer.parseInt(resultSet.getString("ID")));
				fish.setName(resultSet.getString("Name"));
				fish.setPrice_U10(Double.parseDouble(resultSet.getString("price_U10")));
				fish.setPrice_10T15(Double.parseDouble(resultSet.getString("price_10T15")));
				fish.setPrice_15T20(Double.parseDouble(resultSet.getString("price_15T20")));
				fish.setPrice_20T25(Double.parseDouble(resultSet.getString("price_20T25")));
				fish.setPrice_25T30(Double.parseDouble(resultSet.getString("price_25T30")));
				fish.setPrice_A30(Double.parseDouble(resultSet.getString("price_A30")));	
				
			}
			
			
			return fish;

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
		String insertQuery= "UPDATE Local_Fish_types set Name =?,price_U10=?, price_10T15=?,price_15T20=?,price_20T25=?,price_25T30=?,price_A30=? where ID=?";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1,type.getName());
			preparedStatement.setDouble(2,type.getPrice_U10());
			preparedStatement.setDouble(3,type.getPrice_10T15());
			preparedStatement.setDouble(4,type.getPrice_15T20());
			preparedStatement.setDouble(5,type.getPrice_20T25());
			preparedStatement.setDouble(6,type.getPrice_25T30());
			preparedStatement.setDouble(7,type.getPrice_A30());
			preparedStatement.setDouble(8,type.getID());
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
