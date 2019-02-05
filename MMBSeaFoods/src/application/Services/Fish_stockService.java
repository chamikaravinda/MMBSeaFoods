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
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Fish_stock (Added_Date, Boat_ID, Harbour,NoofFishes,Total_Weight,"
				+ "fishprice,commitionprice,totalprice,Lot_ID,Status)" + 
							"VALUES (?,?,?,?,?,?,?,?,?,?)";
		
		
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
				resultSet=preparedStatement.executeUpdate();
				
				if(resultSet != 0) {
					ResultSet result=preparedStatement.getGeneratedKeys();
					if(result.next()) {
						return result.getLong(1);
					}else {
						return 0;
					}
				}
				else 
				return 0;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}finally {
			connection.close();
		}
	}
	
	public ArrayList<Fish_stock> getUnsoldStocks() throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Fish_stock where Status = 'UNSOLD' ";
		ArrayList<Fish_stock> list =new ArrayList<>();
		
		try {
			preparedStatement =connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			System.out.println(resultSet);
			while(resultSet.next()) {
				Fish_stock fishStock=new Fish_stock();
				fishStock.setID(Integer.parseInt(resultSet.getString("ID")));
				fishStock.setAdded_Date(resultSet.getString("Added_Date"));
				fishStock.setLot_ID(Integer.parseInt(resultSet.getString("Lot_ID")));
				fishStock.setTotal_Weight(Double.parseDouble(resultSet.getString("Total_Weight")));
				fishStock.setTotalBuying_price(Double.parseDouble(resultSet.getString("totalprice")));
				fishStock.setBoat_ID(Integer.parseInt(resultSet.getString("Boat_ID")));
				list.add(fishStock);
				
				
			}
			return list;
			
		}catch(Exception e) {
			return null;
		}finally {
			connection.close();
		}
		
		
	}

	public boolean sellStock(int ID) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String Updatequery = "UPDATE Fish_stock set Status=?"
						+ " where Lot_ID= '"+ID+"' ";

		try {

				preparedStatement = connection.prepareStatement(Updatequery);
				preparedStatement.setString(1, "SOLD");
				resultSet=preparedStatement.executeUpdate();
				
				if(resultSet != 0) {
					System.out.println("2nd method ok");

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
