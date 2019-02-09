package application.Services;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Models.LocalBuyers;
public class LocalBuyerService {
	
	Connection connection;
	

	public boolean addLocalBuyer(LocalBuyers Lbuyer) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO Local_Fish_Buyers (Name, Mobile_No)" + 
							"VALUES (?,?)";
		try {
			
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, Lbuyer.getName());
			preparedStatement.setString(2, Lbuyer.getMobile_No());
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
	
	
	public ArrayList<LocalBuyers> getLocalBuyer() throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Local_Fish_Buyers";
		ArrayList<LocalBuyers> list =new ArrayList<>();
		
		try {
			preparedStatement =connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				LocalBuyers Lbuyers=new LocalBuyers();
				Lbuyers.setID(Integer.parseInt(resultSet.getString("ID")));
				Lbuyers.setMobile_No(resultSet.getString("Mobile_No"));
				Lbuyers.setName(resultSet.getString("Name"));
				list.add(Lbuyers);
				
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




