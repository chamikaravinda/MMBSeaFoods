package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Models.LFish_stock;
import application.Models.Local_stock_items;
import javafx.collections.ObservableList;


public class LFish_stockService {
	
	Connection connection;

	public ArrayList<LFish_stock> getUnsoldLocalStocks() throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Local_Fish_stock ";
		ArrayList<LFish_stock> list =new ArrayList<>();
		
		try {
			preparedStatement =connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				LFish_stock local_fishStock=new LFish_stock();
				
				
				local_fishStock.setID(Integer.parseInt(resultSet.getString("ID")));
				local_fishStock.setFish_Type(Integer.parseInt(resultSet.getString("Fish_Type")));//Check it if you want
				local_fishStock.setTotal_Weight(Double.parseDouble(resultSet.getString("Total_Weight")));
				
			//resultSet.getInt("Fish_Type")
				list.add(local_fishStock);
				
				
			}
			return list;
			
		}catch(Exception e) {
			return null;
		}finally {
			connection.close();
		}
		
		
	}

	public boolean newStock(ObservableList<Local_stock_items> list) throws SQLException {
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Local_Fish_stock where Fish_Type =? ";
		
		try {
		for(Local_stock_items item :list) {
			
			
				preparedStatement =connection.prepareStatement(query);
				preparedStatement.setInt(1, item.getFish_Type());
				resultSet = preparedStatement.executeQuery();
				double  currentWeight = -1;
				if(resultSet.next()) {
					
					currentWeight= resultSet.getDouble("Total_Weight"); 
				}
				
				if(currentWeight !=-1) {
					PreparedStatement preparedStatement2=null;
					String updateQuery= "UPDATE Local_Fish_stock set Total_Weight =?  where Fish_Type= ? ";
					preparedStatement2 =connection.prepareStatement(updateQuery);
					preparedStatement2.setDouble(1, item.getTotal_Weight()+resultSet.getDouble("Total_Weight"));
					preparedStatement2.setInt(2, item.getFish_Type());
					preparedStatement2.executeUpdate();
				}else {
					PreparedStatement preparedStatement3=null;
					String insertQuery= "INSERT INTO Local_Fish_stock (Fish_Type,Total_Weight) VALUES (?,?)";
					preparedStatement3 =connection.prepareStatement(insertQuery);
					preparedStatement3.setInt(1, item.getFish_Type());
					preparedStatement3.setDouble(2, item.getTotal_Weight());
					preparedStatement3.executeUpdate();
				}
			
	
			}
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
	}
	
	public boolean sellStock(ObservableList<LFish_stock> items) throws SQLException {
		
		
		
		connection=DBConnection.Connector();
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Local_Fish_stock where Fish_Type =? ";

		PreparedStatement preparedStatement2=null;
		String updateQuery= "UPDATE Local_Fish_stock set Total_Weight =?  where Fish_Type= ? ";
		try {
		for(LFish_stock item:items) {
				preparedStatement =connection.prepareStatement(query);
				preparedStatement.setInt(1, item.getFish_Type());
				resultSet = preparedStatement.executeQuery();
				
				preparedStatement2 =connection.prepareStatement(updateQuery);
				preparedStatement2.setDouble(1, resultSet.getDouble("Total_Weight")-item.getTotal_Weight());
				preparedStatement2.setInt(2, item.getFish_Type());
				preparedStatement2.executeUpdate();
		}
		return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
	}






	
	
}
