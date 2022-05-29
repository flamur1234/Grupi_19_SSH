package home.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import org.json.JSONArray;

import services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.dbConnection;

	
	public class LoginRegjistroAdminController implements Initializable {
		
		 String url = "jdbc:mysql://localhost:3307/smars";
		 String user = "root";
		 String pass = "0303";

	    @FXML
	    private Button kycuid;
	    
	    @FXML
	    private TextField txtUsername1;
	    
	    @FXML
	    private PasswordField txtPassword1;
	   
	    
	
	    
	    Stage dialogStage = new Stage();
	    Scene scene;
	    
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	   
	    public LoginRegjistroAdminController() {
	   }
	        @FXML
	        public void loginAction(ActionEvent event){
            String first_name = txtUsername1.getText().toString();
	        String pass = txtPassword1.getText().toString();
	    
	        
	        
	        try{
	        	URL url = new URL("http://localhost:8000/api/admin/"+first_name+"/"+pass);
	    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    		conn.setUseCaches(false);
	    		conn.setDoInput(true);
	    		conn.setDoOutput(true);
	    		conn.setRequestMethod("GET");
	    		conn.getInputStream();
	    		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));	
//	    		JsonParser parser = new JsonParser();
//	    		JSONArray jsonArray = (JSONArray) parser.parseDoc(in.readLine());
	    		JSONArray obj = new JSONArray(in.readLine());
	    		if(obj.length() == 0){
	                infoBox("Please enter correct Name and Password", null, "Failed");
	            }else{
	                infoBox("Login Successfull",null,"Success" );
	                Node node = (Node)event.getSource();
	                dialogStage = (Stage) node.getScene().getWindow();
	                dialogStage.close();
	                scene = new Scene(FXMLLoader.load(getClass().getResource("/home/fxml/RegjistroAdminKryesore.fxml")));
	                dialogStage.setScene(scene);
	                dialogStage.show();
	            }
	        }
	        catch(Exception e){
	            e.printStackTrace();
	        }
	        
	    }
	    public static void infoBox(String infoMessage, String headerText, String title){
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setContentText(infoMessage);
	        alert.setTitle(title);
	        alert.setHeaderText(headerText);
	        alert.showAndWait();
	    }

	    @Override
	    public void initialize(URL location, ResourceBundle resources) {

	    }
	    private void loadStage(String fxml) {
	        try {
	            Parent root = FXMLLoader.load(getClass().getResource(fxml));
	            Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.setTitle("SMARS");
	            stage.getIcons().add(new Image("/home/icons/icon.png"));
	            stage.initModality(Modality.APPLICATION_MODAL);
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	}
