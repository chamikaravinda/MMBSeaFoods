package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Models.Fish_Lot;
import application.Models.Vehicles;

public class Fish_LotServices {

	Connection connection;

	public long addLot(Fish_Lot lot) throws SQLException {
		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;
		String insertQuery = "INSERT INTO Fish_Lot (Added_Date, Lorry_Number, Ice_fee,Lorry_fee,other_fees,buying_price,Buying_Weight,display_Name)"
				+ "VALUES (?,?,?,?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, lot.getAdded_Date());
			preparedStatement.setString(2, lot.getLorry_Number());
			preparedStatement.setDouble(3, lot.getIce_fee());
			preparedStatement.setDouble(4, lot.getLorry_fee());
			preparedStatement.setDouble(5, lot.getOther_fees());
			double buying_Price = lot.getIce_fee() + lot.getLorry_fee() + lot.getOther_fees();
			preparedStatement.setDouble(6, buying_Price);
			preparedStatement.setDouble(7, 0);
			preparedStatement.setString(8, "On " + lot.getAdded_Date() + " From Lorry " + lot.getLorry_Number());
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

	public ArrayList<Fish_Lot> getUnslodLots() throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Fish_Lot where Sold_To IS NULL ORDER BY  ID DESC ";
		ArrayList<Fish_Lot> list = new ArrayList<>();

		try {
			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Fish_Lot lot = new Fish_Lot();
				lot.setID(Integer.parseInt(resultSet.getString("ID")));
				lot.setAdded_Date(resultSet.getString("Added_Date"));
				lot.setBuying_Weight(Double.parseDouble(resultSet.getString("Buying_Weight")));
				lot.setLorry_Number(resultSet.getString("Lorry_Number"));
				lot.setBuying_price(Double.parseDouble(resultSet.getString("Buying_price")));
				lot.setDisplay_Name(resultSet.getString("display_Name"));

				list.add(lot);
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

	public Fish_Lot getTheLot(String DisplayName) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Fish_Lot where display_Name= ?";
		Fish_Lot lot = new Fish_Lot();

		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, DisplayName);

		resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {

			lot.setAdded_Date(resultSet.getString("Added_Date"));
			lot.setBuying_Weight(Double.parseDouble(resultSet.getString("Buying_Weight")));
			lot.setLorry_Number(resultSet.getString("Lorry_Number"));
			lot.setBuying_price(Double.parseDouble(resultSet.getString("Buying_price")));
			lot.setDisplay_Name(resultSet.getString("display_Name"));
			lot.setID(Integer.parseInt(resultSet.getString("ID")));
		}
		connection.close();
		return lot;
	}

	public Fish_Lot getTheLot(int ID) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Fish_Lot where ID= ?";
		Fish_Lot lot = new Fish_Lot();

		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, ID);

		resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {

			lot.setAdded_Date(resultSet.getString("Added_Date"));
			lot.setBuying_Weight(Double.parseDouble(resultSet.getString("Buying_Weight")));
			lot.setLorry_Number(resultSet.getString("Lorry_Number"));
			lot.setBuying_price(Double.parseDouble(resultSet.getString("buying_price")));
			lot.setDisplay_Name(resultSet.getString("display_Name"));
			lot.setIce_fee(Double.parseDouble(resultSet.getString("Ice_fee")));
			lot.setLorry_fee(Double.parseDouble(resultSet.getString("Lorry_fee")));
			lot.setOther_fees(Double.parseDouble(resultSet.getString("other_fees")));
			lot.setBrokerFee(Double.parseDouble(resultSet.getString("brokerFee")));
			lot.setID(Integer.parseInt(resultSet.getString("ID")));
			lot.setSold_Date(resultSet.getString("Sold_Date"));
		}
		connection.close();
		return lot;
	}

	public boolean UpdateFish_Lot(Fish_Lot lot) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;
		String Updatequery = "UPDATE Fish_Lot set Buying_Weight=?,brokerFee=?,buying_price=?" + " where ID= '"
				+ lot.getID() + "' ";

		try {

			preparedStatement = connection.prepareStatement(Updatequery);
			preparedStatement.setDouble(1, lot.getBuying_Weight());
			preparedStatement.setDouble(2, lot.getBrokerFee());
			preparedStatement.setDouble(3, lot.getBuying_price());
			resultSet = preparedStatement.executeUpdate();

			if (resultSet != 0) {
				return true;
			} else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}
	}

	public boolean SellFishLot(Fish_Lot lot) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;
		String Updatequery = "UPDATE Fish_Lot set Sold_Weight=?,Sold_price=?,Sold_To=?,Sold_Date=?" + " where ID= '"
				+ lot.getID() + "' ";

		try {

			preparedStatement = connection.prepareStatement(Updatequery);
			preparedStatement.setDouble(1, lot.getSold_Weight());
			preparedStatement.setDouble(2, lot.getSold_price());
			preparedStatement.setDouble(3, lot.getSold_To());
			preparedStatement.setString(4, lot.getSold_Date());
			resultSet = preparedStatement.executeUpdate();

			if (resultSet != 0) {
				System.out.println("1st method ok");
				return true;
			} else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}
	}

	public boolean deleteLot(int id) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;

		String deleteQuery = "delete from Fish_Lot WHERE ID=? ";

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
