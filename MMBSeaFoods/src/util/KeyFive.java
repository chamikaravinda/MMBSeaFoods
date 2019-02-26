package util;

public class KeyFive {
	
	private String hdd;
	
	
	public KeyFive() {
		
		
		String option1=MiscUtils.getMacAddress();
		String option2=DiskUtils.getSerialNumber("C");
		
		if(( option1 != null || option1 != "" ) && ( option2 != null || option2 !="" )) {
			hdd=option1;
		}else if(option1 == null || option1 == "") {
			hdd=option2;
		}else {
			hdd=option1;
		}
		
		hdd = hdd.replaceAll("\\D+", "");
		
		
	}

	
	public String getYearOneKey() {
		
		long id_long = Long.parseLong(hdd);
		
		id_long=(id_long+98765412)*13;
					
		String id_string=Long.toString(id_long);
		
		LicenseKeyFormatting licenseKeyFormatting =  new LicenseKeyFormatting();		
		
		String key=licenseKeyFormatting.licenseKeyFormattingMy(id_string,4);
		
		System.out.println("Year one Key : "+key);
				
		return key;
		
	}
	
	public String getYearTwoKey() {	
		
		long id_long = Long.parseLong(hdd);
		
		id_long=(id_long+96325812)*12;
					
		String id_string=Long.toString(id_long);
		
		LicenseKeyFormatting licenseKeyFormatting =  new LicenseKeyFormatting();		
		
		String key=licenseKeyFormatting.licenseKeyFormattingMy(id_string,4);
		
		System.out.println("Year Two Key : "+key);
				
		return key;	
		
	}
	
	
	public String getYearThreeKey() {
		
		long id_long = Long.parseLong(hdd);
		
		id_long=(id_long+15948751)*15;	
		
		String id_string=Long.toString(id_long);
		
		LicenseKeyFormatting licenseKeyFormatting =  new LicenseKeyFormatting();	
		
		String key=licenseKeyFormatting.licenseKeyFormattingMy(id_string,4);
		
		System.out.println("Year Three Key : "+key);
			
		return key;
		
	}
	
	public String getYearFourKey() {
			
		long id_long = Long.parseLong(hdd);
		
		id_long=(id_long+32145690)*12;
				
		String id_string=Long.toString(id_long);
		
		LicenseKeyFormatting licenseKeyFormatting =  new LicenseKeyFormatting();
				
		String key=licenseKeyFormatting.licenseKeyFormattingMy(id_string,4);
		
		System.out.println("Year Four Key : "+key);
			
		return key;
				
	}
	
	
	public String getYearFiveKey() {
				
		long id_long = Long.parseLong(hdd);
		
		id_long=(id_long+14569855)*19;
				
		String id_string=Long.toString(id_long);
		
		LicenseKeyFormatting licenseKeyFormatting =  new LicenseKeyFormatting();
				
		String key=licenseKeyFormatting.licenseKeyFormattingMy(id_string,4);
		
		System.out.println("Year Four Key : "+key);
			
		return key;			
		
	}
	
	
	public String getProductID() {
		
	
		long hdd_long = Long.parseLong(hdd);
		
		
		hdd_long=(hdd_long+696586245)*17;
				
		
		String id=Long.toString(hdd_long);
		
		/*StringBuilder sb=new StringBuilder();
		
		sb.append("nova"+hdd_string+"bm"+cpu_string);
		
		String id=sb.toString();*/
		
		System.out.println("Product ID : "+id);
			
		return id;			
		
	}



}
