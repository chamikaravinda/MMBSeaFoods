package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Models.Fish_stock;
import application.Models.Vehicles;

public class Fish_stockService {

	Connection connection;

	public long addFish_Stock(Fish_stock stock) throws SQLException {
		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;
		String insertQuery = "INSERT INTO Fish_stock (Added_Date, Boat_ID, Harbour,NoofFishes,Total_Weight,"
				+ "fishprice,commitionprice,totalprice,Lot_ID,Status) VALUES (?,?,?,?,?,?,?,?,?,?)";

		try {

			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, stock.getAdded_Date());
			preparedStatement.setInt(2, stock.getBoat_ID());
			preparedStatement.setString(3, stock.getHarbour());
			preparedStatement.setInt(4, stock.getNoofFishes());
			preparedStatement.setDouble(5, stock.getTotal_Weight());
			preparedStatement.setDouble(6, stock.getFishprice());
			preparedStatement.setDouble(7, stock.getCommition_price());
			preparedStatement.setDouble(8, stock.getTotalBuying_price());
			preparedStatement.setInt(9, stock.getLot_ID());
			preparedStatement.setString(10, "UNSOLD");
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

	public ArrayList<Fish_stock> getUnsoldStocks() throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Fish_stock where Status = 'UNSOLD' ORDER BY  ID DESC";
		ArrayList<Fish_stock> list = new ArrayList<>();

		try {
			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			System.out.println(resultSet);
			while (resultSet.next()) {
				Fish_stock fishStock = new Fish_stock();
				fishStock.setID(Integer.parseInt(resultSet.getString("ID")));
				fishStock.setAdded_Date(resultSet.getString("Added_Date"));
				fishStock.setLot_ID(Integer.parseInt(resultSet.getString("Lot_ID")));
				fishStock.setTotal_Weight(Double.parseDouble(resultSet.getString("Total_Weight")));
				fishStock.setTotalBuying_price(Double.parseDouble(resultSet.getString("totalprice")));
				fishStock.setBoat_ID(Integer.parseInt(resultSet.getString("Boat_ID")));
				fishStock.setCommition_price(resultSet.getDouble("commitionprice"));
				fishStock.setHarbour("Harbour");
				fishStock.setNoofFishes(resultSet.getInt("NoofFishes"));
				fishStock.setFishprice(resultSet.getDouble("fishprice"));
				list.add(fishStock);

			}
			return list;

		} catch (Exception e) {
			return null;
		} finally {
			connection.close();
		}

	}

	public boolean sellStock(int ID) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;
		String Updatequery = "UPDATE Fish_stock set Status=?" + " where Lot_ID= '" + ID + "' ";

		try {

			preparedStatement = connection.prepareStatement(Updatequery);
			preparedStatement.setString(1, "SOLD");
			resultSet = preparedStatement.executeUpdate();

			if (resultSet != 0) {
				System.out.println("2nd method ok");

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

	public ArrayList<Fish_stock> getLotStocks(int id) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Fish_stock where Lot_ID = ? ORDER BY  ID DESC";
		ArrayList<Fish_stock> list = new ArrayList<>();

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			System.out.println(resultSet);
			while (resultSet.next()) {
				Fish_stock fishStock = new Fish_stock();
				fishStock.setID(Integer.parseInt(resultSet.getString("ID")));
				fishStock.setAdded_Date(resultSet.getString("Added_Date"));
				fishStock.setLot_ID(Integer.parseInt(resultSet.getString("Lot_ID")));
				fishStock.setTotal_Weight(Double.parseDouble(resultSet.getString("Total_Weight")));
				fishStock.setTotalBuying_price(Double.parseDouble(resultSet.getString("totalprice")));
				fishStock.setBoat_ID(Integer.parseInt(resultSet.getString("Boat_ID")));
				fishStock.setFishprice(Double.parseDouble(resultSet.getString("fishprice")));
				list.add(fishStock);

			}
			return list;

		} catch (Exception e) {
			return null;
		} finally {
			connection.close();
		}

	}

	public Fish_stock getStocks(int id) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Fish_stock where ID = ?  ";

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			Fish_stock fishStock = new Fish_stock();
			if (resultSet.next()) {

				fishStock.setID(Integer.parseInt(resultSet.getString("ID")));
				fishStock.setAdded_Date(resultSet.getString("Added_Date"));
				fishStock.setLot_ID(Integer.parseInt(resultSet.getString("Lot_ID")));
				fishStock.setTotal_Weight(Double.parseDouble(resultSet.getString("Total_Weight")));
				fishStock.setTotalBuying_price(Double.parseDouble(resultSet.getString("totalprice")));
				fishStock.setBoat_ID(Integer.parseInt(resultSet.getString("Boat_ID")));
				fishStock.setHarbour(resultSet.getString("Harbour"));
				fishStock.setNoofFishes(Integer.parseInt(resultSet.getString("NoofFishes")));
				fishStock.setFishprice(Double.parseDouble(resultSet.getString("fishprice")));
				fishStock.setCommition_price(Double.parseDouble(resultSet.getString("commitionprice")));

			}
			return fishStock;

		} catch (Exception e) {
			return null;
		} finally {
			connection.close();
		}

	}
	
	public boolean deleteStock(int id) throws SQLException {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;
		int resultSet;

		String deleteQuery = "delete from Fish_stock where ID = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {

			connection.close();
		}

		return true;
	}
}
