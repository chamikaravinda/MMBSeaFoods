package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.cglib.core.Local;

import application.Models.Buyers;
import application.Models.Fish_stock;
import application.Models.LocalPurchase;
import application.Models.LocalSales;
import application.Models.Local_sale_item;
import application.Models.Local_stock_items;
import javafx.collections.ObservableList;
import javafx.util.converter.PercentageStringConverter;

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

	public boolean addStockItems(ObservableList<Local_stock_items> list, int stockId) throws SQLException {
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

	public LocalPurchase getLocalPurchase(int id) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		String Query = "SELECT * FROM Local_Purchases WHERE ID=?";

		try {
			preparedStatement = connection.prepareStatement(Query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			LocalPurchase purchase = new LocalPurchase();
			if (resultSet.next()) {
				purchase.setID(resultSet.getInt("ID"));
				purchase.setDate(resultSet.getString("Date"));
				purchase.setBoatID(resultSet.getInt("Boat_ID"));
				purchase.setHabour(resultSet.getString("Habour"));
				purchase.setTotal_Price(resultSet.getDouble("Total_Price"));
				purchase.setTotal_Weight(resultSet.getDouble("Total_Weight"));

			}
			return purchase;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.close();
		}

	}

	public ArrayList<LocalPurchase> getLocalPurchase() throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<LocalPurchase> list = new ArrayList<>();

		String Query = "SELECT * FROM Local_Purchases  ORDER BY  ID DESC";

		try {
			preparedStatement = connection.prepareStatement(Query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				LocalPurchase purchase = new LocalPurchase();
				purchase.setID(resultSet.getInt("ID"));
				purchase.setDate(resultSet.getString("Date"));
				purchase.setBoatID(resultSet.getInt("Boat_ID"));
				purchase.setHabour(resultSet.getString("Habour"));
				purchase.setTotal_Price(resultSet.getDouble("Total_Price"));
				purchase.setTotal_Weight(resultSet.getDouble("Total_Weight"));
				list.add(purchase);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.close();
		}

	}
	
	public ArrayList<Local_stock_items> getLPurchaseStockItems(int id) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Local_stock_items> list = new ArrayList<>();

		String Query = "SELECT * FROM Local_stock_items WHERE Fish_stock_ID=?  ORDER BY  ID DESC";

		try {
			preparedStatement = connection.prepareStatement(Query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Local_stock_items item=new Local_stock_items();
				
				item.setFish_Type(resultSet.getInt("Fish_Type"));
				item.setBuying_Price(resultSet.getDouble("buying_Price"));
				item.setTotal_Weight(resultSet.getDouble("Total_Weight"));
				
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.close();
		}

	}
	
	public ArrayList<Local_sale_item> getLocalSaleItems(int id) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Local_sale_item> list = new ArrayList<>();

		String Query = "SELECT * FROM Local_sale_items WHERE Fish_sale_ID=?  ORDER BY  ID DESC";

		try {
			preparedStatement = connection.prepareStatement(Query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Local_sale_item item=new Local_sale_item();
				
				item.setFish_Type(resultSet.getInt("Fish_Type"));
				item.setBuying_Price(resultSet.getDouble("buying_Price"));
				item.setTotal_Weight(resultSet.getDouble("Total_Weight"));
				
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.close();
		}

	}
	
	public LocalSales getLocalSales(int id) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Local_Sales where ID = ?  ";

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			LocalSales sale= new LocalSales();
			if (resultSet.next()) {

				sale.setID(Integer.parseInt(resultSet.getString("ID")));
				sale.setDate(resultSet.getString("Date"));
				sale.setBuyerID(Integer.parseInt(resultSet.getString("BuyerID")));
				sale.setPrice(Double.parseDouble(resultSet.getString("Total_Price")));
				sale.setTotalWeight(Double.parseDouble(resultSet.getString("Total_Weight")));
			}
			System.out.println(sale.getPrice());
			return sale;

		} catch (Exception e) {
			return null;
		} finally {
			connection.close();
		}

	}

}
