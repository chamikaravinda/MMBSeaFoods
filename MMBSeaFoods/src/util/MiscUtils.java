package util;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;


public class MiscUtils {


  public static String getMacAddress() {
	  
	  
	  InetAddress ip;
	  String hdd="";
		try {
				
			ip = InetAddress.getLocalHost();	
			
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
				
			byte[] mac = network.getHardwareAddress();
				
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X", mac[i]));		
			}
			
			hdd=sb.toString();
			hdd = hdd.replaceAll("\\D+", "");
			System.out.println(hdd);
				
			
			
			
			
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			
		} catch (SocketException e){
				
			e.printStackTrace();
				
		}
		
		return hdd;
  
  }
  
  
}