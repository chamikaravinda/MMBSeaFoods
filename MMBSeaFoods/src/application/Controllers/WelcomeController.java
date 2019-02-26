package application.Controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.WinRegistry;

public class WelcomeController {
	
	
	
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss");
	
	static String installed_date_value_in_registry_check = "";
	static String installed_date_for_save_in_registry = "";
	static String installed_time_for_save_in_registry = "";
	static String validation="";
	
	
	static String today_date = "";
	static String today_time = "";
	
	static String last_date = "";
	static String last_time = "";
	
	

	public void Login(ActionEvent event) {
		
		try {
			
			
			
			validation = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER,
					"SOFTWARE\\NovaMMBSeaFood", "Validation");
			
			
			
			

			if(validation==null || !validation.equals("JK56-UIPK-1HJK-OLMN-DJNM") ) {
				
				Alert alert=new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Product Validation Is Failed");
				alert.showAndWait();
				Platform.exit();
				
			}else {
				
				
				
				
				
				
				installed_date_value_in_registry_check = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER,
						"SOFTWARE\\NovaMMBSeaFood", "InstalledDate");
				System.out.println("NovaMMBSeaFood Installed Date= "
						+ installed_date_value_in_registry_check);

				if (installed_date_value_in_registry_check == null) {

					System.out.println("Create First Running saving...");
					
					createPeriodTime();
					createFiveYears();
					
					
					Date date = new Date();
					
					
					today_date = dateFormat.format(date);
					today_time = time_format.format(date);
					
					WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "LastLoginDate", today_date);
					WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "LastLoginTime", today_time);
					 
