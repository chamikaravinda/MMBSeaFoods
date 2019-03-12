package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Models.Fish_stock;
import application.Models.Stock_Fish;

public class stock_FishService {

	Connection connection;

	public boolean addFish(Stock_Fish fish) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;
		String insertQuery = "INSERT INTO stock_fishes (Type, Total_Weight, buying_Price,Fish_stock_ID)"
				+ "VALUES (?,?,?,?)";
		try {

			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, fish.getType());
			preparedStatement.setDouble(2, fish.getWeight());
			preparedStatement.setDouble(3, fish.getPrice());
			preparedStatement.setDouble(4, fish.getFish_stock_ID());
			resultSet = preparedStatement.executeUpdate();
			if (resultSet != 0)
				return true;
			else
				return false;

		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}
	}

	public ArrayList<Stock_Fish> getStocksFish(int stockID) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from stock_fishes where Fish_stock_ID = ? ";
		ArrayList<Stock_Fish> list = new ArrayList<>();

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, stockID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Stock_Fish fish = new Stock_Fish();
				fish.setID(Integer.parseInt(resultSet.getString("ID")));
				fish.setType(resultSet.getInt("Type"));
				fish.setWeight(resultSet.getDouble("Total_Weight"));
				fish.setPrice(resultSet.getDouble("buying_Price"));
				fish.setFish_stock_ID(resultSet.getInt("Fish_stock_ID"));
				list.add(fish);
			}
			return list;

		} catch (Exception e) {
			return null;
		} finally {
			connection.close();
		}
	}

	public boolean deleteStockFishes(int id) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;

		String deleteQuery = "delete from stock_fishes where Fish_stock_ID=? ";

		try {
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {

			connection.close();
		}


	}

}
