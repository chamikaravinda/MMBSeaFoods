package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import application.Models.LFish_stock;


public class LFish_stockService {
	
	Connection connection;
	
	public long addFish_Stock(LFish_stock lstock) throws SQLException {
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;	
			String avalibility= "SELECT * FROM Local_Fish_stock where Fish_Type=?";
			preparedStatement=connection.prepareStatement(avalibility);
			ResultSet resultSet=preparedStatement.executeQuery();
			
			
			
			if(lstock.getFish_Type()==resultSet.getInt("Fish_Type")) {

				
				String UpdateExsitedType=" UPDATE Local_Fish_stock"+" SET Total_Weight=?"+"WHERE Fish_Type="+"lstock.getFish_Type()+ ";
				preparedStatement=connection.prepareStatement(UpdateExsitedType);
				preparedStatement.setDouble(1, lstock.getTotal_Weight());
				ResultSet resultSet1=preparedStatement.executeQuery();
			}
			else {
				
				String insertQuery= "INSERT INTO Local_Fish_stock (Fish_Type,Total_Weight)" + 
						"VALUES (?,?)";
				
				
				preparedStatement = connection.prepareStatement(insertQuery);
				preparedStatement.setString(1, lstock.getFishName());
				preparedStatement.setDouble(2, lstock.getTotal_Weight());
				ResultSet resultSet1=preparedStatement.executeQuery();
				
				
				
			}
		  
	
			
			
		return 0;
	}
			
			
			
			
				
			
	
	
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

	
	
}
