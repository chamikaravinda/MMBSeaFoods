package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.Models.ProfiteAndLose;

public class ProfiteAndLossService {

	Connection connection=DBConnection.Connector();
	
	public void addProfitAndLossEntry(ProfiteAndLose entry) throws SQLException {
		
	
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO ProfiteAndLose (Date, Reason, To_Pay,Paid)" + 
							"VALUES (?,?,?,?)";
		try {
			
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, entry.getDate());
			preparedStatement.setString(2, entry.getReason());
			preparedStatement.setDouble(3,entry.getTo_Pay());
			preparedStatement.setDouble(4,entry.getPaid());
			resultSet=preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			connection.close();
			e.printStackTrace();
		}finally {
			connection.close();
		}
		
	}
	
}
