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

public class LocalBuyerAccountService {

	Connection connection;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	Local_Fish_typesServices service =new Local_Fish_typesServices();
	
	
	public boolean addEntries(ObservableList<LFish_stock> list,int BuyerID) throws SQLException {
		
		connection=DBConnection.Connector();
		
		PreparedStatement preparedStatement3=null;
		String insertQuery= "INSERT INTO Local_Fish_Buyers_Account (Date,Reason,To_Pay,Paid,Buyer_ID) VALUES (?,?,?,?,?)";
		try {
		for(LFish_stock item :list) {
		
			preparedStatement3 =connection.prepareStatement(insertQuery);
		
			preparedStatement3.setString(1,format1.format( new Date()));
			
			Local_Fish_types fish= service.getLocalfishTypes(item.getFish_Type());
			preparedStatement3.setString(2, "Selling "+item.getTotal_Weight()+" of "+fish.getName() );
			preparedStatement3.setDouble(3, item.getTotal_Weight()*fish.getPrice());
			preparedStatement3.setDouble(4,0);
			preparedStatement3.setInt(5, BuyerID);
			preparedStatement3.executeUpdate();
		}
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}
	
}

	public boolean addEntriesUncleard(ObservableList<LFish_stock> list,int buyerID) throws SQLException {
		
		connection=DBConnection.Connector();
		
		PreparedStatement preparedStatement3=null;
		String insertQuery= "INSERT INTO Local_Fish_Uncleared (Date,Reason,To_Pay,Paid,Buyer_ID) VALUES (?,?,?,?,?)";
		try {
		for(LFish_stock item :list) {
		
			preparedStatement3 =connection.prepareStatement(insertQuery);
		
			preparedStatement3.setString(1,format1.format( new Date()));
			
			Local_Fish_types fish= service.getLocalfishTypes(item.getFish_Type());
			preparedStatement3.setString(2, "Purchasing "+item.getTotal_Weight()+" of "+fish.getName() );
			preparedStatement3.setDouble(3, item.getTotal_Weight()*fish.getPrice());
			preparedStatement3.setDouble(4,0);
			preparedStatement3.setInt(5, buyerID);
		    preparedStatement3.executeUpdate();
		}
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}
	
	}
	
	public boolean addLocalSale(ObservableList<LFish_stock> list,int buyerID) throws SQLException {
		
		connection=DBConnection.Connector();
		
		PreparedStatement preparedStatement3=null;
		String insertQuery= "INSERT INTO Local_Sales (Date,BuyerID,Fish_type,Price,Total_Weight) VALUES (?,?,?,?,?)";
		try {
		for(LFish_stock item :list) {
		
			preparedStatement3 =connection.prepareStatement(insertQuery);
		
			preparedStatement3.setString(1,format1.format( new Date()));
			
			Local_Fish_types fish= service.getLocalfishTypes(item.getFish_Type());
			preparedStatement3.setInt(2, buyerID );
			preparedStatement3.setDouble(3,item.getFish_Type());
			preparedStatement3.setDouble(4,item.getPrice());
			preparedStatement3.setDouble(5,item.getPrice());
		    preparedStatement3.executeUpdate();
		}
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}
	
	}
}
