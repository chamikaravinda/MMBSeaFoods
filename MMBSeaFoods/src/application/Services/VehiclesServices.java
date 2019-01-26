package application.Services;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import application.Models.Vehicles;
import application.Models.Vehicles_Leased_Payments;

public class VehiclesServices {
	
	Connection connection;
	
	public ArrayList<Vehicles> getVehicles() throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Vehicles";
		ArrayList<Vehicles> list =new ArrayList<>();
		
		try {
			preparedStatement =connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Vehicles vehicles =new Vehicles();
				vehicles.setID(Integer.parseInt(resultSet.getString("ID")));
				vehicles.setVehicle_No(resultSet.getString("Vehicle_No"));
				vehicles.setTotal_Lease(Double.parseDouble(resultSet.getString("Total_Lease")));
				vehicles.setPaid_Amount(Double.parseDouble(resultSet.getString("Paid_Amount")));
				vehicles.setTo_Pay(Double.parseDouble(resultSet.getString("To_Pay")));
				vehicles.setPremium_Amount(Double.parseDouble(resultSet.getString("Premium_Amount")));
				String date= (resultSet.getString("Last_Payment"));
				vehicles.setLast_Payment(date);
				list.add(vehicles);
				
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
	
	
	public boolean addVehicles(Vehicles vehicle) throws SQLException {
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Vehicles (Vehicle_No, Total_Lease, Paid_Amount,To_Pay,Premium_Amount)" + 
							"VALUES (?,?,?,?,?)";
		
		
		try {
			if(!isVehicleInDB(vehicle.getVehicle_No())) {
				preparedStatement = connection.prepareStatement(insertQuery);
				preparedStatement.setString(1, vehicle.getVehicle_No());
				preparedStatement.setDouble(2, vehicle.getTotal_Lease());
				preparedStatement.setDouble(3, vehicle.getPaid_Amount());
				preparedStatement.setDouble(4, vehicle.getTo_Pay());
				preparedStatement.setDouble(5, vehicle.getPremium_Amount());
				resultSet=preparedStatement.executeUpdate();
				
				if(resultSet != 0) {
					return true;
				}
				else 
					return false;
			} 
		
			else return false;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
	}
	
	public boolean isVehicleInDB(String vNO) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet;
		String Query= "Select * From Vehicles";
		
		preparedStatement = connection.prepareStatement(Query);
		resultSet=preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			if(vNO.equalsIgnoreCase(resultSet.getString("Vehicle_No"))) {
					return true;
				}
		}
		
		return false;
	}
	
	public boolean Vehicles_Leased_Payment_Add(Vehicles_Leased_Payments payment ) throws SQLException {
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Vehicles_Leased_Payments (Vehicle_ID, Paid_Amount, Payment_Date)" + 
							"VALUES (?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, payment.getVehicle_ID());
			preparedStatement.setDouble(2, payment.getPaid_Amount());
			preparedStatement.setString(3, payment.getPayment_Date().toString());
			resultSet=preparedStatement.executeUpdate();
			
			if(resultSet != 0) {
				return true;
			}
			else 
				return false;
	
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
	}
	
	public Vehicles getVehicle(String Vno) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Vehicles where Vehicle_No =?";
		Vehicles vehicles =new Vehicles();
		int VID = 0;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, Vno);
			resultSet=preparedStatement.executeQuery();
			
			vehicles.setID(Integer.parseInt(resultSet.getString("ID")));
			vehicles.setVehicle_No(resultSet.getString("Vehicle_No"));
			vehicles.setTotal_Lease(Double.parseDouble(resultSet.getString("Total_Lease")));
			vehicles.setPaid_Amount(Double.parseDouble(resultSet.getString("Paid_Amount")));
			vehicles.setTo_Pay(Double.parseDouble(resultSet.getString("To_Pay")));
			vehicles.setPremium_Amount(Double.parseDouble(resultSet.getString("Premium_Amount")));
			String date= (resultSet.getString("Last_Payment"));
			vehicles.setLast_Payment(date);
			return  vehicles;
	}catch(Exception e) {
		e.printStackTrace();
		return null;
		}finally {
			connection.close();
		}
	}
	
	public boolean updateVehicle(Vehicles vehicle) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String Updatequery = "UPDATE Vehicles set Vehicle_No=?,Total_Lease=?,Paid_Amount=?,To_Pay=?,Last_Payment=?"
						+ " where ID= '"+vehicle.getID()+"' ";

		try {

				preparedStatement = connection.prepareStatement(Updatequery);
				preparedStatement.setString(1, vehicle.getVehicle_No());
				preparedStatement.setDouble(2, vehicle.getTotal_Lease());
				preparedStatement.setDouble(3, vehicle.getPaid_Amount());
				preparedStatement.setDouble(4, vehicle.getTo_Pay());
				preparedStatement.setString(5, vehicle.getLast_Payment());
				resultSet=preparedStatement.executeUpdate();
				
				if(resultSet != 0) {
					return true;
				}
				else 
					return false;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
	}
	
	

}