					String expDate=getExpireDateToLoginMethodTwo();
					
					
					startBillMaker(event,true,expDate);

				}else {
					
					Boolean checkedValue=isCheck();
					
					Date date = new Date();
					
					today_date = dateFormat.format(date);
					today_time = time_format.format(date);
					
					if(checkedValue) {
						
						
										
						WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "LastLoginDate", today_date);
						WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "LastLoginTime", today_time);
					}
					
					String expDate=getExpireDateToLoginMethodTwo();
					System.out.println("expDate : "+expDate);
					
					String keyCheckedValue=keyChecker(today_date, today_time);
					
					
					if (keyCheckedValue.equals("OK")) {
						startBillMaker(event,checkedValue,expDate);
					}else if(keyCheckedValue.equals("ONE")){
						//startActivationPage(primaryStageObj,"ONE");
					}else if(keyCheckedValue.equals("TWO")){
						//startActivationPage(primaryStageObj,"TWO");
					}else if(keyCheckedValue.equals("THREE")){
						//startActivationPage(primaryStageObj,"THREE");
					}else if(keyCheckedValue.equals("FOUR")){
						//startActivationPage(primaryStageObj,"FOUR");
					}else if(keyCheckedValue.equals("FIVE")){
						//startActivationPage(primaryStageObj,"FIVE");
					}
					

					
				}
				
				
				
				
				
				
				
				
				
			}
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String keyChecker(String date_today,String time_today) {
		
		try {
			
			
			
			String year=getExpireDateToKeyChecking(date_today,time_today);
			System.out.println(year);
			
			if(year=="trial") {
				return "OK";
			}else if(year=="Year_1") {
				
				String key_Year_1_BUY = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
						"Year_1_BUY");
				System.out.println("Windows Distribution For NovaMMBSeaFood Year 1 Checking... = " + key_Year_1_BUY);
				
				if(key_Year_1_BUY.equals("89lko")) {
					return "OK";
				}else {
					System.out.println("null");
					return "ONE";
					
				}
				
			}else if(year=="Year_2") {
				
				String key_Year_2_BUY = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
						"Year_2_BUY");
				System.out.println("Windows Distribution For NovaMMBSeaFood Year 1 Checking... = " + key_Year_2_BUY);
				
				if(key_Year_2_BUY.equals("21hjk")) {
					return "OK";
				}else {
					System.out.println("null");
					return "TWO";
					
				}
				
			}else if(year=="Year_3") {
				
				String key_Year_3_BUY = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
						"Year_3_BUY");
				System.out.println("Windows Distribution For NovaMMBSeaFood Year 1 Checking... = " + key_Year_3_BUY);
				
				if(key_Year_3_BUY.equals("mnf56")) {
					return "OK";
				}else {
					System.out.println("null");
					return "THREE";
					
				}
				
			}else if(year=="Year_4") {
				
				String key_Year_4_BUY = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
						"Year_4_BUY");
				System.out.println("Windows Distribution For NovaMMBSeaFood Year 1 Checking... = " + key_Year_4_BUY);
				
				if(key_Year_4_BUY.equals("op893")) {
					return "OK";
				}else {
					System.out.println("null");
					return "FOUR";
					
				}
				
			}else if(year=="Year_5") {
				
				String key_Year_5_BUY = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
						"Year_5_BUY");
				System.out.println("Windows Distribution For NovaMMBSeaFood Year 1 Checking... = " + key_Year_5_BUY);
				
				if(key_Year_5_BUY.equals("fvbg6")) {
					return "OK";
				}else {
					System.out.println("null");
					return "FIVE";
					
				}
				
			}
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return null;
		
	}
	
	public String getExpireDateToKeyChecking(String date_today,String time_today) {

		try {

			

			
			
			
			System.out.println("in method getExpireDate date:" + date_today);
			System.out.println("in method getExpireDate time:" + time_today);

			String i_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"InstalledDate");
			System.out.println("Windows Distribution For NovaMMBSeaFood InstalledDate= " + i_date);
			
			String i_time = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"InstalledTime");
			System.out.println("Windows Distribution For NovaMMBSeaFood InstalledTime= " + i_time);
			

			String trial_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"FreeTiralExpire");
			System.out.println("Windows Distribution For NovaMMBSeaFood FreeTiralExpire= " + trial_date);

			String fir_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"1_YearExpire");
			System.out.println("Windows Distribution For NovaMMBSeaFood FirstYearExpire= " + fir_date);

			String sec_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"2_YearExpire");
			System.out.println("Windows Distribution For NovaMMBSeaFood SecondYearExpire= " + sec_date);

			String third_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"3_YearExpire");
			System.out.println("Windows Distribution For NovaMMBSeaFood ThirdYearExpire= " + third_date);

			String fourth_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"4_YearExpire");
			System.out.println("Windows Distribution For NovaMMBSeaFood FourthYearExpire= " + fourth_date);

			String fifth_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"5_YearExpire");
			System.out.println("Windows Distribution For NovaMMBSeaFood FifthYearExpire= " + fifth_date);

			

			if (isDateOfInterestValid(trial_date, date_today,i_time,time_today)) {
				return "trial";
			} else if (isDateOfInterestValid(fir_date, date_today,i_time,time_today)) {				
				return "Year_1";
			} else if (isDateOfInterestValid(sec_date, date_today,i_time,time_today)) {
				return "Year_2";
			} else if (isDateOfInterestValid(third_date, date_today,i_time,time_today)) {
				return "Year_3";
			} else if (isDateOfInterestValid(fourth_date, date_today,i_time,time_today)) {
				return "Year_4";
			} else if (isDateOfInterestValid(fifth_date, date_today,i_time,time_today)) {
				return "Year_5";
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
	
	
	
	public String getExpireDateToLoginMethodTwo() {

		try {
			

			String year_1 = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"Year_1_BUY");			
			
			String year_2 = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"Year_2_BUY");
			
			String year_3 = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"Year_3_BUY");			

			String year_4 = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"Year_4_BUY");

			String year_5 = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"Year_5_BUY");
			

			String trial_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"FreeTiralExpire");		
			
			String year_1_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"1_YearExpire");			
			
			String year_2_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"2_YearExpire");
			
			String year_3_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"3_YearExpire");			

			String year_4_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"4_YearExpire");

			String year_5_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"5_YearExpire");
			
			if(year_5.equals("fvbg6")) {
				return year_5_date;
			}else if(year_4.equals("op893")) {
				return year_4_date;
			}else if(year_3.equals("mnf56")) {
				return year_3_date;
			}else if(year_2.equals("21hjk")) {
				return year_2_date;
			}else if(year_1.equals("89lko")) {
				return year_1_date;
			}else{
				return trial_date;
			}

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
	
	
	
	
	

	public String getExpireDateToLogin(String date_today,String time_today) {

		try {

			

			
			
			
			System.out.println("in method getExpireDate date:" + date_today);
			System.out.println("in method getExpireDate time:" + time_today);

			String i_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"InstalledDate");
			System.out.println("Windows Distribution For NovaMMBSeaFood InstalledDate= " + i_date);
			
			String i_time = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"InstalledTime");
			System.out.println("Windows Distribution For NovaMMBSeaFood InstalledTime= " + i_time);
			

			String trial_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"FreeTiralExpire");
			System.out.println("Windows Distribution For NovaMMBSeaFood FreeTiralExpire= " + trial_date);

			String fir_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"1_YearExpire");
			System.out.println("Windows Distribution For NovaMMBSeaFood FirstYearExpire= " + fir_date);

			String sec_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"2_YearExpire");
			System.out.println("Windows Distribution For NovaMMBSeaFood SecondYearExpire= " + sec_date);

			String third_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"3_YearExpire");
			System.out.println("Windows Distribution For NovaMMBSeaFood ThirdYearExpire= " + third_date);

			String fourth_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"4_YearExpire");
			System.out.println("Windows Distribution For NovaMMBSeaFood FourthYearExpire= " + fourth_date);

			String fifth_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\NovaMMBSeaFood",
					"5_YearExpire");
			System.out.println("Windows Distribution For NovaMMBSeaFood FifthYearExpire= " + fifth_date);

			

			if (isDateOfInterestValid(trial_date, date_today,i_time,time_today)) {
				return trial_date;
			} else if (isDateOfInterestValid(fir_date, date_today,i_time,time_today)) {				
				return fir_date;
			} else if (isDateOfInterestValid(sec_date, date_today,i_time,time_today)) {
				return sec_date;
			} else if (isDateOfInterestValid(third_date, date_today,i_time,time_today)) {
				return third_date;
			} else if (isDateOfInterestValid(fourth_date, date_today,i_time,time_today)) {
				return fourth_date;
			} else if (isDateOfInterestValid(fifth_date, date_today,i_time,time_today)) {
				return fifth_date;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
	
	
	
	
	
	public boolean isCheck() {

		Boolean checker = null;
		
		
		try {
			
			
			Date date = new Date();
			
			today_date = dateFormat.format(date);
			today_time = time_format.format(date);
			
			
			last_date = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER,"SOFTWARE\\NovaMMBSeaFood", "LastLoginDate");						
			last_time = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER,"SOFTWARE\\NovaMMBSeaFood", "LastLoginTime");

			

			checker = isDateOfInterestValid(today_date, last_date,today_time,last_time);

			if (checker) {

				System.out.println("checker printing...");
				
			}
	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	
		
		return checker;

	}
	
	
	public static boolean isDateOfInterestValid(String currentDate, String lastDate,String currentTime, String lastTime) {

		
		
		Date cd = null; 
		Date ld = null; 

		try {
			cd = dateFormat.parse(currentDate);
			ld = dateFormat.parse(lastDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long diff = cd.getTime() - ld.getTime();
		int diffDays = (int) (diff / (24 * 1000 * 60 * 60));

		if (diffDays < 0) {

			System.out.println("last logged date : " + lastDate);
			System.out.println("warning... user cannot login...");
			return false;
		} else if (diffDays == 0) {

			System.out.println("last logged date is today : " + lastDate);
			System.out.println("time checking...");

			long difference = 0;

			try {

				
				
				Date time2 = time_format.parse(currentTime);

				Date time1 = time_format.parse(lastTime);

				difference = time2.getTime() - time1.getTime();

			} catch (ParseException e) {

				e.printStackTrace();
			}

			if (difference >= 0) {

				System.out.println("time ok");
				System.out.println("welcome... user can login...");
				return true;
			} else {

				System.out.println("time not ok");
				System.out.println("warning... user cannot login...");
				return false;
			}

		} else {

			System.out.println("last logged date : " + lastDate);
			System.out.println("welcome... user can login...");
			return true;

		}
	}
	
	
	
	/*public void startActivationPage(Stage primaryStage,String year) {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/ViewActivation.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());

			ActivationController controller = loader.getController();
			controller.getYear(year);
			//controller.getExpDate(getExpireDate());

			primaryStage.initStyle(StageStyle.TRANSPARENT); 
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.centerOnScreen();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/
	

	public void startBillMaker(ActionEvent event,Boolean chkVal,String dateExp) {
		
		
		

		try {

			
			
			
			
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage primaryStage =new Stage();
			FXMLLoader loader = new FXMLLoader();
			AnchorPane root = loader.load(getClass().getResource("/application/Views/Login.fxml").openStream());
			
			Scene scene = new Scene(root);
			
			LoginController controller = loader.getController();
			controller.getChecker(chkVal);
			controller.getExpDate(dateExp);

			
			
			
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		/*FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/ViewLogin.fxml"));
		Parent root = loader.load();

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());

		LoginController controller = loader.getController();
		System.out.println("Passing Values");
		controller.getChecker(chkVal);
		controller.getExpDate(dateExp);

		System.out.println("Starting");
		
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.centerOnScreen();*/
		
		
		
		
		
	}

	
	
	
	
	public void createFiveYears() {
		try {

			
					
			
			/*WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_1", keyFive.getYearOneKey());
			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_2", keyFive.getYearTwoKey());
			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_3", keyFive.getYearThreeKey());
			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_4", keyFive.getYearFourKey());
			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_5", keyFive.getYearFiveKey());*/
			
			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_1_BUY", "ef50t");
			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_2_BUY", "hg36r");
			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_3_BUY", "68yrf");
			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_4_BUY", "31fgh");
			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "Year_5_BUY", "6w4rt");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void createPeriodTime() {

		try {

			Date date = new Date();

			installed_date_for_save_in_registry = dateFormat.format(date);
			installed_time_for_save_in_registry = time_format.format(date);

			WinRegistry.createKey(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood");

			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "InstalledDate",
					installed_date_for_save_in_registry);
			
			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "InstalledTime",
					installed_time_for_save_in_registry);

			Date installDate = dateFormat.parse(installed_date_for_save_in_registry);
			Calendar calInstallDate = Calendar.getInstance();
			calInstallDate.setTime(installDate);

			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "FreeTiralExpire",
					addDaysToDate(1, calInstallDate));

			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "1_YearExpire",
					addDaysToDate(1 + 365, calInstallDate));

			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "2_YearExpire",
					addDaysToDate(1 + 365 + 365, calInstallDate));

			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "3_YearExpire",
					addDaysToDate(1 + 365 + 365 + 365, calInstallDate));

			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "4_YearExpire",
					addDaysToDate(1 + 365 + 365 + 365 + 365, calInstallDate));

			WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\NovaMMBSeaFood", "5_YearExpire",
					addDaysToDate(1 + 365 + 365 + 365 + 365 + 365, calInstallDate));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String addDaysToDate(int noOfDays, Calendar c) {

		c.setTime(new Date());
		c.add(Calendar.DATE, noOfDays);
		String output = dateFormat.format(c.getTime());
		System.out.println("addDaysToDate running... : "+output);
		return output;
	}
	
	

}
