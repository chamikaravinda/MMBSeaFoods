package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import application.Models.Boat_Account;
import application.Models.Commition;
import application.Models.F_Fish_Buyers_Account;
import application.Models.F_Fish_Buyers_Account_Uncleard;
import application.Models.Fish_Lot;
import application.Models.LocalSales;
import application.Models.Local_Buyers_Account;
import application.Models.Locl_Buyers_Account_Uncleared;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AccountServices {
	
	Connection connection;
	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
	
	
	
	

	public ObservableList<String> getAllBoatNames() {

		ObservableList<String> boatNameList=FXCollections.observableArrayList();
			
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}		
				
			String query = "SELECT * FROM Local_Boats";			
				
			preparedStatement=connection.prepareStatement(query);
				
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {	
				
				String n=resultSet.getString("BoatNo");
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
		}finally {
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





	public ArrayList<Boat_Account> getAllBOATList(int id) {


		ArrayList<Boat_Account> boatList=new ArrayList<Boat_Account>();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = "SELECT * FROM Local_Boat_Account WHERE Boat_ID=?";
			
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(id));
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Boat_Account boat = new Boat_Account();
				
				
				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setBoat_ID(Integer.parseInt(resultSet.getString(6)));
				
				
				
				
				boatList.add(boat);
			}
			
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All BOAT List :" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In get All BOAT List : " + e);	
		}finally {
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


		String id="";

		try {

			connection=DBConnection.Connector();

			preparedStatement =connection.prepareStatement("SELECT * FROM Local_Boats WHERE BoatNo=?");
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





	public ArrayList<Boat_Account> getAllBOQListUncleared(int id) {
		
		ArrayList<Boat_Account> boatList=new ArrayList<Boat_Account>();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = "SELECT * FROM Local_Boat_Account_UnCleared WHERE Boat_ID=?";
			
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(id));
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Boat_Account boat = new Boat_Account();
				
				
				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setBoat_ID(Integer.parseInt(resultSet.getString(6)));
				
				
				
				
				boatList.add(boat);
			}
			
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All BOAT List Uncleared :" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In get All BOAT List Uncleared : " + e);	
		}finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In get All BOAT List Uncleared : " + e);
			}
			
			
		}
		
	return boatList;
	}





	public int getBoatIDByNameForeign(String name) {
		String id="";

		try {

			connection=DBConnection.Connector();

			preparedStatement =connection.prepareStatement("SELECT * FROM Boats WHERE BoatNo=?");
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
		ObservableList<String> boatNameList=FXCollections.observableArrayList();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}		
				
			String query = "SELECT * FROM Boats";			
				
			preparedStatement=connection.prepareStatement(query);
				
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {	
				
				String n=resultSet.getString("BoatNo");
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
		}finally {
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

		ArrayList<Boat_Account> boatList=new ArrayList<Boat_Account>();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = "SELECT * FROM Boat_Account WHERE Boat_ID=?";
			
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(id));
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Boat_Account boat = new Boat_Account();
				
				
				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setBoat_ID(Integer.parseInt(resultSet.getString(6)));
				
				
				
				
				boatList.add(boat);
			}
			
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All BOAT List Foreign :" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In get All BOAT List Foreign : " + e);	
		}finally {
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





	public ArrayList<Boat_Account> getAllBOQListUnclearedForeign(int id) {
		
		ArrayList<Boat_Account> boatList=new ArrayList<Boat_Account>();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = "SELECT * FROM Boat_Account_UnCleared WHERE Boat_ID=?";
			
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(id));
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Boat_Account boat = new Boat_Account();
				
				
				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setReason(resultSet.getString(3));
				boat.setTo_Pay(Double.parseDouble(resultSet.getString(4)));
				boat.setPaid(Double.parseDouble(resultSet.getString(5)));
				boat.setBoat_ID(Integer.parseInt(resultSet.getString(6)));
				
				
				
				
				boatList.add(boat);
			}
			
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All BOAT List Uncleared Foreign :" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In get All BOAT List Uncleared Foreign : " + e);	
		}finally {
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



		//===============================================================================================================================
		//===============================================================================================================================
		//===============================================================================================================================
		//===============================================================================================================================
		//===============================================================================================================================
		//===============================================================================================================================
		//===============================================================================================================================


	public ObservableList<String> getAllBuyersNames() {


		ObservableList<String> buyersNameList=FXCollections.observableArrayList();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}		
				
			String query = "SELECT * FROM Local_Fish_Buyers";			
				
			preparedStatement=connection.prepareStatement(query);
				
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {	
				
				String n=resultSet.getString("Name");
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
		}finally {
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
		String id="";

		try {

			connection=DBConnection.Connector();

			preparedStatement =connection.prepareStatement("SELECT * FROM Local_Fish_Buyers WHERE Name=?");
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


		ArrayList<Local_Buyers_Account> boatList=new ArrayList<Local_Buyers_Account>();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = "SELECT * FROM Local_Fish_Buyers_Account WHERE Buyer_ID=?";
			
			preparedStatement=connection.prepareStatement(query);
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
				
				
				
				
				boatList.add(boat);
			}
			
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Buyers List :" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In get All Buyers List : " + e);	
		}finally {
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





	public ArrayList<Locl_Buyers_Account_Uncleared> getAllBuyerListUncleared(int id) {


		ArrayList<Locl_Buyers_Account_Uncleared> boatList=new ArrayList<Locl_Buyers_Account_Uncleared>();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = "SELECT * FROM Local_Fish_Uncleared WHERE Buyer_ID=?";
			
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(id));
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Locl_Buyers_Account_Uncleared boat = new Locl_Buyers_Account_Uncleared();
				
				
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
			System.out.println("Exception at  get All Buyer List Uncleared :" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In get All Buyer List Uncleared : " + e);	
		}finally {
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


		ObservableList<String> buyersNameList=FXCollections.observableArrayList();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}		
				
			String query = "SELECT * FROM Foreign_Fish_Buyers";			
				
			preparedStatement=connection.prepareStatement(query);
				
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {	
				
				String n=resultSet.getString("Name");
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
		}finally {
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


		String id="";

		try {

			connection=DBConnection.Connector();

			preparedStatement =connection.prepareStatement("SELECT * FROM Foreign_Fish_Buyers WHERE Name=?");
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


		ArrayList<F_Fish_Buyers_Account> boatList=new ArrayList<F_Fish_Buyers_Account>();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = "SELECT * FROM F_Fish_Buyers_Account WHERE Buyer_ID=?";
			
			preparedStatement=connection.prepareStatement(query);
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
				
				
				
				
				boatList.add(boat);
			}
			
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Buyers List :" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In get All Buyers List : " + e);	
		}finally {
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


		ArrayList<F_Fish_Buyers_Account_Uncleard> boatList=new ArrayList<F_Fish_Buyers_Account_Uncleard>();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = "SELECT * FROM F_Fish_Uncleared WHERE Buyer_ID=?";
			
			preparedStatement=connection.prepareStatement(query);
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
		}finally {
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





	public ArrayList<LocalSales> getAllForienSales() throws SQLException {


		ArrayList<LocalSales> boatList=new ArrayList<LocalSales>();
		
		try {
			
			connection=DBConnection.Connector();			
			String query = "SELECT * FROM Local_Sales";
			
			preparedStatement=connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				LocalSales boat = new LocalSales();
				
				
				boat.setID(Integer.parseInt(resultSet.getString(1)));
				boat.setDate(resultSet.getString(2));
				boat.setBuyerID(Integer.parseInt(resultSet.getString(3)));
				boat.setFishType(Integer.parseInt(resultSet.getString(4)));
				boat.setPrice(Double.parseDouble(resultSet.getString(5)));
				boat.setTotalWeight(Double.parseDouble(resultSet.getString(6)));
				System.out.println(boat.getBuyerName() +boat.getDate() +boat.getPrice());
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
		}finally {	
				connection.close();

		}
	}





	public String getBuyerNameByID(int buyerID) {



		String name="";

		try {

			connection=DBConnection.Connector();

			preparedStatement =connection.prepareStatement("SELECT * FROM Local_Fish_Buyers WHERE ID=?");
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
		
		
		ArrayList<Commition> boatList=new ArrayList<Commition>();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = "SELECT * FROM Third_Party_Account";
			
			preparedStatement=connection.prepareStatement(query);
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
		}finally {
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


		ArrayList<Commition> boatList=new ArrayList<Commition>();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = "SELECT * FROM Third_Party_Acc_Uncleared";
			
			preparedStatement=connection.prepareStatement(query);
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
		}finally {
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


		
		
		ArrayList<Fish_Lot> boatList=new ArrayList<Fish_Lot>();
		
		try {
			
			connection=DBConnection.Connector();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = "SELECT * FROM Fish_Lot";
			
			preparedStatement=connection.prepareStatement(query);
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
				
				
				
				
				
				boatList.add(boat);
			}
			
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Sales List Foreign :" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In get All Sales List Foreign : " + e);	
		}finally {
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
	
	
	
	
	
	
	
	
	

}