package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import application.Models.Boat_Account;
import application.Models.Boat_Account_UnCleared;
import application.Models.Commition;
import application.Models.F_Fish_Buyers_Account;
import application.Models.F_Fish_Buyers_Account_Uncleard;
import application.Models.Fish_Lot;
import application.Models.LocalBoatAccount;
import application.Models.LocalBoatAccountUnCleared;
import application.Models.LocalSales;
import application.Models.Local_Buyers_Account;
import application.Models.Locl_Buyers_Account_Uncleared;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AccountServices {

	Connection connection;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	public ObservableList<String> getAllBoatNames() {

		ObservableList<String> boatNameList = FXCollections.observableArrayList();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Local_Boats";

			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String n = resultSet.getString("BoatNo");
				boatNameList.add(n);
				System.out.println(n);

			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Get Boat Names :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Get Boat Names : " + e);
			System.out.println("Exception In at  Get Boat Names :" + e.getLocalizedMessage());
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get Boat Names : " + e);
			}

		}

		return boatNameList;
	}

	public ArrayList<LocalBoatAccount> getAllBOATList(int id) {

		ArrayList<LocalBoatAccount> boatList = new ArrayList<LocalBoatAccount>();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Local_Boat_Account WHERE Boat_ID=? ORDER BY ID DESC";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(id));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				LocalBoatAccount boat = new LocalBoatAccount();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setBoat_ID(Integer.parseInt(resultSet.getString(6)));
				boat.setPurchase_ID(resultSet.getInt("purchase_ID"));
				boatList.add(boat);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All BOAT List :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All BOAT List : " + e);
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All BOAT List : " + e);
			}

		}

		return boatList;

	}

	public int getBoatIDByName(String name) {

		String id = "";

		try {

			connection = DBConnection.Connector();

			preparedStatement = connection.prepareStatement("SELECT * FROM Local_Boats WHERE BoatNo=?");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getString(1);
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return Integer.parseInt(id);
	}

	public ArrayList<LocalBoatAccountUnCleared> getAllBoatAccountUncleared() {

		ArrayList<LocalBoatAccountUnCleared> boatList = new ArrayList<LocalBoatAccountUnCleared>();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Local_Boat_Account_UnCleared ";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				LocalBoatAccountUnCleared boat = new LocalBoatAccountUnCleared();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setBoat_ID(Integer.parseInt(resultSet.getString(6)));
				boat.setPurchase_ID(Integer.parseInt(resultSet.getString(7)));

				boatList.add(boat);
			}
			return boatList;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All BOAT List Uncleared :" + e.getLocalizedMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All BOAT List Uncleared : " + e);
			}

		}
	}

	public int getBoatIDByNameForeign(String name) {
		String id = "";

		try {

			connection = DBConnection.Connector();

			preparedStatement = connection.prepareStatement("SELECT * FROM Boats WHERE BoatNo=?");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getString(1);
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return Integer.parseInt(id);
	}

	public ObservableList<String> getAllBoatNamesForeign() {
		ObservableList<String> boatNameList = FXCollections.observableArrayList();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Boats";

			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String n = resultSet.getString("BoatNo");
				boatNameList.add(n);
				System.out.println(n);

			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Get Boat Names :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Get Boat Names : " + e);
			System.out.println("Exception In at  Get Boat Names :" + e.getLocalizedMessage());
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get Boat Names : " + e);
			}

		}

		return boatNameList;
	}

	public ArrayList<Boat_Account> getAllBOATListForeign(int id) {

		ArrayList<Boat_Account> boatList = new ArrayList<Boat_Account>();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Boat_Account WHERE Boat_ID=? ORDER BY ID DESC";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(id));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Boat_Account boat = new Boat_Account();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setStock_ID(Long.parseLong(resultSet.getString(6)));
				boat.setBoat_ID(Integer.parseInt(resultSet.getString(7)));

				boatList.add(boat);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All BOAT List Foreign :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All BOAT List Foreign : " + e);
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All BOAT List Foreign : " + e);
			}

		}

		return boatList;
	}

	public ArrayList<Boat_Account_UnCleared> getAllBOQListUnclearedForeign(int id) {

		ArrayList<Boat_Account_UnCleared> boatList = new ArrayList<Boat_Account_UnCleared>();

		try {

			connection = DBConnection.Connector();

			String query = "SELECT * FROM Boat_Account_UnCleared  WHERE Boat_ID=? ORDER BY ID ORDER BY ID DESC";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Boat_Account_UnCleared boat = new Boat_Account_UnCleared();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setStock_ID(Long.parseLong(resultSet.getString(6)));
				boat.setBoat_ID(Integer.parseInt(resultSet.getString(7)));

				boatList.add(boat);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All BOAT List Uncleared Foreign :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All BOAT List Uncleared Foreign : " + e);
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All BOAT List Uncleared Foreign : " + e);
			}

		}

		return boatList;
	}

	public ArrayList<Boat_Account_UnCleared> getAllUnclearedpayments() {

		ArrayList<Boat_Account_UnCleared> boatList = new ArrayList<Boat_Account_UnCleared>();

		try {

			connection = DBConnection.Connector();

			String query = "SELECT * FROM Boat_Account_UnCleared ORDER BY ID DESC";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Boat_Account_UnCleared boat = new Boat_Account_UnCleared();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setStock_ID(Long.parseLong(resultSet.getString(6)));
				boat.setBoat_ID(Integer.parseInt(resultSet.getString(7)));

				boatList.add(boat);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All BOAT List Uncleared Foreign :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All BOAT List Uncleared Foreign : " + e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All BOAT List Uncleared Foreign : " + e);
			}

		}

		return boatList;
	}

	// ===============================================================================================================================
	// ===============================================================================================================================
	// ===============================================================================================================================
	// ===============================================================================================================================
	// ===============================================================================================================================
	// ===============================================================================================================================
	// ===============================================================================================================================

	public ObservableList<String> getAllBuyersNames() {

		ObservableList<String> buyersNameList = FXCollections.observableArrayList();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Local_Fish_Buyers";

			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String n = resultSet.getString("Name");
				buyersNameList.add(n);
				System.out.println(n);

			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Get Buyers Names :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Get Buyers Names : " + e);
			System.out.println("Exception In at  Get Buyers Names :" + e.getLocalizedMessage());
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get Buyers Names : " + e);
			}

		}

		return buyersNameList;

	}

	public int getBuyerIDByName(String name) {
		String id = "";

		try {

			connection = DBConnection.Connector();

			preparedStatement = connection.prepareStatement("SELECT * FROM Local_Fish_Buyers WHERE Name=?");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getString(1);
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return Integer.parseInt(id);
	}

	public ArrayList<Local_Buyers_Account> getAllBuyersList(int id) {

		ArrayList<Local_Buyers_Account> boatList = new ArrayList<Local_Buyers_Account>();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Local_Fish_Buyers_Account WHERE Buyer_ID=? ORDER BY ID DESC";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(id));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Local_Buyers_Account boat = new Local_Buyers_Account();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setBuyer_ID(Integer.parseInt(resultSet.getString(6)));
				boat.setPurchase_ID(Integer.parseInt(resultSet.getString(7)));
				boatList.add(boat);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Buyers List :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All Buyers List : " + e);
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All Buyers List : " + e);
			}

		}

		return boatList;
	}

	public ArrayList<Locl_Buyers_Account_Uncleared> getAllBuyerListUncleared() {

		ArrayList<Locl_Buyers_Account_Uncleared> boatList = new ArrayList<Locl_Buyers_Account_Uncleared>();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Local_Fish_Uncleared  ORDER BY ID DESC";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Locl_Buyers_Account_Uncleared boat = new Locl_Buyers_Account_Uncleared();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setBuyer_ID(Integer.parseInt(resultSet.getString(6)));
				boat.setPurchase_ID(Integer.parseInt(resultSet.getString(7)));
				boatList.add(boat);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Buyer List Uncleared :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All Buyer List Uncleared : " + e);
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All Buyer List Uncleared : " + e);
			}

		}

		return boatList;

	}

	public ObservableList<String> getAllBuyersNamesForeign() {

		ObservableList<String> buyersNameList = FXCollections.observableArrayList();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Foreign_Fish_Buyers";

			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String n = resultSet.getString("Name");
				buyersNameList.add(n);
				System.out.println(n);

			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Get Buyers Names Foreign :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Get Buyers Names Foreign : " + e);
			System.out.println("Exception In at  Get Buyers Names Foreign :" + e.getLocalizedMessage());
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get Buyers Names Foreign : " + e);
			}

		}

		return buyersNameList;

	}

	public int getBuyerIDByNameForeign(String name) {

		String id = "";

		try {

			connection = DBConnection.Connector();

			preparedStatement = connection.prepareStatement("SELECT * FROM Foreign_Fish_Buyers WHERE Name=?");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getString(1);
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return Integer.parseInt(id);

	}

	public ArrayList<F_Fish_Buyers_Account> getAllBuyersListForeign(int id) {

		ArrayList<F_Fish_Buyers_Account> boatList = new ArrayList<F_Fish_Buyers_Account>();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM F_Fish_Buyers_Account WHERE Buyer_ID=?";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(id));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				F_Fish_Buyers_Account boat = new F_Fish_Buyers_Account();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setBuyer_ID(Integer.parseInt(resultSet.getString(6)));
				boat.setLot_ID(Integer.parseInt(resultSet.getString(7)));

				boatList.add(boat);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Buyers List :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All Buyers List : " + e);
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All Buyers List : " + e);
			}

		}

		return boatList;

	}

	public ArrayList<F_Fish_Buyers_Account_Uncleard> getAllBuyerListUnclearedForeign(int id) {

		ArrayList<F_Fish_Buyers_Account_Uncleard> boatList = new ArrayList<F_Fish_Buyers_Account_Uncleard>();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM F_Fish_Uncleared WHERE Buyer_ID=?";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(id));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				F_Fish_Buyers_Account_Uncleard boat = new F_Fish_Buyers_Account_Uncleard();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setBuyer_ID(Integer.parseInt(resultSet.getString(6)));

				boatList.add(boat);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Buyer List Uncleared Foreign :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All Buyer List Uncleared Foreign : " + e);
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All Buyer List Uncleared Foreign : " + e);
			}

		}

		return boatList;

	}

	public ArrayList<F_Fish_Buyers_Account_Uncleard> getAllForiegnBuyerAccountUnclear() {

		ArrayList<F_Fish_Buyers_Account_Uncleard> boatList = new ArrayList<F_Fish_Buyers_Account_Uncleard>();

		try {

			connection = DBConnection.Connector();

			String query = "SELECT * FROM F_Fish_Uncleared ORDER BY ID DESC";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				F_Fish_Buyers_Account_Uncleard boat = new F_Fish_Buyers_Account_Uncleard();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setBuyer_ID(Integer.parseInt(resultSet.getString(6)));
				boat.setLot_ID(Integer.parseInt(resultSet.getString(7)));

				boatList.add(boat);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();

		} catch (Exception e) {

			System.out.println("Exception In get All Buyer List Uncleared Foreign : " + e);
			e.printStackTrace();
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All Buyer List Uncleared Foreign : " + e);
			}

		}

		return boatList;

	}

	public ArrayList<LocalSales> getAllLocalSales() throws SQLException {

		ArrayList<LocalSales> boatList = new ArrayList<LocalSales>();

		try {

			connection = DBConnection.Connector();
			String query = "SELECT * FROM Local_Sales ORDER BY ID DESC";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				LocalSales boat = new LocalSales();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setBuyerID(Integer.parseInt(resultSet.getString(3)));
				boat.setPrice(Double.parseDouble(resultSet.getString(4)));
				boat.setTotalWeight(Double.parseDouble(resultSet.getString(5)));
				boatList.add(boat);
			}

			return boatList;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Sales List :" + e.getLocalizedMessage());
			return null;

		} catch (Exception e) {
			System.out.println("Exception In get All Sales List : " + e);
			return null;
		} finally {
			connection.close();

		}
	}

	public String getBuyerNameByID(int buyerID) {

		String name = "";

		try {

			connection = DBConnection.Connector();

			preparedStatement = connection.prepareStatement("SELECT * FROM Local_Fish_Buyers WHERE ID=?");
			preparedStatement.setString(1, String.valueOf(buyerID));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				name = resultSet.getString(2);
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return name;

	}

	public ArrayList<Commition> getAllCommitionList() {

		ArrayList<Commition> boatList = new ArrayList<Commition>();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Third_Party_Account ORDER BY ID DESC";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Commition boat = new Commition();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));

				boatList.add(boat);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Commition List :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All Commition List : " + e);
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All Commition List : " + e);
			}

		}

		return boatList;
	}

	public ArrayList<Commition> getAllCommitionListUncleared() {

		ArrayList<Commition> boatList = new ArrayList<Commition>();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Third_Party_Acc_Uncleared  ORDER BY ID DESC";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Commition boat = new Commition();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));

				boatList.add(boat);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Commition List Uncleared :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All Commition List Uncleared  : " + e);
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All Commition List Uncleared : " + e);
			}

		}

		return boatList;

	}

	public ArrayList<Fish_Lot> getAllSaleListForeign() {

		ArrayList<Fish_Lot> boatList = new ArrayList<Fish_Lot>();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Fish_Lot ";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Fish_Lot boat = new Fish_Lot();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setAdded_Date(resultSet.getString(2));
				boat.setLorry_Number(resultSet.getString(3));
				boat.setBuying_Weight(Double.parseDouble(resultSet.getString(4)));
				boat.setIce_fee(Double.parseDouble(resultSet.getString(5)));
				boat.setLorry_fee(Double.parseDouble(resultSet.getString(6)));

				boat.setOther_fees(Double.parseDouble(resultSet.getString(7)));
				boat.setBrokerFee(Double.parseDouble(resultSet.getString(8)));
				boat.setBuying_price(Double.parseDouble(resultSet.getString(9)));

				boat.setSold_Weight(Double.parseDouble(resultSet.getString(10)));
				boat.setSold_price(Double.parseDouble(resultSet.getString(11)));
				boat.setDisplay_Name(resultSet.getString(12));
				boat.setSold_To(Integer.parseInt(resultSet.getString(13)));
				boat.setSold_Date(resultSet.getString(14));
				boatList.add(boat);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Sales List Foreign :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All Sales List Foreign : " + e);
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All Sales List Foreign : " + e);
			}

		}

		return boatList;
	}

	public ArrayList<Fish_Lot> getSoldLots() {

		ArrayList<Fish_Lot> boatList = new ArrayList<Fish_Lot>();

		try {

			connection = DBConnection.Connector();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "SELECT * FROM Fish_Lot WHERE Sold_To IS NOT NULL ORDER BY ID DESC";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Fish_Lot boat = new Fish_Lot();

				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setAdded_Date(resultSet.getString(2));
				boat.setLorry_Number(resultSet.getString(3));
				boat.setBuying_Weight(Double.parseDouble(resultSet.getString(4)));
				boat.setIce_fee(Double.parseDouble(resultSet.getString(5)));
				boat.setLorry_fee(Double.parseDouble(resultSet.getString(6)));

				boat.setOther_fees(Double.parseDouble(resultSet.getString(7)));
				boat.setBrokerFee(Double.parseDouble(resultSet.getString(8)));
				boat.setBuying_price(Double.parseDouble(resultSet.getString(9)));

				boat.setSold_Weight(Double.parseDouble(resultSet.getString(10)));
				boat.setSold_price(Double.parseDouble(resultSet.getString(11)));
				boat.setDisplay_Name(resultSet.getString(12));
				boat.setSold_To(Integer.parseInt(resultSet.getString(13)));
				boat.setSold_Date(resultSet.getString(14));
				boatList.add(boat);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Sales List Foreign :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All Sales List Foreign : " + e);
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All Sales List Foreign : " + e);
			}

		}

		return boatList;
	}

	public void AddNewPaid(Boat_Account boat) {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;

		String addEntryQuery = "INSERT INTO Local_Boat_Account(Date,Reason,To_Pay,Paid,Boat_ID)" + " VALUES(?,?,?,?,?)";

		try {

			preparedStatement = connection.prepareStatement(addEntryQuery);
			preparedStatement.setString(1, boat.getDate());
			preparedStatement.setString(2, boat.getReason());
			preparedStatement.setDouble(3, boat.getTo_Pay());
			preparedStatement.setDouble(4, boat.getPaid());
			preparedStatement.setDouble(5, boat.getBoat_ID());

			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void setUncleared(int id) {

		String i = String.valueOf(id);

		try {

			connection = DBConnection.Connector();
			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "DELETE FROM Local_Boat_Account_UnCleared WHERE Boat_ID = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, i);

			preparedStatement.execute();

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at setUncleared :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In setUncleared  : " + e);
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In setUncleared : " + e);
			}

		}

	}

	public void AddNewPaidForeign(Boat_Account boat) {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;

		String addEntryQuery = "INSERT INTO Boat_Account(Date,Reason,To_Pay,Paid,Boat_ID)" + " VALUES(?,?,?,?,?)";

		try {

			preparedStatement = connection.prepareStatement(addEntryQuery);
			preparedStatement.setString(1, boat.getDate());
			preparedStatement.setString(2, boat.getReason());
			preparedStatement.setDouble(3, boat.getTo_Pay());
			preparedStatement.setDouble(4, boat.getPaid());
			System.out.println("ooooooooooooooo" + boat.getPaid());
			preparedStatement.setDouble(5, boat.getBoat_ID());

			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void setUnclearedForeign(int id) {

		String i = String.valueOf(id);

		try {

			connection = DBConnection.Connector();
			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "DELETE FROM Boat_Account_UnCleared WHERE Boat_ID = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, i);

			preparedStatement.execute();

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at setUncleared :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In setUncleared  : " + e);
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In setUncleared : " + e);
			}

		}

	}

	public void AddNewRecievedForeign(F_Fish_Buyers_Account_Uncleard boat) {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;

		String addEntryQuery = "INSERT INTO F_Fish_Buyers_Account(Date,Reason,To_Pay,Paid,Buyer_ID)"
				+ " VALUES(?,?,?,?,?)";

		try {

			preparedStatement = connection.prepareStatement(addEntryQuery);
			preparedStatement.setString(1, boat.getDate());
			preparedStatement.setString(2, boat.getReason());
			preparedStatement.setDouble(3, boat.getTo_Pay());
			preparedStatement.setDouble(4, boat.getPaid());
			preparedStatement.setDouble(5, boat.getBuyer_ID());

			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public boolean AddRecievedForeignBuyerPayment(F_Fish_Buyers_Account boat) {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;

		String addEntryQuery = "INSERT INTO F_Fish_Buyers_Account(Date,Reason,To_Pay,Paid,Buyer_ID,Lot_ID)"
				+ " VALUES(?,?,?,?,?,?)";

		try {

			preparedStatement = connection.prepareStatement(addEntryQuery);
			preparedStatement.setString(1, boat.getDate());
			preparedStatement.setString(2, boat.getReason());
			preparedStatement.setDouble(3, boat.getTo_Pay());
			preparedStatement.setDouble(4, boat.getPaid());
			preparedStatement.setDouble(5, boat.getBuyer_ID());
			preparedStatement.setInt(6, boat.getLot_ID());

			if (preparedStatement.execute()) {

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public boolean AddRecievedForeignBuyerPayment_Uncleard(F_Fish_Buyers_Account_Uncleard boat) {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;

		String addEntryQuery = "INSERT INTO F_Fish_Uncleared(Date,Reason,To_Pay,Paid,Buyer_ID,Lot_ID)"
				+ " VALUES(?,?,?,?,?,?)";

		try {

			preparedStatement = connection.prepareStatement(addEntryQuery);
			preparedStatement.setString(1, boat.getDate());
			preparedStatement.setString(2, boat.getReason());
			preparedStatement.setDouble(3, boat.getTo_Pay());
			preparedStatement.setDouble(4, boat.getPaid());
			preparedStatement.setDouble(5, boat.getBuyer_ID());
			preparedStatement.setInt(6, boat.getLot_ID());

			int result = preparedStatement.executeUpdate();
			if (result != 0) {

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public boolean removeEntryForiegnFishBuyerAccount(int ID) {
		try {

			connection = DBConnection.Connector();

			String query = "DELETE FROM F_Fish_Uncleared WHERE ID = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, ID);

			if (preparedStatement.execute()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at setUnclearedBuyerRecievedForeign :" + e.getLocalizedMessage());
			return false;
		} catch (Exception e) {
			System.out.println("Exception In setUnclearedBuyerRecievedForeign  : " + e);
			return false;
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In setUnclearedBuyerRecievedForeign : " + e);
			}

		}

	}

	public boolean removeForiegnFishBuyer(int ID) {
		try {

			connection = DBConnection.Connector();
			System.out.println("ID recived is : " + ID);
			String query = "DELETE FROM F_Fish_Buyers_Account WHERE ID = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, ID);

			if (preparedStatement.execute()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In setUnclearedBuyerRecievedForeign : " + e);
			}

		}

	}

	public void setUnclearedBuyerRecievedForeign(int buyer_ID) {

		String i = String.valueOf(buyer_ID);

		try {

			connection = DBConnection.Connector();
			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "DELETE FROM F_Fish_Uncleared WHERE Buyer_ID = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, i);

			preparedStatement.execute();

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at setUnclearedBuyerRecievedForeign :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In setUnclearedBuyerRecievedForeign  : " + e);
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In setUnclearedBuyerRecievedForeign : " + e);
			}

		}

	}

	public void AddNewRecieved(Locl_Buyers_Account_Uncleared boat) {

		connection = DBConnection.Connector();
		PreparedStatement preparedStatement = null;

		String addEntryQuery = "INSERT INTO Local_Fish_Buyers_Account(Date,Reason,To_Pay,Paid,Buyer_ID)"
				+ " VALUES(?,?,?,?,?)";

		try {

			preparedStatement = connection.prepareStatement(addEntryQuery);
			preparedStatement.setString(1, boat.getDate());
			preparedStatement.setString(2, boat.getReason());
			preparedStatement.setDouble(3, boat.getTo_Pay());
			preparedStatement.setDouble(4, boat.getPaid());
			preparedStatement.setDouble(5, boat.getBuyer_ID());

			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void setUnclearedBuyerRecieved(int buyer_ID) {

		String i = String.valueOf(buyer_ID);

		try {

			connection = DBConnection.Connector();
			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = "DELETE FROM Local_Fish_Uncleared WHERE Buyer_ID = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, i);

			preparedStatement.execute();

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at setUnclearedBuyerRecievedForeign :" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In setUnclearedBuyerRecievedForeign  : " + e);
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In setUnclearedBuyerRecievedForeign : " + e);
			}

		}

	}

	public boolean RemoveFromBoatAccount_Unclear(int id) {

		try {

			connection = DBConnection.Connector();

			String query = "DELETE FROM Boat_Account_UnCleared WHERE ID = ?";

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
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In setUnclearedBuyerRecievedForeign : " + e);
			}

		}

	}

	public boolean removeCommisionAccountSelected(ArrayList<Commition> list) {
		try {

			connection = DBConnection.Connector();

			String query = "DELETE FROM Third_Party_Acc_Uncleared WHERE ID = ?";

			preparedStatement = connection.prepareStatement(query);
			for (Commition entry : list) {
				preparedStatement.setInt(1, entry.getID());
				preparedStatement.execute();
			}
			return true;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at setUnclearedBuyerRecievedForeign :" + e.getLocalizedMessage());
			return false;
		} catch (Exception e) {
			System.out.println("Exception In setUnclearedBuyerRecievedForeign  : " + e);
			return false;
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In setUnclearedBuyerRecievedForeign : " + e);
			}

		}

	}

}