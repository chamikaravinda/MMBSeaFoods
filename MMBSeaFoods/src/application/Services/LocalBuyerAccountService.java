package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import application.Models.LFish_stock;
import application.Models.LocalSales;
import application.Models.Local_Buyers_Account;
import application.Models.Local_Fish_types;
import application.Models.Local_sale_item;
import javafx.collections.ObservableList;

public class LocalBuyerAccountService {

	Connection connection;

	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	Local_Fish_typesServices service = new Local_Fish_typesServices();

	public boolean addEntries(Local_Buyers_Account entry) throws SQLException {

		connection = DBConnection.Connector();

		PreparedStatement preparedStatement3 = null;
		String insertQuery = "INSERT INTO Local_Fish_Buyers_Account (Date,Reason,To_Pay,Paid,Buyer_ID,purchase_ID) VALUES (?,?,?,?,?,?)";
		try {
			preparedStatement3 = connection.prepareStatement(insertQuery);
			preparedStatement3.setString(1, entry.getDate());
			preparedStatement3.setString(2, entry.getReason());
			preparedStatement3.setDouble(3, entry.getTo_Pay());
			preparedStatement3.setDouble(4, entry.getPaid());
			preparedStatement3.setInt(5, entry.getBuyer_ID());
			preparedStatement3.setInt(6, entry.getPurchase_ID());
			preparedStatement3.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}

	}

	public boolean addEntriesUncleared(Local_Buyers_Account entry) throws SQLException {
		connection = DBConnection.Connector();
		PreparedStatement preparedStatement3 = null;
		String insertQuery = "INSERT INTO Local_Fish_Uncleared (Date,Reason,To_Pay,Paid,Buyer_ID,purchase_ID) VALUES (?,?,?,?,?,?)";
		try {
			preparedStatement3 = connection.prepareStatement(insertQuery);
			preparedStatement3.setString(1, entry.getDate());
			preparedStatement3.setString(2, entry.getReason());
			preparedStatement3.setDouble(3, entry.getTo_Pay());
			preparedStatement3.setDouble(4, entry.getPaid());
			preparedStatement3.setInt(5, entry.getBuyer_ID());
			preparedStatement3.setInt(6, entry.getPurchase_ID());
			preparedStatement3.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}

	}

	public long addLocalSale(LocalSales sale) throws SQLException {

		connection = DBConnection.Connector();

		PreparedStatement preparedStatement3 = null;
		int result = 0;
		String insertQuery = "INSERT INTO Local_Sales (Date,BuyerID, Total_Price,Total_Weight) VALUES (?,?,?,?)";
		try {

			preparedStatement3 = connection.prepareStatement(insertQuery);

			preparedStatement3.setString(1, sale.getDate());
			preparedStatement3.setInt(2, sale.getBuyerID());
			preparedStatement3.setDouble(3, sale.getPrice());
			preparedStatement3.setDouble(4, sale.getTotalWeight());
			result = preparedStatement3.executeUpdate();
			if (result != 0) {
				ResultSet resultSet = preparedStatement3.getGeneratedKeys();
				if (resultSet.next()) {
					return resultSet.getLong(1);
				} else {
					return 0;
				}
			} else
				return 0;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			connection.close();
		}

	}
	
	public boolean addLocalSaleItems(ObservableList<Local_sale_item> list) throws SQLException {

		connection = DBConnection.Connector();

		PreparedStatement preparedStatement3 = null;
		int result = 0;
		String insertQuery = "INSERT INTO Local_sale_items (Fish_Type,Total_Weight, buying_Price,Fish_sale_ID) VALUES (?,?,?,?)";
		try {
			for(Local_sale_item sale: list) {
			preparedStatement3 = connection.prepareStatement(insertQuery);

			preparedStatement3.setInt(1, sale.getFish_Type());
			preparedStatement3.setDouble(2, sale.getTotal_Weight());
			preparedStatement3.setDouble(3, sale.getBuying_Price());
			preparedStatement3.setInt(4, sale.getFish_sale_ID());
			result = preparedStatement3.executeUpdate();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}

	}
	
	public boolean RemoveFromLocalBuyersAccount_Uncleared(int id) {

		try {
			connection = DBConnection.Connector();

			PreparedStatement preparedStatement = null;

			String query = "DELETE FROM Local_Fish_Uncleared WHERE ID = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, id);

			if (preparedStatement.execute()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			return false;

		} catch (Exception e) {
			return false;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In setUnclearedBuyerRecievedForeign : " + e);
			}

		}

	}
	
	public boolean RemoveFromLocalBuyersAccount(int id) {

		try {
			connection = DBConnection.Connector();

			PreparedStatement preparedStatement = null;

			String query = "DELETE FROM Local_Fish_Buyers_Account WHERE ID = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, id);

			if (preparedStatement.execute()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			return false;

		} catch (Exception e) {
			return false;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In setUnclearedBuyerRecievedForeign : " + e);
			}

		}

	}
}
