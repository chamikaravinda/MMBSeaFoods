package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import application.Models.Boat_Account;
import application.Models.Boat_Account_UnCleared;
import application.Models.F_Fish_Buyers_Account;
import application.Models.F_Fish_Buyers_Account_Uncleard;
import application.Models.LocalBoatAccountUnCleared;
import application.Models.User;

public class Boat_AccountServices {

	Connection connection;

	// search and get particular Boat account
	public ArrayList<Boat_Account> getAllentries(int ID) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		Boat_Account boat_Account = new Boat_Account();

		String getAllentriesQuery = "select * from Boat_Account where ID =? ";

		ArrayList<Boat_Account> list = new ArrayList<Boat_Account>();
		try {

			preparedStatement = connection.prepareStatement(getAllentriesQuery);
			preparedStatement.setInt(1, ID);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Boat_Account boat = new Boat_Account();
				boat.setBoat_ID(resultSet.getInt("Boat_ID"));
				boat.setDate(resultSet.getString("Date"));
				boat.setPaid(resultSet.getDouble("Paid"));
				boat.setReason(resultSet.getString("Reason"));
				boat.setTo_Pay(resultSet.getDouble("To_Pay"));

				list.add(boat);

			}

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.close();
		}

	}

	// add entries
	public boolean addEntries(Boat_Account entry) {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;

		String addEntryQuery = "INSERT INTO Boat_Account(Date,Reason,To_Pay,Paid,Boat_ID,Stock_ID)"
				+ " VALUES(?,?,?,?,?,?)";

		try {

			preparedStatement = connection.prepareStatement(addEntryQuery);
			preparedStatement.setString(1, entry.getDate());
			preparedStatement.setString(2, entry.getReason());
			preparedStatement.setDouble(3, entry.getTo_Pay());
			preparedStatement.setDouble(4, entry.getPaid());
			preparedStatement.setDouble(5, entry.getBoat_ID());
			preparedStatement.setDouble(6, entry.getStock_ID());
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
			try {
				connection.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	// add uncleard entries

	public boolean addEntries_Uncleard(Boat_Account_UnCleared entry) throws SQLException {
		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		int resultSet;

		String selectQuery = "SELECT * FROM Boat_Account_UnCleared WHERE Boat_ID=?";

		preparedStatement = connection.prepareStatement(selectQuery);
		preparedStatement.setInt(1, entry.getBoat_ID());
		ResultSet result = preparedStatement.executeQuery();
		
		try {
			if (result.next()) {

				String updateQuery = "UPDATE Boat_Account_UnCleared SET To_Pay=? WHERE Boat_ID = ?";
				preparedStatement2 = connection.prepareStatement(updateQuery);
				preparedStatement2.setDouble(1, result.getDouble("To_Pay") + entry.getTo_Pay());
				preparedStatement2.setInt(2, entry.getBoat_ID());
				preparedStatement2.executeUpdate();

				return true;
			} else {

				String addEntryQuery = "INSERT INTO Boat_Account_UnCleared(To_Pay,Boat_ID)" + " VALUES(?,?)";

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

	public boolean RemoveFromBoatAccount(int id) {

		try {

			connection = DBConnection.Connector();
			PreparedStatement preparedStatement = null;

			String query = "DELETE FROM Boat_Account WHERE ID = ?";

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

	public boolean RemoveFromBoatAccountStockEntry(int id) throws SQLException {

		try {

			connection = DBConnection.Connector();
			PreparedStatement preparedStatement = null;

			String query = "DELETE FROM Boat_Account WHERE Stock_ID = ?";

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

	public boolean RemoveFromBoatAccountStockEntryUncleard(Boat_Account_UnCleared entry) {

		try {
			
			connection = DBConnection.Connector();
			PreparedStatement preparedStatement = null;
			PreparedStatement preparedStatement2 = null;
			int resultSet;

			String selectQuery = "SELECT * FROM Boat_Account_UnCleared WHERE Boat_ID=?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, entry.getBoat_ID());
			ResultSet result = preparedStatement.executeQuery();
			
			if(result.next()) {
			String query = "UPDATE Boat_Account_UnCleared SET To_Pay=? WHERE Boat_ID = ?";

			preparedStatement2 = connection.prepareStatement(query);

			preparedStatement2.setDouble(1, result.getDouble("To_Pay") - entry.getTo_Pay() );
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

}
