package application.Services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import application.Models.LocalBuyers;




public class LocalBuyerService{
	
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
	
	
	public LocalBuyers getLocalBoat(int ID) throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		String query= "select * from Local_Fish_Buyers where ID=?";
		LocalBuyers local_buyer =new LocalBuyers();
		try {
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setInt(1, ID);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				local_buyer.setID(Integer.parseInt(resultSet.getString("ID")));
				local_buyer.setName(resultSet.getString("Name"));
				local_buyer.setMobile_No(resultSet.getString("Mobile_No"));
				
			}
			return local_buyer;
			
		}catch(Exception e) {
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
		
	}
	
	public LocalBuyers getLocalBoat(String name) throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		String query= "select * from Local_Fish_Buyers where Name=?";
		LocalBuyers local_buyer =new LocalBuyers();
		try {
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				local_buyer.setID(Integer.parseInt(resultSet.getString("ID")));
				local_buyer.setName(resultSet.getString("Name"));
				local_buyer.setMobile_No(resultSet.getString("Mobile_No"));
				
			}
			return local_buyer;
			
		}catch(Exception e) {
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
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
			e.printStackTrace();
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
		
		
	}
	
	public LocalBuyers getLocalBuyer(int id) throws SQLException{
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query= "select * from Local_Fish_Buyers WHERE ID=?";
		ArrayList<LocalBuyers> list =new ArrayList<>();
		
		try {
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			LocalBuyers Lbuyers=new LocalBuyers();
			if(resultSet.next()) {
				
				Lbuyers.setID(Integer.parseInt(resultSet.getString("ID")));
				Lbuyers.setMobile_No(resultSet.getString("Mobile_No"));
				Lbuyers.setName(resultSet.getString("Name"));				
				return Lbuyers;
			}
			return null;
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			preparedStatement.close();
			resultSet.close();
			connection.close();
		}
		
		
	}
	
	
	
	/// update Query to Local boat------------------------------------------------------------
		public boolean UpdateLocalBuyer(LocalBuyers LBuyer) throws SQLException {
			
			connection=DBConnection.Connector();
			PreparedStatement preparedStatement=null;
			int resultSet;
			String insertQuery= "UPDATE Local_Fish_Buyers set Name =? , Mobile_No =? " + 
								" where ID = ? ";
			try {
				
				preparedStatement = connection.prepareStatement(insertQuery);
				preparedStatement.setString(1, LBuyer.getName());
				preparedStatement.setString(2, LBuyer.getMobile_No());
				preparedStatement.setInt(3, LBuyer.getID());
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




