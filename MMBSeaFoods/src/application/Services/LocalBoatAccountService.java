package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import application.Models.LFish_stock;
import application.Models.Local_Fish_types;
import javafx.collections.ObservableList;

public class LocalBoatAccountService {

	Connection connection;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	Local_Fish_typesServices service =new Local_Fish_typesServices();
	
	
	public boolean addEntries(ObservableList<LFish_stock> list,int BoatID) throws SQLException {
		
		connection=DBConnection.Connector();
		
		PreparedStatement preparedStatement3=null;
		ResultSet resultSet3;
		String insertQuery= "INSERT INTO Local_Boat_Account (Date,Reason,To_Pay,Paid,Boat_ID) VALUES (?,?,?,?,?)";
		try {
		for(LFish_stock item :list) {
		
			preparedStatement3 =connection.prepareStatement(insertQuery);
		
			preparedStatement3.setString(1,format1.format( new Date()));
			
			Local_Fish_types fish= service.getLocalfishTypes(item.getFish_Type());
			preparedStatement3.setString(2, "Purchasing "+item.getTotal_Weight()+" of "+fish.getName() );
			preparedStatement3.setDouble(3, item.getTotal_Weight()*fish.getPrice());
			preparedStatement3.setDouble(4,0);
			preparedStatement3.setInt(5, BoatID);
			resultSet3 = preparedStatement3.executeQuery();
		}
		return true;
		} catch (Exception e) {
			return false;
		} finally {
			connection.close();
		}
	
}

	public boolean addEntriesUncleard(ObservableList<LFish_stock> list,int BoatID) throws SQLException {
		
		connection=DBConnection.Connector();
		
		PreparedStatement preparedStatement3=null;
		ResultSet resultSet3;
		String insertQuery= "INSERT INTO Local_Boat_Account_UnCleared (Date,Reason,To_Pay,Paid,Boat_ID) VALUES (?,?,?,?,?)";
		try {
		for(LFish_stock item :list) {
		
			preparedStatement3 =connection.prepareStatement(insertQuery);
		
			preparedStatement3.setString(1,format1.format( new Date()));
			
			Local_Fish_types fish= service.getLocalfishTypes(item.getFish_Type());
			preparedStatement3.setString(2, "Purchasing "+item.getTotal_Weight()+" of "+fish.getName() );
			preparedStatement3.setDouble(3, item.getTotal_Weight()*fish.getPrice());
			preparedStatement3.setDouble(4,0);
			preparedStatement3.setInt(5, BoatID);
			resultSet3 = preparedStatement3.executeQuery();
		}
		return true;
		} catch (Exception e) {
			return false;
		} finally {
			connection.close();
		}
	
}
}
