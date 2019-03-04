package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Models.Buyers;
import application.Models.LocalPurchase;
import application.Models.Local_stock_items;
import javafx.collections.ObservableList;

public class Local_PurchasesService {

	Connection connection;

	public long addLocalPurchase(LocalPurchase purchase) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;
		String insertQuery = "INSERT INTO Local_Purchases (Date, Boat_ID,Habour,Total_Weight,Total_Price)"
				+ "VALUES (?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, purchase.getDate());
			preparedStatement.setInt(2, purchase.getBoatID());
			preparedStatement.setString(3, purchase.getHabour());
			preparedStatement.setDouble(4, purchase.getTotal_Weight());
			preparedStatement.setDouble(5, purchase.getTotal_Price());
			resultSet = preparedStatement.executeUpdate();
			if (resultSet != 0) {
				ResultSet result = preparedStatement.getGeneratedKeys();
				if (result.next()) {
					return result.getLong(1);
				} else {
					return 0;
				}
			} else
				return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			connection.close();
		}

	}

	public boolean addStockItems(ObservableList<Local_stock_items> list,int stockId) throws SQLException {
		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			for (Local_stock_items item : list) {
				
				String insertQuery = "INSERT INTO Local_stock_items (Fish_Type,Total_Weight,buying_Price,Fish_stock_ID) VALUES (?,?,?,?)";
				preparedStatement = connection.prepareStatement(insertQuery);
				preparedStatement.setInt(1, item.getFish_Type());
				preparedStatement.setDouble(2, item.getTotal_Weight());
				preparedStatement.setDouble(3, item.getBuying_Price());
				preparedStatement.setDouble(4, stockId);
				preparedStatement.executeUpdate();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}
	}

}
