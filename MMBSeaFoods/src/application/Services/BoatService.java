package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Models.Boat;
import application.Models.Vehicles;

public class BoatService {
	
	Connection connection;
	

	public boolean addBoat(Boat boat) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Boats (BoatNo, Mobile_No,Owner)" + 
							"VALUES (?,?,?)";
		try {
			
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, boat.getBoatNameorNumber());
			preparedStatement.setString(2, boat.getMobile());
			preparedStatement.setString(3, boat.getOwner());
			resultSet=preparedStatement.executeUpdate();
			if(resultSet!=0)
				return true;
			else 
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
		
	}
	
	public ArrayList<Boat> getBoat() throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Boats";
		ArrayList<Boat> list =new ArrayList<>();
		
		try {
			preparedStatement =connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Boat boat=new Boat();
				boat.setID(Integer.parseInt(resultSet.getString("ID")));
				boat.setBoatNameorNumber(resultSet.getString("BoatNo"));
				boat.setMobile(resultSet.getString("Mobile_No"));
				boat.setOwner(resultSet.getString("Owner"));
				list.add(boat);
				
			}
			return list;
			
		}catch(Exception e) {
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
		
		
	}
	
	
	public Boat getBoat(String name) throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Boats where BoatNo=?";
		Boat boat =new Boat();
		try {
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				boat.setID(Integer.parseInt(resultSet.getString("ID")));
				boat.setBoatNameorNumber(resultSet.getString("BoatNo"));
				boat.setMobile(resultSet.getString("Mobile_No"));
				boat.setOwner(resultSet.getString("Owner"));	
				
			}
			return boat;
			
		}catch(Exception e) {
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
		
		
	}
	
	public Boat getBoat(int ID) throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Boats where ID=?";
		Boat boat =new Boat();
		try {
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setInt(1, ID);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				boat.setID(Integer.parseInt(resultSet.getString("ID")));
				boat.setBoatNameorNumber(resultSet.getString("BoatNo"));
				boat.setMobile(resultSet.getString("Mobile_No"));
				boat.setOwner(resultSet.getString("Owner"));	
				
			}
			return boat;
			
		}catch(Exception e) {
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
		
		
	}
	
	public boolean UpdateBoat(Boat boat) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "UPDATE Boats set BoatNo =? , Mobile_No =? , Owner =? " + 
							" where ID = ? ";
		try {
			
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, boat.getBoatNameorNumber());
			preparedStatement.setString(2, boat.getMobile());
			preparedStatement.setString(3, boat.getOwner());
			preparedStatement.setInt(4, boat.getID());
			resultSet=preparedStatement.executeUpdate();
			if(resultSet!=0)
				return true;
			else 
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
		
	}
	
}
