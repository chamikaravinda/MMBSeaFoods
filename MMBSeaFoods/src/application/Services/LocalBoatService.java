package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Models.Boat;
import application.Models.LocalBoat;


public class LocalBoatService {
	
	Connection connection;
	

	public boolean addLocalBoat(LocalBoat local_boat) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Local_Boats (BoatNo, Mobile_No,Owner)" + 
							"VALUES (?,?,?)";
		try {
			
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, local_boat.getBoatNameorNumber());
			preparedStatement.setString(2, local_boat.getMobile());
			preparedStatement.setString(3, local_boat.getOwner());
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
	
	public ArrayList<LocalBoat> getLocalBoat() throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Local_Boats";
		ArrayList<LocalBoat> list =new ArrayList<>();
		
		try {
			preparedStatement =connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				LocalBoat local_boat=new LocalBoat();
				local_boat.setID(Integer.parseInt(resultSet.getString("ID")));
				local_boat.setBoatNameorNumber(resultSet.getString("BoatNo"));
				local_boat.setMobile(resultSet.getString("Mobile_No"));
				local_boat.setOwner(resultSet.getString("Owner"));
				list.add(local_boat);
				
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
	
	
	public LocalBoat getLocalBoat(String name) throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Local_Boats where BoatNo=?";
		LocalBoat local_boat =new LocalBoat();
		try {
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				local_boat.setID(Integer.parseInt(resultSet.getString("ID")));
				local_boat.setBoatNameorNumber(resultSet.getString("BoatNo"));
				local_boat.setMobile(resultSet.getString("Mobile_No"));
				local_boat.setOwner(resultSet.getString("Owner"));	
				
			}
			return local_boat;
			
		}catch(Exception e) {
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
		
		
	}
	
	public LocalBoat getLocalBoat(int ID) throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Local_Boats where ID=?";
		LocalBoat local_boat =new LocalBoat();
		try {
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setInt(1, ID);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				local_boat.setID(Integer.parseInt(resultSet.getString("ID")));
				local_boat.setBoatNameorNumber(resultSet.getString("BoatNo"));
				local_boat.setMobile(resultSet.getString("Mobile_No"));
				local_boat.setOwner(resultSet.getString("Owner"));	
				
			}
			return local_boat;
			
		}catch(Exception e) {
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
		
		
	}
	
/// update Query to Local boat------------------------------------------------------------
	public boolean UpdateLocalBoat(LocalBoat Lboat) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "UPDATE Local_Boats set BoatNo =? , Mobile_No =? , Owner =? " + 
							" where ID = ? ";
		try {
			
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, Lboat.getBoatNameorNumber());
			preparedStatement.setString(2, Lboat.getMobile());
			preparedStatement.setString(3, Lboat.getOwner());
			preparedStatement.setInt(4, Lboat.getID());
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
	
	//////End of the Local Boat Query
	
	
}
