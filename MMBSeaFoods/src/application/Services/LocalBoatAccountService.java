package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import application.Models.LFish_stock;
import application.Models.LocalBoatAccount;
import application.Models.Local_Fish_types;
import javafx.collections.ObservableList;

public class LocalBoatAccountService {

	Connection connection;

	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	Local_Fish_typesServices service = new Local_Fish_typesServices();

	public boolean addEntries(LocalBoatAccount entry) throws SQLException {

		connection = DBConnection.Connector();

		PreparedStatement preparedStatement3 = null;
		String insertQuery = "INSERT INTO Local_Boat_Account (Date,Reason,To_Pay,Paid,Boat_ID,purchase_ID) VALUES (?,?,?,?,?,?)";
		try {
			preparedStatement3 = connection.prepareStatement(insertQuery);
			preparedStatement3.setString(1, format1.format(new Date()));
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

	public boolean addEntriesUncleard(LocalBoatAccount entry) throws SQLException {

		connection = DBConnection.Connector();

		PreparedStatement preparedStatement3 = null;
		String insertQuery = "INSERT INTO Local_Boat_Account_UnCleared (Date,Reason,To_Pay,Paid,Boat_ID,purchase_ID) VALUES (?,?,?,?,?,?)";
		try {
			preparedStatement3 = connection.prepareStatement(insertQuery);
			preparedStatement3.setString(1, format1.format(new Date()));
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
}
