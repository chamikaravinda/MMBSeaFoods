/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controllers;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
    	
    	try {
    		
    		 /*	Parent root = FXMLLoader.load(getClass().getResource("/application/Views/Welcome.fxml"));
    	        
    	        Scene scene = new Scene(root);
    	        
    	        
    	        //Image image=new Image("images/nova.png");
    			
    			//stage.getIcons().add(image);
    			stage.setTitle("Nova Solutions");
    	        
    	        stage.setScene(scene);
    	        stage.show();*/
    		
    		
    		
    		
    		FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/Views/Welcome.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root);
			

			System.out.println("Start");
			

			
			stage.setScene(scene);
			stage.show();
			stage.centerOnScreen();
			
		} catch (Throwable t) {
		
			JOptionPane.showMessageDialog(null, t.getClass().getSimpleName() + ": " + t.getMessage());
			throw t; 
		
		}
       
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
