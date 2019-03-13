package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import application.Models.LFish_stock;
import application.Models.LocalBoatAccount;
import application.Models.LocalBoatAccountUnCleared;
import application.Models.Local_Fish_types;
import javafx.collections.ObservableList;

public class LocalBoatAccountService {

	Connection connection;

	Local_Fish_typesServices service = new Local_Fish_typesServices();

	public boolean addEntries(LocalBoatAccount entry) throws SQLException {

		connection = DBConnection.Connector();

		PreparedStatement preparedStatement3 = null;
		String insertQuery = "INSERT INTO Local_Boat_Account (Date,Reason,To_Pay,Paid,Boat_ID,purchase_ID) VALUES (?,?,?,?,?,?)";
		try {
			preparedStatement3 = connection.prepareStatement(insertQuery);
			preparedStatement3.setString(1, entry.getDate());
			preparedStatement3.setString(2, entry.getReason());
			preparedStatement3.setDouble(3, entry.getTo_Pay());
			preparedStatement3.setDouble(4, entry.getPaid());
			preparedStatement3.setInt(5, entry.getBoat_ID());
			preparedStatement3.setInt(6, entry.getPurchase_ID());
			preparedStatement3.executeUpdate();

			return true;
		} catch (Exception e) {
			return false;
		} finally {
			connection.close();
		}

	}

	public boolean addEntriesUncleard(LocalBoatAccountUnCleared entry) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		int resultSet;

		String selectQuery = "SELECT * FROM Local_Boat_Account_UnCleared WHERE Boat_ID=?";

		preparedStatement = connection.prepareStatement(selectQuery);
		preparedStatement.setInt(1, entry.getBoat_ID());
		ResultSet result = preparedStatement.executeQuery();

		try {
			if (result.next()) {

				String updateQuery = "UPDATE Local_Boat_Account_UnCleared SET To_Pay=? WHERE Boat_ID = ?";
				preparedStatement2 = connection.prepareStatement(updateQuery);
				preparedStatement2.setDouble(1, result.getDouble("To_Pay") + entry.getTo_Pay());
				preparedStatement2.setInt(2, entry.getBoat_ID());
				preparedStatement2.executeUpdate();

				return true;
			} else {

				String addEntryQuery = "INSERT INTO Local_Boat_Account_UnCleared(To_Pay,Boat_ID)" + " VALUES(?,?)";

				preparedStatement2 = connection.prepareStatement(addEntryQuery);

				preparedStatement2.setDouble(1, entry.getTo_Pay());
				preparedStatement2.setDouble(2, entry.getBoat_ID());
				resultSet = preparedStatement2.executeUpdate();

				if (resultSet != 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}
	}

	public boolean RemoveFromBoatAccount_Unclear(int id) {

		try {

			connection = DBConnection.Connector();
			PreparedStatement preparedStatement = null;
			String query = "DELETE FROM Local_Boat_Account_UnCleared WHERE ID = ?";

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

	public boolean RemoveFromBoatAccount(int id) {

		try {

			connection = DBConnection.Connector();
			PreparedStatement preparedStatement = null;
			String query = "DELETE FROM Local_Boat_Account WHERE ID = ?";

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

	public boolean RemovePurchaseFromBoatAccount_Unclear(LocalBoatAccountUnCleared entry) throws SQLException {
		try {

			connection = DBConnection.Connector();
			PreparedStatement preparedStatement = null;
			PreparedStatement preparedStatement2 = null;
			int resultSet;

			String selectQuery = "SELECT * FROM Local_Boat_Account_UnCleared WHERE Boat_ID=?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, entry.getBoat_ID());
			ResultSet result = preparedStatement.executeQuery();

			if (result.next()) {
				String query = "UPDATE Local_Boat_Account_UnCleared SET To_Pay=? WHERE Boat_ID = ?";

				preparedStatement2 = connection.prepareStatement(query);

				preparedStatement2.setDouble(1, result.getDouble("To_Pay") - entry.getTo_Pay());
				preparedStatement2.setInt(2, entry.getBoat_ID());
				preparedStatement2.execute();
				return true;
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In setUnclearedBuyerRecievedForeign : " + e);
			}

		}

	}

	public boolean RemovePurchaseFromBoatAccount(int id) throws SQLException {

		try {

			connection = DBConnection.Connector();
			PreparedStatement preparedStatement = null;
			String query = "DELETE FROM Local_Boat_Account WHERE purchase_ID = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, id);

			preparedStatement.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}

	}

}
