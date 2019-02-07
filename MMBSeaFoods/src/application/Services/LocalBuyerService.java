package application.Services;



/*
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Models.LocalBuyers;
public class LocalBuyerService {
	
	Connection connection;
	

	public boolean addBuyer(Buyers buyer) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Foreign_Fish_Buyers (Name, Mobile_No)" + 
							"VALUES (?,?)";
		try {
			
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, buyer.getName());
			preparedStatement.setString(2, buyer.getMobile_No());
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
	
	
	public ArrayList<Buyers> getBoat() throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Foreign_Fish_Buyers";
		ArrayList<Buyers> list =new ArrayList<>();
		
		try {
			preparedStatement =connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Buyers buyers=new Buyers();
				buyers.setID(Integer.parseInt(resultSet.getString("ID")));
				buyers.setMobile_No(resultSet.getString("Mobile_No"));
				buyers.setName(resultSet.getString("Name"));
				list.add(buyers);
				
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
	
}




*/